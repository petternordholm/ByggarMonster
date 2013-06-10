package se.byggarmonster.lib.parser.source;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;

import se.byggarmonster.lib.compilationunit.CompilationUnit;
import se.byggarmonster.lib.parser.JavaLexer;
import se.byggarmonster.lib.parser.JavaParser;

public class SourceParser {
	private final String source;

	public SourceParser(final String source) {
		this.source = source;
	}

	public CompilationUnit getCompilationUnit() {
		final JavaLexer lexer = new JavaLexer(new ANTLRInputStream(source));
		final CommonTokenStream tokens = new CommonTokenStream(lexer);
		final JavaParser parser = new JavaParser(tokens);
		try {
			final BaseListener builderInstance = new BaseListener();
			parser.setBuildParseTree(true);
			parser.addParseListener(builderInstance);
			parser.compilationUnit();
			return builderInstance.getCompilationUnit();
		} catch (final RecognitionException e) {
			throw new RuntimeException("Exception during parsing", e);
		}
	}
}
