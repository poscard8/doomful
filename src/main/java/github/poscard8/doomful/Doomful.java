package github.poscard8.doomful;

import github.poscard8.doomful.core.DoomfulConfig;
import github.poscard8.doomful.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Doomful.ID)
public class Doomful
{
    public static final String ID = "doomful";

    public static ResourceLocation asResource(String name) { return new ResourceLocation(ID, name); }

    public Doomful(FMLJavaModLoadingContext context)
    {
        IEventBus bus = context.getModEventBus();

        DoomfulItems.register(bus);
        DoomfulCreativeModeTabs.register(bus);
        DoomfulRecipeSerializers.register(bus);
        DoomfulParticleTypes.register(bus);
        DoomfulBannerPatterns.register(bus);

        context.getContainer().addConfig(new ModConfig(ModConfig.Type.COMMON, DoomfulConfig.spec(), context.getContainer(), "doomful.toml"));
    }

}
