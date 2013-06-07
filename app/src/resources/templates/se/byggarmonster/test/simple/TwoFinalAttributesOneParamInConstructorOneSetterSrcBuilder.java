package se.byggarmonster.test.simple;

public class TwoFinalAttributesOneParamInConstructorOneSetterSrcBuilder {
    private int myInt;
    private String myString;

    public TwoFinalAttributesOneParamInConstructorOneSetterSrc build() {
        TwoFinalAttributesOneParamInConstructorOneSetterSrc instance = new TwoFinalAttributesOneParamInConstructorOneSetterSrc(myString);
        instance.setMyInt(myInt);
        return instance;
    }

    public TwoFinalAttributesOneParamInConstructorOneSetterSrcBuilder from(final TwoFinalAttributesOneParamInConstructorOneSetterSrc from) {
        TwoFinalAttributesOneParamInConstructorOneSetterSrcBuilder instance = new TwoFinalAttributesOneParamInConstructorOneSetterSrcBuilder();
        instance.myString = from.getMyString();
        instance.myInt = from.getMyInt();
        return instance;
    }

    public TwoFinalAttributesOneParamInConstructorOneSetterSrcBuilder withmyInt(final int myInt) {
        this.myInt = myInt;
        return this;
    }

    public TwoFinalAttributesOneParamInConstructorOneSetterSrcBuilder withmyString(final String myString) {
        this.myString = myString;
        return this;
    }

}
