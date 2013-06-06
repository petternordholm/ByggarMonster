package se.byggarmonster.test;

public class StringHolderSrc {
	private final int myInt;
	private final String myString;

	public StringHolderSrc(final int theOtherConstructorParam,
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
