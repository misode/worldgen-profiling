package net.misode.event;

import jdk.jfr.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.profiling.jfr.JfrProfiler;
import net.minecraft.world.World;

@Name(SurfaceBuildEvent.EVENT_NAME)
@Label("Surface Build")
@Category({JfrProfiler.MINECRAFT, JfrProfiler.WORLD_GENERATION})
public class SurfaceBuildEvent extends Event {
    public static final String EVENT_NAME = "minecraft.ChunkSurfaceBuild";
    public static final EventType TYPE = EventType.getEventType(SurfaceBuildEvent.class);

    @Name(value="chunkPosX")
    @Label(value="Chunk X Position")
    public final int chunkPosX;

    @Name(value="chunkPosZ")
    @Label(value="Chunk Z Position")
    public final int chunkPosZ;

    @Name(value="level")
    @Label(value="Level")
    public final String level;

    public SurfaceBuildEvent(ChunkPos chunkPos, RegistryKey<World> level) {
        this.chunkPosX = chunkPos.x;
        this.chunkPosZ = chunkPos.z;
        this.level = level.getValue().toString();
    }
}
