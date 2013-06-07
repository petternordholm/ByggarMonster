package se.byggarmonster.test;

public interface ResultInspector {
	public void inspect(String sourceFile, String asserted, String actual);
}
