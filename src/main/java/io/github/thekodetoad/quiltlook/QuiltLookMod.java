package io.github.thekodetoad.quiltlook;

import io.github.thekodetoad.quiltlook.config.ModConfig;
import io.github.thekodetoad.quiltlook.utils.Utils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import java.io.File;

public class QuiltLookMod implements ClientModInitializer {

	public static final Logger LOGGER = LogManager.getLogger();

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
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void onMouseMovement(float x, float y) {
		this.yaw += x * 0.15;
		this.pitch += y * 0.15;
	}

	public static QuiltLookMod getInstance() {
		return instance;
	}

}
