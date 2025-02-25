package github.poscard8.doomful.client.particle;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Particle displayed when a mob is killed with an upgraded weapon of that type.
 */
@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DoomParticle extends TextureSheetParticle
{
    public DoomParticle(ClientLevel level, double x, double y, double z, double dx, double dy, double dz)
    {
        super(level, x, y, z, dx, dy, dz);
        this.quadSize *= 2.5F;
        this.friction = 0.96F;
        this.hasPhysics = false;
    }

    @Override
    public ParticleRenderType getRenderType() { return ParticleRenderType.PARTICLE_SHEET_OPAQUE; }

    @Override
    protected int getLightColor(float v) { return 15 << 20 | super.getLightColor(v); }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType>
    {
        public final SpriteSet sprite;

        public Provider(SpriteSet sprite) { this.sprite = sprite; }

        @Override
        @Nullable
        public DoomParticle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double dx, double dy, double dz)
        {
            DoomParticle particle = new DoomParticle(level, x, y, z, dx, dy, dz);

            particle.pickSprite(sprite);
            particle.setParticleSpeed(0.0D, 0.1D, 0.0D);
            particle.setLifetime(60);

            return particle;
        }

    }

}
