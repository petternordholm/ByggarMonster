package se.byggarmonster.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import se.byggarmonster.lib.ByggarMonsterAPIBuilder;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 * Will create builders for all data types found in src/test/resources.
 */
public class TestRunner {
	private static final String ASSERTED_JAVA = "Asserted.java";
	private static final String SRC_JAVA = "Src.java";
	private static final String SRC_TEST_RESOURCES = "src/resources";

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

	private void testBuilder(final String name) throws IOException {
		final List<String> sourceFiles = getAllFiles(SRC_TEST_RESOURCES + "/"
				+ name, SRC_JAVA);
		assertTrue("There should be at least one test for the builder, " + name
				+ ".", sourceFiles.size() > 0);
		for (final String sourceFile : sourceFiles) {
			final String assertedFile = sourceFile.substring(0,
					sourceFile.length() - SRC_JAVA.length())
					+ ASSERTED_JAVA;
			final String source = Files.toString(new File(sourceFile),
					Charsets.UTF_8);
			final String asserted = Files.toString(new File(assertedFile),
					Charsets.UTF_8);
			final String actual = new ByggarMonsterAPIBuilder() //
					.withSource(source) //
					.withBuilder(name) //
					.build() //
					.toString();
			System.out.println("Testing " + source + " " + asserted);
			assertEquals(asserted, actual);
		}
	}

	@Test
	public void testThatBuildersAreGeneratedFromResourcesFolder()
			throws IOException {
		testBuilder("simple");
	}
}
