package se.byggarmonster.lib;

public class ByggarMonsterAPIBuilder {
	private String builder;
	private String source;

	public ByggarMonsterAPI build() {
		return new ByggarMonsterAPI(source, builder);
	}

	public ByggarMonsterAPIBuilder withBuilder(final String builder) {
		this.builder = builder;
		return this;
	}

	public ByggarMonsterAPIBuilder withSource(final String source) {
		this.source = source;
		return this;
	}
}
