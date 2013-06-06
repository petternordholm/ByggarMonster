package se.byggarmonster.test.simple;

public class TwoFinalAttributesAttributeAndConstructorParamNameDiffersSrcBuilder {
    private int myInt;
    private String myString;

    public TwoFinalAttributesAttributeAndConstructorParamNameDiffersSrc build() {
        return new TwoFinalAttributesAttributeAndConstructorParamNameDiffersSrc(myString, myInt);
    }

    public TwoFinalAttributesAttributeAndConstructorParamNameDiffersSrcBuilder withmyInt(final int myInt) {
        this.myInt = myInt;
        return this;
    }

    public TwoFinalAttributesAttributeAndConstructorParamNameDiffersSrcBuilder withmyString(final String myString) {
        this.myString = myString;
        return this;
    }

}