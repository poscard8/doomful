package github.poscard8.doomful.core;

import github.poscard8.doomful.registry.DoomfulItems;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;

/**
 * These are added to existing loot tables without changing the rest of them.
 */
public class DoomfulLootPools
{
    public static LootPool dungeon()
    {
        int[] weights = DoomfulConfig.getDungeonLootWeights();

        return LootPool.lootPool()
                .add(LootItem.lootTableItem(DoomfulItems.ARTIFACT_UPGRADE_SMITHING_TEMPLATE.get()).setWeight(weights[0]))
                .add(LootItem.lootTableItem(DoomfulItems.RELIC_UPGRADE_SMITHING_TEMPLATE.get()).setWeight(weights[1]))
                .add(EmptyLootItem.emptyItem().setWeight(weights[2]))
                .build();
    }

    public static LootPool skeleton()
    {
        int[] weights = DoomfulConfig.getSkeletonLootWeights();

        return LootPool.lootPool()
                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                .add(LootItem.lootTableItem(DoomfulItems.UNDEAD_RELIC.get()).setWeight(weights[0]))
                .add(EmptyLootItem.emptyItem().setWeight(weights[1]))
                .build();
    }

    public static LootPool piglinBrute()
    {
        int[] weights = DoomfulConfig.getPiglinBruteLootWeights();

        return LootPool.lootPool()
                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                .add(LootItem.lootTableItem(DoomfulItems.MEATY_RELIC.get()).setWeight(weights[0]))
                .add(EmptyLootItem.emptyItem().setWeight(weights[1]))
                .build();
    }

    public static LootPool blaze()
    {
        int[] weights = DoomfulConfig.getBlazeLootWeights();

        return LootPool.lootPool()
                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                .add(LootItem.lootTableItem(DoomfulItems.ATROCIOUS_RELIC.get()).setWeight(weights[0]))
                .add(EmptyLootItem.emptyItem().setWeight(weights[1]))
                .build();
    }

    public static LootPool vindicator()
    {
        int[] weights = DoomfulConfig.getVindicatorLootWeights();

        return LootPool.lootPool()
                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                .add(LootItem.lootTableItem(DoomfulItems.OMINOUS_RELIC.get()).setWeight(weights[0]))
                .add(EmptyLootItem.emptyItem().setWeight(weights[1]))
                .build();
    }

    public static LootPool shulker()
    {
        int[] weights = DoomfulConfig.getShulkerLootWeights();

        return LootPool.lootPool()
                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                .add(LootItem.lootTableItem(DoomfulItems.ENDER_RELIC.get()).setWeight(weights[0]))
                .add(EmptyLootItem.emptyItem().setWeight(weights[1]))
                .build();
    }

    public static LootPool elderGuardian()
    {
        int[] weights = DoomfulConfig.getElderGuardianLootWeights();

        return LootPool.lootPool()
                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                .add(LootItem.lootTableItem(DoomfulItems.SUBMERGED_RELIC.get()).setWeight(weights[0]))
                .add(EmptyLootItem.emptyItem().setWeight(weights[1]))
                .build();
    }

}
