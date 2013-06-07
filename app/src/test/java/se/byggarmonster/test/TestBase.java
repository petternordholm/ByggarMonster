package se.byggarmonster.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import se.byggarmonster.lib.ByggarMonsterAPIBuilder;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class TestBase {
	public static final String ASSERTED_JAVA = "SrcBuilder.java";
	public static final String SRC_JAVA = "Src.java";
	public static final String SRC_TEST_RESOURCES = "src/resources/templates";

	public List<String> getAllFiles(final String folder, final String ending) {
		final ArrayList<String> files = new ArrayList<String>();
		final File[] faFiles = new File(folder).listFiles();
		for (final File file : faFiles) {
			if (file.getName().endsWith(ending)) {
				files.add(file.getAbsolutePath());
			}
			if (file.isDirectory()) {
				files.addAll(getAllFiles(file.getAbsolutePath(), ending));
			}
		}
		return files;
	}

	protected void testBuilder(final List<String> sourceFiles,
	        final String template, final ResultInspector resultInspector)
	        throws IOException {
		assertTrue("There should be at least one test for the builder, "
		        + template + ".", sourceFiles.size() > 0);
		for (final String sourceFile : sourceFiles) {
			System.out.println("Testing " + sourceFile);
			final String assertedFile = sourceFile.substring(0,
			        sourceFile.length() - SRC_JAVA.length())
			        + ASSERTED_JAVA;
			final String source = Files.toString(new File(sourceFile),
			        Charsets.UTF_8);
			final String asserted = Files.toString(new File(assertedFile),
			        Charsets.UTF_8);
			final String actual = new ByggarMonsterAPIBuilder() //
			        .withSource(source) //
			        .withTemplate(template) //
			        .build() //
			        .toString();
			resultInspector.inspect(sourceFile, asserted, actual);
		}
	}

}
