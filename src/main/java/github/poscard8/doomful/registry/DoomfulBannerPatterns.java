package github.poscard8.doomful.registry;

import github.poscard8.doomful.Doomful;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class DoomfulBannerPatterns
{
    public static final DeferredRegister<BannerPattern> ALL = DeferredRegister.create(Registries.BANNER_PATTERN, Doomful.ID);

    public static final RegistryObject<BannerPattern> DOOM = ALL.register("doom", () -> new BannerPattern("dm"));

    public static void register(IEventBus bus) { ALL.register(bus); }

}
