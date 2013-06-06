package se.byggarmonster.test.simple;

public class TwoFinalAttributesDifferentOrderedAttributesInConstructorSrcBuilder {
    private int myInt;
    private String myString;

    public TwoFinalAttributesDifferentOrderedAttributesInConstructorSrc build() {
        return new TwoFinalAttributesDifferentOrderedAttributesInConstructorSrc(myString, myInt);
    }

    public TwoFinalAttributesDifferentOrderedAttributesInConstructorSrcBuilder withmyInt(final int myInt) {
        this.myInt = myInt;
        return this;
    }

    public TwoFinalAttributesDifferentOrderedAttributesInConstructorSrcBuilder withmyString(final String myString) {
        this.myString = myString;
        return this;
    }

}
