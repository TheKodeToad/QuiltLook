package io.github.thekodetoad.quiltlook;

import net.minecraft.client.MinecraftClient;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;

public class ModEvents {

	public static void register() {
		ClientTickEvents.START.register(ModEvents::onTick);
	}

	private static void onTick(MinecraftClient client) {
		QuiltLookMod.getInstance().setActive(ModKeys.ACTIVATE.isPressed());
	}

}
