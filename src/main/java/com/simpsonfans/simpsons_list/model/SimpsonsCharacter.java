package com.simpsonfans.simpsons_list.model;

public class SimpsonsCharacter {
	private String firstName;
	private String lastName;
	private Integer age;
	
	public SimpsonsCharacter(String first, String last, Integer theirAge) {
		this.firstName = first;
		this.lastName = last;
		this.age = theirAge;
	}
	
	public void setFirstName(String name) {
		this.firstName = name;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setLastName(String name) {
		this.lastName = name;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setAge(String name) {
		this.firstName = name;
	}
	
	public Integer getAge() {
		return age;
	}
}
