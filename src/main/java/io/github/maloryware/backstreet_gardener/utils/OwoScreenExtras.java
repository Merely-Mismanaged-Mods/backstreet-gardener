package io.github.maloryware.backstreet_gardener.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import io.wispforest.owo.ui.base.BaseComponent;
import io.wispforest.owo.ui.component.TextureComponent;
import io.wispforest.owo.ui.core.*;
import net.minecraft.util.Identifier;

import static io.github.maloryware.backstreet_gardener.BackstreetGardener.BSGLOGGER;

public class OwoScreenExtras {





	@SuppressWarnings("RedundantCast") //bro never divided in his entire fucking life
	public static class AnimatedTextureComponent extends TextureComponent {




		private int tickDelta;
		private boolean loop;
		private PositionedRectangle originalVisibleArea;
		private Positioning originalPositioning;
		private final int sectionY;
		private int currentStep;
		private final int maxStep;


		public static AnimatedTextureComponent texture(Identifier texture, int regionWidth, int regionHeight, int textureWidth, int textureHeight, int portionY){
			return new AnimatedTextureComponent(texture, regionWidth, regionHeight, textureWidth, textureHeight, portionY);
		}

		protected AnimatedTextureComponent(Identifier texture, int regionWidth, int regionHeight, int textureWidth, int textureHeight, int portionY) {
			super(texture, 0, 0, regionWidth, regionHeight, textureWidth, textureHeight);
			this.sectionY = portionY;
			this.maxStep = (int) (textureHeight / sectionY) - (textureHeight % sectionY);
			this.loop = true;
			this.currentStep = 0;

		}

		@Override
		public AnimatedTextureComponent visibleArea(PositionedRectangle visibleArea) {
			super.visibleArea(visibleArea);
			this.originalVisibleArea = visibleArea;
			return this;
		}

		@Override
		public AnimatedTextureComponent positioning(Positioning positioning) {
			this.originalPositioning = positioning;
			return this;
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

			if (currentStep == 1) {
				super.positioning(originalPositioning);
				super.visibleArea(originalVisibleArea);
				this.update(delta, mouseX, mouseY);
				this.visibleArea.update(delta);


			}

			if(currentStep < maxStep) {
				BSGLOGGER.info("\nIterating.\ncurrentStep:{},\nmaxStep:{},\npositioning:{}, {},\nvisibleArea: x-{}, y-{}, w-{}, h-{}", currentStep, maxStep, positioning.get().x, positioning.get().y, visibleArea.get().x(), visibleArea.get().y(), visibleArea.get().width(), visibleArea.get().height());
				super.positioning(Positioning.absolute(positioning.get().x, positioning.get().y - sectionY));
				super.visibleArea(PositionedRectangle.of(visibleArea.get().x(), visibleArea.get().y() + sectionY, visibleArea.get().width(), visibleArea.get().height()));
				currentStep++;

			}
			else if(loop){
				BSGLOGGER.info("\nLoop enabled - finished iterating.\ncurrentStep:{},\nmaxStep:{},\npositioning:{}, {},\nvisibleArea: x-{}, y-{}, w-{}, h-{}", currentStep, maxStep, positioning.get().x, positioning.get().y, visibleArea.get().x(), visibleArea.get().y(), visibleArea.get().width(), visibleArea.get().height());
				currentStep = 1;
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
