package se.byggarmonster.test.simple;

public class TwoFinalAttributesSrcBuilder {
    private int myInt;
    private String myString;

    public TwoFinalAttributesSrc build() {
        TwoFinalAttributesSrc instance = new TwoFinalAttributesSrc(myInt, myString);
        return instance;
    }

    public TwoFinalAttributesSrcBuilder withmyInt(final int myInt) {
        this.myInt = myInt;
        return this;
    }

    public TwoFinalAttributesSrcBuilder withmyString(final String myString) {
        this.myString = myString;
        return this;
    }

}
