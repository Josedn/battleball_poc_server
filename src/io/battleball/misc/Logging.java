package io.battleball.misc;

public class Logging {
	public enum LogLevel{
		DEBUG, WARNING, INFO, NOTHING
	}
	public static LogLevel logLevel = LogLevel.DEBUG;
	
	public static void writeLine(Object o, LogLevel logLevel)
	{
		System.out.println(o);
	}
	public static void writeLine(Object o)
	{
		System.out.println(o);
	}
}
