package org.suai.server;


import java.io.IOException;

import java.lang.Thread;
import java.lang.Runnable;
import java.lang.Math;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.suai.console.ConsoleHandler;

import org.suai.message.SystemMessage;


public class CasinoGame extends Thread {

	private Server server;

	private HashMap<Session, Integer> players = new HashMap<>();

	private int seconds = 10;
	private boolean isOpen = true;
	public static final int MAX_PLAYERS = 100;
	public static final int DEFAULT_BET = 0;
	public static final int MAX_BET = 100;


	public CasinoGame(Server server) {
		this.server = server;
	}


	private synchronized void sendMessagePlayers(SystemMessage message) {
		Iterator it = this.players.keySet().iterator();

    	while(it.hasNext()) {
        	Session session = (Session)it.next();

        	try {
        		session.send(message);
        	}
        	catch(IOException exception) {}
    	}
	}


	private void timer(int seconds, Runnable action) throws InterruptedException {
		while(seconds != 0) {
			action.run();

			sleep(1000);
			seconds--;
		}
	}


	public synchronized SystemMessage setBet(Session session, Integer bet) {
		boolean exists = this.players.containsKey(session);
		String text = null;

		if((this.isOpen && (this.players.size() < this.MAX_PLAYERS)) || exists) {
			if(bet < this.MAX_BET) {
				this.players.put(session, bet);
				text = "You made bets. You bet on the " + bet + ".";
			}
			else {
				text = "Incorrect bet. Min bet is 0; max bet is 99.";
			}
		}
		else {
			if(this.isOpen == false) {
				text = "Game already begin.";
			}
			else {
				text = "Maximum number of players " + this.MAX_PLAYERS + ".";
			}
		}

		return new SystemMessage("@casino", text);
	}


	private void winnerHandler() {
		Random random = new Random();
		int winBet = random.nextInt(this.MAX_BET);
		ArrayList<String> winners = new ArrayList<>();

		int nearestBet = 0;
		int difference = this.MAX_BET;

		Iterator it = this.players.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			Session player = (Session)pair.getKey();
			int bet = (Integer)pair.getValue();

			if(Math.abs(bet - winBet) == difference) {
				winners.add(player.getNameClient());
			}
			else if(Math.abs(bet - winBet) < difference) {
				difference = Math.abs(bet - winBet);
				nearestBet = bet;

				winners = new ArrayList<>();
				winners.add(player.getNameClient());
			}
		}

		String text = "Winners are:\n";
		for(String name : winners) {
			text += name + "\n";
		}

		text += "Winning bet is " + winBet + ". Nearest bet is " + nearestBet + ".";

		SystemMessage message = new SystemMessage("@casino", text);
		sendMessagePlayers(message);
	}


	@Override
	public void run() {
		try {
			timer(this.seconds, () -> {
				String text = "Game will start in " + this.seconds + " seconds.";
				SystemMessage message = new SystemMessage("@casino", text);

				sendMessagePlayers(message);

				this.seconds--;
			});

			this.isOpen = false;

			sendMessagePlayers(new SystemMessage("@casino", "Game begins."));
			sendMessagePlayers(new SystemMessage("@casino", "You have 10 seconds to make your bets (@bet number)."));

			timer(10, () -> {});

			sendMessagePlayers(new SystemMessage("@casino", "Bets are made. Choosing winner."));

			winnerHandler();
			this.server.removeCasino();
		}
		catch(InterruptedException exception) {
			sendMessagePlayers(new SystemMessage("@casino", "Casino is broken."));
			ConsoleHandler.printSystemMessage("Casino is broken", exception);
		}
	}

}
