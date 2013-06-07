package se.byggarmonster.lib.impl.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodMapping {
	/**
	 * Method => attribute
	 */
	private final Map<String, String> mapping = new HashMap<String, String>();

	public String getAttribute(final String method) {
		return mapping.get(method);
	}

	public List<String> getMethods() {
		return new ArrayList<String>(mapping.keySet());
	}

	public void include(final String method, final String attribute) {
		mapping.put(method, attribute);
	}
}
