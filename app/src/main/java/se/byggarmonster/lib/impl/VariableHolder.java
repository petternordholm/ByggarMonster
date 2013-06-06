package se.byggarmonster.lib.impl;

public class VariableHolder {
	private final String name;
	private final String type;

	public VariableHolder(final String name, final String type) {
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
