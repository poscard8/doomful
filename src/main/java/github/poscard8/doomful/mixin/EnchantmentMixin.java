package github.poscard8.doomful.mixin;

import github.poscard8.doomful.core.DoomfulConfig;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Makes disabled enchantments unobtainable in survival. This increases other enchantments' chance at the enchanting table.
 */
@Mixin(Enchantment.class)
@SuppressWarnings("ALL")
public abstract class EnchantmentMixin
{
    private Enchantment self = (Enchantment) (Object) this;

    @Inject(method = "isTreasureOnly", at = @At("RETURN"), cancellable = true)
    private void doomful$isTreasureOnly(CallbackInfoReturnable<Boolean> ci)
    {
        if (self == Enchantments.SMITE) ci.setReturnValue(!DoomfulConfig.isSmiteEnabled());
        if (self == Enchantments.BANE_OF_ARTHROPODS) ci.setReturnValue(!DoomfulConfig.isBaneOfArthropodsEnabled());
        if (self == Enchantments.IMPALING) ci.setReturnValue(!DoomfulConfig.isImpalingEnabled());
    }

    @Inject(method = "isTradeable", at = @At("RETURN"), cancellable = true)
    private void doomful$isTradeable(CallbackInfoReturnable<Boolean> ci)
    {
        if (self == Enchantments.SMITE) ci.setReturnValue(DoomfulConfig.isSmiteEnabled());
        if (self == Enchantments.BANE_OF_ARTHROPODS) ci.setReturnValue(DoomfulConfig.isBaneOfArthropodsEnabled());
        if (self == Enchantments.IMPALING) ci.setReturnValue(DoomfulConfig.isImpalingEnabled());
    }

    @Inject(method = "isDiscoverable", at = @At("RETURN"), cancellable = true)
    private void doomful$isDiscoverable(CallbackInfoReturnable<Boolean> ci)
    {
        if (self == Enchantments.SMITE) ci.setReturnValue(DoomfulConfig.isSmiteEnabled());
        if (self == Enchantments.BANE_OF_ARTHROPODS) ci.setReturnValue(DoomfulConfig.isBaneOfArthropodsEnabled());
        if (self == Enchantments.IMPALING) ci.setReturnValue(DoomfulConfig.isImpalingEnabled());
    }

}
