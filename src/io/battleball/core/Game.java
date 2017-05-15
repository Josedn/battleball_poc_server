package io.battleball.core;

import io.battleball.core.gameclients.GameClientManager;
import io.battleball.net.ConnectionManager;

public class Game {
	private ConnectionManager connectionManager;
	private GameClientManager clientManager;
	
	public Game(int port)
	{
		this.clientManager = new GameClientManager();
		this.connectionManager = new ConnectionManager(port, this.clientManager);
	}
	
}
