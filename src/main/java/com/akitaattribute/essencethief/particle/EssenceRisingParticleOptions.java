package com.akitaattribute.essencethief.particle;

import com.akitaattribute.essencethief.api.EssenceColor;
import com.akitaattribute.essencethief.registry.ModParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Locale;

public record EssenceRisingParticleOptions(float red, float green, float blue, float scale) implements ParticleOptions {
    public static final Codec<EssenceRisingParticleOptions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.FLOAT.fieldOf("red").forGetter(EssenceRisingParticleOptions::red),
        Codec.FLOAT.fieldOf("green").forGetter(EssenceRisingParticleOptions::green),
        Codec.FLOAT.fieldOf("blue").forGetter(EssenceRisingParticleOptions::blue),
        Codec.FLOAT.fieldOf("scale").forGetter(EssenceRisingParticleOptions::scale)
    ).apply(instance, EssenceRisingParticleOptions::new));

    public static final Deserializer<EssenceRisingParticleOptions> DESERIALIZER = new Deserializer<>() {
        @Override
        public EssenceRisingParticleOptions fromCommand(ParticleType<EssenceRisingParticleOptions> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float red = reader.readFloat();
            reader.expect(' ');
            float green = reader.readFloat();
            reader.expect(' ');
            float blue = reader.readFloat();
            reader.expect(' ');
            float scale = reader.readFloat();
            return new EssenceRisingParticleOptions(red, green, blue, scale);
        }

        @Override
        public EssenceRisingParticleOptions fromNetwork(ParticleType<EssenceRisingParticleOptions> type, FriendlyByteBuf buffer) {
            return new EssenceRisingParticleOptions(buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
        }
    };

    public EssenceRisingParticleOptions {
        red = clamp01(red);
        green = clamp01(green);
        blue = clamp01(blue);
        scale = Math.max(0.05F, scale);
    }

    public static EssenceRisingParticleOptions fromColor(EssenceColor color) {
        return new EssenceRisingParticleOptions(color.redFloat(), color.greenFloat(), color.blueFloat(), 1.0F);
    }

    @Override
    public ParticleType<?> getType() {
        return ModParticles.ESSENCE_RISING.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeFloat(red);
        buffer.writeFloat(green);
        buffer.writeFloat(blue);
        buffer.writeFloat(scale);
    }

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.3f %.3f %.3f %.3f", getType(), red, green, blue, scale);
    }

    private static float clamp01(float value) {
        return Math.max(0.0F, Math.min(1.0F, value));
    }
}
