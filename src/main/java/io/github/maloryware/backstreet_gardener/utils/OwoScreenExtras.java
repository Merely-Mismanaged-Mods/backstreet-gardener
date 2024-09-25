package io.github.maloryware.backstreet_gardener.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import io.wispforest.owo.ui.component.TextureComponent;
import io.wispforest.owo.ui.core.*;
import net.minecraft.util.Identifier;

public class OwoScreenExtras {





	@SuppressWarnings("RedundantCast") //bro never divided in his entire fucking life
	public static class AnimatedTextureComponent extends TextureComponent {




		private int tickDelta;
		private final int sectionCount;
		private boolean loop;
		private final int originalY;
		private final int sectionY;
		private int currentStep;
		private final int maxStep;

		protected AnimatedTextureComponent(Identifier texture, int regionWidth, int regionHeight, int textureWidth, int textureHeight) {
			super(texture, 0, 0, regionWidth, regionHeight, textureWidth, textureHeight);
			this.sectionY = regionHeight;
			this.maxStep = sectionCount = (int) (textureHeight / regionHeight);
			this.loop = true;
			this.currentStep = 0;
			this.originalY = this.positioning().get().y;

		}

		public AnimatedTextureComponent setTickDelta(int i){
			this.tickDelta = i;
			return this;
		}

		public AnimatedTextureComponent loop(boolean bl){
			this.loop = bl;
			return this;
		}

		@Override
		public void draw(OwoUIDrawContext context, int mouseX, int mouseY, float partialTicks, float delta) {
			super.draw(context, mouseX, mouseY, partialTicks, delta);
			if(currentStep < maxStep) {
				this.positioning(Positioning.absolute(this.positioning().get().x, this.positioning().get().y - sectionY));
				this.visibleArea(PositionedRectangle.of(this.visibleArea().get().x(), this.visibleArea().get().y() + sectionY, this.visibleArea.get().width(), this.visibleArea().get().height()));
				currentStep++;
			}
			else if (loop) {
				currentStep = 0;
				this.positioning(Positioning.absolute(this.positioning().get().x, this.originalY));
				this.visibleArea(PositionedRectangle.of(this.visibleArea().get().x(), 0, this.visibleArea.get().width(), this.visibleArea().get().height()));

			}
			this.update(delta, mouseX, mouseY);
			this.visibleArea.update(delta);

		}

		// get texture
		// get width/height of texture section
		// establish how many sections there are (height/sectionHeight)
		// on each render call, render then step down, incrementing currentStep, moving texture up and visible box down




	}


	public static class ColorableTextureComponent extends TextureComponent {

		private Color COLOR_GRADIENT_FADE_START;
		private Color COLOR_GRADIENT_FADE_END;
		private Color COLOR_FIXED;
		protected PARAM param;
		public enum PARAM {
			GRADIENT,
			FIXED
		}

		// constructors

		public static ColorableTextureComponent texture(Identifier texture, int startPosX, int startPosY, int regionWidth, int regionHeight, int textureWidth, int textureHeight, PARAM select) {
			return new ColorableTextureComponent(texture, startPosX, startPosY, regionWidth, regionHeight, textureWidth, textureHeight, select);
		}
		public static ColorableTextureComponent texture(Identifier texture, int regionWidth, int regionHeight, int textureWidth, int textureHeight, PARAM select){
			return new ColorableTextureComponent(texture, 0, 0, regionWidth, regionHeight, textureWidth, textureHeight, select);
		}
		public static ColorableTextureComponent texture(Identifier texture, int regionWidth, int regionHeight, int textureWidth, int textureHeight) {
			return new ColorableTextureComponent(texture, 0, 0, regionWidth, regionHeight, textureWidth, textureHeight, PARAM.FIXED);
		}
		public static ColorableTextureComponent texture(Identifier texture, int startPosX, int startPosY, int regionWidth, int regionHeight, int textureWidth, int textureHeight){
			return new ColorableTextureComponent(texture, startPosX, startPosY, regionWidth, regionHeight, textureWidth, textureHeight, PARAM.FIXED);
		}


		protected ColorableTextureComponent(Identifier texture, int startPosX, int startPosY, int regionWidth, int regionHeight, int textureWidth, int textureHeight, PARAM select) {
			super(texture, startPosX, startPosY, regionWidth, regionHeight, textureWidth, textureHeight);
			this.param = select;
		}

		/**
		 * Sets the Z index for the component
		 * @param z Z index for the component
		 * @return Itself
		 */

		public ColorableTextureComponent setZIndex(int z){
			this.zIndex(z);
			return this;
		}

		/**
		 * Sets the color for a ColorableTextureComponent of type FIXED
		 * @param color A color object
		 * @return Itself
		 */

		public ColorableTextureComponent setColor(Color color){
			this.COLOR_FIXED = color;
			return this;
		}

		public ColorableTextureComponent setGradientColors(Color a, Color b){
			this.COLOR_GRADIENT_FADE_START = a;
			this.COLOR_GRADIENT_FADE_END = b;
			return this;
		}

		@Override
		public void draw(OwoUIDrawContext context, int mouseX, int mouseY, float partialTicks, float delta) {

			switch (param){
				case GRADIENT:
				{

					/* TODO */
					break;
				}

				case FIXED:
				{
					RenderSystem.setShaderColor(this.COLOR_FIXED.red(), this.COLOR_FIXED.green(), this.COLOR_FIXED.blue(), this.COLOR_FIXED.alpha());
					break;
				}
			}
			super.draw(context, mouseX, mouseY, partialTicks, delta);
			RenderSystem.setShaderColor(1, 1, 1, 1);
		}
	}
}
