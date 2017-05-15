package io.battleball.core.gameclients;

import org.java_websocket.WebSocket;

import io.battleball.core.User;
import io.battleball.core.gameclients.messages.ClientMessage;
import io.battleball.core.gameclients.messages.GameClientMessageHandler;
import io.battleball.core.gameclients.messages.ServerMessage;

public class GameClient {
	private WebSocket connection;
	private GameClientMessageHandler messageHandler;
	private User user;

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	public GameClient(WebSocket connection) {
		this.connection = connection;
		this.messageHandler = new GameClientMessageHandler(this);
		this.user = null;
	}

	public void stop() {
		if (user != null) {
			user.onDisconnect();
		}
		if (connection != null) {
			if (connection.isOpen())
				connection.close();
			connection = null;
		}
		if (messageHandler != null) {
			messageHandler.destroy();
			messageHandler = null;
		}
	}

	public void handleMessage(String rawMessage) {
		ClientMessage message = new ClientMessage(rawMessage);
		this.messageHandler.handleMessage(message);
	}

	public void sendMessage(ServerMessage message) {
		if (connection.isOpen()) {
			connection.send(message.toString());
		} else {
			stop();
		}
	}

}
