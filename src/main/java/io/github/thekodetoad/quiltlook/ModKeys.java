package io.github.thekodetoad.quiltlook;

import static com.mojang.blaze3d.platform.InputUtil.KEY_V_CODE;
import static net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper.registerKeyBinding;

import io.github.thekodetoad.quiltlook.config.ActivationMode;
import net.minecraft.client.option.KeyBind;
import net.minecraft.client.option.StickyKeyBind;

public class ModKeys {

	private static final String KEY_BASE = "quiltlook.keys.";
	private static final String KEY_CATEGORY = KEY_BASE + "title";

	public static final KeyBind ACTIVATE = new StickyKeyBind(KEY_BASE + "activate", KEY_V_CODE, KEY_CATEGORY,
			() -> QuiltLookMod.getInstance().getConfig().getActivationMode() == ActivationMode.TOGGLE);

	public static void register() {
		registerKeyBinding(ACTIVATE);
	}

}
