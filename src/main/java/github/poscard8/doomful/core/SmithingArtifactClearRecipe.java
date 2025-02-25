package github.poscard8.doomful.core;

import com.google.gson.JsonObject;
import github.poscard8.doomful.registry.DoomfulRecipeSerializers;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

/**
 * Recipe type to remove upgrades from weapons.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SmithingArtifactClearRecipe extends SmithingArtifactRecipe {

    public SmithingArtifactClearRecipe(ResourceLocation id, Ingredient template, Ingredient base, Ingredient addition)
    {
        super(id, template, base, addition);
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess)
    {
        ItemStack base = container.getItem(1);
        Optional<Upgrade> optional = Upgrade.ofItem(base);
        if (optional.isEmpty()) return ItemStack.EMPTY;

        ItemStack result = base.copy();
        Upgrade.clearUpgrade(result);
        return result;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) { return Items.IRON_SWORD.getDefaultInstance(); }

    @Override
    public RecipeSerializer<?> getSerializer() { return DoomfulRecipeSerializers.SMITHING_ARTIFACT_CLEAR.get(); }


    public static class Serializer implements RecipeSerializer<SmithingArtifactClearRecipe>
    {

        public SmithingArtifactClearRecipe fromJson(ResourceLocation id, JsonObject jsonObject)
        {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getNonNull(jsonObject, "template"));
            Ingredient ingredient1 = Ingredient.fromJson(GsonHelper.getNonNull(jsonObject, "base"));
            Ingredient ingredient2 = Ingredient.fromJson(GsonHelper.getNonNull(jsonObject, "addition"));
            return new SmithingArtifactClearRecipe(id, ingredient, ingredient1, ingredient2);
        }

        public SmithingArtifactClearRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer)
        {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            Ingredient ingredient1 = Ingredient.fromNetwork(buffer);
            Ingredient ingredient2 = Ingredient.fromNetwork(buffer);
            return new SmithingArtifactClearRecipe(id, ingredient, ingredient1, ingredient2);
        }

        public void toNetwork(FriendlyByteBuf buffer, SmithingArtifactClearRecipe recipe)
        {
            recipe.template.toNetwork(buffer);
            recipe.base.toNetwork(buffer);
            recipe.addition.toNetwork(buffer);
        }
    }

}
