package se.byggarmonster.lib.impl.simple;

import java.util.HashMap;
import java.util.Map;

import se.byggarmonster.lib.parser.JavaBaseListener;
import se.byggarmonster.lib.parser.JavaParser.FormalParameterDeclsContext;
import se.byggarmonster.lib.parser.JavaParser.QualifiedNameContext;

public class SimpleBuilder extends JavaBaseListener {
	/**
	 * Name => Type
	 */
	private final Map<String, String> constructorParameters;

	public SimpleBuilder() {
		constructorParameters = new HashMap<String, String>();
	}

	/**
	 * Constructor arguments
	 */
	@Override
	public void exitFormalParameterDeclsRest(
			final se.byggarmonster.lib.parser.JavaParser.FormalParameterDeclsRestContext ctx) {
		System.out.println("exiting exitFormalParameterDeclsRest "
				+ ctx.getText());
		constructorParameters.put(ctx.getText(),
				((FormalParameterDeclsContext) ctx.getParent()).type()
						.getText());
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
		for (final String name : constructorParameters.keySet())
			sb.append(" " + name + "(" + constructorParameters.get(name) + ")");
		return sb.toString();
	}
}
