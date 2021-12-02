package net.misode.mixin;

import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.random.ChunkRandom;
import net.misode.event.FeatureGenerationEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

@Mixin(ChunkGenerator.class)
public abstract class ChunkGeneratorMixin {

	@Unique
	private FeatureGenerationEvent featureGenerationEvent;

	@Inject(
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/PlacedFeature;generate(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Ljava/util/Random;Lnet/minecraft/util/math/BlockPos;)Z"),
			method="generateFeatures",
			locals = LocalCapture.CAPTURE_FAILEXCEPTION
	)
	private void generateFeatures(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor, CallbackInfo info, ChunkPos chunkPos, ChunkSectionPos chunkSectionPos, BlockPos blockPos, Map<Integer, List<StructureFeature<?>>> map, List<BiomeSource.class_6827> list, ChunkRandom chunkRandom, long l, Set<Biome> biomes, int i, Registry<PlacedFeature> registry, Registry<StructureFeature<?>> registry2, int j, int step, IntSet list2, int n, int[] structureFeature, BiomeSource.class_6827 supplier, int exception, int crashReport, PlacedFeature placedFeature, Supplier<String> supplier2) {
	    String featureKey = registry.getKey(placedFeature).map(k -> k.getValue().toString()).orElse("unregistered");
		featureGenerationEvent = new FeatureGenerationEvent(chunkPos, world.toServerWorld().getRegistryKey(), featureKey, step);
		featureGenerationEvent.begin();
	}

	@Inject(
			at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/gen/feature/PlacedFeature;generate(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Ljava/util/Random;Lnet/minecraft/util/math/BlockPos;)Z"),
			method="generateFeatures"
	)
	private void generateFeaturesCommit(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor, CallbackInfo info) {
		if (featureGenerationEvent != null) {
			featureGenerationEvent.commit();
		}
	}
}
