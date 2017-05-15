package io.battleball.net;

import org.java_websocket.WebSocket;

public interface IConnectionHandler {
	void handleMessage(WebSocket socket, String message);
	void handleDisconnect(WebSocket socket);
	void handleStartClient(WebSocket socket);
}
