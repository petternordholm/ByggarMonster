package se.byggarmonster.test.simple;

import java.util.List;

public class UsingImportedTypeInAttribute {
	private final List<String> type;

	public UsingImportedTypeInAttribute(final List<String> type) {
		this.type = type;
	}

	public List<String> getType() {
		return type;
	}
}
