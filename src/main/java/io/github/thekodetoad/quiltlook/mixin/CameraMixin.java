package io.github.thekodetoad.quiltlook.mixin;

import io.github.thekodetoad.quiltlook.QuiltLookMod;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Camera.class)
public class CameraMixin {

	@Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getYaw(F)F"))
	public float getYaw(Entity instance, float tickDelta) {
		if (QuiltLookMod.getInstance().isActive()) {
			return QuiltLookMod.getInstance().getYaw();
		}

		return instance.getYaw(tickDelta);
	}

	@Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getPitch(F)F"))
	public float getPitch(Entity instance, float tickDelta) {
		if (QuiltLookMod.getInstance().isActive()) {
			return QuiltLookMod.getInstance().getPitch();
		}

		return instance.getPitch(tickDelta);
	}

}
