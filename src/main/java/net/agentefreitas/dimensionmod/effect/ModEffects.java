package net.agentefreitas.dimensionmod.effect;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.agentefreitas.dimensionmod.effect.custom.*;

public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, DimensionMod.MOD_ID);

    public static final RegistryObject<MobEffect> PIG_CURSE_EFFECT =
            MOB_EFFECTS.register("pig_curse_effect", PigCurseEffect::new);

    public static void register(IEventBus eventBus) {MOB_EFFECTS.register(eventBus);}
}
