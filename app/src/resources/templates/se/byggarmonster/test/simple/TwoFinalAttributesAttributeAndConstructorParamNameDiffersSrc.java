package se.byggarmonster.test.simple;


public class TwoFinalAttributesAttributeAndConstructorParamNameDiffersSrc {
	private final int myInt;
	private final String myString;

	public TwoFinalAttributesAttributeAndConstructorParamNameDiffersSrc(
	        final String myStringDiff, final int myIntDiff) {
		this.myString = myStringDiff;
		this.myInt = myIntDiff;
	}

	public int getMyInt() {
		return myInt;
	}

	public String getMyString() {
		return myString;
	}
}
