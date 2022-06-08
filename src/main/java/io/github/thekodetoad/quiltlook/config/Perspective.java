package io.github.thekodetoad.quiltlook.config;

import net.minecraft.client.resource.language.I18n;

public enum Perspective {
	NONE(null),
	FIRST_PERSON(net.minecraft.client.option.Perspective.FIRST_PERSON),
	THIRD_PERSON_BACK(net.minecraft.client.option.Perspective.THIRD_PERSON_BACK),
	THIRD_PERSON_FRONT(net.minecraft.client.option.Perspective.THIRD_PERSON_FRONT);

	private final net.minecraft.client.option.Perspective gameOption;

	Perspective(net.minecraft.client.option.Perspective gameOption) {
		this.gameOption = gameOption;
	}

	public String getName() {
		return I18n.translate("quiltlook.config.perspective." + name());
	}

	public net.minecraft.client.option.Perspective getGameOption() {
		return gameOption;
	}
}
