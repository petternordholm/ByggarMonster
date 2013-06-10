package se.byggarmonster.lib.impl.data;

public class NameTypePair {
	private final String name;
	private final String type;

	public NameTypePair(final String name, final String type) {
		this.type = type;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}
}
