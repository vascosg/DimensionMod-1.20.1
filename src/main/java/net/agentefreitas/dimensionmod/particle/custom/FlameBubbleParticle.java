package net.agentefreitas.dimensionmod.particle.custom;

import net.agentefreitas.dimensionmod.packet.FlameBubblePacket;
import net.agentefreitas.dimensionmod.packet.Messages;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

public class FlameBubbleParticle extends TextureSheetParticle {

    protected FlameBubbleParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ);

        // Define o tamanho da partícula (0.2f é o padrão da bolha)
        this.setSize(0.02F, 0.02F);
        this.quadSize *= this.random.nextFloat() * 0.6F + 0.2F;

        // Define a velocidade inicial
        this.xd = pXSpeed * 0.2D + (Math.random() * 2.0D - 1.0D) * 0.02D;
        this.yd = pYSpeed * 0.2D + (Math.random() * 2.0D - 1.0D) * 0.02D;
        this.zd = pZSpeed * 0.2D + (Math.random() * 2.0D - 1.0D) * 0.02D;

        // Tempo de vida (duração)
        this.lifetime = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.lifetime-- <= 0) {
            this.remove();
        } else {
            // Lógica de movimento da bolha:
            // Ela sobe (yd) e perde um pouco de velocidade horizontal (friction)
            this.yd -= 0.002D;
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 0.85D;
            this.yd *= 0.85D;
            this.zd *= 0.85D;

            // Se a bolha tocar num bloco sólido (teto), ela morre
            if (this.onGround) {
                this.remove();
            }
        }

        if (!this.removed) {
            Player player = this.level.getNearestPlayer(this.x, this.y, this.z, 1.5D, p -> !p.isSpectator());

            if (player != null) {
                 System.out.println("Partícula perto de: " + player.getName().getString());
                this.onPlayerTouch(player);
            }
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet pSprites) {
            this.sprites = pSprites;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            FlameBubbleParticle particle = new FlameBubbleParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
            particle.pickSprite(this.sprites);
            return particle;
        }
    }

    private void onPlayerTouch(net.minecraft.world.entity.player.Player player) {

        if (player.level().isClientSide() && player == Minecraft.getInstance().player) {

            System.out.println("DEBUG PARTÍCULA: Tocou no player local! Enviando pacote...");

            // ENVIA O PACOTE PARA O SERVIDOR
            Messages.sendToServer(new FlameBubblePacket());

            // Efeitos visuais locais
            this.remove();
            this.level.playLocalSound(this.x, this.y, this.z, SoundEvents.GENERIC_BURN, SoundSource.PLAYERS, 0.2f, 1.0f, false);
            this.level.addParticle(ParticleTypes.SMOKE, this.x, this.y, this.z, 0, 0, 0);
        }
    }
}
