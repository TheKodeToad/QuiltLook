package io.github.thekodetoad.quiltlook;

import io.github.thekodetoad.quiltlook.config.QuiltLookConfiguration;
import io.github.thekodetoad.quiltlook.utils.Utils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class QuiltLookMod implements ClientModInitializer {

	private static QuiltLookMod instance;

	@NotNull
	private final MinecraftClient client = MinecraftClient.getInstance();
	private QuiltLookConfiguration config = new QuiltLookConfiguration();

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

	public QuiltLookConfiguration getConfig() {
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
				initialPerspective = client.options.getPerspective();
				client.options.setPerspective(config.getPerspective().getGameOption());
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
