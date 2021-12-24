package net.coursework.app;

import java.util.Objects;

public class ChatObject {
	private int id;
	private String message;
	private String time;
	
	public ChatObject(int id, String message, String time) {
		this.id = id;
		this.message = message;
		this.time = time;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String getTime() {
		return this.time;
	}
	
	@Override
	public boolean equals(Object o) {
		return (id == ((ChatObject)o).getId()) && (((ChatObject)o).getMessage().equals(message)) && (((ChatObject)o).getTime().equals(time));
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, message, time);
	}
	
	@Override
	public String toString() {
		return "User{id=" + Integer.toString(id) + ", message=" + message + ", time=" + time + "}";
	}
}
