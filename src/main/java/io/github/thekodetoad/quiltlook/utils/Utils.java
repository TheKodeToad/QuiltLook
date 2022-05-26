package io.github.thekodetoad.quiltlook.utils;

import io.github.thekodetoad.quiltlook.QuiltLookMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

import java.util.Optional;

public class Utils {

	public static Entity safeCameraEntity() {
		return MinecraftClient.getInstance().cameraEntity == null ? MinecraftClient.getInstance().player
				: MinecraftClient.getInstance().cameraEntity;
	}

}
