package se.byggarmonster.lib;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import se.byggarmonster.lib.parser.JavaLexer;
import se.byggarmonster.lib.parser.JavaParser;

public class ByggarMonsterAPI {
	private final String builder;
	private final String source;

	public ByggarMonsterAPI(final String source, final String builder) {
		this.source = source;
		this.builder = builder;
	}

	public String getBuilder() {
		return builder;
	}

	public String getSource() {
		return source;
	}

	@Override
	public String toString() {
		String builderCode = "";
		final ANTLRStringStream stringStream = new ANTLRStringStream(source);
		final JavaLexer lexer = new JavaLexer(stringStream);
		final CommonTokenStream tokens = new CommonTokenStream();
		tokens.setTokenSource(lexer);
		final JavaParser parser = new JavaParser(tokens);
		try {
			parser.compilationUnit();
			// TODO: How to use the parser??
		} catch (final RecognitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (final Object objCommonToken : tokens.getTokens()) {
			final CommonToken commonToken = (CommonToken) objCommonToken;
			if (commonToken.getType() == JavaParser.PACKAGE) {
				builderCode += commonToken.getText();
			}
		}
		return builderCode;
	}
}
