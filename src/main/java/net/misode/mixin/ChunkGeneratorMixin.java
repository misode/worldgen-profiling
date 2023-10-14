package net.misode.mixin;

import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.util.PlacedFeatureIndexer;
import net.minecraft.world.gen.structure.Structure;
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
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/PlacedFeature;generate(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockPos;)Z"),
			method="generateFeatures",
			locals = LocalCapture.CAPTURE_FAILEXCEPTION
	)
	private void generateFeatures(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor, CallbackInfo info, ChunkPos chunkPos, ChunkSectionPos chunkSectionPos, BlockPos blockPos, Registry<Structure> registry, Map<Integer, List<Structure>> map, List<PlacedFeatureIndexer.IndexedFeatures> list, ChunkRandom chunkRandom, long l, Set<Biome> set, int i, Registry<PlacedFeature> registry2, int j, int step, int m, IntSet intSet, int n, int[] is, PlacedFeatureIndexer.IndexedFeatures indexedFeatures2, int o, int p, PlacedFeature placedFeature, Supplier<String> supplier2) {
		String featureKey = registry2.getKey(placedFeature).map(k -> k.getValue().toString()).orElse("unregistered");
		featureGenerationEvent = new FeatureGenerationEvent(chunkPos, world.toServerWorld().getRegistryKey(), featureKey, step);
		featureGenerationEvent.begin();
	}

	@Inject(
			at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/gen/feature/PlacedFeature;generate(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockPos;)Z"),
			method="generateFeatures"
	)
	private void generateFeaturesCommit(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor, CallbackInfo info) {
		if (featureGenerationEvent != null) {
			featureGenerationEvent.commit();
		}
	}
}
