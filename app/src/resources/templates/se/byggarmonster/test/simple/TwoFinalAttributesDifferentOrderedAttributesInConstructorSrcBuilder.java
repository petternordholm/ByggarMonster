package se.byggarmonster.test.simple;

public class TwoFinalAttributesDifferentOrderedAttributesInConstructorSrcBuilder {
    private int myInt;
    private String myString;

    public TwoFinalAttributesDifferentOrderedAttributesInConstructorSrc build() {
        TwoFinalAttributesDifferentOrderedAttributesInConstructorSrc instance = new TwoFinalAttributesDifferentOrderedAttributesInConstructorSrc(myString, myInt);
        return instance;
    }

    public TwoFinalAttributesDifferentOrderedAttributesInConstructorSrcBuilder from(final TwoFinalAttributesDifferentOrderedAttributesInConstructorSrc from) {
        TwoFinalAttributesDifferentOrderedAttributesInConstructorSrcBuilder instance = new TwoFinalAttributesDifferentOrderedAttributesInConstructorSrcBuilder();
        instance.myString = from.getMyString();
        instance.myInt = from.getMyInt();
        return instance;
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
