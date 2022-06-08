package io.github.thekodetoad.quiltlook.config;

import net.minecraft.client.resource.language.I18n;

public enum ActivationMode {
	HOLD,
	TOGGLE;

	public String getName() {
		return I18n.translate("quiltlook.config.activation_mode." + name());
	}

}
