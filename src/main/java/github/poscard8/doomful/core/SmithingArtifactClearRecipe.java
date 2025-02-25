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
import net.minecraft.world.item.crafting.SmithingRecipeInput;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

/**
 * Recipe type to remove upgrades from weapons.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SmithingArtifactClearRecipe extends SmithingArtifactRecipe {

    public SmithingArtifactClearRecipe(Ingredient template, Ingredient base, Ingredient addition)
    {
        super(template, base, addition);
    }

    @Override
    public ItemStack assemble(SmithingRecipeInput recipeInput, HolderLookup.Provider lookup)
    {
        ItemStack base = recipeInput.getItem(1);
        Optional<Upgrade> optional = Upgrade.ofItem(base);
        if (optional.isEmpty()) return ItemStack.EMPTY;

        ItemStack result = base.copy();
        Upgrade.clearUpgrade(result);
        return result;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider lookup) { return Items.IRON_SWORD.getDefaultInstance(); }

    @Override
    public RecipeSerializer<?> getSerializer() { return DoomfulRecipeSerializers.SMITHING_ARTIFACT_CLEAR.get(); }


    public static class Serializer implements RecipeSerializer<SmithingArtifactClearRecipe>
    {
        static final MapCodec<SmithingArtifactClearRecipe> CODEC = RecordCodecBuilder.mapCodec(
                recipeInstance -> recipeInstance.group(
                                Ingredient.CODEC.fieldOf("template").forGetter(recipe -> recipe.template),
                                Ingredient.CODEC.fieldOf("base").forGetter(recipe -> recipe.base),
                                Ingredient.CODEC.fieldOf("addition").forGetter(recipe -> recipe.addition)
                        )
                        .apply(recipeInstance, SmithingArtifactClearRecipe::new)
        );

        static final StreamCodec<RegistryFriendlyByteBuf, SmithingArtifactClearRecipe> STREAM_CODEC = StreamCodec.of(
                SmithingArtifactClearRecipe.Serializer::toNetwork, SmithingArtifactClearRecipe.Serializer::fromNetwork
        );

        public static SmithingArtifactClearRecipe fromNetwork(RegistryFriendlyByteBuf buffer)
        {
            Ingredient template = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient base = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient addition = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);

            return new SmithingArtifactClearRecipe(template, base, addition);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buffer, SmithingArtifactClearRecipe recipe)
        {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.template);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.base);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.addition);
        }

        @Override
        public MapCodec<SmithingArtifactClearRecipe> codec() { return CODEC; }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SmithingArtifactClearRecipe> streamCodec() { return STREAM_CODEC; }

    }

}
