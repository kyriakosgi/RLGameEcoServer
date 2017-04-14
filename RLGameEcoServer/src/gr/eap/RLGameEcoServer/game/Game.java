package gr.eap.RLGameEcoServer.game;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import gr.eap.RLGameEcoServer.player.Participant;
import gr.eap.RLGameEcoServer.player.Player;

public class Game {
	private UUID uid;
	private Date startDateTime;
	private Duration duration;
	private transient GameState state;
	private transient ArrayList<Participant> participants = new ArrayList<Participant>();
	private int boardSize;
	private int baseSize;
	private int numberOfPawns;
	private GameStatus status;

	// player1 and player2 properties will be read-only and will get updated
	// when needed, so that we can correctly serialize those properties
	private Participant player1;
	private Participant player2;

	public Participant getPlayer1() {
		return player1;
	}

	public Participant getPlayer2() {
		return player2;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public int getBaseSize() {
		return baseSize;
	}

	public int getNumberOfPawns() {
		return numberOfPawns;
	}

	public ArrayList<Participant> getParticipants() {
		return participants;
	}

	public UUID getUid() {
		return uid;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public Game(int boardSize, int baseSize, int numberOfPawns) {
		uid = UUID.randomUUID();
		this.boardSize = boardSize;
		this.baseSize = baseSize;
		this.numberOfPawns = numberOfPawns;
		state = new GameState();
		state.setBoard(new int[boardSize * boardSize]);
	}

	public boolean addPlayer1(Player player1) {
		return addPlayer(player1, Participant.Role.PLAYER1);
	}

	public boolean addPlayer2(Player player2) {
		return addPlayer(player2, Participant.Role.PLAYER2);
	}

	private boolean addPlayer(Player player, Participant.Role role) {
		boolean returnValue = true;

		// Find if player is already participating in the game
		for (Participant participant : participants) {
			if (participant.getPlayers().contains(player)) {
				returnValue = false;
				break;
			}
		}
		
		if (returnValue) {
			//Find if there is already a participant with the desired role
			Participant participant = null;
			for (Participant p : participants) {
				if (p.getRole().equals(role)) {
					participant = p;
					break;
				}
			}

			// if there is no participant with the desired role, so we'll have to create one
			if (participant == null) {
				participant = new Participant();
				participant.setRole(role);
				switch (role) {
				case PLAYER1:
					this.player1 = participant;
					break;
				case PLAYER2:
					this.player2 = participant;
					break;
				default:
					break;
				}
				participants.add(participant);
			}
			//Finally add the player to the new or existing participant
			participant.addPlayer(player);
		}

		return returnValue;

	}

	public void join(Player player, Participant.Role role) {
		// Set the player
		addPlayer(player, role);

		// Refresh games list on all clients
		GamesRegister.getInstance().sendGamesList();


	}

	public void shareState() {
		// TODO Not yet implemented
	}

	public void endGame() {
		// TODO Not yet implemented
	}

	public void performMove(Participant player, int from, int to) {
		// TODO Not yet implemented
	}

}
