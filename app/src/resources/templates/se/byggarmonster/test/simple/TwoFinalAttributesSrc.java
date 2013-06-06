package se.byggarmonster.test.simple;

public class TwoFinalAttributesSrc {
	private final int myInt;
	private final String myString;

	public TwoFinalAttributesSrc(final int theOtherConstructorParam,
	        final String myStringConstrParam) {
		this.myString = myStringConstrParam;
		this.myInt = theOtherConstructorParam;
	}

	public int getMyInt() {
		return myInt;
	}

	public String getMyString() {
		return myString;
	}
}
