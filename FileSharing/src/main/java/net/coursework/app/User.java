package net.coursework.app;

import java.util.Objects;

public class User {
	private int id;
	private String name;
	private String password;
	
	public User(int id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName() {
		this.name = name;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	@Override
	public boolean equals(Object o) {
		return (id == ((User)o).getId()) && (((User)o).getName().equals(name)) && (((User)o).getPassword().equals(password));
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, name, password);
	}
	
	@Override
	public String toString() {
		return "User{id=" + Integer.toString(id) + ", name=" + name + ", password=" + password + "}";
	}
}
