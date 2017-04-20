package gr.eap.RLGameEcoServer.game;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import gr.eap.RLGameEcoServer.comm.ConnectionState;
import gr.eap.RLGameEcoServer.comm.Message;
import gr.eap.RLGameEcoServer.comm.Message.Type;
import gr.eap.RLGameEcoServer.player.Participant;
import gr.eap.RLGameEcoServer.player.Participant.Role;
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
	private boolean player1Ready = false;
	private boolean player2Ready = false;

	// player1 and player2 properties will be read-only and will get updated
	// when needed, so that we can correctly serialize those properties
	private Participant player1;
	private Participant player2;
	private Participant spectator;

	public boolean isPlayer1Ready() {
		return player1Ready;
	}

	public void setPlayer1Ready(boolean player1Ready) {
		// Check if the property value changes so that we know that we have to
		// send the games list to the clients
		if (this.player1Ready != player1Ready) {
			this.player1Ready = player1Ready;
			// If this change starts the game then the game starting procedure
			// will send the games list to the clients so we don't have to do it
			// here as well
			if (!checkForGameStart())
				GamesRegister.getInstance().sendGamesList();
			;
		}
	}

	public boolean isPlayer2Ready() {
		return player2Ready;
	}

	public void setPlayer2Ready(boolean player2Ready) {
		// Check if the property value changes so that we know that we have to
		// send the games list to the clients
		if (this.player2Ready != player2Ready) {
			this.player2Ready = player2Ready;
			// If this change starts the game then the game starting procedure
			// will send the games list to the clients so we don't have to do it
			// here as well
			if (!checkForGameStart())
				GamesRegister.getInstance().sendGamesList();
			;
		}
	}

	public Participant getPlayer1() {
		//Create a new Participant so that the method never returns null
		if (player1 == null){
			player1= new Participant();
			player1.setRole(Role.PLAYER1);
			participants.add(player1);
		}
		return player1;
	}

	public Participant getPlayer2() {
		//Create a new Participant so that the method never returns null
		if (player2 == null){
			player2= new Participant();
			player2.setRole(Role.PLAYER2);
			participants.add(player2);
		}
		return player2;
	}

	public Participant getSpectator() {
		//Create a new Participant so that the method never returns null
		if (spectator == null){
			spectator= new Participant();
			spectator.setRole(Role.SPECTATOR);
			participants.add(spectator);
		}
		return spectator;
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

	public boolean addPlayer(Player player, Participant.Role role) {
		boolean returnValue = true;

		// Find if player is already participating in the game
		for (Participant participant : participants) {
			if (participant.getPlayers().contains(player)) {
				returnValue = false;
				break;
			}
		}

		if (returnValue) {
			// Find if there is already a participant with the desired role
			Participant participant = null;
			for (Participant p : participants) {
				if (p.getRole().equals(role)) {
					participant = p;
					break;
				}
			}

			// if there is no participant with the desired role, so we'll have
			// to create one
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
			// Finally add the player to the new or existing participant
			participant.addPlayer(player);

			// Refresh games list for all clients
			GamesRegister.getInstance().sendGamesList();
		}

		return returnValue;

	}


	private boolean checkForGameStart() {
		if (getStatus().equals(GameStatus.WAITING_FOR_PLAYERS) && !(getPlayer1().getPlayers().isEmpty())
				&& !(getPlayer2().getPlayers().isEmpty()) && isPlayer1Ready() && isPlayer2Ready()) {
			startGame();
			return true;
		} else {
			return false;
		}

	}

	private void startGame() {
		// Set the start date and time as the current date and time
		setStartDateTime(new Date());

		// Set the game and players status
		setStatus(GameStatus.IN_PROGRESS);
		for (Participant participant : participants) {
			for (Player player : participant.getPlayers()) {
				player.setConnectionState(ConnectionState.IN_GAME);
			}
		}

		// Send a message to all participants so that the client knows that they
		// are now in game
		Message message = new Message();
		message.setText("Game starting ...");
		message.setType(Type.SYSTEM_INFO);
		for (Participant participant : participants) {
			message.getRecipients().addAll(participant.getPlayers());
		}
		message.send();

		// Refresh the games list on all clients (it will not affect the game
		// players as the are in game)
		GamesRegister.getInstance().sendGamesList();

		// Send game state to all participants
		shareState();

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
