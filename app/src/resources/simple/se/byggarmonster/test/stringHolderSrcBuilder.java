package se.byggarmonster.test;

public class StringHolderSrcBuilder {
    private int myInt;
    private String myString;

    public StringHolderSrc build() {
        return new StringHolderSrc(myInt, myString);
    }

    public StringHolderSrcBuilder withmyInt(final int myInt) {
        this.myInt = myInt;
        return this;
    }

    public StringHolderSrcBuilder withmyString(final String myString) {
        this.myString = myString;
        return this;
    }

}
