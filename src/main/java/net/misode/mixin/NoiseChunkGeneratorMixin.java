package net.misode.mixin;

import net.minecraft.world.ChunkRegion;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.noise.NoiseConfig;
import net.misode.event.SurfaceBuildEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NoiseChunkGenerator.class)
public abstract class NoiseChunkGeneratorMixin {

    @Unique
    private SurfaceBuildEvent surfaceBuildEvent;

	@Inject(at = @At("HEAD"), method="buildSurface")
	private void buildSurface(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk, CallbackInfo info) {
		surfaceBuildEvent = new SurfaceBuildEvent(chunk.getPos(), region.toServerWorld().getRegistryKey());
		surfaceBuildEvent.begin();
	}

	@Inject(at = @At("RETURN"), method="buildSurface")
	private void buildSurfaceReturn(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk, CallbackInfo info) {
		surfaceBuildEvent.commit();
	}
}
