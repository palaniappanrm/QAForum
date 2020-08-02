package com.eceplatform.QAForum.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.eceplatform.QAForum.enums.UserType;


@Entity
@Table(name="USER")
public class User {

	@Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "EMAIL", nullable = false)
    private String email;
    @Column(name = "IMAGE_URL")
    private String imageUrl;
    @Column(name = "USER_TYPE", nullable = false)
    private UserType userType;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
    
}
