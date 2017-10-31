package org.suai.io;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Iterator;

import org.suai.user.UserData;


public class UserIO {

	private String path;


	public UserIO(String path) {
		this.path = path;
	}


	public synchronized ConcurrentHashMap<String, UserData> read() {
		ConcurrentHashMap<String, UserData> users = new ConcurrentHashMap<>();
		String line = null;

		try(
			BufferedReader in = new BufferedReader(new FileReader(this.path))
		) {
			while((line = in.readLine()) != null) {
				if(validateLine(line)) {
					addUser(users, line);
				}
			}
		}
		catch(IOException exception) {
			System.out.println(exception);
		}

		return users;
	}


	private boolean validateLine(String line) {
		if(line.matches("^user=\\w+&phones=[0-9]+(,[0-9]+)*&avatar=\\w+(/\\w+)*.jpg$")) {
			return true;
		}

		return false;
	}


	private void addUser(ConcurrentHashMap<String, UserData> users, String line) {
		String[] components = line.split("user=|&phones=|,|&avatar=");

		ArrayList<String> phones = new ArrayList<>();
		for(int i = 2; i < components.length - 1; i++) {
			phones.add(components[i]);
		}

		UserData data = users.get(components[1]);
		if(data == null) {
			data = new UserData(phones, components[components.length - 1]);
			users.put(components[1], data);
		}
		else {
			data.merge(phones);
		}
	}


	public synchronized void write(ConcurrentHashMap<String, UserData> users) {
		Iterator<Map.Entry<String, UserData>> iterator = users.entrySet().iterator();
		Map.Entry<String, UserData> entry = null;

		try(
			BufferedWriter out = new BufferedWriter(new FileWriter(this.path))
		) {
			while(iterator.hasNext()) {
				entry = iterator.next();

				StringBuilder builder = new StringBuilder();
				builder.append("user=");
				builder.append(entry.getKey());
				builder.append('&');
				builder.append(entry.getValue());

				out.write(builder.toString());
				out.newLine();
			}

			out.flush();
		}
		catch(IOException exception) {
			System.out.println(exception);
		}
	}

}