package se.byggarmonster.test.simple;


public class TwoFinalAttributesSrcBuilder {
    private int myInt;
    private String myString;

    public TwoFinalAttributesSrc build() {
        TwoFinalAttributesSrc instance = new TwoFinalAttributesSrc(myInt, myString);
        return instance;
    }

    public TwoFinalAttributesSrcBuilder from(final TwoFinalAttributesSrc from) {
        TwoFinalAttributesSrcBuilder instance = new TwoFinalAttributesSrcBuilder();
        instance.myString = from.getMyString();
        instance.myInt = from.getMyInt();
        return instance;
    }

    public TwoFinalAttributesSrcBuilder withMyInt(final int myInt) {
        this.myInt = myInt;
        return this;
    }

    public TwoFinalAttributesSrcBuilder withMyString(final String myString) {
        this.myString = myString;
        return this;
    }

}
