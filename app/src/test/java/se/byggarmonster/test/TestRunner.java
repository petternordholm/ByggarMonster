package se.byggarmonster.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import se.byggarmonster.lib.ByggarMonsterAPIBuilder;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class TestRunner {
	private interface ResultInspector {
		public void inspect(String sourceFile, String asserted, String actual);
	}

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

	public void testImplemented(final String name) throws IOException {
		final List<String> sourceFiles = getAllFiles(SRC_TEST_RESOURCES
		        + "/se/byggarmonster/test/" + name, SRC_JAVA);
		assertTrue("There should be at least one test for the builder, " + name
		        + ".", sourceFiles.size() > 0);
		testBuilder(sourceFiles, SRC_TEST_RESOURCES + "/" + name + ".txt",
		        new ResultInspector() {
			        @Override
			        public void inspect(final String sourceFile,
			                final String asserted, final String actual) {
				        assertEquals(sourceFile, asserted, actual);
			        }
		        });
	}

	@Test
	public void testSimple() throws IOException {
		testTemplate("simple");
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
			        new ResultInspector() {
				        @Override
				        public void inspect(final String sourceFile,
				                final String asserted, final String actual) {
					        assertNotEquals(
					                sourceFile
					                        + "Is implemented, move it to implemented test suite.",
					                asserted, actual);
					        try {
						        assertEquals(asserted, actual);
					        } catch (final AssertionError e) {
						        System.err.println(e.toString());
					        } catch (final Exception e) {
						        e.printStackTrace();
					        }
				        }
			        });
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
