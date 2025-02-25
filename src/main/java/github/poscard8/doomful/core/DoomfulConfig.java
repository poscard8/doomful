package github.poscard8.doomful.core;

import net.minecraftforge.common.ForgeConfigSpec;

public class DoomfulConfig
{
    static final ForgeConfigSpec SPEC;
    static final ForgeConfigSpec.Builder BUILDER;

    static final ForgeConfigSpec.DoubleValue ARTHROPOD_DAMAGE_BOOST;
    static final ForgeConfigSpec.DoubleValue CUBOID_DAMAGE_BOOST;
    static final ForgeConfigSpec.DoubleValue UNDEAD_DAMAGE_BOOST;
    static final ForgeConfigSpec.DoubleValue PIG_DAMAGE_BOOST;
    static final ForgeConfigSpec.DoubleValue CONSTRUCT_DAMAGE_BOOST;
    static final ForgeConfigSpec.DoubleValue ILLAGER_DAMAGE_BOOST;
    static final ForgeConfigSpec.DoubleValue ENDER_DAMAGE_BOOST;
    static final ForgeConfigSpec.DoubleValue AQUATIC_DAMAGE_BOOST;

    static final ForgeConfigSpec.DoubleValue ARTIFACT_TEMPLATE_CHANCE;
    static final ForgeConfigSpec.DoubleValue RELIC_TEMPLATE_CHANCE;
    static final ForgeConfigSpec.DoubleValue UNDEAD_RELIC_CHANCE;
    static final ForgeConfigSpec.DoubleValue MEATY_RELIC_CHANCE;
    static final ForgeConfigSpec.DoubleValue ATROCIOUS_RELIC_CHANCE;
    static final ForgeConfigSpec.DoubleValue OMINOUS_RELIC_CHANCE;
    static final ForgeConfigSpec.DoubleValue ENDER_RELIC_CHANCE;
    static final ForgeConfigSpec.DoubleValue SUBMERGED_RELIC_CHANCE;

    static final ForgeConfigSpec.BooleanValue UNDEAD_RENAME;
    static final ForgeConfigSpec.BooleanValue AQUATIC_RENAME;

    static final ForgeConfigSpec.BooleanValue ITEM_DESCRIPTIONS;

    public static ForgeConfigSpec spec() { return SPEC; }

    public static float getArthropodDamageMultiplier() { return (float) (1 + ARTHROPOD_DAMAGE_BOOST.get() / 100.0D); }

    public static float getCuboidDamageMultiplier() { return (float) (1 + CUBOID_DAMAGE_BOOST.get() / 100.0D); }

    public static float getUndeadDamageMultiplier() { return (float) (1 + UNDEAD_DAMAGE_BOOST.get() / 100.0D); }

    public static float getPigDamageMultiplier() { return (float) (1 + PIG_DAMAGE_BOOST.get() / 100.0D); }

    public static float getConstructDamageMultiplier() { return (float) (1 + CONSTRUCT_DAMAGE_BOOST.get() / 100.0D); }

    public static float getIllagerDamageMultiplier() { return (float) (1 + ILLAGER_DAMAGE_BOOST.get() / 100.0D); }

    public static float getEnderDamageMultiplier() { return (float) (1 + ENDER_DAMAGE_BOOST.get() / 100.0D); }

    public static float getAquaticDamageMultiplier() { return (float) (1 + AQUATIC_DAMAGE_BOOST.get() / 100.0D); }

    public static int[] getDungeonLootWeights()
    {
        int artifactWeight = (int) Math.round(ARTIFACT_TEMPLATE_CHANCE.get() * 10);
        int relicWeight = (int) Math.round(RELIC_TEMPLATE_CHANCE.get() * 10);
        int emptyWeight = 1000 - artifactWeight - relicWeight;

        return new int[]{artifactWeight, relicWeight, emptyWeight};
    }

    public static int[] getSkeletonLootWeights() { return createMobLootWeights(UNDEAD_RELIC_CHANCE); }

    public static int[] getPiglinBruteLootWeights() { return createMobLootWeights(MEATY_RELIC_CHANCE); }

    public static int[] getBlazeLootWeights() { return createMobLootWeights(ATROCIOUS_RELIC_CHANCE); }

    public static int[] getVindicatorLootWeights() { return createMobLootWeights(OMINOUS_RELIC_CHANCE); }

    public static int[] getShulkerLootWeights() { return createMobLootWeights(ENDER_RELIC_CHANCE); }

    public static int[] getElderGuardianLootWeights() { return createMobLootWeights(SUBMERGED_RELIC_CHANCE); }

