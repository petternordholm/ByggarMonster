package se.byggarmonster.test.simple;

public class TwoFinalAttributesSrc {
	private final int myInt;
	private final String myString;

	public TwoFinalAttributesSrc(final int myInt, final String myString) {
		this.myString = myString;
		this.myInt = myInt;
	}

	public int getMyInt() {
		return myInt;
	}

	public String getMyString() {
		return myString;
	}
}
