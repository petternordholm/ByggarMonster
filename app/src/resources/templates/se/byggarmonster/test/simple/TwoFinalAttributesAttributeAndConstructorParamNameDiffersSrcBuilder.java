package se.byggarmonster.test.simple;

public class TwoFinalAttributesAttributeAndConstructorParamNameDiffersSrcBuilder {
    private int myInt;
    private String myString;

    public TwoFinalAttributesAttributeAndConstructorParamNameDiffersSrc build() {
        TwoFinalAttributesAttributeAndConstructorParamNameDiffersSrc instance = new TwoFinalAttributesAttributeAndConstructorParamNameDiffersSrc(myString, myInt);
        return instance;
    }

    public TwoFinalAttributesAttributeAndConstructorParamNameDiffersSrcBuilder from(final TwoFinalAttributesAttributeAndConstructorParamNameDiffersSrc from) {
        TwoFinalAttributesAttributeAndConstructorParamNameDiffersSrcBuilder instance = new TwoFinalAttributesAttributeAndConstructorParamNameDiffersSrcBuilder();
        instance.myString = from.getMyString();
        instance.myInt = from.getMyInt();
        return instance;
    }

    public TwoFinalAttributesAttributeAndConstructorParamNameDiffersSrcBuilder withMyInt(final int myInt) {
        this.myInt = myInt;
        return this;
    }

    public TwoFinalAttributesAttributeAndConstructorParamNameDiffersSrcBuilder withMyString(final String myString) {
        this.myString = myString;
        return this;
    }

}
