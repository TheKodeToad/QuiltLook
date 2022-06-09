package io.github.thekodetoad.quiltlook.utils;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

public class Utils {

	public static @NotNull Entity safeCameraEntity() {
		return MinecraftClient.getInstance().cameraEntity == null ? MinecraftClient.getInstance().player
				: MinecraftClient.getInstance().cameraEntity;
	}

	public static <T extends Enum<T>> Consumer<Integer> cycleConsumer(Supplier<T> get, Consumer<T> set, T[] values) {
		return (amount) -> {
			int ordinal = get.get().ordinal() + amount;

			if(ordinal >= values.length) {
				ordinal = 0;
			}

			set.accept(values[ordinal]);
		};
	}
}
