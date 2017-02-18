package gr.eap.RLGameEcoServer;

import java.util.List;

public interface Command {
	public List<Command> execute();
}
