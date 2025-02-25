package github.poscard8.doomful.core;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import github.poscard8.doomful.registry.DoomfulRecipeSerializers;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

/**
 * Recipe type to apply mod upgrades to weapons.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SmithingArtifactRecipe implements SmithingRecipe
{
    public final Ingredient template;
    public final Ingredient base;
    public final Ingredient addition;

    public SmithingArtifactRecipe(Ingredient template, Ingredient base, Ingredient addition)
    {
        this.template = template;
        this.base = base;
        this.addition = addition;
    }

    @Override
    public boolean isTemplateIngredient(ItemStack stack) { return template.test(stack); }

    @Override
    public boolean isBaseIngredient(ItemStack stack) { return base.test(stack); }

    @Override
    public boolean isAdditionIngredient(ItemStack stack) { return addition.test(stack); }

    @Override
    public boolean matches(SmithingRecipeInput recipeInput, Level level)
    {
        return template.test(recipeInput.getItem(0)) && base.test(recipeInput.getItem(1)) && addition.test(recipeInput.getItem(2));
    }

    /**
     * No result is set if there's an invalid addition or the weapon and the artifact/relic have the same upgrade.
     */
    @Override
    public ItemStack assemble(SmithingRecipeInput recipeInput, HolderLookup.Provider lookup)
    {
        ItemStack base = recipeInput.getItem(1);
        ItemStack addition = recipeInput.getItem(2);

        Optional<Upgrade> existingOptional = Upgrade.ofItem(base);
        Optional<Upgrade> newOptional = Upgrade.ofArtifact(addition);

        if (newOptional.isEmpty()) return ItemStack.EMPTY;

        @Nullable Upgrade existingUpgrade = existingOptional.orElse(null);
        Upgrade newUpgrade = newOptional.get();

        if (newUpgrade == existingUpgrade) return ItemStack.EMPTY;

        ItemStack result = base.copy();
        newUpgrade.applyTo(result);
        return result;
    }

    /**
     * Example item.
     */
    @Override
    public ItemStack getResultItem(HolderLookup.Provider p_331967_)
    {
        ItemStack stack = Items.IRON_SWORD.getDefaultInstance();
        Upgrade.ARTHROPOD.applyTo(stack);
        return stack;
    }

    @Override
    public RecipeSerializer<?> getSerializer() { return DoomfulRecipeSerializers.SMITHING_ARTIFACT.get(); }


    public static class Serializer implements RecipeSerializer<SmithingArtifactRecipe>
    {

        static final MapCodec<SmithingArtifactRecipe> CODEC = RecordCodecBuilder.mapCodec(
                recipeInstance -> recipeInstance.group(
                                Ingredient.CODEC.fieldOf("template").forGetter(recipe -> recipe.template),
                                Ingredient.CODEC.fieldOf("base").forGetter(recipe -> recipe.base),
                                Ingredient.CODEC.fieldOf("addition").forGetter(recipe -> recipe.addition)
                        )
                        .apply(recipeInstance, SmithingArtifactRecipe::new)
        );

        static final StreamCodec<RegistryFriendlyByteBuf, SmithingArtifactRecipe> STREAM_CODEC = StreamCodec.of(
                SmithingArtifactRecipe.Serializer::toNetwork, SmithingArtifactRecipe.Serializer::fromNetwork
        );

        public static SmithingArtifactRecipe fromNetwork(RegistryFriendlyByteBuf buffer)
        {
            Ingredient template = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient base = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient addition = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);

            return new SmithingArtifactRecipe(template, base, addition);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buffer, SmithingArtifactRecipe recipe)
        {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.template);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.base);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.addition);
        }

        @Override
        public MapCodec<SmithingArtifactRecipe> codec() { return CODEC; }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SmithingArtifactRecipe> streamCodec() { return STREAM_CODEC; }

    }
}
