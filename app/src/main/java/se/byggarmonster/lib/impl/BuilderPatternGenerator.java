package se.byggarmonster.lib.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import se.byggarmonster.lib.parser.JavaBaseListener;
import se.byggarmonster.lib.parser.JavaParser.BlockStatementContext;
import se.byggarmonster.lib.parser.JavaParser.ConstructorBodyContext;
import se.byggarmonster.lib.parser.JavaParser.ExpressionContext;
import se.byggarmonster.lib.parser.JavaParser.FieldDeclarationContext;
import se.byggarmonster.lib.parser.JavaParser.FormalParameterDeclsContext;
import se.byggarmonster.lib.parser.JavaParser.MemberDeclarationContext;
import se.byggarmonster.lib.parser.JavaParser.MethodBodyContext;
import se.byggarmonster.lib.parser.JavaParser.NormalClassDeclarationContext;
import se.byggarmonster.lib.parser.JavaParser.QualifiedNameContext;

public class BuilderPatternGenerator extends JavaBaseListener {
	private String className;
	/**
	 * Name => Type
	 */
	private final LinkedList<NameTypePair> constructorParameters;
	/**
	 * Constructor parameter => member attribute
	 */
	private final Map<String, String> memberMapping;
	/**
	 * Name => Type
	 */
	private final ArrayList<NameTypePair> members;
	private String packageName;

	public BuilderPatternGenerator() {
		constructorParameters = new LinkedList<NameTypePair>();
		members = new ArrayList<NameTypePair>();
		memberMapping = new HashMap<String, String>();
	}

	@Override
	public void exitConstructorBody(final ConstructorBodyContext ctx) {
		findMemberMappingsInBlocks(ctx.blockStatement());
	}

	/**
	 * Constructor arguments
	 */
	@Override
	public void exitFormalParameterDeclsRest(
	        final se.byggarmonster.lib.parser.JavaParser.FormalParameterDeclsRestContext ctx) {
		constructorParameters.addFirst(new NameTypePair(ctx
		        .variableDeclaratorId().getText(),
		        ((FormalParameterDeclsContext) ctx.getParent()).type()
		                .getText()));
	}

	@Override
	public void exitMemberDeclaration(final MemberDeclarationContext mdc) {
		if (mdc.getChild(1) instanceof FieldDeclarationContext) {
			final FieldDeclarationContext fieldDeclarationContext = (FieldDeclarationContext) mdc
			        .getChild(1);
			members.add(new NameTypePair(fieldDeclarationContext
			        .variableDeclarators().getText(), mdc.type().getText()));
		}
	}

	@Override
	public void exitMethodBody(final MethodBodyContext ctx) {
		findMemberMappingsInBlocks(ctx.block().blockStatement());
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

	private void findMemberMappingsInBlocks(
	        final List<BlockStatementContext> blocks) {
		for (final BlockStatementContext bsc : blocks) {
			if (bsc.statement().statementExpression() == null)
				continue;
			final ExpressionContext exprContext = bsc.statement()
			        .statementExpression().expression();
			final String memberName = removeThis(exprContext.getChild(0)
			        .getText());
			final String constructorName = exprContext.getChild(2).getText();
			memberMapping.put(constructorName, memberName);
		}
	}

	public String getClassName() {
		return className;
	}

	public List<NameTypePair> getConstructorParameters() {
		return constructorParameters;
	}

	public List<NameTypePair> getMembers() {
		return members;
	}

	public String getPackageName() {
		return packageName;
	}

	private List<NameTypePair> mapToMembers(final List<NameTypePair> pairs) {
		final List<NameTypePair> mapped = new ArrayList<NameTypePair>();
		for (final NameTypePair constructorParameter : pairs) {
			new HashMap<String, Object>();
			mapped.add(new NameTypePair(checkNotNull(
			        memberMapping.get(constructorParameter.getName()),
			        constructorParameter.getName() + " has no memberMapping"),
			        checkNotNull(constructorParameter.getType(),
			                constructorParameter.getName() + " has null type")));
		}
		return mapped;
	}

	private String removeThis(final String text) {
		if (!text.startsWith("this."))
			return text;
		return text.substring("this.".length());
	}

	public String render(final String templatePath) {
		final Map<String, Object> context = new HashMap<String, Object>();
		context.put("packageName", checkNotNull(getPackageName()));
		context.put("className", checkNotNull(getClassName()));
		context.put("members", toListOfNameTypeMap(getMembers()));
		context.put("constructorParameters",
		        toListOfNameTypeMap(mapToMembers(getConstructorParameters())));
		return TemplateHelper.render(templatePath, context);
	}

	private List<Map<String, Object>> toListOfNameTypeMap(
	        final List<NameTypePair> list) {
		final ArrayList<Map<String, Object>> constructorParameters = new ArrayList<Map<String, Object>>();
		for (final NameTypePair constructorParameter : list) {
			final Map<String, Object> map = new HashMap<String, Object>();
			map.put("name",
			        checkNotNull(constructorParameter.getName(),
			                constructorParameter.getName() + " is null"));
			map.put("type",
			        checkNotNull(constructorParameter.getType(),
			                constructorParameter.getName() + " has null type"));
			constructorParameters.add(map);
		}
		return constructorParameters;
	}
}
