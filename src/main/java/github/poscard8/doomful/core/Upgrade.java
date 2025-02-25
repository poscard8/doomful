package github.poscard8.doomful.core;

import com.mojang.serialization.Codec;
import github.poscard8.doomful.registry.DoomfulDataComponentTypes;
import github.poscard8.doomful.registry.DoomfulParticleTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static github.poscard8.doomful.Doomful.asResource;

/**
 * Fundamental mechanic of the mod.
 */
public class Upgrade
{
    public static final Map<ResourceLocation, Upgrade> KEY_MAP = new HashMap<>();
    public static final Codec<Upgrade> CODEC = Codec.stringResolver(upgrade -> upgrade.key.toString(), string -> KEY_MAP.get(ResourceLocation.tryParse(string)));

    public static final Upgrade ARTHROPOD = new Upgrade(asResource("arthropod"), Type.ARTIFACT, EntityTypeTags.SENSITIVE_TO_BANE_OF_ARTHROPODS, DoomfulConfig::getArthropodDamageMultiplier, Upgrade::getArthropodName, getTargetName("arthropod"), DoomfulParticleTypes.ARTHROPOD);
    public static final Upgrade CUBOID = new Upgrade(asResource("cuboid"), Type.ARTIFACT, mob -> mob.getType().is(DoomfulTags.EntityTypes.CUBOIDS), DoomfulConfig::getCuboidDamageMultiplier, Upgrade::getCuboidName, getTargetName("cuboid"), cuboidDescription(), DoomfulParticleTypes.CUBOID);
    public static final Upgrade UNDEAD = new Upgrade(asResource("undead"), Type.RELIC, EntityTypeTags.SENSITIVE_TO_SMITE, DoomfulConfig::getUndeadDamageMultiplier, Upgrade::getUndeadName, getTargetName("undead"), DoomfulParticleTypes.UNDEAD);
    public static final Upgrade PIG = new Upgrade(asResource("pig"), Type.RELIC, DoomfulTags.EntityTypes.PIGS, DoomfulConfig::getPigDamageMultiplier, Upgrade::getPigName, getTargetName("pig"), DoomfulParticleTypes.PIG);
    public static final Upgrade CONSTRUCT = new Upgrade(asResource("construct"), Type.RELIC, mob -> mob.getType().is(DoomfulTags.EntityTypes.CONSTRUCTS), DoomfulConfig::getConstructDamageMultiplier, Upgrade::getConstructName, getTargetName("construct"), constructDescription(), DoomfulParticleTypes.CONSTRUCT);
    public static final Upgrade ILLAGER = new Upgrade(asResource("illager"), Type.RELIC, EntityTypeTags.ILLAGER, DoomfulConfig::getIllagerDamageMultiplier, Upgrade::getIllagerName, getTargetName("illager"), DoomfulParticleTypes.ILLAGER);
    public static final Upgrade ENDER = new Upgrade(asResource("ender"), Type.RELIC, DoomfulTags.EntityTypes.ENDER, DoomfulConfig::getEnderDamageMultiplier, Upgrade::getEnderName, getTargetName("ender"), DoomfulParticleTypes.ENDER);
    public static final Upgrade AQUATIC = new Upgrade(asResource("aquatic"), Type.RELIC, EntityTypeTags.SENSITIVE_TO_IMPALING, DoomfulConfig::getAquaticDamageMultiplier, Upgrade::getAquaticName, getTargetName("aquatic"), DoomfulParticleTypes.AQUATIC);

    static final String NBT_KEY = "doomful_upgrade";

    public final ResourceLocation key;
    public final Type type;
    public final Predicate<Mob> mobPredicate;
    public final Supplier<Float> multiplierGetter;
    public final Supplier<Component> nameGetter;
    public final Component targetName;
    public final List<Component> description;
    public final Supplier<SimpleParticleType> particleTypeGetter;

    public Upgrade(ResourceLocation key, Type type, TagKey<EntityType<?>> tag, Supplier<Float> multiplierGetter, Supplier<Component> nameGetter, Component targetName, Supplier<SimpleParticleType> particleTypeGetter)
    {
        this(key, type, mob -> mob.getType().is(tag), multiplierGetter, nameGetter, targetName, particleTypeGetter);
    }

    public Upgrade(ResourceLocation key, Type type, Predicate<Mob> mobPredicate, Supplier<Float> multiplierGetter, Supplier<Component> nameGetter, Component targetName, Supplier<SimpleParticleType> particleTypeGetter)
    {
        this(key, type, mobPredicate, multiplierGetter, nameGetter, targetName, List.of(), particleTypeGetter);
    }

