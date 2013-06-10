package se.byggarmonster.lib.impl.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ClassDataBuilder {
	private String className;
	private final MemberMapping constructorMemberMapping;
	private final LinkedList<NameTypePair> constructorParameters;
	private final MethodMapping getterMapping;
	private final List<String> imports;
	private final List<NameTypePair> members;
	private String packageName;
	private final MethodMapping setterMapping;

	public ClassDataBuilder() {
		constructorParameters = new LinkedList<NameTypePair>();
		members = new ArrayList<NameTypePair>();
		constructorMemberMapping = new MemberMapping();
		setterMapping = new MethodMapping();
		getterMapping = new MethodMapping();
		imports = new ArrayList<String>();
	}

	public ClassData build() {
		return new ClassData(className, packageName, constructorParameters,
		        members, constructorMemberMapping, setterMapping,
		        getterMapping, imports);
	}

	public ClassDataBuilder withClassName(final String text) {
		this.className = text;
		return this;
	}

	public ClassDataBuilder withConstructorMemberMappings(
	        final Map<String, String> foundMappings) {
		constructorMemberMapping.include(foundMappings);
		return this;
	}

	public ClassDataBuilder withConstructorParameter(
	        final NameTypePair nameTypePair) {
		constructorParameters.addFirst(nameTypePair);
		return this;
	}

	public ClassDataBuilder withGetterMapping(final String text,
	        final String string) {
		getterMapping.include(text, string);
		return this;
	}

	public void withImport(final String text) {
		imports.add(text);
	}

	public ClassDataBuilder withMember(final NameTypePair nameTypePair) {
		this.members.add(nameTypePair);
		return this;
	}

	public ClassDataBuilder withPackageName(final String text) {
		this.packageName = text;
		return this;
	}

	public ClassDataBuilder withSetterMapping(final String text,
	        final String name) {
		setterMapping.include(text, name);
		return this;
	}
}
