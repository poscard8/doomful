package github.poscard8.doomful.registry;

import github.poscard8.doomful.Doomful;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class DoomfulCreativeModeTabs
{
    public static final DeferredRegister<CreativeModeTab> ALL = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Doomful.ID);

    public static final RegistryObject<CreativeModeTab> MAIN = ALL.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("doomful.name"))
                    .icon(DoomfulItems.RELIC_UPGRADE_SMITHING_TEMPLATE.get()::getDefaultInstance)
                    .displayItems((parameters, output) ->
                    {
                        output.accept(DoomfulItems.ARTIFACT_UPGRADE_SMITHING_TEMPLATE.get());
                        output.accept(DoomfulItems.RELIC_UPGRADE_SMITHING_TEMPLATE.get());
                        output.accept(DoomfulItems.SPIDER_ARTIFACT.get());
                        output.accept(DoomfulItems.CUBIC_ARTIFACT.get());
                        output.accept(DoomfulItems.UNDEAD_RELIC.get());
                        output.accept(DoomfulItems.MEATY_RELIC.get());
                        output.accept(DoomfulItems.ATROCIOUS_RELIC.get());
                        output.accept(DoomfulItems.OMINOUS_RELIC.get());
                        output.accept(DoomfulItems.ENDER_RELIC.get());
                        output.accept(DoomfulItems.SUBMERGED_RELIC.get());
                        output.accept(DoomfulItems.DOOM_BANNER_PATTERN.get());
                    })
                    .build()
    );

    public static void register(IEventBus bus) { ALL.register(bus); }

}
