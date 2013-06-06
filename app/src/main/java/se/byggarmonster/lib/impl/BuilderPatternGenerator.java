package se.byggarmonster.lib.impl;

import java.util.HashMap;
import java.util.Map;

import se.byggarmonster.lib.parser.JavaBaseListener;
import se.byggarmonster.lib.parser.JavaParser.FieldDeclarationContext;
import se.byggarmonster.lib.parser.JavaParser.FormalParameterDeclsContext;
import se.byggarmonster.lib.parser.JavaParser.MemberDeclarationContext;
import se.byggarmonster.lib.parser.JavaParser.NormalClassDeclarationContext;
import se.byggarmonster.lib.parser.JavaParser.QualifiedNameContext;
import se.byggarmonster.lib.parser.JavaParser.VariableDeclaratorContext;

public abstract class BuilderPatternGenerator extends JavaBaseListener {
	private String className;
	/**
	 * Name => Type
	 */
	private final Map<String, String> constructorParameters;
	/**
	 * Name => Type
	 */
	private final Map<String, String> memberAttributes;
	private String packageName;

	public BuilderPatternGenerator() {
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

	@Override
	public void exitNormalClassDeclaration(
			final NormalClassDeclarationContext ctx) {
		this.className = ctx.Identifier().getText();
	}

	/**
	 * package name
	 */
	@Override
	public void exitQualifiedName(final QualifiedNameContext ctx) {
		this.packageName = ctx.getText();
	}

	public String getClassName() {
		return className;
	}

	public Map<String, String> getConstructorParameters() {
		return constructorParameters;
	}

	public Map<String, String> getMemberAttributes() {
		return memberAttributes;
	}

	public String getPackageName() {
		return packageName;
	}

	@Override
	public abstract String toString();
}
