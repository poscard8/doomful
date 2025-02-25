package github.poscard8.doomful.registry;

import github.poscard8.doomful.Doomful;
import github.poscard8.doomful.core.SmithingArtifactClearRecipe;
import github.poscard8.doomful.core.SmithingArtifactRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DoomfulRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> ALL = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Doomful.ID);

    public static final RegistryObject<RecipeSerializer<SmithingArtifactRecipe>> SMITHING_ARTIFACT = ALL.register("smithing_artifact", SmithingArtifactRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<SmithingArtifactClearRecipe>> SMITHING_ARTIFACT_CLEAR = ALL.register("smithing_artifact_clear", SmithingArtifactClearRecipe.Serializer::new);

    public static void register(IEventBus bus) { ALL.register(bus); }

}
