package github.poscard8.doomful.registry;

import github.poscard8.doomful.Doomful;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DoomfulParticleTypes
{
    public static final DeferredRegister<ParticleType<?>> ALL = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Doomful.ID);

    public static final RegistryObject<SimpleParticleType> ARTHROPOD = ALL.register("arthropod", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> CUBOID = ALL.register("cuboid", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> UNDEAD = ALL.register("undead", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> PIG = ALL.register("pig", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> CONSTRUCT = ALL.register("construct", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ILLAGER = ALL.register("illager", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ENDER = ALL.register("ender", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> AQUATIC = ALL.register("aquatic", () -> new SimpleParticleType(false));

    public static void register(IEventBus bus) { ALL.register(bus); }
}
