package se.byggarmonster.lib.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import se.byggarmonster.lib.parser.JavaBaseListener;
import se.byggarmonster.lib.parser.JavaParser.FieldDeclarationContext;
import se.byggarmonster.lib.parser.JavaParser.FormalParameterDeclsContext;
import se.byggarmonster.lib.parser.JavaParser.MemberDeclarationContext;
import se.byggarmonster.lib.parser.JavaParser.NormalClassDeclarationContext;
import se.byggarmonster.lib.parser.JavaParser.QualifiedNameContext;
import se.byggarmonster.lib.parser.JavaParser.VariableDeclaratorContext;

public class BuilderPatternGenerator extends JavaBaseListener {
	private String className;
	/**
	 * Name => Type
	 */
	private final Map<String, Object> constructorParameters;
	/**
	 * Name => Type
	 */
	private final Map<String, Object> memberAttributes;
	private String packageName;

	public BuilderPatternGenerator() {
		constructorParameters = new HashMap<String, Object>();
		memberAttributes = new HashMap<String, Object>();
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

	public Map<String, Object> getConstructorParameters() {
		return constructorParameters;
	}

	public Map<String, Object> getMemberAttributes() {
		return memberAttributes;
	}

	public String getPackageName() {
		return packageName;
	}

	public String render(final String templatePath) {
		final Map<String, Object> context = new HashMap<String, Object>();
		context.put("packageName", getPackageName());
		context.put("className", getClassName());
		final ArrayList<Map<String, Object>> members = new ArrayList<Map<String, Object>>();
		for (final String member : getMemberAttributes().keySet()) {
			final Map<String, Object> memberAttributesMap = new HashMap<String, Object>();
			memberAttributesMap.put("name", member);
			memberAttributesMap.put("type", getMemberAttributes().get(member));
			members.add(memberAttributesMap);
		}

		context.put("members", members);
		return TemplateHelper.render(templatePath, context);
	}
}
