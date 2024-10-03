package io.github.maloryware.backstreet_gardener.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import io.wispforest.owo.ui.component.TextureComponent;
import io.wispforest.owo.ui.core.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static io.github.maloryware.backstreet_gardener.BackstreetGardener.BSGLOGGER;

@SuppressWarnings({"FieldCanBeLocal, ", "unused"})
public class OwoScreenExtras {

	public enum ColorParams {
		GRADIENT,
		FIXED,
		DEFAULT
	}

	public enum AnimParams {
		STATIC,
		ANIMATED
	}
	public enum AnimSpeed{
		FAST,
		SLOW,
		DEFAULT
	}

	public static int fitTo(int ref, int boxSize, int maxValue){
		return (ref * boxSize/maxValue);
	}


	public static class AdvancedTextureComponent extends TextureComponent{

		private Color COLOR_GRADIENT_FADE_START;
		private Color COLOR_GRADIENT_FADE_END;

		private Color COLOR_FIXED;
		protected ColorParams color;
		protected AnimParams anim;
		protected AnimSpeed speed;

		private int buffer = 0;
		private boolean loop;
		private PositionedRectangle originalVisibleArea;
		private Positioning originalPositioning;
		private int sectionY;
		private int currentStep;
		private final int maxStep;


		public static AdvancedTextureComponent texture(Identifier texture, int regionWidth, int regionHeight, int textureWidth, int textureHeight, ColorParams param) {
			return new AdvancedTextureComponent(texture, regionWidth, regionHeight, textureWidth, textureHeight, textureHeight, param, AnimParams.STATIC, AnimSpeed.DEFAULT);
		}

		public static AdvancedTextureComponent texture(Identifier texture, int regionWidth, int regionHeight, int textureWidth, int textureHeight, int portionY, AnimParams param) {
			return new AdvancedTextureComponent(texture, regionWidth, regionHeight, textureWidth, textureHeight, portionY, ColorParams.DEFAULT, param, AnimSpeed.DEFAULT);
		}

		public static AdvancedTextureComponent texture(Identifier texture, int regionWidth, int regionHeight, int textureWidth, int textureHeight, int portionY) {
			return new AdvancedTextureComponent(texture, regionWidth, regionHeight, textureWidth, textureHeight, portionY, ColorParams.DEFAULT, AnimParams.STATIC, AnimSpeed.DEFAULT);
		}

		public static AdvancedTextureComponent texture(Identifier texture, int regionWidth, int regionHeight, int textureWidth, int textureHeight, int portionY, ColorParams color, AnimParams anim) {
			return new AdvancedTextureComponent(texture, regionWidth, regionHeight, textureWidth, textureHeight, portionY, color, anim, AnimSpeed.DEFAULT);
		}

		protected AdvancedTextureComponent(Identifier texture, int regionWidth, int regionHeight, int textureWidth, int textureHeight, int portionY, ColorParams color, AnimParams anim, AnimSpeed speed) {
			super(texture, 0, 0, regionWidth, regionHeight, textureWidth, textureHeight);
			this.color = color;
			this.anim = anim;
			this.sectionY = portionY;
			this.maxStep = (textureHeight / sectionY) - (textureHeight % sectionY);
			this.loop = true;
			this.currentStep = 1;
			this.speed = speed;

		}

		public AdvancedTextureComponent speed(AnimSpeed speed){
			this.speed = speed;
			return this;
		}

		@Override
		public AdvancedTextureComponent id(@Nullable String id) {
			super.id(id);
			return this;
		}

		/**
		 * Sets the Z index for the component
		 *
		 * @param z Z index for the component
		 * @return The component itself
		 */


		public AdvancedTextureComponent setZIndex(int z) {
			this.zIndex(z);
			return this;
		}

		/**
		 * Sets the color for a ColorableTextureComponent of type FIXED
		 *
		 * @param color A color object
		 * @return The component itself
		 */

		public AdvancedTextureComponent setColor(Color color) {
			this.COLOR_FIXED = color;
			return this;
		}

		public AdvancedTextureComponent setGradientColors(Color a, Color b) {
			this.COLOR_GRADIENT_FADE_START = a;
			this.COLOR_GRADIENT_FADE_END = b;
			return this;
		}

