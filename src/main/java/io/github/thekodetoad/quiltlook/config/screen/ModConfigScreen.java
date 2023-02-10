package io.github.thekodetoad.quiltlook.config.screen;

import java.io.IOException;

import dev.isxander.yacl.api.ConfigCategory;
import dev.isxander.yacl.api.Option;
import dev.isxander.yacl.api.YetAnotherConfigLib;
import dev.isxander.yacl.gui.controllers.BooleanController;
import dev.isxander.yacl.gui.controllers.cycling.EnumController;
import dev.isxander.yacl.gui.controllers.slider.FloatSliderController;
import dev.isxander.yacl.gui.controllers.slider.IntegerSliderController;
import io.github.thekodetoad.quiltlook.QuiltLookMod;
import io.github.thekodetoad.quiltlook.config.ActivationMode;
import io.github.thekodetoad.quiltlook.config.ModConfig;
import io.github.thekodetoad.quiltlook.config.Perspective;
import io.github.thekodetoad.quiltlook.config.XRestrictionMode;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ModConfigScreen {

	private static final String KEY_BASE = "quiltlook.config.", TITLE = KEY_BASE + "title";

	public static Screen create(Screen parent) {
		ModConfig config = QuiltLookMod.getInstance().getConfig();

		return YetAnotherConfigLib.createBuilder().title(Text.translatable(TITLE)).save(() -> {
			try {
				config.save();
			} catch (IOException error) {
				QuiltLookMod.LOGGER.error("Could not save config", error);
			}
		}).category(ConfigCategory.createBuilder().name(Text.translatable(KEY_BASE + "behaviour"))
				.option(Option.createBuilder(Perspective.class).name(Text.translatable(KEY_BASE + "perspective"))
						.tooltip(Text.translatable(KEY_BASE + "perspective.desc"))
						.binding(Perspective.THIRD_PERSON_BACK, config::getPerspective, config::setPerspective)
						.controller(EnumController::new).build())
				.option(Option.createBuilder(ActivationMode.class).name(Text.translatable(KEY_BASE + "activation_mode"))
						.tooltip(Text.translatable(KEY_BASE + "activation_mode.desc"))
						.binding(ActivationMode.HOLD, config::getActivationMode, config::setActivationMode)
						.controller(EnumController::new).build())
				.option(Option.createBuilder(XRestrictionMode.class).name(Text.translatable(KEY_BASE + "restrict_x"))
						.tooltip(Text.translatable(KEY_BASE + "restrict_x.desc"))
						.binding(XRestrictionMode.NEVER, config::getXRestrictionMode, config::setXRestrictionMode)
						.controller(EnumController::new).build())
				.option(Option.createBuilder(int.class).name(Text.translatable(KEY_BASE + "x_restriction"))
						.tooltip(Text.translatable(KEY_BASE + "x_restriction.desc"))
						.binding(200, config::getXRestriction, config::setXRestriction)
						.controller((option) -> new IntegerSliderController(option, 0, 360, 1)).build())
				.option(Option.createBuilder(boolean.class).name(Text.translatable(KEY_BASE + "restrict_y"))
						.tooltip(Text.translatable(KEY_BASE + "restrict_y.desc"))
						.binding(true, config::getRestrictY, config::setRestrictY).controller(BooleanController::new)
						.build())
				.build())
				.category(ConfigCategory.createBuilder().name(Text.translatable(KEY_BASE + "controls"))
						.option(Option.createBuilder(boolean.class).name(Text.translatable(KEY_BASE + "invert_x"))
								.binding(false, config::getInvertX, config::setInvertX)
								.controller(BooleanController::new).build())
						.option(Option.createBuilder(boolean.class).name(Text.translatable(KEY_BASE + "invert_y"))
								.binding(false, config::getInvertY, config::setInvertY)
								.controller(BooleanController::new).build())
						.option(Option.createBuilder(int.class).name(Text.translatable(KEY_BASE + "sensitivity"))
								.binding(100, () -> (int) config.getSensitivity() * 100,
										(value) -> config.setSensitivity(value / 100F))
								.controller((option) -> new IntegerSliderController(option, 1, 200, 1)).build())
						.build())
				.build().generateScreen(parent);
	}

}
