package net.coursework.app;

import java.util.Objects;

import java.util.ArrayList;

public class Chat {
	private ArrayList<Integer> participants;
	private ArrayList<ChatObject> chatObjects;
	
	public Chat(ArrayList<Integer> participants, ArrayList<ChatObject> chatObjects) {
		this.participants = new ArrayList<Integer>(participants);
		this.chatObjects = new ArrayList<ChatObject>(chatObjects);
	}
	
	public Chat(ArrayList<Integer> participants) {
		this.participants = new ArrayList<Integer>(participants);
		this.chatObjects = new ArrayList<ChatObject>();
	}
	
	public void addMessage(int id, String message, String time) {
		chatObjects.add(new ChatObject(id, message, time));
	}
	
	public ArrayList<Integer> getParticipants() {
		return this.participants;
	}
	
	public ArrayList<ChatObject> getChatObjects() {
		return this.chatObjects;
	}
}
