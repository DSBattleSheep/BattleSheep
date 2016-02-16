package org.sd.battlesheep.model;

public class UsernameAlreadyTakenException extends Exception {
	
	private static final long serialVersionUID = -5172801008477164263L;

	public UsernameAlreadyTakenException(String message) {
		super(message);
	}
}
