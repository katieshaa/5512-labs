package org.suai.tasks;


import java.util.ArrayList;


public class MyTask {

	public ArrayList<Integer> answers;

	public int start; //область диапазона чисел, коотррые будут проверяться на простоту
	public int end;
	
	
	public MyTask(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	
	public int getStart() {
		return this.start;
	}
	
	
	public int getEnd() {
		return this.end;
	}


	public void setAnswers(ArrayList<Integer> answers) {
		this.answers = answers;
	}
	
	
	public ArrayList<Integer> getAnswers() {
		return this.answers;
	}

}