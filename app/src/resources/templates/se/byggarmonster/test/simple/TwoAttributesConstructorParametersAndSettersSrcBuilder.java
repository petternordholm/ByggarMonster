package se.byggarmonster.test.simple;

public class TwoAttributesConstructorParametersAndSettersSrcBuilder {
    private int myInt;
    private String myString;

    public TwoAttributesConstructorParametersAndSettersSrc build() {
        TwoAttributesConstructorParametersAndSettersSrc instance = new TwoAttributesConstructorParametersAndSettersSrc(myInt, myString);
        instance.setMyString(myString);
        instance.setMyInt(myInt);
        return instance;
    }

    public TwoAttributesConstructorParametersAndSettersSrcBuilder from(final TwoAttributesConstructorParametersAndSettersSrc from) {
        TwoAttributesConstructorParametersAndSettersSrcBuilder instance = new TwoAttributesConstructorParametersAndSettersSrcBuilder();
        instance.myString = from.getMyString();
        instance.myInt = from.getMyInt();
        return instance;
    }

    public TwoAttributesConstructorParametersAndSettersSrcBuilder withmyInt(final int myInt) {
        this.myInt = myInt;
        return this;
    }

    public TwoAttributesConstructorParametersAndSettersSrcBuilder withmyString(final String myString) {
        this.myString = myString;
        return this;
    }

}
