package se.byggarmonster.lib.compilationunit;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CompilationUnitBuilder {
	private String className;
	private final MemberMapping constructorMemberMapping;
	private final LinkedList<NameTypePair> constructorParameters;
	private final MethodMapping getterMapping;
	private final List<String> imports;
	private final List<NameTypePair> members;
	private String packageName;
	private final MethodMapping setterMapping;

	public CompilationUnitBuilder() {
		constructorParameters = new LinkedList<NameTypePair>();
		members = new ArrayList<NameTypePair>();
		constructorMemberMapping = new MemberMapping();
		setterMapping = new MethodMapping();
		getterMapping = new MethodMapping();
		imports = new ArrayList<String>();
	}

	public CompilationUnit build() {
		return new CompilationUnit(className, packageName,
		        constructorParameters, members, constructorMemberMapping,
		        setterMapping, getterMapping, imports);
	}

	public CompilationUnitBuilder withClassName(final String text) {
		this.className = text;
		return this;
	}

	public CompilationUnitBuilder withConstructorMemberMappings(
	        final Map<String, String> foundMappings) {
		constructorMemberMapping.include(foundMappings);
		return this;
	}

	public CompilationUnitBuilder withConstructorParameter(
	        final NameTypePair nameTypePair) {
		constructorParameters.addFirst(nameTypePair);
		return this;
	}

	public CompilationUnitBuilder withGetterMapping(final String text,
	        final String string) {
		getterMapping.include(text, string);
		return this;
	}

	public CompilationUnitBuilder withImport(final String text) {
		imports.add(text);
		return this;
	}

	public CompilationUnitBuilder withMember(final NameTypePair nameTypePair) {
		this.members.add(nameTypePair);
		return this;
	}

	public CompilationUnitBuilder withPackageName(final String text) {
		this.packageName = text;
		return this;
	}

	public CompilationUnitBuilder withSetterMapping(final String text,
	        final String name) {
		setterMapping.include(text, name);
		return this;
	}
}
