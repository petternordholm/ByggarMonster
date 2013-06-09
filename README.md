# ByggarMonster #

Name translates to Builder Pattern. This software will parse your POJO:s and create builders for them.

## Example ##
Template: https://github.com/tomasbjerre/ByggarMonster/blob/master/app/src/resources/templates/simple.txt

Generated builders: https://github.com/tomasbjerre/ByggarMonster/tree/master/app/src/resources/templates/se/byggarmonster/test/simple

Web demo is available at: http://byggarmonster.bjurr.se/

This software will parse pure data objects, like:

    public class TwoFinalAttributesSrc {
		private final int myInt;
		private final String myString;
	
		public TwoFinalAttributesSrc(final int myInt, final String myString) {
			this.myString = myString;
			this.myInt = myInt;
		}
	
		public int getMyInt() {
			return myInt;
		}
	
		public String getMyString() {
			return myString;
		}
	}

And produce builders, like:

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
