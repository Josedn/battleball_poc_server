package io.battleball.core;

import io.battleball.core.gameclients.GameClient;
import io.battleball.misc.Logging;
import io.battleball.misc.Logging.LogLevel;

public class User {
	private int id;
	private String username;
	private String look;
	private GameClient session;
	private boolean disconnected;

	/**
	 * @param id
	 * @param username
	 * @param look
	 * @param session
	 */
	public User(int id, String username, String look, GameClient session) {
		this.id = id;
		this.username = username;
		this.look = look;
		this.session = session;
		this.disconnected = false;
	}

	public void onDisconnect() {
		if (!disconnected)
		{
			Logging.writeLine(username + " has logged out", LogLevel.INFO);
		}
	}

}
