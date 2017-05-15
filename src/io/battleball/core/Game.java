package io.battleball.core;

import io.battleball.core.gameclients.GameClientManager;
import io.battleball.core.rooms.MapModel;
import io.battleball.core.rooms.Room;
import io.battleball.net.ConnectionManager;

public class Game {
	private ConnectionManager connectionManager;
	private GameClientManager clientManager;
	public MapModel mapModel;
	public Room room;

	private final static long DELTA_TIME = 500;

	public Game(int port) {
		this.clientManager = new GameClientManager();
		this.connectionManager = new ConnectionManager(port, this.clientManager);
		this.mapModel = new MapModel();
		this.room = new Room(mapModel.width, mapModel.height);
		
		Thread roomTask = new Thread(new Runnable() {
			
			@Override
			public void run() {
				roomCycleTask();
			}
		});
		
		roomTask.start();
	}

	private void roomCycleTask() {
		long startTime, spentTime, sleepTime;

		while (true) {
			startTime = System.currentTimeMillis();

			onCycle();

			spentTime = System.currentTimeMillis() - startTime;

			sleepTime = DELTA_TIME - spentTime;

			if (sleepTime < 0) {
				sleepTime = 0;
			}

			if (sleepTime > DELTA_TIME) {
				sleepTime = DELTA_TIME;
			}
			
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void onCycle() {
		room.onCycle();
	}

}
