package se.byggarmonster.test.simple;

public class TwoFinalAttributesDifferentOrderedAttributesInConstructorSrc {
	private final int myInt;
	private final String myString;

	public TwoFinalAttributesDifferentOrderedAttributesInConstructorSrc(
	        final String myStringConstrParam, final int theOtherConstructorParam) {
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
