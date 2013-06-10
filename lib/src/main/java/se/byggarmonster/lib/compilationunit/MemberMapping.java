package se.byggarmonster.lib.compilationunit;

import java.util.HashMap;
import java.util.Map;

public class MemberMapping {
	/**
	 * Constructor parameter => member attribute
	 */
	private final Map<String, String> memberMapping = new HashMap<String, String>();

	public String getAttribute(final String parameter) {
		return memberMapping.get(parameter);
	}

	public void include(final Map<String, String> mappings) {
		memberMapping.putAll(mappings);
	}
}
