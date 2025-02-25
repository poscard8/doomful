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
import net.minecraft.world.item.crafting.SmithingRecipe;
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
    public final ResourceLocation id;
    public final Ingredient template;
    public final Ingredient base;
    public final Ingredient addition;

    public SmithingArtifactRecipe(ResourceLocation id, Ingredient template, Ingredient base, Ingredient addition)
    {
        this.id = id;
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
    public boolean matches(Container container, Level level)
    {
        return template.test(container.getItem(0)) && base.test(container.getItem(1)) && addition.test(container.getItem(2));
    }

    /**
     * No result is set if there's an invalid addition or the weapon and the artifact/relic have the same upgrade.
     */
    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess)
    {
        ItemStack base = container.getItem(1);
        ItemStack addition = container.getItem(2);

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
    public ItemStack getResultItem(RegistryAccess registryAccess)
    {
        ItemStack stack = Items.IRON_SWORD.getDefaultInstance();
        Upgrade.ARTHROPOD.applyTo(stack);
        return stack;
    }

    @Override
    public ResourceLocation getId() { return id; }

    @Override
    public RecipeSerializer<?> getSerializer() { return DoomfulRecipeSerializers.SMITHING_ARTIFACT.get(); }


    public static class Serializer implements RecipeSerializer<SmithingArtifactRecipe>
    {

        public SmithingArtifactRecipe fromJson(ResourceLocation id, JsonObject jsonObject)
        {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getNonNull(jsonObject, "template"));
            Ingredient ingredient1 = Ingredient.fromJson(GsonHelper.getNonNull(jsonObject, "base"));
            Ingredient ingredient2 = Ingredient.fromJson(GsonHelper.getNonNull(jsonObject, "addition"));
            return new SmithingArtifactRecipe(id, ingredient, ingredient1, ingredient2);
        }

        public SmithingArtifactRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer)
        {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            Ingredient ingredient1 = Ingredient.fromNetwork(buffer);
            Ingredient ingredient2 = Ingredient.fromNetwork(buffer);
            return new SmithingArtifactRecipe(id, ingredient, ingredient1, ingredient2);
        }

        public void toNetwork(FriendlyByteBuf buffer, SmithingArtifactRecipe recipe)
        {
            recipe.template.toNetwork(buffer);
            recipe.base.toNetwork(buffer);
            recipe.addition.toNetwork(buffer);
        }
    }
}
