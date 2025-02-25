package github.poscard8.doomful.event;

import github.poscard8.doomful.core.DoomfulLootPools;
import github.poscard8.doomful.core.Upgrade;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber
@SuppressWarnings("unused")
public class CommonEvents
{
    @SubscribeEvent
    static void onLootTableLoad(LootTableLoadEvent event)
    {
        if (!event.getName().getNamespace().equals(ResourceLocation.DEFAULT_NAMESPACE)) return; // only working with vanilla loot tables
        String name = event.getName().getPath();
        LootTable lootTable = event.getTable();

        switch (name)
        {
            case "chests/simple_dungeon" -> lootTable.addPool(DoomfulLootPools.dungeon());
            case "entities/skeleton" -> lootTable.addPool(DoomfulLootPools.skeleton());
            case "entities/piglin_brute" -> lootTable.addPool(DoomfulLootPools.piglinBrute());
            case "entities/blaze", "entities/breeze" -> lootTable.addPool(DoomfulLootPools.blazeAndBreeze());
            case "entities/vindicator" -> lootTable.addPool(DoomfulLootPools.vindicator());
            case "entities/shulker" -> lootTable.addPool(DoomfulLootPools.shulker());
            case "entities/elder_guardian" -> lootTable.addPool(DoomfulLootPools.elderGuardian());
            default -> {}
        }
    }

    @SubscribeEvent
    static void onLivingDamage(LivingDamageEvent event)
    {
        DamageSource damageSource = event.getSource();
        Entity entity = event.getEntity();

        if (damageSource.getEntity() instanceof Player player && entity instanceof Mob mob)
        {
            ItemStack stack = player.getMainHandItem();
            Optional<Upgrade> optional = Upgrade.ofItem(stack);
            optional.ifPresent(upgrade ->
            {
                float multiplier = upgrade.getDamageMultiplier(mob);
                float base = event.getAmount();
                float finalAmount = base * multiplier;

                event.setAmount(finalAmount);
            });
        }
    }

    /**
     * Handling particle generation on the server.
     */
    @SubscribeEvent
    static void onLivingDeath(LivingDeathEvent event)
    {
        DamageSource damageSource = event.getSource();
        Entity entity = event.getEntity();
        Level level = entity.level();

        if (damageSource.getEntity() instanceof Player player && entity instanceof Mob mob)
        {
            ItemStack stack = player.getMainHandItem();
            Optional<Upgrade> optional = Upgrade.ofItem(stack);
            optional.ifPresent(upgrade ->
            {
                if (upgrade.targetsMob(mob))
                {
                    SimpleParticleType particleType = upgrade.getParticleType();
                    if (level instanceof ServerLevel serverLevel) serverLevel.sendParticles(particleType, entity.getX(), entity.getEyeY(), entity.getZ(), 1, 0, 0, 0, 1);
                }
            });
        }
    }

    @SubscribeEvent
    static void onItemTooltip(ItemTooltipEvent event)
    {
        ItemStack stack = event.getItemStack();
        Optional<Upgrade> optional = Upgrade.ofItem(stack);
        optional.ifPresent(upgrade -> event.getToolTip().addAll(1, upgrade.getTooltips()));
    }

}
