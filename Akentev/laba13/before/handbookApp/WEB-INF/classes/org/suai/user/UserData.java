package org.suai.user;


import java.util.ArrayList;


public class UserData {

	private ArrayList<String> phones;


	public UserData(ArrayList<String> phones) {
		this.phones = phones;
	}


	public synchronized void merge(ArrayList<String> newPhones) {
		String newPhone = null;
		String phone = null;
		boolean repeat = false;

		for(int i = 0; i < newPhones.size(); i++) {
			newPhone = newPhones.get(i);
			repeat = false;

			for(int j = 0; j < this.phones.size(); j++) {
				phone = this.phones.get(j);

				if(newPhone.equals(phone)) {
					repeat = true;
					break;
				}
			}

			if(!repeat) {
				this.phones.add(newPhone);
			}
		}
	}


	public String toHtml() {
		return toString("<br/>");
	}


	@Override
	public String toString() {
		return "phones=" + toString(",");
	}


	private synchronized String toString(String delimiter) {
		StringBuilder builder = new StringBuilder();

		for(int i = 0; i < this.phones.size() - 1; i++) {
			builder.append(this.phones.get(i));
			builder.append(delimiter);
		}

		builder.append(this.phones.get(this.phones.size() - 1));

		return builder.toString();
	}

}