package se.byggarmonster.lib;

public class ByggarMonsterAPIBuilder {
	private String source;
	private String template;

	public ByggarMonsterAPI build() {
		return new ByggarMonsterAPI(source, template);
	}

	public ByggarMonsterAPIBuilder withSource(final String source) {
		this.source = source;
		return this;
	}

	public ByggarMonsterAPIBuilder withTemplate(final String template) {
		this.template = template;
		return this;
	}
}
