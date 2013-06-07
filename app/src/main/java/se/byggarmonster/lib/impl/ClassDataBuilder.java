package se.byggarmonster.lib.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import se.byggarmonster.lib.impl.data.MemberMapping;

public class ClassDataBuilder {
	private String className;

	/**
	 * Name => Type
	 */
	private final LinkedList<NameTypePair> constructorParameters;
	private final Map<String, String> getterMapping;
	private final MemberMapping memberMapping;
	/**
	 * Name => Type
	 */
	private final List<NameTypePair> members;

	private String packageName;

	/**
	 * Setter => type
	 */
	private final Map<String, String> setterMapping;

	public ClassDataBuilder() {
		constructorParameters = new LinkedList<NameTypePair>();
		members = new ArrayList<NameTypePair>();
		memberMapping = new MemberMapping();
		setterMapping = new HashMap<String, String>();
		getterMapping = new HashMap<String, String>();
	}

	public ClassData build() {
		return new ClassData(className, packageName, constructorParameters,
		        members, memberMapping, setterMapping, getterMapping);
	}

	public ClassDataBuilder withClassName(final String text) {
		this.className = text;
		return this;
	}

	public ClassDataBuilder withConstructorParameter(
	        final NameTypePair nameTypePair) {
		constructorParameters.addFirst(nameTypePair);
		return this;
	}

	public ClassDataBuilder withGetterMapping(final String text,
	        final String string) {
		getterMapping.put(text, string);
		return this;
	}

	public ClassDataBuilder withMember(final NameTypePair nameTypePair) {
		this.members.add(nameTypePair);
		return this;
	}

	public ClassDataBuilder withMemberMappings(
	        final Map<String, String> foundMappings) {
		memberMapping.include(foundMappings);
		return this;
	}

	public ClassDataBuilder withPackageName(final String text) {
		this.packageName = text;
		return this;
	}

	public ClassDataBuilder withSetterMapping(final String text,
	        final String name) {
		setterMapping.put(text, name);
		return this;
	}
}
