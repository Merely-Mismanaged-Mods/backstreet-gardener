package io.github.maloryware.backstreet_gardener.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import io.wispforest.owo.ui.component.TextureComponent;
import io.wispforest.owo.ui.core.Color;
import io.wispforest.owo.ui.core.OwoUIDrawContext;
import net.minecraft.util.Identifier;

public class OwoScreenExtras {













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

		public ColorableTextureComponent setZIndex(int z){
			this.zIndex(z);
			return this;
		}

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
					super.draw(context, mouseX, mouseY, partialTicks, delta);
					/* TODO */
					break;
				}

				case FIXED:
				{
					super.draw(context, mouseX, mouseY, partialTicks, delta);
					RenderSystem.setShaderColor(this.COLOR_FIXED.red(), this.COLOR_FIXED.green(), this.COLOR_FIXED.blue(), this.COLOR_FIXED.alpha());
					break;
				}

			}
		}
	}


}
