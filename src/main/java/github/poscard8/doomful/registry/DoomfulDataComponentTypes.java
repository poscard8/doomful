package github.poscard8.doomful.registry;

import github.poscard8.doomful.Doomful;
import github.poscard8.doomful.core.Upgrade;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class DoomfulDataComponentTypes
{
    public static final DeferredRegister<DataComponentType<?>> ALL = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Doomful.ID);

    public static final RegistryObject<DataComponentType<Upgrade>> UPGRADE = ALL.register("upgrade", new DataComponentType.Builder<Upgrade>().persistent(Upgrade.CODEC)::build);

    public static void register(IEventBus bus) { ALL.register(bus); }
}
