package gr.eap.RLGameEcoServer.comm;

import java.util.List;

public interface Command {
	public List<Response> execute();
}
