package github.poscard8.doomful.event;

import github.poscard8.doomful.Doomful;
import github.poscard8.doomful.client.particle.DoomParticle;
import github.poscard8.doomful.registry.DoomfulItems;
import github.poscard8.doomful.registry.DoomfulParticleTypes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Doomful.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@SuppressWarnings("unused")
public class ModBusEvents
{
    @SubscribeEvent
    static void registerTabContent(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS)
        {
            event.getEntries().putAfter(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE.getDefaultInstance(), DoomfulItems.RELIC_UPGRADE_SMITHING_TEMPLATE.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE.getDefaultInstance(), DoomfulItems.ARTIFACT_UPGRADE_SMITHING_TEMPLATE.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    @Mod.EventBusSubscriber(modid = Doomful.ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Client
    {
        @SubscribeEvent
        static void registerParticleProviders(RegisterParticleProvidersEvent event)
        {
            event.registerSpriteSet(DoomfulParticleTypes.ARTHROPOD.get(), DoomParticle.Provider::new);
            event.registerSpriteSet(DoomfulParticleTypes.CUBOID.get(), DoomParticle.Provider::new);
            event.registerSpriteSet(DoomfulParticleTypes.UNDEAD.get(), DoomParticle.Provider::new);
            event.registerSpriteSet(DoomfulParticleTypes.PIG.get(), DoomParticle.Provider::new);
            event.registerSpriteSet(DoomfulParticleTypes.CONSTRUCT.get(), DoomParticle.Provider::new);
            event.registerSpriteSet(DoomfulParticleTypes.ILLAGER.get(), DoomParticle.Provider::new);
            event.registerSpriteSet(DoomfulParticleTypes.ENDER.get(), DoomParticle.Provider::new);
            event.registerSpriteSet(DoomfulParticleTypes.AQUATIC.get(), DoomParticle.Provider::new);
        }
    }

}
