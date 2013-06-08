package se.byggarmonster.main;

import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import se.byggarmonster.lib.ByggarMonsterAPIBuilder;

public class Main {
	public static final String OPTION_STDOUT = "stdout";
	public static final String PARAM_OUTPUT = "-outputFolder";
	public static final String PARAM_PACKAGE = "-package";
	public static final String PARAM_SOURCE = "-source";
	public static final String PARAM_TEMPLATE = "-template";

	public static String doMain(final String[] args) {
		final Params params = new Params(parsArgs(args));
		checkState(
		        params.get(PARAM_OUTPUT).isPresent()
		                && (params.get(PARAM_SOURCE).isPresent()//
		                || params.get(PARAM_PACKAGE).isPresent()) //
		        , PARAM_OUTPUT + " and " + PARAM_SOURCE + " or "
		                + PARAM_PACKAGE + " must be defined");

		checkState(params.get(PARAM_TEMPLATE).isPresent(), PARAM_TEMPLATE
		        + " must be defined");
		final String templatePath = params.get(PARAM_TEMPLATE).get();

		ByggarMonsterAPIBuilder byggarMonsterAPIBuilder = new ByggarMonsterAPIBuilder()
		        .withTemplateFile(templatePath);

		if (params.get(PARAM_SOURCE).isPresent()) {
			final String sourcePath = params.get(PARAM_SOURCE).get();
			byggarMonsterAPIBuilder = byggarMonsterAPIBuilder
			        .withSourceFile(sourcePath);
			if (notStdout(params)) {
				byggarMonsterAPIBuilder.toFile(params.get(PARAM_OUTPUT).get());
				return "Wrote "
				        + new File(params.get(PARAM_OUTPUT).get())
				                .getAbsoluteFile();
			} else
				return byggarMonsterAPIBuilder.toString();
		} else {
			// TODO: use package and output folder
		}

		throw new RuntimeException("Did not understand input parameters.");
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
