package logisticspipes.proxy.specialconnection;

import java.util.ArrayList;
import java.util.List;

import buildcraft.transport.TileGenericPipe;

import logisticspipes.interfaces.routing.ISpecialPipedConnection;

public class SpecialConnection {
	
	private List<ISpecialPipedConnection> handler = new ArrayList<ISpecialPipedConnection>();
	
	public void registerHandler(ISpecialPipedConnection connectionHandler) {
		if(connectionHandler.init()) {
			handler.add(connectionHandler);
		}
	}
	
	public List<TileGenericPipe> getConnectedPipes(TileGenericPipe tile) {
		for(ISpecialPipedConnection connectionHandler:handler) {
			if(connectionHandler.isType(tile)) {
				return connectionHandler.getConnections(tile);
			}
		}
		return new ArrayList<TileGenericPipe>();
	}
}
