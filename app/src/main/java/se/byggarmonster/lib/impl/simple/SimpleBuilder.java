package se.byggarmonster.lib.impl.simple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import se.byggarmonster.lib.impl.BuilderPatternGenerator;
import se.byggarmonster.lib.impl.TemplateHelper;

public class SimpleBuilder extends BuilderPatternGenerator {
	@Override
	public String toString() {
		final Map<String, Object> context = new HashMap<String, Object>();
		context.put("packageName", getPackageName());
		context.put("className", getClassName());
		final ArrayList<Map<String, Object>> members = new ArrayList<Map<String, Object>>();
		for (final String member : getMemberAttributes().keySet()) {
			final Map<String, Object> memberAttributesMap = new HashMap<String, Object>();
			memberAttributesMap.put("name", member);
			memberAttributesMap.put("type", getMemberAttributes().get(member));
			members.add(memberAttributesMap);
		}

		context.put("members", members);
		return TemplateHelper.render("src/resources/simple/template.txt",
		        context);
	}
}
