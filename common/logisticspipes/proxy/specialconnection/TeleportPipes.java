package logisticspipes.proxy.specialconnection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import logisticspipes.interfaces.routing.ISpecialPipedConnection;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import buildcraft.transport.Pipe;
import buildcraft.transport.TileGenericPipe;

/** Support for teleport pipes **/
public class TeleportPipes implements ISpecialPipedConnection {

	private static Class<? extends Pipe> PipeItemTeleport;
	private static Method teleportPipeMethod;
	private static Object teleportManager;

	public boolean init() {
		try {
			try {
				PipeItemTeleport = (Class<? extends Pipe>) Class.forName("buildcraft.additionalpipes.pipes.PipeItemTeleport");
			} catch (Exception e) {
				PipeItemTeleport = (Class<? extends Pipe>) Class.forName("net.minecraft.src.buildcraft.additionalpipes.pipes.PipeItemTeleport");
			}
			teleportPipeMethod = PipeItemTeleport.getMethod("getConnectedPipes", boolean.class);
			ModLoader.getLogger().fine("Additional pipes detected, adding compatibility");
			return true;
		} catch (Exception e1) {
			try {
				PipeItemTeleport = (Class<? extends Pipe>) Class.forName("buildcraft.additionalpipes.pipes.PipeItemsTeleport");
				Class<?> tpmanager = Class.forName("buildcraft.additionalpipes.pipes.TeleportManager");
				teleportManager = tpmanager.getField("instance").get(null);
				teleportPipeMethod = tpmanager.getMethod("getConnectedPipes",Class.forName("buildcraft.additionalpipes.pipes.PipeTeleport"),boolean.class);
				ModLoader.getLogger().fine("Additional pipes detected, adding compatibility");
				return true;
			} catch (Exception e2) {
				ModLoader.getLogger().fine("Additional pipes not detected: " + e2.getMessage());
				return false;
			}
		}
	}


	@Override
	public boolean isType(TileGenericPipe tile) {
		if(PipeItemTeleport.isAssignableFrom(((TileGenericPipe)tile).pipe.getClass())) return true;
		return false;
	}

	private LinkedList<? extends Pipe> getConnectedTeleportPipes(Pipe pipe) throws Exception {
		if (teleportManager != null) {
			return (LinkedList<? extends Pipe>) teleportPipeMethod.invoke(teleportManager, pipe, false);
		}
		return (LinkedList<? extends Pipe>) teleportPipeMethod.invoke(pipe, false);
	}
	
	@Override
	public List<TileGenericPipe> getConnections(TileGenericPipe tile) {
		List<TileGenericPipe> list = new ArrayList<TileGenericPipe>();
		try {
			LinkedList<? extends Pipe> pipes = getConnectedTeleportPipes(((TileGenericPipe)tile).pipe);
			for(Pipe pipe : pipes) {
				list.add(pipe.container);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
