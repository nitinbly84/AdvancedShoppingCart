package com.shoppingcart.utilities;

import java.util.ArrayList;

import com.shoppingcart.executors.BillProcessor;

/**
 * @author Nitin Agrawal
 * @Date 03-May-2020
 * A Utility class to create & execute a stack of commands.
 * It will stack those method calls from which this class is called.
 * Else it will stack wrong method calls.
 * So call this class only from those methods which you want in the stack.
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class StackCommands {
	
	private static String logFileName = "StackCommands.txt";
	private static MessageWriter messageWriter = new MessageWriter(logFileName);
	private ArrayList<ArrayList> commands = new ArrayList<>();
	
	public void stackCommands(Object ...args) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		String methodName = stackTrace[2].getMethodName();
			ArrayList command = new ArrayList();
			command.add(methodName);
			Class[] params = new Class[args.length];
			for(int i = 0; i < args.length; i++) {
				params[i] = args[i].getClass();
			}
			command.add(params);
			command.add(args);
			commands.add(command);
	}
	
	public boolean executeCommands(Object billProcessor) {
		for(ArrayList command : commands) {
			try {
				BillProcessor.class
				             .getDeclaredMethod(command.get(0).toString(), (Class<?>[]) command.get(1)).invoke(billProcessor, (Object[])command.get(2));
			} catch (Exception e) {
				messageWriter.writeToFile(e.getMessage())
							 .writeToFile(e.getStackTrace());
			}
		}
		// Clearing the commands list associated with this instance, so that commands are not reprocessed even after process() is called multiple
		// times on the same instance. Though same instance can be reused to add the same commands or new commands in its pipeline.
		commands.clear();
		return true;
	}
}
