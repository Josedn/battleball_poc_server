package io.battleball.core.gameclients;

import java.util.HashMap;

import org.java_websocket.WebSocket;

import io.battleball.net.IConnectionHandler;

public class GameClientManager implements IConnectionHandler {
	
	private HashMap<Integer, GameClient> clients;
	
	public GameClientManager()
	{
		this.clients = new HashMap<>();
	}

	@Override
	public void handleMessage(WebSocket socket, String message) {
		this.clients.get(socket.hashCode()).handleMessage(message);
	}

	@Override
	public void handleDisconnect(WebSocket socket) {
		this.clients.get(socket.hashCode()).stop();
		this.clients.remove(socket.hashCode());
	}

	@Override
	public void handleStartClient(WebSocket socket) {
		this.clients.put(socket.hashCode(), new GameClient(socket));
	}

}
