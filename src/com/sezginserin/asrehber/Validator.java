package com.sezginserin.asrehber;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	
	protected static final int INVALID_NAME = 1;
	protected static final int INVALID_EMAIL = 2;
	
	public boolean validEmail(String email) {
		Pattern emailPattern = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher emailMatcher = emailPattern.matcher(email);
		return emailMatcher.matches();
	}
	
	public int validate (String nameString, String emailString) {
		if (nameString.trim().matches("")) {
			return Validator.INVALID_NAME;
		}
		
		if (!emailString.matches("") && !this.validEmail(emailString)) {
			return Validator.INVALID_EMAIL;
		}
		return 0;		
	}
}
