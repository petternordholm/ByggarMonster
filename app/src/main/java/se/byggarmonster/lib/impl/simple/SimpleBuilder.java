package se.byggarmonster.lib.impl.simple;

import org.antlr.v4.runtime.tree.ParseTree;

import se.byggarmonster.lib.impl.Builder;

public class SimpleBuilder implements Builder {
	private final StringBuilder sb;

	public SimpleBuilder() {
		sb = new StringBuilder();
	}

	@Override
	public String toString() {
		return sb.toString();
	}

	@Override
	public void visit(final ParseTree parseTree) {
		sb.append(parseTree.getText() + "\n");
	}
}
