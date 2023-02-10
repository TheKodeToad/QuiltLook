package io.github.thekodetoad.quiltlook.config;

import dev.isxander.yacl.api.NameableEnum;
import net.minecraft.text.Text;

public enum ActivationMode implements NameableEnum {
	HOLD, TOGGLE;

	@Override
	public Text getDisplayName() {
		return Text.translatable("quiltlook.config.activation_mode." + name());
	}

}
