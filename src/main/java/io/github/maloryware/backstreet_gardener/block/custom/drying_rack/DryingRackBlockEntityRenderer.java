package io.github.maloryware.backstreet_gardener.block.custom.drying_rack;

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

@SuppressWarnings("unused")
public class DryingRackBlockEntityRenderer implements BlockEntityRenderer<DryingRackBlockEntity> {

	private final ItemRenderer itemRenderer;

	public DryingRackBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		this.itemRenderer = ctx.getItemRenderer();
	}

	@Override
	public void render(DryingRackBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		Direction direction = entity.getCachedState().get(Properties.HORIZONTAL_FACING);
		DefaultedList<ItemStack> items = entity.getItems();
		int k = (int)entity.getPos().asLong();

		int lightSource = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().offset(entity.getCachedState().get(Properties.HORIZONTAL_FACING)));
		var facing = entity.getCachedState().get(Properties.HORIZONTAL_FACING);
		for(int i = 0; i < 6; i++){
			ItemStack stack = items.get(i);
			if (stack != ItemStack.EMPTY) {
				matrices.push();
				if(facing == Direction.NORTH || facing == Direction.SOUTH ) {
					matrices.translate(0.34375 + 0.1 * (i-1), 0.858, 0.6);
					matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(220F));
					matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90F));
				}
				else{
					matrices.translate(0.4, 0.858, 0.34375 + 0.1 * (i-1));
					matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(220F));

				}
				// matrices.scale(0.375F, 0.375F, 0.375F);

				this.itemRenderer.renderItem(stack, ModelTransformationMode.GROUND, lightSource, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), k + i);
				matrices.pop();
			}
		}
	}
}
