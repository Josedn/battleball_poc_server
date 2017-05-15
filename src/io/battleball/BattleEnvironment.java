package io.battleball;

import java.util.Scanner;

import io.battleball.core.Game;
import io.battleball.misc.Logging;
import io.battleball.misc.Logging.LogLevel;

public class BattleEnvironment {
	public BattleEnvironment()
	{
		Game game = new Game(8181);
		Logging.writeLine("The environment has initialized successfully. Ready for connections.", LogLevel.INFO);
		
		Scanner scanner = new Scanner(System.in);
		
		String command;
		while (true)
        {
			command = scanner.nextLine();
			String[] args = command.split(" ");
			switch (args[0])
            {
                case "exit":
                {
                    Logging.writeLine("Stopping server...", LogLevel.INFO);
                    return;
                }
                default:
                {
                    Logging.writeLine("Invalid command", LogLevel.INFO);
                    break;
                }
            }
        }
		
	}
	
	public static void main(String[] args)
	{
		new BattleEnvironment();
	}
}
