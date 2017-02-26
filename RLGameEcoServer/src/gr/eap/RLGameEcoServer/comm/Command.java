package gr.eap.RLGameEcoServer.comm;

import java.util.List;

public interface Command {
	public int getId();
	public void setId(int value);
	public List<Response> execute();
}
