package com.simpsonfans.simpsons_list.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SimpsonsCharacter")
public class SimpsonsCharacter {

	@Id
	@Column(name = "GUID")
	private UUID GUID;
	
	@Column(name = "FirstName")
	private String firstName;
	
	@Column(name = "LastName")
	private String lastName;
	
	@Column(name = "Age")
	private Integer age;
	
	@Column(name = "Occupation")
	private String occupation;
	
	@Column(name = "VoicedBy")
	private String voicedBy;
	
	//Default constructor required by Hibernate
  public SimpsonsCharacter() {
  }
	
	public SimpsonsCharacter(
			UUID GUID,
			String firstName,
			String lastName,
			Integer age,
			String occupation,
			String voicedBy
	) {
		this.GUID = GUID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.occupation = occupation;
		this.voicedBy = voicedBy;
	}
	
	public void setGUID(UUID GUID) {
		this.GUID = GUID;
	}
	
	public UUID getGUID() {
		return GUID;
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
	
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public Integer getAge() {
		return age;
	}
	
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	
	public String getOccupation() {
		return occupation;
	}
	
	public void setVoicedBy(String voicedBy) {
		this.voicedBy = voicedBy;
	}
	
	public String getVoicedBy() {
		return voicedBy;
	}
	
}
