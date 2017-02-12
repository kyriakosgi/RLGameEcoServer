package gr.eap.RLGameEcoServer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Properties;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class Server extends WebSocketServer {

	
	public Server( int port , Draft d ) throws UnknownHostException {
		super( new InetSocketAddress( port ), Collections.singletonList( d ) );
	}
	
	public Server( InetSocketAddress address, Draft d ) {
		super( address, Collections.singletonList( d ) );
	}

	@Override
	public void onClose(WebSocket arg0, int arg1, String arg2, boolean arg3) {
		// TODO Auto-generated method stub
		System.out.println("closed " + arg2);

	}

	@Override
	public void onError(WebSocket arg0, Exception arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMessage(WebSocket arg0, String arg1) {
		// TODO Auto-generated method stub
		System.out.println(arg1);
		for (Long i = (long) 0; i<1000000000;i++);
		System.out.println(arg1);
	}

	@Override
	public void onOpen(WebSocket arg0, ClientHandshake arg1) {
		// TODO Auto-generated method stub
		System.out.println("open");
	}

	public static void main(String[] args) {
		//Load application settings as Properties object
		Properties settings = new Properties();
		try(Reader reader = new FileReader("settings")){
			settings.load(reader);
		} catch (FileNotFoundException e) {
			// TODO Log
			System.out.println(e.getMessage());
			return;
		} catch (IOException e) {
			// TODO Log
			System.out.println(e.getMessage());;
			return;
		}

		//Ensure there is a Port number in application settings
		if (!settings.containsKey("Port")) settings.put("Port", "9003");
		
		//Start Web Sockets Server
		try {
			new Server(Integer.parseInt(settings.getProperty("Port")), new Draft_17() ).start();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());;
			return;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());;
			return;
		}

	}

}
