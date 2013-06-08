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
	public static final String OPTION_STDOUT = "stdout";
	public static final String PARAM_OUTPUT = "-outputFolder";
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
		final Params params = new Params(parsArgs(args));
		String output = "";

		checkState(
		        params.get(PARAM_OUTPUT).isPresent()
		                && (params.get(PARAM_SOURCE).isPresent()//
		                || params.get(PARAM_PACKAGE).isPresent()) //
		        , PARAM_OUTPUT + " and " + PARAM_SOURCE + " or "
		                + PARAM_PACKAGE + " must be defined");

		checkState(params.get(PARAM_TEMPLATE).isPresent(), PARAM_TEMPLATE
		        + " must be defined");
		final String templatePath = params.get(PARAM_TEMPLATE).get();
		checkFileExists(templatePath);

		if (params.get(PARAM_SOURCE).isPresent()) {
			final String sourcePath = params.get(PARAM_SOURCE).get();
			checkFileExists(sourcePath);

			output = new ByggarMonsterAPIBuilder() //
			        .withSource(content(sourcePath)) //
			        .withTemplate(content(templatePath)) //
			        .build() //
			        .toString();

			if (notStdout(params))
				try {
					Files.write(output.getBytes(),
					        new File(params.get(PARAM_OUTPUT).get()));
				} catch (final IOException e) {
					System.err.println("Could not write to "
					        + new File(params.get(PARAM_OUTPUT).get())
					                .getAbsoluteFile());
				}
		} else {
			// TODO: use package and output folder
		}

		return output;
	}

	public static void main(final String[] args) {
		System.out.println(doMain(args));
	}

	private static boolean notStdout(final Params params) {
		return !params.get(PARAM_OUTPUT).get().equals(OPTION_STDOUT);
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
