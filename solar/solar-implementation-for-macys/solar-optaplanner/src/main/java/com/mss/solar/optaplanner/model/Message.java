package com.mss.solar.optaplanner.model;

import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
public class Message {
	//@Id
	protected String text;

	public Message() {
	}

	public Message(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}