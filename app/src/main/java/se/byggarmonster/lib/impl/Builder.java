package se.byggarmonster.lib.impl;

import org.antlr.v4.runtime.tree.ParseTree;

public interface Builder {
	public void visit(final ParseTree parseTree);
}
