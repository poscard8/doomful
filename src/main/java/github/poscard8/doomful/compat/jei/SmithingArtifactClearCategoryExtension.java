package github.poscard8.doomful.compat.jei;

import github.poscard8.doomful.core.SmithingArtifactClearRecipe;
import github.poscard8.doomful.core.Upgrade;
import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.gui.ingredient.IRecipeSlotDrawable;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.extensions.vanilla.smithing.ISmithingCategoryExtension;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmithingRecipeInput;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Most of the code is taken from JEI source code.
 */
@ParametersAreNonnullByDefault
public class SmithingArtifactClearCategoryExtension implements ISmithingCategoryExtension<SmithingArtifactClearRecipe>
{
    static final List<Upgrade> UPGRADES = new ArrayList<>(Upgrade.KEY_MAP.values());
    static final Random RANDOM = new Random();

    @Override
    public <T extends IIngredientAcceptor<T>> void setTemplate(SmithingArtifactClearRecipe recipe, T ingredientAcceptor) { ingredientAcceptor.addIngredients(recipe.template); }

    @Override
    public <T extends IIngredientAcceptor<T>> void setBase(SmithingArtifactClearRecipe recipe, T ingredientAcceptor)
    {
        List<ItemStack> upgradedItems = new ArrayList<>();

        for (ItemStack stack : recipe.base.getItems())
        {
            ItemStack copy = stack.copy();
            getRandomUpgrade().applyTo(copy);
            upgradedItems.add(copy);
        }

        Ingredient newIngredient = Ingredient.of(upgradedItems.stream());
        ingredientAcceptor.addIngredients(newIngredient);
    }

    @Override
    public <T extends IIngredientAcceptor<T>> void setAddition(SmithingArtifactClearRecipe recipe, T ingredientAcceptor) { ingredientAcceptor.addIngredients(recipe.addition); }

    // mostly copied from JEI
    @Override
    public <T extends IIngredientAcceptor<T>> void setOutput(SmithingArtifactClearRecipe recipe, T ingredientAcceptor)
    {
        Ingredient templateIngredient = recipe.template;
        Ingredient baseIngredient = recipe.base;
        Ingredient additionIngredient = recipe.addition;

        ItemStack[] templates = templateIngredient.getItems();
        ItemStack[] bases = baseIngredient.getItems();
        ItemStack[] additions = additionIngredient.getItems();

        if (additions.length == 0) return;
        ItemStack addition = additions[0];

        for (ItemStack template : templates)
        {
            for (ItemStack base : bases)
            {
                getRandomUpgrade().applyTo(base);
                SmithingRecipeInput recipeInput = new SmithingRecipeInput(template, base, addition);

                ItemStack output = recipe.assemble(recipeInput, RegistryAccess.EMPTY);
                ingredientAcceptor.addItemStack(output);
            }
        }
    }

    @Override
    public void onDisplayedIngredientsUpdate(SmithingArtifactClearRecipe recipe, IRecipeSlotDrawable templateSlot, IRecipeSlotDrawable baseSlot,
                                             IRecipeSlotDrawable additionSlot, IRecipeSlotDrawable outputSlot, IFocusGroup focuses)
    {
        ItemStack template = templateSlot.getDisplayedItemStack().orElse(ItemStack.EMPTY);
        ItemStack base = baseSlot.getDisplayedItemStack().orElse(ItemStack.EMPTY);
        ItemStack addition = additionSlot.getDisplayedItemStack().orElse(ItemStack.EMPTY);
        SmithingRecipeInput recipeInput = new SmithingRecipeInput(template, base, addition);

        ItemStack output = recipe.assemble(recipeInput, RegistryAccess.EMPTY);
        outputSlot.createDisplayOverrides().addItemStack(output);
    }

    static Upgrade getRandomUpgrade()
    {
        int size = UPGRADES.size();
        int index = RANDOM.nextInt(size);
        return UPGRADES.get(index);
    }


}
