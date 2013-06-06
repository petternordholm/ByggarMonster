package se.byggarmonster.lib;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;

import se.byggarmonster.lib.impl.BuilderPatternGenerator;
import se.byggarmonster.lib.parser.JavaLexer;
import se.byggarmonster.lib.parser.JavaParser;

public class ByggarMonsterAPI {
	private final String source;
	private final String templatePath;

	public ByggarMonsterAPI(final String source, final String templatePath) {
		this.source = source;
		this.templatePath = templatePath;
	}

	public String getSource() {
		return source;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	@Override
	public String toString() {
		final String builderCode = "";
		final JavaLexer lexer = new JavaLexer(new ANTLRInputStream(source));
		final CommonTokenStream tokens = new CommonTokenStream(lexer);
		final JavaParser parser = new JavaParser(tokens);
		try {
			final BuilderPatternGenerator builderInstance = new BuilderPatternGenerator();
			parser.setBuildParseTree(true);
			parser.addParseListener(builderInstance);
			parser.compilationUnit();
			return builderInstance.render(templatePath);
		} catch (final RecognitionException e) {
			e.printStackTrace();
		}
		return builderCode;
	}
}
