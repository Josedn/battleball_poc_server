package io.battleball;

import java.util.Scanner;

import io.battleball.core.Game;
import io.battleball.misc.Logging;
import io.battleball.misc.Logging.LogLevel;

public class BattleEnvironment {

	private static Game game;

	public static Game getGame() {
		return game;
	}

	public BattleEnvironment() {
		game = new Game(8181);
		Logging.writeLine("The environment has initialized successfully. Waiting for server...", LogLevel.INFO);

		Scanner scanner = new Scanner(System.in);

		String command;
		while (true) {
			command = scanner.nextLine();
			String[] args = command.split(" ");
			switch (args[0]) {
			case "cycle": {
				Logging.writeLine("Doing game cycle...", LogLevel.INFO);
				game.onCycle();
				break;
			}
			case "exit": {
				Logging.writeLine("Stopping server...", LogLevel.INFO);
				return;
			}
			default: {
				Logging.writeLine("Invalid command", LogLevel.INFO);
				break;
			}
			}
		}
	}

	public static void main(String[] args) {
		new BattleEnvironment();
	}
}
