package gr.eap.RLGameEcoServer.player;

public abstract class Participant {
//TODO we may need to add a reference to the Game class
	public enum Role {
		NONE,
		PLAYER1,
		PLAYER2,
		OBSERVER
	}
	private int id;
	private String name;
	private Role role;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
}
