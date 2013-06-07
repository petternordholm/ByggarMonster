package se.byggarmonster.test;

import java.io.IOException;

import org.junit.Test;

/**
 * These tests may fail. These tests will only test features that are not yet
 * implemented. Testing unimplemented features enables an easy way of
 * communicating features that are to be implemented. Consider these tests as
 * TODO:s.
 */
public class TestNotImplemented extends TestBase {
	@Test
	public void testUnimplementedSimple() throws IOException {
		testBuilder(
		        getAllFiles(SRC_TEST_RESOURCES
		                + "/se/byggarmonster/test/unimplemented/simple",
		                SRC_JAVA), SRC_TEST_RESOURCES + "/simple.txt");
	}
}
