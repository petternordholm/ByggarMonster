package se.byggarmonster.lib.impl.simple;

import java.util.ArrayList;
import java.util.List;

import se.byggarmonster.lib.parser.JavaBaseListener;
import se.byggarmonster.lib.parser.JavaParser.QualifiedNameContext;

public class SimpleBuilder extends JavaBaseListener {
	private final List<String> constructorParameterNames;

	public SimpleBuilder() {
		constructorParameterNames = new ArrayList<String>();
	}

	/**
	 * Constructor arguments
	 */
	@Override
	public void exitFormalParameterDeclsRest(
			final se.byggarmonster.lib.parser.JavaParser.FormalParameterDeclsRestContext ctx) {
		System.out.println("exiting exitFormalParameterDeclsRest "
				+ ctx.getText());
		constructorParameterNames.add(ctx.getText());
	}

	/**
	 * package name, class attribute
	 */
	@Override
	public void exitQualifiedName(final QualifiedNameContext ctx) {
		System.out.println("exiting exitQualifiedName " + ctx.getText());
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (final String name : constructorParameterNames)
			sb.append(" " + name);
		return sb.toString();
	}
}