    public Upgrade(ResourceLocation key, Type type, Predicate<Mob> mobPredicate, Supplier<Float> multiplierGetter, Supplier<Component> nameGetter, Component targetName, List<Component> description, Supplier<SimpleParticleType> particleTypeGetter)
    {
        this.key = key;
        this.type = type;
        this.mobPredicate = mobPredicate;
        this.multiplierGetter = multiplierGetter;
        this.nameGetter = nameGetter;
        this.targetName = targetName;
        this.description = description;
        this.particleTypeGetter = particleTypeGetter;

        KEY_MAP.put(key, this);
    }

    public static Optional<Upgrade> ofArtifact(ItemStack stack)
    {
        return stack.getItem() instanceof ArtifactItem artifactItem ? Optional.of(artifactItem.upgrade) : Optional.empty();
    }

    /**
     * Should not be used on artifact items.
     */
    public static Optional<Upgrade> ofItem(ItemStack stack) { return Optional.ofNullable(stack.getComponents().get(DoomfulDataComponentTypes.UPGRADE.get())); }

    public static void clearUpgrade(ItemStack stack) { stack.set(DoomfulDataComponentTypes.UPGRADE.get(), null); }

    /**
     * Names are set by methods instead of fields since some of them are determined by the mod config.
     */
    static Component getArthropodName() { return Component.translatable("enchantment.minecraft.bane_of_arthropods").withStyle(ChatFormatting.DARK_RED); }

    static Component getCuboidName() { return Component.translatable("doomful.upgrade.cuboid").withStyle(style -> style.withColor(0x8CD647)); }

    static Component getUndeadName()
    {
        String string = DoomfulConfig.isUndeadUpgradeRenamed() ? "enchantment.minecraft.smite" : "doomful.upgrade.undead";
        return Component.translatable(string).withStyle(ChatFormatting.GREEN);
    }

    static Component getPigName() { return Component.translatable("doomful.upgrade.pig").withStyle(style -> style.withColor(0xE87487)); }

    static Component getConstructName() { return Component.translatable("doomful.upgrade.construct").withStyle(ChatFormatting.DARK_GRAY); }

    static Component getIllagerName() { return Component.translatable("doomful.upgrade.illager").withStyle(ChatFormatting.YELLOW); }

    static Component getEnderName() { return Component.translatable("doomful.upgrade.ender").withStyle(ChatFormatting.DARK_PURPLE); }

    static Component getAquaticName()
    {
        String string = DoomfulConfig.isAquaticUpgradeRenamed() ? "enchantment.minecraft.impaling" : "doomful.upgrade.aquatic";
        return Component.translatable(string).withStyle(ChatFormatting.DARK_AQUA);
    }

    static Component getTargetName(String string) { return Component.translatable("doomful.target." + string); }

    /**
     * Description is necessary for cuboids and constructs.
     */
    static List<Component> cuboidDescription()
    {
        List<Component> components = new ArrayList<>();

        components.add(Component.translatable("doomful.upgrade.cuboid.description").withStyle(ChatFormatting.GRAY));
        components.add(CommonComponents.space().append(EntityType.SLIME.getDescription().copy().withStyle(ChatFormatting.BLUE)));
        components.add(CommonComponents.space().append(EntityType.MAGMA_CUBE.getDescription().copy().withStyle(ChatFormatting.BLUE)));

        return components;
    }

    static List<Component> constructDescription()
    {
        List<Component> components = new ArrayList<>();

        components.add(Component.translatable("doomful.upgrade.construct.description").withStyle(ChatFormatting.GRAY));
        components.add(CommonComponents.space().append(EntityType.CREEPER.getDescription().copy().withStyle(ChatFormatting.BLUE)));
        components.add(CommonComponents.space().append(EntityType.IRON_GOLEM.getDescription().copy().withStyle(ChatFormatting.BLUE)));
        components.add(CommonComponents.space().append(EntityType.SNOW_GOLEM.getDescription().copy().withStyle(ChatFormatting.BLUE)));
        components.add(CommonComponents.space().append(EntityType.BLAZE.getDescription().copy().withStyle(ChatFormatting.BLUE)));
        components.add(CommonComponents.space().append(EntityType.RAVAGER.getDescription().copy().withStyle(ChatFormatting.BLUE)));
        components.add(CommonComponents.space().append(EntityType.WITHER.getDescription().copy().withStyle(ChatFormatting.BLUE)));

        return components;
    }


    public ArtifactItem asArtifact() { return new ArtifactItem(this); }

    public void applyTo(ItemStack stack) { stack.set(DoomfulDataComponentTypes.UPGRADE.get(), this); }

