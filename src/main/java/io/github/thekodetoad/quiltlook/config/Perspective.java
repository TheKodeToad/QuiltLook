package io.github.thekodetoad.quiltlook.config;

import dev.isxander.yacl.api.NameableEnum;
import net.minecraft.text.Text;

// used because Minecraft's perspective enum may have different names on different versions,
// or even different names on different mappings depending on the obfuscator settings.
public enum Perspective implements NameableEnum {
	NONE(null), FIRST_PERSON(net.minecraft.client.option.Perspective.FIRST_PERSON),
	THIRD_PERSON_BACK(net.minecraft.client.option.Perspective.THIRD_PERSON_BACK),
	THIRD_PERSON_FRONT(net.minecraft.client.option.Perspective.THIRD_PERSON_FRONT);

	private final net.minecraft.client.option.Perspective gameOption;

	Perspective(net.minecraft.client.option.Perspective gameOption) {
		this.gameOption = gameOption;
	}

	@Override
	public Text getDisplayName() {
		return Text.translatable("quiltlook.config.perspective." + name());
	}

	public net.minecraft.client.option.Perspective getGameOption() {
		return gameOption;
	}

}
