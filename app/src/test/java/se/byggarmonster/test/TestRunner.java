package se.byggarmonster.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import se.byggarmonster.main.Main;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.io.Files;

public class TestRunner {
	private interface ResultInspector {
		public void inspect(String sourceFile, String asserted, String actual);
	}

	public static final String ASSERTED_JAVA = "SrcBuilder.java";
	private static ResultInspector implementedResultInspector = new ResultInspector() {
		@Override
		public void inspect(final String sourceFile, final String asserted,
		        final String actual) {
			assertEquals(sourceFile, asserted, actual);
		}
	};
	private static ResultInspector notImplementedInspector = new ResultInspector() {
		@Override
		public void inspect(final String sourceFile, final String asserted,
		        final String actual) {
			try {
				assertEquals(asserted, actual);
			} catch (final AssertionError e) {
				System.err.println(e.toString());
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	};
	public static final String SRC_JAVA = "Src.java";
	public static final String SRC_TEST_RESOURCES = "src/resources/templates";

	public List<String> getAllFiles(final String folder, final String ending) {
		final List<String> sourceFiles = new ArrayList<String>();
		final Optional<File[]> files = Optional.fromNullable(new File(folder)
		        .listFiles());
		if (files.isPresent())
			for (final File file : files.get()) {
				if (file.getName().endsWith(ending)) {
					sourceFiles.add(file.getAbsolutePath());
				}
				if (file.isDirectory()) {
					sourceFiles.addAll(getAllFiles(file.getAbsolutePath(),
					        ending));
				}
			}
		return sourceFiles;
	}

	private Optional<String> getContentIfExists(final String file) {
		try {
			return Optional.of(Files.toString(new File(file), Charsets.UTF_8));
		} catch (final IOException e) {
			return Optional.absent();
		}
	}

	protected void testBuilder(final List<String> sourceFiles,
	        final String templateFile, final ResultInspector resultInspector)
	        throws IOException {
		for (final String sourceFile : sourceFiles) {
			testSourceFile(templateFile, resultInspector, sourceFile);
		}
	}

	public void testImplemented(final String name) throws IOException {
		final List<String> sourceFiles = getAllFiles(SRC_TEST_RESOURCES
		        + "/se/byggarmonster/test/" + name, SRC_JAVA);
		assertTrue("There should be at least one test for the builder, " + name
		        + ".", sourceFiles.size() > 0);
		testBuilder(sourceFiles, SRC_TEST_RESOURCES + "/" + name + ".txt",
		        implementedResultInspector);
	}

	@Test
	public void testSimple() throws IOException {
		testTemplate("simple");
	}

	private void testSourceFile(final String templateFile,
	        final ResultInspector resultInspector, final String sourceFile) {
		System.out.println("Testing " + sourceFile);
		final String assertedFile = sourceFile.substring(0, sourceFile.length()
		        - SRC_JAVA.length())
		        + ASSERTED_JAVA;
		final Optional<String> asserted = getContentIfExists(assertedFile);
		if (asserted.isPresent())
			resultInspector.inspect(sourceFile, asserted.get(),
			        usingMain(sourceFile, templateFile));
		else
			fail("Could nor read " + assertedFile);
	}

	private void testTemplate(final String string) throws IOException {
		testImplemented("simple");
		testUnImplemented("simple");
	}

	public void testUnImplemented(final String name) {
		try {
			testBuilder(
			        getAllFiles(SRC_TEST_RESOURCES
			                + "/se/byggarmonster/test/unimplemented/" + name,
			                SRC_JAVA),
			        SRC_TEST_RESOURCES + "/" + name + ".txt",
			        notImplementedInspector);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private String usingMain(final String source, final String template) {
		return Main.doMain(("java " + Main.PARAM_SOURCE + " " + source + " "
		        + Main.PARAM_TEMPLATE + " " + template).split(" "));
	}
}