    public boolean targetsMob(Mob mob) { return mobPredicate.test(mob); }

    /**
     * Can be configured.
     */
    public float getDamageMultiplier() { return multiplierGetter.get(); }

    public float getDamageMultiplier(Mob mob) { return targetsMob(mob) ? getDamageMultiplier() : 1; }

    public Component getName() { return nameGetter.get(); }

    /**
     * Tooltips displayed on upgraded weapons, not artifact or smithing template items.
     */
    public List<Component> getTooltips()
    {
        List<Component> components = new ArrayList<>();

        components.add(Component.translatable("doomful.upgrade.label").withStyle(ChatFormatting.GRAY));
        components.add(CommonComponents.space().append(getName()));
        return components;
    }

    public boolean hasDescription() { return !description.isEmpty(); }

    public SimpleParticleType getParticleType() { return particleTypeGetter.get(); }

    /**
     * Artifact or relic type.
     */
    public record Type(Component appliesTo, Component ingredients, Component upgradeDescription, Component baseSlotDescription,
                       Component additionsSlotDescription, List<ResourceLocation> baseSlotEmptyIcons, List<ResourceLocation> additionalSlotEmptyIcons)
    {
        static final Component APPLIES_TO = Component.translatable("doomful.upgrade.applies_to").withStyle(ChatFormatting.BLUE);
        static final Component BASE_SLOT_DESCRIPTION = Component.translatable("doomful.upgrade.base_slot_description");
        static final List<ResourceLocation> BASE_SLOT_EMPTY_ICONS = List.of(getEmptySlot("sword"), getEmptySlot("axe"), getEmptySlot("bow"), getEmptySlot("crossbow"), getEmptySlot("trident"));

        static final Component ARTIFACT_INGREDIENTS = Component.translatable("doomful.upgrade.artifact_ingredients").withStyle(ChatFormatting.BLUE);
        static final Component ARTIFACT_UPGRADE_DESCRIPTION = Component.translatable("doomful.upgrade.artifact_upgrade_description").withStyle(ChatFormatting.GRAY);
        static final Component ARTIFACT_ADDITIONAL_SLOT_DESCRIPTION = Component.translatable("doomful.upgrade.artifact_additional_slot_description");
        static final Component RELIC_INGREDIENTS = Component.translatable("doomful.upgrade.relic_ingredients").withStyle(ChatFormatting.BLUE);
        static final Component RELIC_UPGRADE_DESCRIPTION = Component.translatable("doomful.upgrade.relic_upgrade_description").withStyle(ChatFormatting.GRAY);
        static final Component RELIC_ADDITIONAL_SLOT_DESCRIPTION = Component.translatable("doomful.upgrade.relic_additional_slot_description");

        static final List<ResourceLocation> ARTIFACT_ADDITIONAL_SLOT_EMPTY_ICONS = List.of(getEmptySlot("spider_artifact"), getEmptySlot("cubic_artifact"), getEmptySlot("amethyst_shard"));
        static final List<ResourceLocation> RELIC_ADDITIONAL_SLOT_EMPTY_ICONS = List.of(getEmptySlot("undead_relic"), getEmptySlot("meaty_relic"), getEmptySlot("atrocious_relic"), getEmptySlot("ominous_relic"), getEmptySlot("ender_relic"), getEmptySlot("submerged_relic"), getEmptySlot("amethyst_shard"));

        public static Type ARTIFACT = new Type(APPLIES_TO, ARTIFACT_INGREDIENTS, ARTIFACT_UPGRADE_DESCRIPTION, BASE_SLOT_DESCRIPTION, ARTIFACT_ADDITIONAL_SLOT_DESCRIPTION, BASE_SLOT_EMPTY_ICONS, ARTIFACT_ADDITIONAL_SLOT_EMPTY_ICONS);
        public static Type RELIC = new Type(APPLIES_TO, RELIC_INGREDIENTS, RELIC_UPGRADE_DESCRIPTION, BASE_SLOT_DESCRIPTION, RELIC_ADDITIONAL_SLOT_DESCRIPTION, BASE_SLOT_EMPTY_ICONS, RELIC_ADDITIONAL_SLOT_EMPTY_ICONS);

        static ResourceLocation getEmptySlot(String name) { return asResource("item/empty_slot_" + name); }

        public SmithingTemplateItem asSmithingTemplate()
        {
            return new SmithingTemplateItem(appliesTo(), ingredients(), upgradeDescription(), baseSlotDescription(), additionsSlotDescription(), baseSlotEmptyIcons(), additionalSlotEmptyIcons());
        }

    }

}
