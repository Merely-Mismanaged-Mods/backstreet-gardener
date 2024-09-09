package io.github.maloryware.backstreet_gardener.datagen;

import io.github.maloryware.backstreet_gardener.BackstreetGardener;
import io.github.maloryware.backstreet_gardener.block.BSGBlocks;
import io.github.maloryware.backstreet_gardener.block.crop.CokePlant;
import io.github.maloryware.backstreet_gardener.block.crop.OpiumPlant;
import io.github.maloryware.backstreet_gardener.block.crop.TobaccoPlant;
import io.github.maloryware.backstreet_gardener.block.crop.WeedPlant;
import io.github.maloryware.backstreet_gardener.item.BSGItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import static io.github.maloryware.backstreet_gardener.BackstreetGardener.ID;

@Environment(EnvType.CLIENT)
public class ModelPredicateProvider extends ModelPredicateProviderRegistry {

	public static void register(){


		ClampedModelPredicateProvider mokingPredicate = (itemStack, clientWorld, livingEntity, seed) ->
		{
			assert livingEntity != null;
			return livingEntity.isUsingItem()
				&& livingEntity.getActiveItem() == itemStack
				&& livingEntity.getOffHandStack() == Items.FLINT_AND_STEEL.getDefaultStack() ? 1.0F : 0.0F;
		};

		register(BSGItems.BONG, Identifier.of(ID,"moking"), mokingPredicate);
		register(BSGItems.BLUNT, Identifier.of(ID,"moking"), mokingPredicate);
		register(BSGItems.JOINT, Identifier.of(ID,"moking"), mokingPredicate);
		register(BSGItems.CIGARETTE, Identifier.of(ID,"moking"), mokingPredicate);



	}




}
