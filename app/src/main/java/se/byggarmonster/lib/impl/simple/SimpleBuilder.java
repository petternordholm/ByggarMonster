package se.byggarmonster.lib.impl.simple;

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
		context.put("members", getMemberAttributes());
		return TemplateHelper.render("src/resources/simple/template.txt",
		        context);
	}
}
