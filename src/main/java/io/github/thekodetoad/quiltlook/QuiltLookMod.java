package io.github.thekodetoad.quiltlook;

import io.github.thekodetoad.quiltlook.config.ModConfig;
import io.github.thekodetoad.quiltlook.utils.Utils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import java.io.File;

public class QuiltLookMod implements ClientModInitializer {

	public static final Logger LOGGER = LogManager.getLogger();
	private static final float DEFAULT_MULIPLIER = 0.15F;

	private static QuiltLookMod instance;

	@NotNull
	private final MinecraftClient client = MinecraftClient.getInstance();
	private ModConfig config = ModConfig.load(new File(client.runDirectory, "config/quiltlook.json"));

	/** the activation state */
	private boolean active;
	/** freelook yaw */
	private float yaw;
	/** freelook pitch */
	private float pitch;
	/** perspective before activated */
	private Perspective initialPerspective;

	@Override
	public void onInitializeClient(ModContainer mod) {
		instance = this;
		ModKeys.register();
		ModEvents.register();
	}

	public ModConfig getConfig() {
		return config;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		if(this.active != active) {
			this.active = active;
			if(active) {
				yaw = Utils.safeCameraEntity().getYaw();
				pitch = Utils.safeCameraEntity().getPitch();

				if(config.getReverseView()) {
					yaw += 180;
					pitch = -pitch;
				}

				if(config.getPerspective().getGameOption() != null) {
					initialPerspective = client.options.getPerspective();
					client.options.setPerspective(config.getPerspective().getGameOption());
				}
			}
			else {
				yaw = pitch = 0;

				if(initialPerspective != null) {
					client.options.setPerspective(initialPerspective);
					initialPerspective = null;
				}
			}
		}
	}

	public float getYaw() {
		// continuously clamp the yaw
		yaw = clampYaw(yaw);
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void onMouseMovement(float x, float y) {
		x *= DEFAULT_MULIPLIER;
		y *= DEFAULT_MULIPLIER;

		if(config.getInvertX()) {
			x = -x;
		}
		if(config.getInvertY()) {
			y = -y;
		}

		if(config.getModifySensitivity()) {
			x *= config.getSensitivity();
			y *= config.getSensitivity();
		}

		this.yaw = clampYaw(yaw + x);
		this.pitch = clampPitch(pitch + y);
	}

	private float clampYaw(float yaw) {
		if(config.getXRestrictionMode().shouldRestrict()) {
			float startingYaw = Utils.safeCameraEntity().getYaw();
			float restriction = config.getXRestriction();
			float min = startingYaw - (restriction / 2);
			float max = startingYaw + (restriction / 2);

			if(yaw < min) {
				return min;
			}
			else if(yaw > max) {
				return max;
			}
		}

		return yaw;
	}

	private float clampPitch(float pitch) {
		if(config.getRestrictY()) {
			if(pitch > 90) {
				return 90;
			}
			else if(pitch < -90) {
				return -90;
			}
		}

		return pitch;
	}

	public static QuiltLookMod getInstance() {
		return instance;
	}

}
