package io.github.thekodetoad.quiltlook.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import io.github.thekodetoad.quiltlook.QuiltLookMod;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ModConfig {

	private static final Gson GSON = new GsonBuilder()
			.excludeFieldsWithoutExposeAnnotation()
			.setPrettyPrinting()
			.create();

	@Expose
	private Perspective perspective = Perspective.THIRD_PERSON_BACK;
	@Expose
	private ActivationMode activationMode = ActivationMode.HOLD;
	private final File file;

	private ModConfig(File file) {
		this.file = file;
	}

	public static ModConfig load(File file) {
		ModConfig config;

		try {
			config = GSON.fromJson(FileUtils.readFileToString(file, StandardCharsets.UTF_8), ModConfig.class);
		}
		catch(IOException error) {
			config = new ModConfig(file);

			if(file.exists()) {
				QuiltLookMod.LOGGER.error("Could not load config", error);
			}
			else {
				try {
					config.save();
				}
				catch(IOException saveError) {
					QuiltLookMod.LOGGER.error("Could not create config", saveError);
				}
			}
		}

		return config;
	}

	public Perspective getPerspective() {
		return perspective;
	}

	public void setPerspective(Perspective perspective) {
		this.perspective = perspective;
	}

	public ActivationMode getActivationMode() {
		return activationMode;
	}

	public void setActivationMode(ActivationMode activationMode) {
		this.activationMode = activationMode;
	}

	public void save() throws IOException {
		file.getParentFile().mkdirs();
		FileUtils.writeStringToFile(file, GSON.toJson(this),
				StandardCharsets.UTF_8);
	}

}
