package com.crowdcollective.simple.ejb;

import java.util.Date;

import javax.ejb.Stateless;

@Stateless
public class SimpleEJB {

	public String helloThere(String name) {
		StringBuilder sb = new StringBuilder();
		sb.append(new Date().toGMTString())
		.append(": ")
		.append("Hello ")
		.append(name)
		.append("!");
		return sb.toString();
	}
	
	public String helloThere() {
		return helloThere("you");
	}
}
