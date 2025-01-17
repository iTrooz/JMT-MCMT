package org.jmt.mcmt.serdes.filter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jmt.mcmt.serdes.ISerDesHookType;
import org.jmt.mcmt.serdes.SerDesRegistry;
import org.jmt.mcmt.serdes.pools.ChunkLockPool;
import org.jmt.mcmt.serdes.pools.ISerDesPool;
import org.jmt.mcmt.serdes.pools.ISerDesPool.ISerDesOptions;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;

public class PistonFilter implements ISerDesFilter {

	ISerDesPool clp;
	ISerDesOptions config;
	
	@Override
	public void init() {
		//TODO Figure out if piston specific chunklock can be used (or just move to using the 
		clp = SerDesRegistry.getOrCreatePool("LEGACY", ChunkLockPool::new);
		Map<String, Object> cfg = new HashMap<>();
		cfg.put("range", "1");
		config = clp.compileOptions(cfg);
	}
	
	@Override
	public void serialise(Runnable task, Object obj, BlockPos bp, Level w, ISerDesHookType hookType) {
		clp.serialise(task, obj, bp, w, config);
	}
	
	@Override
	public Set<Class<?>> getTargets() {
		Set<Class<?>> out = new HashSet<Class<?>>();
		out.add(PistonMovingBlockEntity.class);
		return out;
	}

}
