package github.poscard8.doomful.compat.jei;

import github.poscard8.doomful.core.SmithingArtifactRecipe;
import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.gui.ingredient.IRecipeSlotDrawable;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.extensions.vanilla.smithing.ISmithingCategoryExtension;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Most of the code is taken from JEI source code.
 */
@ParametersAreNonnullByDefault
public class SmithingArtifactCategoryExtension implements ISmithingCategoryExtension<SmithingArtifactRecipe>
{
    @Override
    public <T extends IIngredientAcceptor<T>> void setTemplate(SmithingArtifactRecipe recipe, T ingredientAcceptor) { ingredientAcceptor.addIngredients(recipe.template); }

    @Override
    public <T extends IIngredientAcceptor<T>> void setBase(SmithingArtifactRecipe recipe, T ingredientAcceptor) { ingredientAcceptor.addIngredients(recipe.base); }

    @Override
    public <T extends IIngredientAcceptor<T>> void setAddition(SmithingArtifactRecipe recipe, T ingredientAcceptor) { ingredientAcceptor.addIngredients(recipe.addition); }

    // mostly copied from JEI
    @Override
    public <T extends IIngredientAcceptor<T>> void setOutput(SmithingArtifactRecipe recipe, T ingredientAcceptor)
    {
        Ingredient templateIngredient = recipe.template;
        Ingredient baseIngredient = recipe.base;
        Ingredient additionIngredient = recipe.addition;

        ItemStack[] additions = additionIngredient.getItems();
        ItemStack[] templates = templateIngredient.getItems();
        ItemStack[] bases = baseIngredient.getItems();

        if (additions.length == 0) return;
        ItemStack addition = additions[0];

        for (ItemStack template : templates)
        {
            for (ItemStack base : bases)
            {
                Container recipeInput = createInput(template, base, addition);
                ItemStack output = recipe.assemble(recipeInput, RegistryAccess.EMPTY);
                ingredientAcceptor.addItemStack(output);
            }
        }
    }

    @Override
    public void onDisplayedIngredientsUpdate(SmithingArtifactRecipe recipe, IRecipeSlotDrawable templateSlot, IRecipeSlotDrawable baseSlot,
                                             IRecipeSlotDrawable additionSlot, IRecipeSlotDrawable outputSlot, IFocusGroup focuses)
    {
        ItemStack template = templateSlot.getDisplayedItemStack().orElse(ItemStack.EMPTY);
        ItemStack base = baseSlot.getDisplayedItemStack().orElse(ItemStack.EMPTY);
        ItemStack addition = additionSlot.getDisplayedItemStack().orElse(ItemStack.EMPTY);
        Container recipeInput = createInput(template, base, addition);
        ItemStack output = recipe.assemble(recipeInput, RegistryAccess.EMPTY);
        outputSlot.createDisplayOverrides().addItemStack(output);
    }

    static Container createInput(ItemStack template, ItemStack base, ItemStack addition)
    {
        Container container = new SimpleContainer(3);
        container.setItem(0, template);
        container.setItem(1, base);
        container.setItem(2, addition);
        return container;
    }

}
