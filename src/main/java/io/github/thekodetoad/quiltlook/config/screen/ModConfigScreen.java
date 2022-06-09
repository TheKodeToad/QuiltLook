package io.github.thekodetoad.quiltlook.config.screen;

import java.io.IOException;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.background.EmptyBackground;
import dev.lambdaurora.spruceui.option.*;
import dev.lambdaurora.spruceui.screen.SpruceScreen;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import dev.lambdaurora.spruceui.widget.container.SpruceOptionListWidget;
import io.github.thekodetoad.quiltlook.QuiltLookMod;
import io.github.thekodetoad.quiltlook.config.ActivationMode;
import io.github.thekodetoad.quiltlook.config.ModConfig;
import io.github.thekodetoad.quiltlook.config.Perspective;
import io.github.thekodetoad.quiltlook.config.XRestrictionMode;
import io.github.thekodetoad.quiltlook.utils.Utils;
import net.minecraft.client.gui.screen.FatalErrorScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.realms.Request.Post;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

public class ModConfigScreen extends SpruceScreen {

	private static final String KEY_BASE = "quiltlook.config.";
	private static final String TITLE = KEY_BASE + "title";

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

		list = new SpruceOptionListWidget(Position.of(0, 25), width, height - 58);

		// behaviour category
		list.addSingleOptionEntry(new SpruceSeparatorOption(KEY_BASE + "behaviour", true, null));

		// perspective & activation mode
		list.addOptionEntry(
				new SpruceCyclingOption(KEY_BASE + "perspective.name",
						Utils.cycleConsumer(config::getPerspective, config::setPerspective, Perspective.values()),
						(option) -> new TranslatableText(KEY_BASE + "perspective", config.getPerspective().getName()),
						new TranslatableText(KEY_BASE + "perspective.desc")),
				new SpruceCyclingOption(KEY_BASE + "activation_mode.name",
						Utils.cycleConsumer(config::getActivationMode, config::setActivationMode,
								ActivationMode.values()),
						(option) -> new TranslatableText(KEY_BASE + "activation_mode",
								config.getActivationMode().getName()),
						new TranslatableText(KEY_BASE + "activation_mode.desc")));

		// Restrict X
		list.addOptionEntry(new SpruceCyclingOption(KEY_BASE + "restrict_x.name",
				Utils.cycleConsumer(config::getXRestrictionMode, config::setXRestrictionMode,
						XRestrictionMode.values()),
				(option) -> new TranslatableText(KEY_BASE + "restrict_x", config.getXRestrictionMode().getName()),
				new TranslatableText(KEY_BASE + "restrict_x.desc")),
				new SpruceDoubleOption(KEY_BASE + "x_restriction.name", 0, 360, 1, config::getXRestrictionDouble,
						config::setXRestrictionDouble,
						(option) -> new TranslatableText(KEY_BASE + "x_restriction",
								Integer.toString((int) config.getXRestriction())),
						new TranslatableText(KEY_BASE + "x_restriction.desc")));

		list.addOptionEntry(new SpruceToggleBooleanOption(KEY_BASE + "restrict_y", config::getRestrictY,
				config::setRestrictY, new TranslatableText(KEY_BASE + "restrict_y.desc")),
				new SpruceToggleBooleanOption(KEY_BASE + "reverse_view", config::getReverseView, config::setReverseView, new TranslatableText(KEY_BASE + "reverse_view.desc")));

		// control category
		list.addSingleOptionEntry(new SpruceSeparatorOption(KEY_BASE + "control", true, null));

		// invert X & Y
		list.addOptionEntry(
				new SpruceToggleBooleanOption(KEY_BASE + "invert_x", config::getInvertX, config::setInvertX,
						new TranslatableText(KEY_BASE + "invert_x.desc")),
				new SpruceToggleBooleanOption(KEY_BASE + "invert_y", config::getInvertY, config::setInvertY,
						new TranslatableText(KEY_BASE + "invert_y.desc")));

		// sensitivity
		list.addOptionEntry(
				new SpruceToggleBooleanOption(KEY_BASE + "modify_sensitivity", config::getModifySensitivity,
						config::setModifySensitivity, new TranslatableText(KEY_BASE + "modify_sensitivity.desc")),
				new SpruceDoubleOption(KEY_BASE + "sensitivity.name", 0.01F, 2, 0.01F, config::getSensitivityDouble,
						config::setSensitivityDouble,
						(option) -> new TranslatableText(KEY_BASE + "sensitivity",
								Integer.toString((int) (config.getSensitivity() * 100))),
						new TranslatableText(KEY_BASE + "sensitivity.desc")));

		list.setRenderTransition(false);
		list.setBackground(EmptyBackground.EMPTY_BACKGROUND);

		addDrawableChild(list);

		SpruceButtonWidget doneButton = new SpruceButtonWidget(Position.of(width / 2 - 75, height - 28), 150, 20,
				new TranslatableText("gui.done"), (button) -> {
					if(save()) {
						client.setScreen(previousScreen);
					}
				});
		addDrawableChild(doneButton);
	}

	@Override
	public void renderTitle(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.renderTitle(matrices, mouseX, mouseY, delta);
		drawCenteredText(matrices, textRenderer, title, width / 2, 12, -1);
	}

	@Override
	public void onClose() {
		if(save()) {
			super.onClose();
		}
	}

	public boolean save() {
		try {
			config.save();
			return true;
		}
		catch(IOException error) {
			client.setScreen(new FatalErrorScreen(new TranslatableText("quiltlook.oh_dear"),
					new TranslatableText("quiltlook.failed_to_save")));
			QuiltLookMod.LOGGER.error("Could not save config", error);
		}
		return false;
	}

}
