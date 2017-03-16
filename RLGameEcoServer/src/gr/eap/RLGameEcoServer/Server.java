package gr.eap.RLGameEcoServer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import gr.eap.RLGameEcoServer.comm.Command;
import gr.eap.RLGameEcoServer.comm.JsonCommObjectSerializer;
import gr.eap.RLGameEcoServer.comm.LogonCommand;
import gr.eap.RLGameEcoServer.comm.Response;
import gr.eap.RLGameEcoServer.db.MySQLHelper;

public class Server extends WebSocketServer {

	public Server(int port, Draft d) throws UnknownHostException {
		super(new InetSocketAddress(port), Collections.singletonList(d));
	}

	public Server(InetSocketAddress address, Draft d) {
		super(address, Collections.singletonList(d));
	}

	@Override
	public void onClose(WebSocket arg0, int arg1, String arg2, boolean arg3) {
		// TODO Auto-generated method stub
		System.out.println("closed " + arg2);

	}

	@Override
	public void onError(WebSocket arg0, Exception arg1) {
		// TODO Auto-generated method stub
		System.err.println("Error:");
		arg1.printStackTrace();

	}

	@Override
	public void onMessage(WebSocket arg0, String arg1) {
		// TODO Auto-generated method stub
		try {

			System.out.println(arg1);
			System.out.println(arg0.hashCode());
			JsonCommObjectSerializer js = new JsonCommObjectSerializer();
			Command cmd = (Command) js.deserialize(arg1);
			if (cmd != null) {
				cmd.setSocketHash(arg0.hashCode());
				for (Response res : cmd.execute()) {
					String outco = js.serialize(res);
					arg0.send(outco);

				}
			} else 
			{
				System.out.println("Unknown command received\r\n" + arg1);
			}
		} catch (Exception ex) {
			System.err.println("onMessage:" + ex);
		}
	}

	@Override
	public void onOpen(WebSocket arg0, ClientHandshake arg1) {
		System.out.println("open");
		System.out.println(arg0.hashCode());
//		Response r = new Response();
//		r.setMessage("Login Successful");
	}

	public static void main(String[] args) {
		// Load application settings as Properties object
		Properties settings = new Properties();
		try (Reader reader = new FileReader("settings")) {
			settings.load(reader);
		} catch (FileNotFoundException e) {
			// TODO Log
			System.err.println(e.getMessage());
			return;
		} catch (IOException e) {
			// TODO Log
			System.err.println(e.getMessage());
			return;
		}

		LogonCommand lg = new LogonCommand();
		lg.setGameId(new UUID(0, 1));
		lg.setId(1);
		lg.setPassword("pass");
		lg.setUserId(2);
		lg.setUserName("kyriakos");
		//lg.setConnection(null);

		try {
			JsonCommObjectSerializer ser = new JsonCommObjectSerializer();
			System.out.println(ser.serialize(lg));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.err.println("e1: " + e1.getMessage());
		}

		// Initialize SQL connection parameters
		MySQLHelper.getInstance().initializeConnectionParameters(settings.getProperty("dbLocation"),
				settings.getProperty("userName"), settings.getProperty("password"));

		// Ensure there is a Port number in application settings
		if (!settings.containsKey("Port"))
			settings.put("Port", "9003");

		// Start Web Sockets Server
		try {
			// Draft draft1 = new Draft_17();
			// Draft.MAX_FAME_SIZE = (1024);
			new Server(Integer.parseInt(settings.getProperty("Port")), new Draft_17()).start();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			;
			return;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			;
			return;
		}

	}

}
