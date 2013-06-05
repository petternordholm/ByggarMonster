package se.byggarmonster.lib;

public class ByggarMonsterAPI {
	private final String builder;
	private final String source;

	public ByggarMonsterAPI(final String source, final String builder) {
		this.source = source;
		this.builder = builder;
	}

	public String getBuilder() {
		return builder;
	}

	public String getSource() {
		return source;
	}

	@Override
	public String toString() {
		return "not implemented!";
	}
}
