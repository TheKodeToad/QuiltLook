package io.github.thekodetoad.quiltlook;

import io.github.thekodetoad.quiltlook.config.ActivationMode;
import net.minecraft.client.option.KeyBind;
import net.minecraft.client.option.StickyKeyBind;

import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

import static com.mojang.blaze3d.platform.InputUtil.*;
import static net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper.registerKeyBinding;

public class ModKeys {

	private static final String KEY_CATEGORY = "quiltlook.keys.title";

	public static final KeyBind ACTIVATE = new StickyKeyBind("quiltlook.keys.activate", KEY_V_CODE, KEY_CATEGORY,
			() -> QuiltLookMod.getInstance().getConfig().getActivationMode() == ActivationMode.TOGGLE);

	public static void register() {
		registerKeyBinding(ACTIVATE);
	}

}
