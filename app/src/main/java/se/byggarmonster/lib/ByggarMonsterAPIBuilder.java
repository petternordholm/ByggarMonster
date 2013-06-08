package se.byggarmonster.lib;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class ByggarMonsterAPIBuilder {
	private String source;
	private String template;

	private File checkFileExists(final String filePath) {
		final File file = new File(filePath);
		checkState(file.exists(), file.getAbsolutePath() + " does not exist.");
		return file;
	}

	private String content(final File file) {
		try {
			return Files.toString(file, Charsets.UTF_8);
		} catch (final IOException e) {
			throw new RuntimeException("Can not read file "
			        + file.getAbsoluteFile());
		}
	}

	public ByggarMonsterAPIBuilder toFile(final String path) {
		try {
			Files.write(toString().getBytes(), new File(path));
		} catch (final IOException e) {
			System.err.println("Could not write to "
			        + new File(path).getAbsoluteFile());
		}
		return this;
	}

	@Override
	public String toString() {
		return new ByggarMonsterAPI(source, template).toString();
	}

	public ByggarMonsterAPIBuilder withSourceContent(final String source) {
		checkNotNull(source, "Parameter can not be null.");
		this.source = source;
		return this;
	}

	public ByggarMonsterAPIBuilder withSourceFile(final String path) {
		return withSourceContent(content(checkFileExists(path)));
	}

	public ByggarMonsterAPIBuilder withTemplateContent(final String template) {
		checkNotNull(template, "Parameter can not be null.");
		this.template = template;
		return this;
	}

	public ByggarMonsterAPIBuilder withTemplateFile(final String path) {
		return withTemplateContent(content(checkFileExists(path)));
	}
}
