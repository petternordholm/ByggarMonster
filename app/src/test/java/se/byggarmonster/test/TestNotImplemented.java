package se.byggarmonster.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;

import org.junit.Test;

/**
 * These tests will only test features that are not yet implemented. Testing
 * unimplemented features enables an easy way of communicating features that are
 * to be implemented. Consider these tests as TODO:s.
 */
public class TestNotImplemented extends TestBase {
	@Test
	public void testUnimplementedSimple() throws IOException {
		try {
			testBuilder(
			        getAllFiles(SRC_TEST_RESOURCES
			                + "/se/byggarmonster/test/unimplemented/simple",
			                SRC_JAVA), SRC_TEST_RESOURCES + "/simple.txt",
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
