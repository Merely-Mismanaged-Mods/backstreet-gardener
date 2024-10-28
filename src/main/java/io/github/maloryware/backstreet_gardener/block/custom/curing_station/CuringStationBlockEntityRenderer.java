package io.github.maloryware.backstreet_gardener.block.custom.curing_station;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

public class CuringStationBlockEntityRenderer implements BlockEntityRenderer<CuringStationBlockEntity>{

	private final ItemRenderer itemRenderer;

	public CuringStationBlockEntityRenderer(BlockEntityRendererFactory.Context ctx){
		this.itemRenderer = ctx.getItemRenderer();
	}

	@Override
	public void render(CuringStationBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		Direction direction = entity.getCachedState().get(Properties.HORIZONTAL_FACING);
		DefaultedList<ItemStack> items = entity.getItems();
		int k = (int)entity.getPos().asLong();

		int lightSource = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos());
		// i love writing renderers! it's so fun and enjoyable!
		// there's probably a better way of writing this but.... guh....

		for(int i = 0; i < 24; i++){
			ItemStack stack = items.get(i);
			if (stack != ItemStack.EMPTY) {
				// sum 0.06 to the side-varying var, and 0.0378125 to y

				matrices.push();
				if(direction == Direction.NORTH) {

					if(i < 8) {
						matrices.translate(0.856875 - 0.09375 * i, 0.5378125, 0.6875);
					}
					if(i >= 8 && i < 16){
						matrices.translate(0.856875 - 0.09375 * (i-8), 0.4128125, 0.4375);
					}
					if(i >= 16){
						matrices.translate(0.856875 - 0.09375 * (i-16), 0.2878125, 0.1875);
					}

					matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(40F));
					matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(30F));
				}
				if(direction == Direction.SOUTH ) {
					if(i < 8) {
						matrices.translate(0.200625 + 0.09375 * i, 0.5378125, 0.2734375);
					}
					if(i >= 8 && i < 16){
						matrices.translate(0.200625 + 0.09375 * (i-8), 0.4128125, 0.5234375);
					}
					if(i >= 16){
						matrices.translate(0.200625 + 0.09375 * (i-16), 0.2878125, 0.7734375);
					}
					matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(30F));
					matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(40F));
					matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(20F));

				}


				if(direction == Direction.EAST ) {
					if(i < 8) {
						matrices.translate(0.26171875, 0.5378125, 0.79859375 - 0.09375 * i);
					}
					if(i >= 8 && i < 16){
						matrices.translate(0.51171875, 0.4128125, 0.79859375 - 0.09375 * (i-8));
					}
					if(i >= 16){
						matrices.translate(0.76171875, 0.2878125, 0.79859375 - 0.09375 * (i-16));
					}

					matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(120F));
					matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(20F));
					matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(50F));
				}
				if(direction == Direction.WEST ) {

					// i could have just done this the entire fucking time???????
					matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(180f));
					matrices.translate(-1,0,-1);

					if(i < 8) {
						matrices.translate(0.26171875, 0.5378125, 0.79859375 - 0.09375 * i);
					}
					if(i >= 8 && i < 16){
						matrices.translate(0.51171875, 0.4128125, 0.79859375 - 0.09375 * (i-8));
					}
					if(i >= 16){
						matrices.translate(0.76171875, 0.2878125, 0.79859375 - 0.09375 * (i-16));
					}

					matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(120F));
					matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(20F));
					matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(50F));


				}
				matrices.scale(0.375F, 0.375F, 0.375F);
				this.itemRenderer.renderItem(stack, ModelTransformationMode.GROUND, lightSource, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), k + i);

			}
			matrices.pop();
		}

	}
}
