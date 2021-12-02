package net.misode.mixin;

import jdk.jfr.Event;
import net.minecraft.util.profiling.jfr.JfrProfiler;
import net.minecraft.util.profiling.jfr.event.*;
import net.misode.event.FeatureGenerationEvent;
import net.misode.event.SurfaceBuildEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(JfrProfiler.class)
public abstract class JfrProviderMixin {

    @Shadow
    @Final
    private static final List<Class<? extends Event>> EVENTS = List.of(
            ChunkGenerationEvent.class,
            PacketReceivedEvent.class,
            PacketSentEvent.class,
            NetworkSummaryEvent.class,
            ServerTickTimeEvent.class,
            WorldLoadFinishedEvent.class,
            SurfaceBuildEvent.class,
            FeatureGenerationEvent.class
    );
}
