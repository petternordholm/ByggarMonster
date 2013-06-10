package se.byggarmonster.lib.impl.data;

import java.util.List;

public class ClassData {
	private final String className;
	private final MemberMapping constructorMemberMapping;
	private final List<NameTypePair> constructorParameters;
	private final MethodMapping getterMapping;
	private final List<String> imports;
	private final List<NameTypePair> members;
	private final String packageName;
	private final MethodMapping setterMapping;

	public ClassData(final String className, final String packageName,
	        final List<NameTypePair> constructorParameters,
	        final List<NameTypePair> members,
	        final MemberMapping constructorMemberMapping,
	        final MethodMapping setterMapping,
	        final MethodMapping getterMapping, final List<String> imports) {
		this.className = className;
		this.packageName = packageName;
		this.constructorParameters = constructorParameters;
		this.members = members;
		this.constructorMemberMapping = constructorMemberMapping;
		this.setterMapping = setterMapping;
		this.getterMapping = getterMapping;
		this.imports = imports;
	}

	public String getClassName() {
		return className;
	}

	public MemberMapping getConstructorMemberMapping() {
		return constructorMemberMapping;
	}

	public List<NameTypePair> getConstructorParameters() {
		return constructorParameters;
	}

	public MethodMapping getGetterMapping() {
		return getterMapping;
	}

	public List<String> getImports() {
		return imports;
	}

	public List<NameTypePair> getMembers() {
		return members;
	}

	public String getPackageName() {
		return packageName;
	}

	public MethodMapping getSetterMapping() {
		return setterMapping;
	}
}
