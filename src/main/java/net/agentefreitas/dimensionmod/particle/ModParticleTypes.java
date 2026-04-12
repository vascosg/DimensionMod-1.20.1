package net.agentefreitas.dimensionmod.particle;

import net.agentefreitas.dimensionmod.DimensionMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticleTypes {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, DimensionMod.MOD_ID);

    public static final RegistryObject<SimpleParticleType> EYE_STARING_PARTICLE =
            PARTICLE_TYPES.register("eye_staring_particle", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> FLAME_BUBBLE_PARTICLE =
            PARTICLE_TYPES.register("flame_bubble_particle", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
