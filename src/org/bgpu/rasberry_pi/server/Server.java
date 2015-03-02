package org.bgpu.rasberry_pi.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bgpu.rasberry_pi.structs.ConfigLoader;

/**
 * главный класс этого проекта
 * @author bazinga
 *
 */
public class Server {
	
	private static final Logger LOGGER = LogManager.getLogger(Server.class);

	public static void main(String... args) throws ClassNotFoundException {
		if (args.length == 0) {// если не переданно не одного параметра
			LOGGER.fatal("jar run without arguments");
			System.exit(1);
		}
		
		//устанавливаем конфиг
		ConfigLoader.setDestination(args[0]);
		
		Class.forName("org.bgpu.rasberry_pi.core.IskanderusController");
		Class.forName("org.bgpu.rasberry_pi.structs.ScriptCollection");
		

		try {
			LOGGER.info("Starting server...");
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(1010);
			LOGGER.info("Started server...");
			while (true) {
				Socket socket = ss.accept();
				LOGGER.info("connected client");
				new Thread(new SocketListener(socket)).start();
			}
		} catch (IOException e) {e.printStackTrace();}
		
	}
}