		/**
		 * Sets the default visible area for the component, for first render and for all subsequent loops.
		 *
		 * @param visibleArea The default visible area of the component
		 * @return The component itself
		 */
		@Override
		public AdvancedTextureComponent visibleArea(PositionedRectangle visibleArea) {
			super.visibleArea(visibleArea);
			this.originalVisibleArea = visibleArea;
			return this;
		}

		@Override
		public AdvancedTextureComponent tooltip(@NotNull Text tooltip) {
			super.tooltip(tooltip);
			return this;
		}

		@Override
		public AdvancedTextureComponent blend(boolean blend) {
			super.blend(blend);
			return this;
		}

		/**
		 * Sets the default position of the component, for first render and for all subsequent loops.
		 *
		 * @param positioning The default positioning of the component
		 * @return The component itself
		 */
		@Override
		public AdvancedTextureComponent positioning(Positioning positioning) {
			super.positioning(positioning);
			this.originalPositioning = positioning;
			return this;
		}

		public AdvancedTextureComponent loop(boolean bl) {
			this.loop = bl;
			return this;
		}


		@Override
		public boolean shouldDrawTooltip(double mouseX, double mouseY) {
			return super.shouldDrawTooltip(mouseX, mouseY)
				&& mouseX >= this.visibleArea().get().x() && mouseX < this.visibleArea().get().x() + this.width() && mouseY >= this.visibleArea().get().y() && mouseY < this.visibleArea().get().y() + this.height();
		}

		@Override
		public void draw(OwoUIDrawContext context, int mouseX, int mouseY, float partialTicks, float delta) {


			if (this.visibleArea().get() == null){
				this.visibleArea(PositionedRectangle.of(0, 0, Size.of(256, 256)));
				BSGLOGGER.info("Visible area was null - setting to 256x256");
			}

			if (Objects.requireNonNull(this.anim) == AnimParams.ANIMATED) {



				if (currentStep == 1) {
					// so this works... but super.positioning() doesn't. amazing. i love programming so much it definitely DOESN'T MAKE ME WANT TO LEVER MY FUCKING NAILS OFF WITH A SPOON
					positioning.set(originalPositioning);
					super.visibleArea(originalVisibleArea);
					this.update(delta, mouseX, mouseY);
					this.visibleArea.update(delta);


				}

				switch (this.speed){
					case FAST -> sectionY*=2;
					case SLOW -> buffer++;
				}

				if (currentStep < maxStep) {
					//BSGLOGGER.info("\nIterating.\ncurrentStep:{},\nmaxStep:{},\npositioning:{}, {},\nvisibleArea: x-{}, y-{}, w-{}, h-{}", currentStep, maxStep, positioning.get().x, positioning.get().y, visibleArea.get().x(), visibleArea.get().y(), visibleArea.get().width(), visibleArea.get().height());

					positioning.set(Positioning.absolute(positioning.get().x, positioning.get().y - sectionY));
					super.visibleArea(PositionedRectangle.of(visibleArea.get().x(), visibleArea.get().y() + sectionY, visibleArea.get().width(), visibleArea.get().height()));
					currentStep++;

					switch(buffer){
						case 1 -> buffer++;
						case 2 -> buffer--;
					}

				} else if (loop) {
					//BSGLOGGER.info("\nLoop enabled - finished iterating.\ncurrentStep:{},\nmaxStep:{},\npositioning:{}, {},\nvisibleArea: x-{}, y-{}, w-{}, h-{}", currentStep, maxStep, positioning.get().x, positioning.get().y, visibleArea.get().x(), visibleArea.get().y(), visibleArea.get().width(), visibleArea.get().height());
					currentStep = 1;
				}
			}
			/*
			else {
				BSGLOGGER.info("Something that wasn't supposed to happen, happened.");
			}

			 */

			if (Objects.requireNonNull(this.color) == ColorParams.FIXED) {
				RenderSystem.setShaderColor(this.COLOR_FIXED.red(), this.COLOR_FIXED.green(), this.COLOR_FIXED.blue(), this.COLOR_FIXED.alpha());
				//BSGLOGGER.info("Color: {}", this.color.toString());
			}
			//this.update(delta, mouseX, mouseY);
			//this.visibleArea.update(delta);
			super.draw(context, mouseX, mouseY, partialTicks, delta);
			RenderSystem.setShaderColor(1, 1, 1, 1);
		}
	}
}
