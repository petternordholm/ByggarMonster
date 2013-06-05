package se.byggarmonster.lib;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ParseTree;

import se.byggarmonster.lib.impl.simple.SimpleBuilder;
import se.byggarmonster.lib.parser.JavaLexer;
import se.byggarmonster.lib.parser.JavaParser;
import se.byggarmonster.lib.parser.JavaParser.CompilationUnitContext;

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
			final CompilationUnitContext cu = parser.compilationUnit();
			if (builderName.equalsIgnoreCase("simple")) {
				final SimpleBuilder builderInstance = new SimpleBuilder();
				traverse(cu, builderInstance);
				return builderInstance.toString();
			}
			throw new RuntimeException("Builder " + builderName
					+ " not supported.");
		} catch (final RecognitionException e) {
			e.printStackTrace();
		}
		return builderCode;
	}

	private void traverse(final ParseTree parseTree, final SimpleBuilder builder) {
		builder.visit(parseTree);
		for (int i = 0; i < parseTree.getChildCount(); i++)
			traverse(parseTree.getChild(i), builder);
	}
}
