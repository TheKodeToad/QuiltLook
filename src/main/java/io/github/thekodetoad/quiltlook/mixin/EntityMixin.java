package io.github.thekodetoad.quiltlook.mixin;

import io.github.thekodetoad.quiltlook.QuiltLookMod;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {

	@Inject(method = "changeLookDirection", at = @At("HEAD"), cancellable = true, remap = false)
	public void interceptMovement(double cursorDeltaX, double cursorDeltaY, CallbackInfo callback) {
		if(QuiltLookMod.getInstance().isActive()) {
			QuiltLookMod.getInstance().onMouseMovement((float) cursorDeltaX, (float) cursorDeltaY);
			callback.cancel();
		}
	}

}
