package se.byggarmonster.lib;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.byggarmonster.lib.compilationunit.CompilationUnit;
import se.byggarmonster.lib.compilationunit.MethodMapping;
import se.byggarmonster.lib.compilationunit.NameTypePair;
import se.byggarmonster.lib.parser.source.SourceParser;
import se.byggarmonster.lib.template.TemplateRenderer;

class ByggarMonsterAPI {
	private final String source;
	private final String templatePath;

	public ByggarMonsterAPI(final String source, final String template) {
		this.source = source;
		this.templatePath = template;
	}

	public String getSource() {
		return source;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	private List<NameTypePair> mapToMembers(
	        final CompilationUnit compilationUnit,
	        final List<NameTypePair> pairs) {
		final List<NameTypePair> mapped = new ArrayList<NameTypePair>();
		for (final NameTypePair constructorParameter : pairs) {
			new HashMap<String, Object>();
			mapped.add(new NameTypePair(checkNotNull(
			        compilationUnit.getConstructorMemberMapping().getAttribute(
			                constructorParameter.getName()),
			        constructorParameter.getName() + " has no memberMapping"),
			        checkNotNull(constructorParameter.getType(),
			                constructorParameter.getName() + " has null type")));
		}
		return mapped;
	}

	public String render(final CompilationUnit compilationUnit,
	        final String templatePath) {
		final Map<String, Object> context = new HashMap<String, Object>();
		context.put(
		        "packageName",
		        checkNotNull(compilationUnit.getPackageName(),
		                "Package name was not parsed."));
		context.put("className", checkNotNull(compilationUnit.getClassName()));
		context.put("members",
		        toListOfNameTypeMap(compilationUnit.getMembers()));
		context.put(
		        "constructorParameters",
		        toListOfNameTypeMap(mapToMembers(compilationUnit,
		                compilationUnit.getConstructorParameters())));
		context.put("setters", toList(compilationUnit.getSetterMapping()));
		context.put("getters", toList(compilationUnit.getGetterMapping()));
		context.put("imports", toList(compilationUnit.getImports()));
		return TemplateRenderer.render(templatePath, context);
	}

	private List<Map<String, String>> toList(final List<String> imports) {
		final List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (final String s : imports) {
			final HashMap<String, String> map = new HashMap<String, String>();
			map.put("it", s);
			list.add(map);
		}
		return list;
	}

	private List<Map<String, Object>> toList(final MethodMapping map) {
		final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (final String key : map.getMethods()) {
			final Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("key", key);
			variables.put("value", map.getAttribute(key));
			list.add(variables);
		}
		return list;
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

	@Override
	public String toString() {
		return render(new SourceParser(source).getCompilationUnit(),
		        templatePath);
	}
}
