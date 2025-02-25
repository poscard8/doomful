package github.poscard8.doomful.core;

import github.poscard8.doomful.Doomful;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unused")
public class DoomfulTags
{
    public static class Items
    {
        public static final TagKey<Item> WEAPONS = create("weapons");
        public static final TagKey<Item> ARTIFACTS = create("artifacts");
        public static final TagKey<Item> RELICS = create("relics");

        static TagKey<Item> create(String name) { return TagKey.create(ForgeRegistries.Keys.ITEMS, Doomful.asResource(name)); }
    }

    public static class EntityTypes
    {
        public static final TagKey<EntityType<?>> CUBOIDS = create("cuboids");
        public static final TagKey<EntityType<?>> PIGS = create("pigs");
        public static final TagKey<EntityType<?>> CONSTRUCTS = create("constructs");
        public static final TagKey<EntityType<?>> ENDER = create("ender");

        static TagKey<EntityType<?>> create(String name) { return TagKey.create(ForgeRegistries.Keys.ENTITY_TYPES, Doomful.asResource(name)); }
    }

    public static class BannerPatterns
    {
        public static final TagKey<BannerPattern> PATTERN_ITEM_DOOM = TagKey.create(Registries.BANNER_PATTERN, Doomful.asResource("pattern_item/doom"));
    }

}
