package se.byggarmonster.main;

import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import se.byggarmonster.lib.ByggarMonsterAPIBuilder;

import com.google.common.io.BaseEncoding;

public class Main {
	public static final String OPTION_STDOUT = "stdout";
	public static final String PARAM_OUTPUT = "-output";
	public static final String PARAM_PACKAGE = "-package";
	public static final String PARAM_SOURCE = "-source";
	public static final String PARAM_TEMPLATE = "-template";

	private static String base64decode(final String source) {
		return new String(BaseEncoding.base64().decode(source));
	}

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
			final String source = params.get(PARAM_SOURCE).get();
			if (isBase64(source)) {
				byggarMonsterAPIBuilder = byggarMonsterAPIBuilder
				        .withSource(base64decode(source));
			} else {
				byggarMonsterAPIBuilder = byggarMonsterAPIBuilder
				        .withSourceFile(source);
			}

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

	private static boolean isBase64(final String source) {
		try {
			base64decode(source);
			return true;
		} catch (final IllegalArgumentException e) {
			return false;
		}
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
