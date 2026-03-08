package net.agentefreitas.dimensionmod.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class EyeStaringParticle extends TextureSheetParticle {
    protected EyeStaringParticle(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z);
        this.lifetime = 20; // Dura 2 segundos
        this.quadSize = 2.0F; // TAMANHO DO OLHO
        this.xd = 0; this.yd = 0; this.zd = 0; // Fica parado
    }

    @Override
    public void tick() {
        super.tick();
        // Faz o fade out (transparência)
        this.alpha = 1.0F - ((float)this.age / (float)this.lifetime);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;
        public Provider(SpriteSet sprites) { this.sprites = sprites; }

        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
            EyeStaringParticle p = new EyeStaringParticle(level, x, y, z);
            p.pickSprite(this.sprites);
            return p;
        }
    }
}