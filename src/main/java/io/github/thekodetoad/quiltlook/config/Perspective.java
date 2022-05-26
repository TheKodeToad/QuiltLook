package io.github.thekodetoad.quiltlook.config;

import net.minecraft.client.resource.language.I18n;

public enum Perspective {
	FIRST_PERSON(net.minecraft.client.option.Perspective.FIRST_PERSON),
	THIRD_PERSON(net.minecraft.client.option.Perspective.THIRD_PERSON_BACK);

	private final net.minecraft.client.option.Perspective gameOption;

	Perspective(net.minecraft.client.option.Perspective gameOption) {
		this.gameOption = gameOption;
	}

	public String getName() {
		return I18n.translate("quiltlook.perspective." + name());
	}

	public net.minecraft.client.option.Perspective getGameOption() {
		return gameOption;
	}
}
