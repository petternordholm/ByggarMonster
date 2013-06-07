package se.byggarmonster.test.toimplement.simple;

public class TwoFinalAttributesOneParamInConstructorOneSetterSrc {
	private int myInt;
	private final String myString;

	public TwoFinalAttributesOneParamInConstructorOneSetterSrc(
	        final String myStringDiff) {
		this.myString = myStringDiff;
	}
	
	public void setMyInt(int myIntDiff) {
		this.myInt = myIntDiff;
    }

	public int getMyInt() {
		return myInt;
	}

	public String getMyString() {
		return myString;
	}
}
