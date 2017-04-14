package gr.eap.RLGameEcoServer.player;

import java.util.ArrayList;
import java.util.List;

public class Participant {
	// TODO we may need to add a reference to the Game class
	public enum Role {
		NONE, PLAYER1, PLAYER2, OBSERVER
	}

	private String name = "";
	private Role role;
	private ArrayList<Player> players = new ArrayList<Player>();
	private Player player1Leader;
	private Player player2Leader;
	
	

	public Player getPlayer1Leader() {
		return player1Leader;
	}

	public Player getPlayer2Leader() {
		return player2Leader;
	}

	public void setPlayer1Leader(Player player1Leader) {
		this.player1Leader = player1Leader;
	}

	public void setPlayer2Leader(Player player2Leader) {
		this.player2Leader = player2Leader;
	}

	public Participant() {
	}

	public Participant(Player player) {
		addPlayer(player);
	}

	public Participant(List<Player> players) {
		if (players.size() > 0) {
			players.addAll(players);
			for (Player p : players) {
				name += ", " + p.getName();
			}
			name = name.substring(2);
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Player> getPlayers() {
		return (ArrayList<Player>) (players.clone());
	}

	public void addPlayer(Player player) {
		if (!players.contains(player))
			players.add(player);
		if (name.equals("")) {
			name += player.getName();
		} else {
			name += ", " + player.getName();
		}
	}

	public void removePlayer(Player player) {
		players.remove(player);
		if (name.contains(player.getName() + ", ")){
			name = name.replace(player.getName() + ", ", "");
		}
		else
		{
			name = name.replace(player.getName(), "");
		}
	}

	//The name property is read-only. It is updated every time we add or remove players and not calculated on demand so that the class can be correctly serialized
	public String getName() {
		return name;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object object) {
		// Every player in the compared participant's players should exists in
		// this participant's players and vice versa
		ArrayList<Player> playersToCompare = ((Participant) object).getPlayers();
		for (Player p : players) {
			if (!playersToCompare.contains(p)) {
				return false;
			}
		}
		for (Player p : playersToCompare) {
			if (!players.contains(p)) {
				return false;
			}
		}

		return true;
	}

}
