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
	@Expose
	private boolean invertY;
	@Expose
	private boolean invertX;
	@Expose
	private boolean modifySensitivity;
	@Expose
	private float sensitivity = 1;
	@Expose
	private XRestrictionMode xRestrictionMode = XRestrictionMode.IN_FIRST_PERSON;
	@Expose
	private int xRestriction = 200;
	@Expose
	private boolean restrictY = true;
	private boolean reverseView;

	private File file;

	private ModConfig() {}

	private ModConfig(File file) {
		this.file = file;
	}

	public static ModConfig load(File file) {
		ModConfig config;

		try {
			config = GSON.fromJson(FileUtils.readFileToString(file, StandardCharsets.UTF_8), ModConfig.class);
			config.file = file;
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

	public boolean getInvertX() {
		return invertX;
	}

	public void setInvertX(boolean invertX) {
		this.invertX = invertX;
	}

	public boolean getInvertY() {
		return invertY;
	}

	public void setInvertY(boolean invertY) {
		this.invertY = invertY;
	}

	public boolean getModifySensitivity() {
		return modifySensitivity;
	}

	public void setModifySensitivity(boolean modifySensitivity) {
		this.modifySensitivity = modifySensitivity;
	}

	public float getSensitivity() {
		return sensitivity;
	}

	public void setSensitivity(float sensitivity) {
		this.sensitivity = sensitivity;
	}

	public XRestrictionMode getXRestrictionMode() {
		return xRestrictionMode;
	}

	public void setXRestrictionMode(XRestrictionMode mode) {
		xRestrictionMode = mode;
	}

	public int getXRestriction() {
		return xRestriction;
	}

	public void setXRestriction(int xRestriction) {
		this.xRestriction = xRestriction;
	}

	public boolean getRestrictY() {
		return restrictY;
	}

	public void setRestrictY(boolean restrictY) {
		this.restrictY = restrictY;
	}

	public boolean getReverseView() {
		return reverseView;
	}

	public void setReverseView(boolean reverseView) {
		this.reverseView = reverseView;
	}

	// used for SpruceDoubleOption

	public double getSensitivityDouble() {
		return sensitivity;
	}

	public void setSensitivityDouble(double sensitivity) {
		this.sensitivity = (float) sensitivity;
	}

	public double getXRestrictionDouble() {
		return xRestriction;
	}

	public void setXRestrictionDouble(double xRestriction) {
		this.xRestriction = (int) xRestriction;
	}

	public void save() throws IOException {
		// ignore the result because it will cause another error anyway.
		file.getParentFile().mkdirs();
		FileUtils.writeStringToFile(file, GSON.toJson(this),
				StandardCharsets.UTF_8);
	}

}
