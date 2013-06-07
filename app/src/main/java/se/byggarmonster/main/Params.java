package se.byggarmonster.main;

import java.util.Map;

import com.google.common.base.Optional;

public class Params {
	private final Map<String, String> map;

	public Params(final Map<String, String> map) {
		this.map = map;
	}

	public Optional<String> get(final String key) {
		return Optional.fromNullable(map.get(key));
	}
}
