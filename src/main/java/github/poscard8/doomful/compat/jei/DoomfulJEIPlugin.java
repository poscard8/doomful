package github.poscard8.doomful.compat.jei;

import github.poscard8.doomful.Doomful;
import github.poscard8.doomful.core.SmithingArtifactClearRecipe;
import github.poscard8.doomful.core.SmithingArtifactRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.registration.IRuntimeRegistration;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmithingRecipe;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

@JeiPlugin
@SuppressWarnings("unused")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DoomfulJEIPlugin implements IModPlugin
{
    public static final ResourceLocation ID = Doomful.asResource("jei_plugin");

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration)
    {
        IModPlugin.super.registerVanillaCategoryExtensions(registration);

        registration.getSmithingCategory().addExtension(SmithingArtifactRecipe.class, new SmithingArtifactCategoryExtension());
        registration.getSmithingCategory().addExtension(SmithingArtifactClearRecipe.class, new SmithingArtifactClearCategoryExtension());
    }

    /**
     * Registering at runtime because {@code recipeManager} is needed for implementation.
     */
    @Override
    public void registerRuntime(IRuntimeRegistration registration)
    {
        IModPlugin.super.registerRuntime(registration);

        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        IRecipeManager jeiRecipeManager = registration.getRecipeManager();

        List<RecipeHolder<SmithingRecipe>> recipes = recipeManager.getAllRecipesFor(RecipeType.SMITHING).stream().filter(recipe -> recipe.id().getNamespace().equals(Doomful.ID)).toList();
        jeiRecipeManager.addRecipes(RecipeTypes.SMITHING, recipes);
    }

    @Override
    public ResourceLocation getPluginUid() { return ID; }

}
