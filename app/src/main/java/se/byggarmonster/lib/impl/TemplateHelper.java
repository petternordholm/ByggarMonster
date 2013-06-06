package se.byggarmonster.lib.impl;

import static java.util.regex.Matcher.quoteReplacement;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class TemplateHelper {
	public static String render(final String templateFile,
	        final Map<String, Object> context) {
		String template;
		try {
			template = Files.toString(new File(templateFile), Charsets.UTF_8);
			template = renderEach(template, context);
			template = renderVariables(template, context);
			return template;
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static String renderEach(final String template,
	        final Map<String, Object> context) {
		return renderVariables(template, context);
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
			final String regionStringAsRegExp = regionString //
			        .replaceAll(quoteReplacement("$"), quoteReplacement("\\$")) //
			        .replaceAll("\\{", "\\\\{") //
			        .replaceAll("\\}", "\\\\}");
			renderedTemplate = template.replaceAll(regionStringAsRegExp, value);
		}
		return renderedTemplate;
	}

}
