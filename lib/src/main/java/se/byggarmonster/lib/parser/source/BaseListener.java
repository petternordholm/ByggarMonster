package se.byggarmonster.lib.parser.source;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;

import se.byggarmonster.lib.compilationunit.CompilationUnit;
import se.byggarmonster.lib.compilationunit.CompilationUnitBuilder;
import se.byggarmonster.lib.compilationunit.NameTypePair;
import se.byggarmonster.lib.parser.JavaBaseListener;
import se.byggarmonster.lib.parser.JavaParser.BlockStatementContext;
import se.byggarmonster.lib.parser.JavaParser.ConstructorBodyContext;
import se.byggarmonster.lib.parser.JavaParser.ConstructorDeclaratorRestContext;
import se.byggarmonster.lib.parser.JavaParser.ExpressionContext;
import se.byggarmonster.lib.parser.JavaParser.FieldDeclarationContext;
import se.byggarmonster.lib.parser.JavaParser.FormalParameterDeclsContext;
import se.byggarmonster.lib.parser.JavaParser.ImportDeclarationContext;
import se.byggarmonster.lib.parser.JavaParser.MemberDeclContext;
import se.byggarmonster.lib.parser.JavaParser.MemberDeclarationContext;
import se.byggarmonster.lib.parser.JavaParser.MethodBodyContext;
import se.byggarmonster.lib.parser.JavaParser.NormalClassDeclarationContext;
import se.byggarmonster.lib.parser.JavaParser.PackageDeclarationContext;

import com.google.common.base.Optional;

public class BaseListener extends JavaBaseListener {
	private final CompilationUnitBuilder compilationUnitBuilder = new CompilationUnitBuilder();

	@Override
	public void exitConstructorBody(final ConstructorBodyContext ctx) {
		compilationUnitBuilder
		        .withConstructorMemberMappings(findMemberMappingsInBlocks(ctx
		                .blockStatement()));
	}

	/**
	 * Constructor arguments
	 */
	@Override
	public void exitFormalParameterDeclsRest(
	        final se.byggarmonster.lib.parser.JavaParser.FormalParameterDeclsRestContext ctx) {
		if (hasParent(ctx, ConstructorDeclaratorRestContext.class))
			compilationUnitBuilder.withConstructorParameter(new NameTypePair(
			        ctx.variableDeclaratorId().getText(),
			        ((FormalParameterDeclsContext) ctx.getParent()).type()
			                .getText()));
	}

	@Override
	public void exitImportDeclaration(final ImportDeclarationContext ctx) {
		compilationUnitBuilder.withImport(ctx.qualifiedName().getText());
	}

	@Override
	public void exitMemberDeclaration(final MemberDeclarationContext mdc) {
		if (mdc.getChild(1) instanceof FieldDeclarationContext) {
			final FieldDeclarationContext fieldDeclarationContext = (FieldDeclarationContext) mdc
			        .getChild(1);
			compilationUnitBuilder.withMember(new NameTypePair(
			        fieldDeclarationContext.variableDeclarators().getText(),
			        mdc.type().getText()));
		}
	}

	@Override
	public void exitMethodBody(final MethodBodyContext ctx) {
		final Map<String, String> foundMappings = findMemberMappingsInBlocks(ctx
		        .block().blockStatement());
		if (foundMappings.size() == 1) {
			final MemberDeclContext memberDeclContext = (MemberDeclContext) ctx
			        .getParent().getParent();
			compilationUnitBuilder.withSetterMapping(memberDeclContext
			        .Identifier().getText(),
			        getMember(foundMappings.values().iterator().next())
			                .getName());
		}

		final Optional<String> returnMember = findReturnInBlocks(ctx.block()
		        .blockStatement());
		if (returnMember.isPresent()) {
			compilationUnitBuilder.withGetterMapping(ctx.getParent()
			        .getParent().getChild(0).getText(), returnMember.get());
		}
	}

	@Override
	public void exitNormalClassDeclaration(
	        final NormalClassDeclarationContext ctx) {
		compilationUnitBuilder.withClassName(ctx.Identifier().getText());
	}

	@Override
	public void exitPackageDeclaration(final PackageDeclarationContext ctx) {
		compilationUnitBuilder.withPackageName(ctx.qualifiedName().getText());
	}

	private Map<String, String> findMemberMappingsInBlocks(
	        final List<BlockStatementContext> blocks) {
		final Map<String, String> foundMappings = new HashMap<String, String>();
		for (final BlockStatementContext bsc : blocks) {
			if (bsc.statement().statementExpression() == null)
				continue;
			final ExpressionContext exprContext = bsc.statement()
			        .statementExpression().expression();
			final String memberName = removeThis(exprContext.getChild(0)
			        .getText());
			final String constructorName = exprContext.getChild(2).getText();
			foundMappings.put(constructorName, memberName);
		}
		return foundMappings;
	}

	private Optional<String> findReturnInBlocks(
	        final List<BlockStatementContext> blocks) {
		for (final BlockStatementContext bsc : blocks) {
			if (bsc.statement().expression().size() > 0)
				return Optional.of(bsc.statement().expression().get(0)
				        .getChild(0).getText());
		}
		return Optional.absent();
	}

	public CompilationUnit getCompilationUnit() {
		return compilationUnitBuilder.build();
	}

	private NameTypePair getMember(final String name) {
		for (final NameTypePair p : compilationUnitBuilder.build().getMembers())
			if (p.getName().equals(name))
				return p;
		throw new RuntimeException(name + " not found");
	}

	private boolean hasParent(final ParserRuleContext ctx, final Class<?> clazz) {
		if (ctx.getParent() == null)
			return false;
		if (ctx.getParent().getClass().equals(clazz))
			return true;
		return hasParent(ctx.getParent(), clazz);
	}

	private String removeThis(final String text) {
		if (!text.startsWith("this."))
			return text;
		return text.substring("this.".length());
	}
}
