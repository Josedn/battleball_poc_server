package io.battleball.core.gameclients.messages;

import io.battleball.BattleEnvironment;
import io.battleball.core.User;
import io.battleball.core.gameclients.GameClient;
import io.battleball.core.rooms.MapModel;
import io.battleball.core.rooms.RoomUser;
import io.battleball.misc.Logging;
import io.battleball.misc.Logging.LogLevel;

public class GameClientMessageHandler {
	private GameClient session;
	private ClientMessage request;
	private static int NextId = 0;

	public GameClientMessageHandler(GameClient session) {
		this.session = session;
	}

	private void login() {
		String username = request.popString();
		String look = request.popString();

		if (session.getUser() == null) {
			Logging.writeLine(username + " has logged in!", LogLevel.INFO);
			session.setUser(new User(NextId++, username, look, session));

			session.sendMessage(new ServerMessage(ServerOpcodes.LOGIN_OK));
		} else {
			Logging.writeLine("Client already logged!", LogLevel.INFO);
			session.stop();
		}
	}

	private void requestMap() {
		Logging.writeLine("Sending map to " + session.getUser().getUsername(), LogLevel.DEBUG);
		ServerMessage response = new ServerMessage(ServerOpcodes.MAP_DATA);
		MapModel model = BattleEnvironment.getGame().mapModel;

		response.appendInt(model.width);
		response.appendInt(model.height);
		for (int i = 0; i < model.width; i++) {
			for (int j = 0; j < model.height; j++) {
				response.appendInt(model.getLayer()[i][j]);
			}
		}

		session.sendMessage(response);

		BattleEnvironment.getGame().room.addPlayerToRoom(session);
	}

	public void destroy() {
		this.session = null;
		this.request = null;
	}

	private void requestChat() {
		String message = request.popString();
		RoomUser user = session.getUser().getCurrentRoom().getRoomUserByUserId(session.getUser().getId());
		if (user == null) {
			session.stop();
		} else {
			user.chat(message);
		}
		Logging.writeLine(session.getUser().getUsername() + ": " + message, LogLevel.DEBUG);
	}

	private void requestMovement() {
		int x = request.popInt();
		int y = request.popInt();

		RoomUser user = session.getUser().getCurrentRoom().getRoomUserByUserId(session.getUser().getId());
		if (user == null) {
			session.stop();
		} else {
			user.moveTo(x, y);
		}
		Logging.writeLine(session.getUser().getUsername() + " wants to move to " + x + ", " + y, LogLevel.DEBUG);
	}

	public void handleMessage(ClientMessage message) {
		this.request = message;

		switch (request.getId()) {
		case ClientOpcodes.LOGIN:
			login();
			break;

		case ClientOpcodes.REQUEST_MAP:
			requestMap();
			break;

		case ClientOpcodes.REQUEST_MOVEMENT:
			requestMovement();
			break;

		case ClientOpcodes.REQUEST_CHAT:
			requestChat();
			break;

		default:
			Logging.writeLine("No handler for id: " + request.getId(), LogLevel.WARNING);
			break;
		}
	}
}
