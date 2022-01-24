package com.gp.java8.app.model;

public class Emp {
	private String name;
	private int id;
	private int age;

	public Emp() {
	}

	public Emp(String name, int id, int age) {
		this.name = name;
		this.id = id;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public static <T> T returnType(T arg) {
		return arg;
	}

	@Override
	public String toString() {
		return "Emp [name=" + name + ", id=" + id + ", age=" + age + "]";
	}
	
	

}
