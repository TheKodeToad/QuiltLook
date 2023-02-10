package io.github.thekodetoad.quiltlook.utils;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

public class Utils {

	@SuppressWarnings("resource")
	public static @NotNull Entity safeCameraEntity() {
		return MinecraftClient.getInstance().cameraEntity == null ? MinecraftClient.getInstance().player
				: MinecraftClient.getInstance().cameraEntity;
	}

}
