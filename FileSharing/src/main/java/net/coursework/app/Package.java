package net.coursework.app;

import java.util.Objects;

public class Package implements Comparable<Package> {
	private int id;
	private String path;
	
	public Package(int id, String path) {
		this.id = id;
		this.path = path;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setPath() {
		this.path = path;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public int compareTo(Package p){
		return path.compareTo(p.getPath());
	}
	
	@Override
	public boolean equals(Object o) {
		return (id == ((Package)o).getId()) && ((Package)o).getPath().equals(path);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, path);
	}
	
	@Override
	public String toString() {
		return "Package{id=" + Integer.toString(id) + ", path=" + path + "}";
	}
}
