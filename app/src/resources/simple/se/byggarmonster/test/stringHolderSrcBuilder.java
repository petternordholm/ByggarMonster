package se.byggarmonster.test;

public class StringHolderSrcBuilder {
	private int myInt;
	private String myString;

	public StringHolderSrc build() {
		return new StringHolderSrc(myString, myInt);
	}

	public StringHolderSrcBuilder withMyInt(final int myInt) {
		this.myInt = myInt;
		return this;
	}

	public StringHolderSrcBuilder withMyString(final String myString) {
		this.myString = myString;
		return this;
	}
}
