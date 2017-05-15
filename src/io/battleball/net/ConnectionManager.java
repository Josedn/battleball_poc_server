package io.battleball.net;

import java.net.InetSocketAddress;
import java.util.Collections;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import io.battleball.misc.Logging;
import io.battleball.misc.Logging.LogLevel;

public class ConnectionManager extends WebSocketServer {
	private IConnectionHandler connectionHandler;
	private int connectionCount;

	public ConnectionManager(int port, IConnectionHandler connectionHandler) {
		super(new InetSocketAddress(port), Collections.singletonList(new Draft_17()));
		this.connectionHandler = connectionHandler;
		this.connectionCount = 0;
		this.start();
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		this.connectionHandler.handleDisconnect(conn);
		Logging.writeLine("Closed connection!", LogLevel.DEBUG);
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		System.out.println("WebSocket Error:");
		ex.printStackTrace();
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		this.connectionHandler.handleMessage(conn, message);
		Logging.writeLine("Message received from " + conn.hashCode() + "! '" + message + "'", LogLevel.DEBUG);
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		connectionCount++;
		this.connectionHandler.handleStartClient(conn);
		Logging.writeLine("New connection id: " + conn.hashCode(), LogLevel.DEBUG);
	}

	@Override
	public void onStart() {
		Logging.writeLine("Server started!");

	}

}
