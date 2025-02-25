package github.poscard8.doomful.core;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

/**
 * Since artifact and relic items have the same functionality, this class covers both of them.
 */
@ParametersAreNonnullByDefault
public class ArtifactItem extends Item
{
    public final Upgrade upgrade;

    public ArtifactItem(Upgrade upgrade)
    {
        super(new Properties().rarity(Rarity.UNCOMMON));
        this.upgrade = upgrade;
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> components, TooltipFlag tooltipFlag)
    {
        super.appendHoverText(stack, ctx, components, tooltipFlag);

        if (DoomfulConfig.hasItemDescriptions())
        {
            components.add(Component.translatable("doomful.tooltip.artifact", upgrade.getName(), upgrade.targetName).withStyle(ChatFormatting.GRAY));

            if (upgrade.hasDescription())
            {
                components.add(Component.empty());
                components.addAll(upgrade.description);
            }
        }
    }

}
