package io.github.thekodetoad.quiltlook.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;

public enum XRestrictionMode {
	NEVER,
	IN_FIRST_PERSON,
	ALWAYS;

	public String getName() {
		return I18n.translate("quiltlook.config.restrict_x." + name());
	}

	public boolean shouldRestrict() {
		return this == ALWAYS || (this == IN_FIRST_PERSON && MinecraftClient.getInstance().options
				.getPerspective() == net.minecraft.client.option.Perspective.FIRST_PERSON);
	}

}
