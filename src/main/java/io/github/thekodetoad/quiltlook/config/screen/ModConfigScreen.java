package io.github.thekodetoad.quiltlook.config.screen;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.background.EmptyBackground;
import dev.lambdaurora.spruceui.background.SimpleColorBackground;
import dev.lambdaurora.spruceui.option.SpruceCyclingOption;
import dev.lambdaurora.spruceui.option.SpruceOption;
import dev.lambdaurora.spruceui.screen.SpruceScreen;
import dev.lambdaurora.spruceui.widget.container.SpruceOptionListWidget;
import io.github.thekodetoad.quiltlook.QuiltLookMod;
import io.github.thekodetoad.quiltlook.config.ActivationMode;
import io.github.thekodetoad.quiltlook.config.ModConfig;
import io.github.thekodetoad.quiltlook.config.Perspective;
import io.github.thekodetoad.quiltlook.utils.Utils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.awt.*;

public class ModConfigScreen extends SpruceScreen {

	private static String KEY_BASE = "quiltlook.config.";
	private static String TITLE = KEY_BASE + "title";

	private ModConfig config = QuiltLookMod.getInstance().getConfig();
	private SpruceOptionListWidget list;

	private Screen previousScreen;

	protected ModConfigScreen(Screen previous) {
		super(new TranslatableText(TITLE));
		previousScreen = previous;
	}

	@Override
	protected void init() {
		super.init();

		// based around okzoomer
		list = new SpruceOptionListWidget(Position.of(0, 22), width, height - 58);

		list.addOptionEntry(new SpruceCyclingOption(KEY_BASE + "perspective.name",
						Utils.cycleConsumer(config::getPerspective, config::setPerspective, Perspective.values()),
						(option) -> new TranslatableText(KEY_BASE + "perspective", config.getPerspective().getName()),
						new TranslatableText(KEY_BASE + "perspective.desc")),
				new SpruceCyclingOption(KEY_BASE + "activation_mode.name",
						Utils.cycleConsumer(config::getActivationMode, config::setActivationMode, ActivationMode.values()),
						(option) -> new TranslatableText(KEY_BASE + "activation_mode", config.getActivationMode().getName()),
						new TranslatableText(KEY_BASE + "activation_mode.desc")));

		list.setRenderTransition(false);
		list.setBackground(EmptyBackground.EMPTY_BACKGROUND);

		addDrawableChild(list);
	}

	@Override
	public void renderTitle(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.renderTitle(matrices, mouseX, mouseY, delta);
		drawCenteredText(matrices, textRenderer, title, width / 2, 8, -1);
	}
}
