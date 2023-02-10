package io.github.thekodetoad.quiltlook.config;

import dev.isxander.yacl.api.NameableEnum;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public enum XRestrictionMode implements NameableEnum {
	NEVER, IN_FIRST_PERSON, ALWAYS;

	@Override
	public Text getDisplayName() {
		return Text.translatable("quiltlook.config.restrict_x." + name());
	}

	public boolean shouldRestrict() {
		return this == ALWAYS || (this == IN_FIRST_PERSON && MinecraftClient.getInstance().options
				.getPerspective() == net.minecraft.client.option.Perspective.FIRST_PERSON);
	}

}
