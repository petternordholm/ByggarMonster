package se.byggarmonster.lib.impl.simple;

import se.byggarmonster.lib.impl.BuilderPatternGenerator;

public class SimpleBuilder extends BuilderPatternGenerator {
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("package " + getPackageName() + ";\n");
		sb.append("className " + getClassName() + "\n");

		sb.append("Constructor parameters:\n");
		for (final String name : getConstructorParameters().keySet())
			sb.append(" " + name + "(" + getConstructorParameters().get(name)
					+ ")");

		sb.append("\nAttributes:\n");
		for (final String name : getMemberAttributes().keySet())
			sb.append(" " + name + "(" + getMemberAttributes().get(name) + ")");

		return sb.toString();
	}
}
