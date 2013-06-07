package se.byggarmonster.lib.impl;

import java.util.List;
import java.util.Map;

public class ClassData {
	private final String className;
	private final List<NameTypePair> constructorParameters;
	private final Map<String, String> getterMapping;
	private final Map<String, String> memberMapping;
	private final List<NameTypePair> members;
	private final String packageName;
	private final Map<String, String> setterMapping;

	public ClassData(final String className, final String packageName,
	        final List<NameTypePair> constructorParameters,
	        final List<NameTypePair> members,
	        final Map<String, String> memberMapping,
	        final Map<String, String> setterMapping,
	        final Map<String, String> getterMapping) {
		this.className = className;
		this.packageName = packageName;
		this.constructorParameters = constructorParameters;
		this.members = members;
		this.memberMapping = memberMapping;
		this.setterMapping = setterMapping;
		this.getterMapping = getterMapping;
	}

	public String getClassName() {
		return className;
	}

	public List<NameTypePair> getConstructorParameters() {
		return constructorParameters;
	}

	public Map<String, String> getGetterMapping() {
		return getterMapping;
	}

	public Map<String, String> getMemberMapping() {
		return memberMapping;
	}

	public List<NameTypePair> getMembers() {
		return members;
	}

	public String getPackageName() {
		return packageName;
	}

	public Map<String, String> getSetterMapping() {
		return setterMapping;
	}
}
