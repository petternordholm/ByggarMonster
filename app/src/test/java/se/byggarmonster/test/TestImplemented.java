package se.byggarmonster.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class TestImplemented extends TestBase {
	@Test
	public void testImplementedSimple() throws IOException {
		testBuilder(
		        getAllFiles(SRC_TEST_RESOURCES
		                + "/se/byggarmonster/test/simple", SRC_JAVA),
		        SRC_TEST_RESOURCES + "/simple.txt", new ResultInspector() {
			        @Override
			        public void inspect(final String sourceFile,
			                final String asserted, final String actual) {
				        assertEquals(sourceFile, asserted, actual);
			        }
		        });
	}
}
