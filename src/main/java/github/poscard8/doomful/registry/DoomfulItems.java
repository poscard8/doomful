package github.poscard8.doomful.registry;

import github.poscard8.doomful.Doomful;
import github.poscard8.doomful.core.ArtifactItem;
import github.poscard8.doomful.core.DoomfulTags;
import github.poscard8.doomful.core.Upgrade;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DoomfulItems
{
    public static final DeferredRegister<Item> ALL = DeferredRegister.create(ForgeRegistries.ITEMS, Doomful.ID);

    public static final RegistryObject<SmithingTemplateItem> ARTIFACT_UPGRADE_SMITHING_TEMPLATE = ALL.register("artifact_upgrade_smithing_template", Upgrade.Type.ARTIFACT::asSmithingTemplate);
    public static final RegistryObject<SmithingTemplateItem> RELIC_UPGRADE_SMITHING_TEMPLATE = ALL.register("relic_upgrade_smithing_template", Upgrade.Type.RELIC::asSmithingTemplate);
    public static final RegistryObject<ArtifactItem> SPIDER_ARTIFACT = ALL.register("spider_artifact", Upgrade.ARTHROPOD::asArtifact);
    public static final RegistryObject<ArtifactItem> CUBIC_ARTIFACT = ALL.register("cubic_artifact", Upgrade.CUBOID::asArtifact);
    public static final RegistryObject<ArtifactItem> UNDEAD_RELIC = ALL.register("undead_relic", Upgrade.UNDEAD::asArtifact);
    public static final RegistryObject<ArtifactItem> MEATY_RELIC = ALL.register("meaty_relic", Upgrade.PIG::asArtifact);
    public static final RegistryObject<ArtifactItem> ATROCIOUS_RELIC = ALL.register("atrocious_relic", Upgrade.CONSTRUCT::asArtifact);
    public static final RegistryObject<ArtifactItem> OMINOUS_RELIC = ALL.register("ominous_relic", Upgrade.ILLAGER::asArtifact);
    public static final RegistryObject<ArtifactItem> ENDER_RELIC = ALL.register("ender_relic", Upgrade.ENDER::asArtifact);
    public static final RegistryObject<ArtifactItem> SUBMERGED_RELIC = ALL.register("submerged_relic", Upgrade.AQUATIC::asArtifact);
    public static final RegistryObject<BannerPatternItem> DOOM_BANNER_PATTERN = ALL.register("doom_banner_pattern", () -> new BannerPatternItem(DoomfulTags.BannerPatterns.PATTERN_ITEM_DOOM, new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static void register(IEventBus bus) { ALL.register(bus); }

}
