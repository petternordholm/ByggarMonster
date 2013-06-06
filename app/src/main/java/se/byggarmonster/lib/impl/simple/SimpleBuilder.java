package se.byggarmonster.lib.impl.simple;

import java.util.HashMap;
import java.util.Map;

import se.byggarmonster.lib.parser.JavaBaseListener;
import se.byggarmonster.lib.parser.JavaParser.FieldDeclarationContext;
import se.byggarmonster.lib.parser.JavaParser.FormalParameterDeclsContext;
import se.byggarmonster.lib.parser.JavaParser.MemberDeclarationContext;
import se.byggarmonster.lib.parser.JavaParser.QualifiedNameContext;
import se.byggarmonster.lib.parser.JavaParser.VariableDeclaratorContext;

public class SimpleBuilder extends JavaBaseListener {
	/**
	 * Name => Type
	 */
	private final Map<String, String> constructorParameters;
	/**
	 * Name => Type
	 */
	private final Map<String, String> memberAttributes;
	private String packageName;

	public SimpleBuilder() {
		constructorParameters = new HashMap<String, String>();
		memberAttributes = new HashMap<String, String>();
	}

	@Override
	public void exitFieldDeclaration(final FieldDeclarationContext ctx) {
		for (int i = 0; i < ctx.variableDeclarators().getChildCount(); i++) {
			final VariableDeclaratorContext child = (VariableDeclaratorContext) ctx
					.variableDeclarators().getChild(i);
			final MemberDeclarationContext mDC = (MemberDeclarationContext) child
					.getParent().getParent().getParent();
			memberAttributes.put(child.getText(), mDC.type().getText());
		}
	}

	/**
	 * Constructor arguments
	 */
	@Override
	public void exitFormalParameterDeclsRest(
			final se.byggarmonster.lib.parser.JavaParser.FormalParameterDeclsRestContext ctx) {
		constructorParameters.put(ctx.getText(),
				((FormalParameterDeclsContext) ctx.getParent()).type()
						.getText());
	}

	/**
	 * package name
	 */
	@Override
	public void exitQualifiedName(final QualifiedNameContext ctx) {
		this.packageName = ctx.getText();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("package " + packageName + ";\n");

		sb.append("Constructor parameters:\n");
		for (final String name : constructorParameters.keySet())
			sb.append(" " + name + "(" + constructorParameters.get(name) + ")");

		sb.append("\nAttributes:\n");
		for (final String name : memberAttributes.keySet())
			sb.append(" " + name + "(" + memberAttributes.get(name) + ")");

		return sb.toString();
	}
}
