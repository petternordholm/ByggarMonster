package se.byggarmonster.main;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import se.byggarmonster.lib.ByggarMonsterAPIBuilder;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class Main {
	public static final String PARAM_SOURCE = "-source";
	public static final String PARAM_TEMPLATE = "-template";

	private static void checkFileExists(final String filePath) {
		checkState(new File(filePath).exists(),
		        new File(filePath).getAbsolutePath() + " does not exist.");
	}

	private static String content(final String path) {
		try {
			return Files.toString(new File(path), Charsets.UTF_8);
		} catch (final IOException e) {
			throw new RuntimeException("Can not read file "
			        + new File(path).getAbsoluteFile());
		}
	}

	public static String doMain(final String[] args) {
		final Map<String, String> argsMap = parsArgs(args);

		checkNotNull(PARAM_SOURCE + " must be defined",
		        argsMap.get(PARAM_SOURCE));
		final String sourcePath = argsMap.get(PARAM_SOURCE);
		checkFileExists(sourcePath);

		checkNotNull(PARAM_TEMPLATE + " must be defined",
		        argsMap.get(PARAM_TEMPLATE));
		final String templatePath = argsMap.get(PARAM_TEMPLATE);
		checkFileExists(templatePath);

		return new ByggarMonsterAPIBuilder() //
		        .withSource(content(sourcePath)) //
		        .withTemplate(content(templatePath)) //
		        .build() //
		        .toString();
	}

	public static void main(final String[] args) {
		System.out.println(doMain(args));
	}

	private static Map<String, String> parsArgs(final String[] args) {
		final HashMap<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < args.length; i++) {
			if (args[i].startsWith("-") && i + 1 < args.length) {
				map.put(args[i], args[i + 1]);
				i++;
			}
		}
		return map;
	}
}
