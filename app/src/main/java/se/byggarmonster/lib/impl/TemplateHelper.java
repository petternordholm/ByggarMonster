package se.byggarmonster.lib.impl;

import static java.util.regex.Matcher.quoteReplacement;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.io.Files;

public class TemplateHelper {
	public static String render(final String templateFile,
	        final Map<String, Object> context) {
		String template;
		try {
			template = Files.toString(new File(templateFile), Charsets.UTF_8);
			template = renderVariables(template, context);
			template = renderEach(template, context);
			return template;
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static String renderEach(final String template,
	        final Map<String, Object> context) {
		String renderedTemplate = new String(template);
		final Pattern p = Pattern.compile(
		        "\\{EACH ([a-zA-Z0-9]*)( \"[^\"]*\")?\\}(.*?)\\{/EACH\\}",
		        Pattern.DOTALL);
		while (true) {
			final Matcher m = p.matcher(renderedTemplate);
			if (!m.find())
				break;
			final String regionString = m.group(0);
			final String variableName = m.group(1);
			Optional<String> betweenValue = Optional.fromNullable(m.group(2));
			if (betweenValue.isPresent()) {
				betweenValue = Optional.of(betweenValue.get().substring(2,
				        m.group(2).length() - 1));
			}
			final String templateBlock = m.group(3);
			final List<Object> members = ((List<Object>) context
			        .get(variableName));

			final StringBuilder renderedBlock = new StringBuilder();
			boolean isFirst = true;
			for (final Object member : members) {
				renderedBlock
				        .append((!isFirst && betweenValue.isPresent() ? betweenValue
				                .get() : "")
				                + renderVariables(templateBlock,
				                        (Map<String, Object>) member));
				isFirst = false;
			}

			renderedTemplate = renderedTemplate.replaceAll(
			        toRegExp(regionString), renderedBlock.toString());
			isFirst = false;
		}
		return renderedTemplate;
	}

	private static String renderVariables(final String template,
	        final Map<String, Object> context) {
		String renderedTemplate = new String(template);
		final Pattern p = Pattern.compile("\\$\\{([^\\}]*)\\}");
		final Matcher m = p.matcher(template);
		while (m.find()) {
			final String regionString = m.group(0);
			final String variableName = m.group(1);
			final String value = (String) context.get(variableName);
			if (value == null)
				continue;
			renderedTemplate = renderedTemplate.replaceAll(
			        toRegExp(regionString), value);
		}
		return renderedTemplate;
	}

	private static String toRegExp(final String regionString) {
		return regionString //
		        .replaceAll(quoteReplacement("$"), quoteReplacement("\\$")) //
		        .replaceAll("\\{", "\\\\{") //
		        .replaceAll("\\}", "\\\\}") //
		        .replaceAll("\\(", "\\\\(") //
		        .replaceAll("\\)", "\\\\)");
	}

}
