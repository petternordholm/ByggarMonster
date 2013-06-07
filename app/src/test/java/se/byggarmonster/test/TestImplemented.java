package se.byggarmonster.test;

import java.io.IOException;

import org.junit.Test;

public class TestImplemented extends TestBase {
	@Test
	public void testImplementedSimple() throws IOException {
		testBuilder(
		        getAllFiles(SRC_TEST_RESOURCES
		                + "/se/byggarmonster/test/simple", SRC_JAVA),
		        SRC_TEST_RESOURCES + "/simple.txt");
	}
}
