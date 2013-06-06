package se.byggarmonster.lib.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import se.byggarmonster.lib.parser.JavaBaseListener;
import se.byggarmonster.lib.parser.JavaParser.FieldDeclarationContext;
import se.byggarmonster.lib.parser.JavaParser.FormalParameterDeclsContext;
import se.byggarmonster.lib.parser.JavaParser.MemberDeclarationContext;
import se.byggarmonster.lib.parser.JavaParser.NormalClassDeclarationContext;
import se.byggarmonster.lib.parser.JavaParser.QualifiedNameContext;

public class BuilderPatternGenerator extends JavaBaseListener {
	private String className;
	/**
	 * Name => Type
	 */
	private final LinkedList<VariableHolder> constructorParameters;
	/**
	 * Name => Type
	 */
	private final ArrayList<VariableHolder> members;
	private String packageName;

	public BuilderPatternGenerator() {
		constructorParameters = new LinkedList<VariableHolder>();
		members = new ArrayList<VariableHolder>();
	}

	/**
	 * Constructor arguments
	 */
	@Override
	public void exitFormalParameterDeclsRest(
	        final se.byggarmonster.lib.parser.JavaParser.FormalParameterDeclsRestContext ctx) {
		constructorParameters.addFirst(new VariableHolder(ctx
		        .variableDeclaratorId().getText(),
		        ((FormalParameterDeclsContext) ctx.getParent()).type()
		                .getText()));
	}

	@Override
	public void exitMemberDeclaration(final MemberDeclarationContext mdc) {
		if (mdc.getChild(1) instanceof FieldDeclarationContext) {
			final FieldDeclarationContext fieldDeclarationContext = (FieldDeclarationContext) mdc
			        .getChild(1);
			members.add(new VariableHolder(fieldDeclarationContext
			        .variableDeclarators().getText(), mdc.type().getText()));
		}
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

	public List<VariableHolder> getConstructorParameters() {
		return constructorParameters;
	}

	public List<VariableHolder> getMembers() {
		return members;
	}

	public String getPackageName() {
		return packageName;
	}

	public String render(final String templatePath) {
		final Map<String, Object> context = new HashMap<String, Object>();
		context.put("packageName", getPackageName());
		context.put("className", getClassName());
		final ArrayList<Map<String, Object>> members = new ArrayList<Map<String, Object>>();
		for (final VariableHolder member : getMembers()) {
			final Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", member.getName());
			map.put("type", member.getType());
			members.add(map);
		}
		context.put("members", members);

		final ArrayList<Map<String, Object>> constructorParameters = new ArrayList<Map<String, Object>>();
		for (final VariableHolder constructorParameter : getConstructorParameters()) {
			final Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", constructorParameter.getName());
			map.put("type", constructorParameter.getType());
			constructorParameters.add(map);
		}
		context.put("constructorParameters", constructorParameters);

		return TemplateHelper.render(templatePath, context);
	}
}
