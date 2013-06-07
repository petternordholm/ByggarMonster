package se.byggarmonster.main;

import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import se.byggarmonster.lib.ByggarMonsterAPIBuilder;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class Main {
	public static final String PARAM_OUTPUTFOLDER = "-outputFolder";
	public static final String PARAM_PACKAGE = "-package";
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
		final Params argsMap = new Params(parsArgs(args));

		checkState(
		        argsMap.get(PARAM_SOURCE).isPresent()
		                || argsMap.get(PARAM_OUTPUTFOLDER).isPresent()
		                && argsMap.get(PARAM_PACKAGE).isPresent(), PARAM_SOURCE
		                + " or " + PARAM_PACKAGE + " and " + PARAM_OUTPUTFOLDER
		                + " must be defined");

		checkState(argsMap.get(PARAM_TEMPLATE).isPresent(), PARAM_TEMPLATE
		        + " must be defined");
		final String templatePath = argsMap.get(PARAM_TEMPLATE).get();
		checkFileExists(templatePath);

		if (argsMap.get(PARAM_SOURCE).isPresent()) {
			final String sourcePath = argsMap.get(PARAM_SOURCE).get();
			checkFileExists(sourcePath);

			return new ByggarMonsterAPIBuilder() //
			        .withSource(content(sourcePath)) //
			        .withTemplate(content(templatePath)) //
			        .build() //
			        .toString();
		} else {
			// TODO: use package and output folder
		}

		return "";
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
