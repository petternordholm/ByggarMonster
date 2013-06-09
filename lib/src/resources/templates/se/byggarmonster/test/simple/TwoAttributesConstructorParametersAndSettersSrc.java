package se.byggarmonster.test.simple;

public class TwoAttributesConstructorParametersAndSettersSrc {
	private int myInt;
	private String myString;

	public TwoAttributesConstructorParametersAndSettersSrc(final int myInt,
	        final String myString) {
		this.myString = myString;
		this.myInt = myInt;
	}

	public int getMyInt() {
		return myInt;
	}

	public String getMyString() {
		return myString;
	}

	public void setMyInt(final int myInt2) {
		this.myInt = myInt2;
	}

	public void setMyString(final String myString2) {
		this.myString = myString2;
	}
}
