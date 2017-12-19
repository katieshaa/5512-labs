/*
	����� MyTask
		int start; - ������ ����� � ���������
		int end; - ��������� ����� � ���������
		ArrayList<int> answers; - ������ � �������� �������
*/

import java.util.ArrayList;

public class MyTask {
	public int start;
	public int end;
	public ArrayList<Integer> answers;
	
	
	public MyTask(int aStart, int aEnd) {
		start = (aStart <= aEnd) ? aStart : aEnd;
		end = (aStart >= aEnd ) ? aStart : aEnd;
	}
	
	public int getStart() { 
		return start;
	}
	
	public int getEnd() { 
		return end;
	}
		
	public ArrayList<Integer> getAnswers() {
		return answers;
	}
	
	
	public void setAnswers(ArrayList<Integer> Answers) {
		answers = Answers;
	}
}