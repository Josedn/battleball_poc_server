package io.battleball.core.gameclients.messages;

import io.battleball.core.gameclients.GameClient;
import io.battleball.misc.Logging;
import io.battleball.misc.Logging.LogLevel;

public class GameClientMessageHandler {
	private GameClient session;
	private ClientMessage request;

	public GameClientMessageHandler(GameClient session) {
		this.session = session;
	}

	private void login() {
		String username = request.popString();
		String look = request.popString();

		if (session.getUser() == null) {
			Logging.writeLine(username + " has logged in!", LogLevel.INFO);
			
			session.sendMessage(new ServerMessage(ServerOpcodes.LOGIN_OK));
		} else {
			Logging.writeLine("Client already logged!", LogLevel.INFO);
			session.stop();
		}
	}

	public void handleMessage(ClientMessage message) {
		this.request = message;

		switch (request.getId()) {
		case ClientOpcodes.LOGIN:
			login();
			break;

		default:
			Logging.writeLine("No handler for id: " + request.getId(), LogLevel.WARNING);
			break;
		}
	}
}
