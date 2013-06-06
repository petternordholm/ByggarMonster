package se.byggarmonster.test.simple;

public class TwoFinalAttributesDifferentOrderedAttributesInConstructorSrc {
	private final int myInt;
	private final String myString;

	public TwoFinalAttributesDifferentOrderedAttributesInConstructorSrc(
	        final String myString, final int myInt) {
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
