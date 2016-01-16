package org.sd.battlesheep.model;

@SuppressWarnings("serial")
public class UsernameAlreadyTakenException extends Exception {
	
	public UsernameAlreadyTakenException(String message) {
		super(message);
	}
}
