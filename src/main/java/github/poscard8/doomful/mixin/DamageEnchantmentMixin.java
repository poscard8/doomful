package github.poscard8.doomful.mixin;

import github.poscard8.doomful.core.DoomfulConfig;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Disables Smite and Bane of Arthropods. Can be re-enabled via config.
 */
@Mixin(DamageEnchantment.class)
@SuppressWarnings("ALL")
public abstract class DamageEnchantmentMixin
{
    private Enchantment self = (Enchantment) (Object) this;

    @Inject(method = "getDamageBonus", at = @At("RETURN"), cancellable = true)
    private void doomful$getDamageBonus(int level, MobType mobType, CallbackInfoReturnable<Float> ci)
    {
        if (self == Enchantments.SMITE && !DoomfulConfig.isSmiteEnabled()) ci.setReturnValue(0.0F);
        if (self == Enchantments.BANE_OF_ARTHROPODS && !DoomfulConfig.isBaneOfArthropodsEnabled()) ci.setReturnValue(0.0F);
    }

}