    public static boolean isUndeadUpgradeRenamed() { return UNDEAD_RENAME.get(); }

    public static boolean isAquaticUpgradeRenamed() { return AQUATIC_RENAME.get(); }

    public static boolean hasItemDescriptions() { return ITEM_DESCRIPTIONS.get(); }

    static int[] createMobLootWeights(ForgeConfigSpec.DoubleValue config)
    {
        int relicWeight = (int) Math.round(config.get() * 10);
        int emptyWeight = 1000 - relicWeight;
        return new int[]{relicWeight, emptyWeight};
    }

    static
    {
        BUILDER = new ForgeConfigSpec.Builder();


        BUILDER.push("Damage Values");

        ARTHROPOD_DAMAGE_BOOST = BUILDER
                .comment("Damage boosts for artifact and relic upgrades")
                .comment("Example: 60 will result in +60% damage")
                .defineInRange("arthropodDamageBoost", 100.0D, -100, 900);
        CUBOID_DAMAGE_BOOST = BUILDER.defineInRange("cuboidDamageBoost", 100.0D, -100, 900);
        UNDEAD_DAMAGE_BOOST = BUILDER.defineInRange("undeadDamageBoost", 40.0D, -100, 900);
        PIG_DAMAGE_BOOST = BUILDER.defineInRange("pigDamageBoost", 50.0D, -100, 900);
        CONSTRUCT_DAMAGE_BOOST = BUILDER.defineInRange("constructDamageBoost", 40.0D, -100, 900);
        ILLAGER_DAMAGE_BOOST = BUILDER.defineInRange("illagerDamageBoost", 40.0D, -100, 900);
        ENDER_DAMAGE_BOOST = BUILDER.defineInRange("enderDamageBoost", 60.0D, -100, 900);
        AQUATIC_DAMAGE_BOOST = BUILDER.defineInRange("aquaticDamageBoost", 60.0D, -100, 900);

        BUILDER.pop();


        BUILDER.push("Loot Tables");

        ARTIFACT_TEMPLATE_CHANCE = BUILDER
                .comment("The chance (in percentage) of getting an Artifact Upgrade Smithing Template from a Dungeon chest")
                .defineInRange("artifactTemplateChance", 45.0D, 0, 75);
        RELIC_TEMPLATE_CHANCE = BUILDER
                .comment("The chance (in percentage) of getting a Relic Upgrade Smithing Template from a Dungeon chest")
                .defineInRange("relicTemplateChance", 15.0D, 0, 25);
        UNDEAD_RELIC_CHANCE = BUILDER
                .comment("The chance of getting an Undead Relic from a Skeleton")
                .defineInRange("undeadRelicChance", 0.3D, 0, 100);
        MEATY_RELIC_CHANCE = BUILDER
                .comment("The chance of getting a Meaty Relic from a Piglin Brute")
                .defineInRange("meatyRelicChance", 10.0D, 0, 100);
        ATROCIOUS_RELIC_CHANCE = BUILDER
                .comment("The chance of getting an Atrocious Relic from a Blaze or a Breeze")
                .defineInRange("atrociousRelicChance", 1.5D, 0, 100);
        OMINOUS_RELIC_CHANCE = BUILDER
                .comment("The chance of getting an Ominous Relic from a Vindicator")
                .defineInRange("ominousRelicChance", 2.5D, 0, 100);
        ENDER_RELIC_CHANCE = BUILDER
                .comment("The chance of getting a Relic of Ender from a Shulker")
                .defineInRange("enderRelicChance", 5.0D, 0, 100);
        SUBMERGED_RELIC_CHANCE = BUILDER
                .comment("The chance of getting a Submerged Relic from an Elder Guardian")
                .defineInRange("submergedRelicChance", 25.0D, 0, 100);

        BUILDER.pop();


        BUILDER.push("Renames");

        UNDEAD_RENAME = BUILDER
                .comment("If true, renames 'Doom of the Undead' to 'Smite' since both have the same functionality")
                .define("undeadRename", false);
        AQUATIC_RENAME = BUILDER
                .comment("If true, renames 'Doom of the Aquatic' to 'Impaling' since both have the same functionality")
                .define("aquaticRename", false);

        BUILDER.pop();


        BUILDER.push("Other");

        ITEM_DESCRIPTIONS = BUILDER
                .comment("If true, artifact and relic items get tooltips which explain what they do")
                .define("itemDescriptions", true);

        BUILDER.pop();


        SPEC = BUILDER.build();
    }

}
