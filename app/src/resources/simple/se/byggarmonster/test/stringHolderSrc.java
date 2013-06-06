package se.byggarmonster.test;

public class stringHolderSrc {
	private final int myInt;
	private final String myString;

	public stringHolderSrc(final String myStringConstrParam,
			final int theOtherConstructorParam) {
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
