package com.akitaattribute.essencethief.client.particle;

import com.akitaattribute.essencethief.particle.EssenceRisingParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;

public final class EssenceRisingParticle extends TextureSheetParticle {
    private EssenceRisingParticle(ClientLevel level, double x, double y, double z, double xVelocity, double yVelocity, double zVelocity,
                                  EssenceRisingParticleOptions options, SpriteSet sprites) {
        super(level, x, y, z, xVelocity, yVelocity, zVelocity);
        this.xd = xVelocity;
        this.yd = yVelocity;
        this.zd = zVelocity;
        this.gravity = 0.0F;
        this.friction = 0.96F;
        this.lifetime = 30;
        this.quadSize = 0.18F * options.scale();
        this.setColor(options.red(), options.green(), options.blue());
        this.setAlpha(0.85F);
        this.pickSprite(sprites);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age > this.lifetime - 6) {
            this.setAlpha(Math.max(0.0F, this.alpha - 0.15F));
        }
    }

    public static final class Provider implements ParticleProvider<EssenceRisingParticleOptions> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public EssenceRisingParticle createParticle(EssenceRisingParticleOptions options, ClientLevel level, double x, double y, double z,
                                                    double xVelocity, double yVelocity, double zVelocity) {
            return new EssenceRisingParticle(level, x, y, z, xVelocity, yVelocity, zVelocity, options, sprites);
        }
    }
}
