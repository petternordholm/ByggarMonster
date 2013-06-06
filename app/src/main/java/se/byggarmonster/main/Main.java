package se.byggarmonster.main;

import java.util.HashMap;
import java.util.Map;

import se.byggarmonster.lib.ByggarMonsterAPIBuilder;

public class Main {
	public static void main(final String[] args) {
		final Map<String, String> argsMap = parsArgs(args);
		System.out.println( //
		        new ByggarMonsterAPIBuilder() //
		                .withSource(argsMap.get("source")) //
		                .withTemplate(argsMap.get("template")) //
		                .build() //
		                .toString() //
		        );
	}

	private static Map<String, String> parsArgs(final String[] args) {
		final HashMap<String, String> map = new HashMap<String, String>();
		for (int i = 0; i + 1 < args.length; i += 2) {
			map.put(args[i], args[i + 1]);
		}
		return map;
	}
}
