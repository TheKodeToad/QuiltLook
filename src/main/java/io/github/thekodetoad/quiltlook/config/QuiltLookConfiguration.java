package io.github.thekodetoad.quiltlook.config;

import com.google.gson.Gson;

import java.io.File;

public class QuiltLookConfiguration {

	private Perspective perspective = Perspective.THIRD_PERSON;
	private ActivationMode activationMode;

	public Perspective getPerspective() {
		return perspective;
	}

	public ActivationMode getActivationMode() {
		return activationMode;
	}

}
