package io.github.maloryware.backstreet_gardener.sound;

import io.github.maloryware.backstreet_gardener.BackstreetGardener;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class BSGSoundsClient {

	public static SoundEvent register(String id){

		return Registry.register(Registries.SOUND_EVENT,
			Identifier.of(BackstreetGardener.ID,id),
			SoundEvent.of(Identifier.of(BackstreetGardener.ID,id)));
	}


	public static final SoundEvent BUBBLING = register("bong_bubbling");
	public static void initialize(){

	}

}
