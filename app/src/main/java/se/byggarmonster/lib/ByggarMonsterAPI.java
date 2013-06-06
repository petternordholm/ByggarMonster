package se.byggarmonster.lib;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;

import se.byggarmonster.lib.impl.simple.SimpleBuilder;
import se.byggarmonster.lib.parser.JavaBaseListener;
import se.byggarmonster.lib.parser.JavaLexer;
import se.byggarmonster.lib.parser.JavaParser;

public class ByggarMonsterAPI {
	private final String builderName;
	private final String source;

	public ByggarMonsterAPI(final String source, final String builder) {
		this.source = source;
		this.builderName = builder;
	}

	public String getBuilder() {
		return builderName;
	}

	public String getSource() {
		return source;
	}

	@Override
	public String toString() {
		final String builderCode = "";
		final JavaLexer lexer = new JavaLexer(new ANTLRInputStream(source));
		final CommonTokenStream tokens = new CommonTokenStream(lexer);
		final JavaParser parser = new JavaParser(tokens);
		try {
			JavaBaseListener builderInstance = null;
			if (builderName.equalsIgnoreCase("simple")) {
				builderInstance = new SimpleBuilder();
			} else {
				throw new RuntimeException("Builder " + builderName
						+ " not supported.");
			}
			parser.setBuildParseTree(true);
			parser.addParseListener(builderInstance);
			parser.compilationUnit();
			return builderInstance.toString();
		} catch (final RecognitionException e) {
			e.printStackTrace();
		}
		return builderCode;
	}
}
