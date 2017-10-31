package org.suai.user;


import java.util.ArrayList;


public class UserData {

	private ArrayList<String> phones;
	private String avatarPath;


	public UserData(ArrayList<String> phones, String avatarPath) {
		this.phones = phones;
		this.avatarPath = avatarPath;
	}


	public synchronized void refreshAvatar(String avatarPath) {
		this.avatarPath = avatarPath;
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


	public String phonesToHtml() {
		return phonesToString("<br/>");
	}


	@Override
	public synchronized String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("phones=");
		builder.append(phonesToString(","));

		builder.append("&avatar=");
		builder.append(this.avatarPath);

		return builder.toString();
	}


	private synchronized String phonesToString(String delimiter) {
		StringBuilder builder = new StringBuilder();
		int size = this.phones.size();

		for(int i = 0; i < size - 1; i++) {
			builder.append(this.phones.get(i));
			builder.append(delimiter);
		}

		builder.append(this.phones.get(size - 1));

		return builder.toString();
	}


	public synchronized String avatarToHtml(int size) {
		StringBuilder builder = new StringBuilder();

		builder.append("<img ");
		builder.append("width='" + size + "' ");
		builder.append("height='" + size + "' ");
		builder.append("src='" + this.avatarPath + "'/>");

		return builder.toString();
	}
	
}