package com.psych.game.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.psych.game.Utils;
import com.psych.game.exceptions.InvalidGameActionException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "games")
public class Game extends Auditable {

    @ManyToMany
    @Getter
    @Setter
    @JsonIdentityReference
    private Set<Player> players = new HashSet<>();

    @NotNull
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private GameMode gameMode;

    @OneToMany(mappedBy = "game",cascade = CascadeType.ALL)
    @Getter
    @Setter
    @JsonManagedReference
    private List<Round> rounds = new ArrayList<>();

    @Getter
    @Setter
    private Boolean hasEllen = false;

    @Getter
    @Setter
    @NotNull
    @ManyToOne
    @JsonIdentityReference
    private Player leader;

    @ManyToMany(cascade = CascadeType.ALL)
    @Getter
    @Setter
    @JsonManagedReference
    private Map<Player,Stat> playStats = new HashMap<>();

    @Getter
    @Setter
    private int numRounds = 10;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    @ManyToMany
    @Getter
    @Setter
    @JsonIdentityReference
    private Set<Player> readyPlayers = new HashSet<>();

    public Game(){}

    public Game(@NotNull GameMode gameMode, Boolean hasEllen, @NotNull Player leader, int numRounds) {
        this.gameMode = gameMode;
        this.hasEllen = hasEllen;
        this.leader = leader;
        this.numRounds = numRounds;
        this.players.add(leader);
    }

    public void addPlayer(Player player) throws InvalidGameActionException {
        if(!gameStatus.equals(GameStatus.PLAYERS_JOINING)){
            throw new InvalidGameActionException("Cant join after the game has started");
        }
        players.add(player);

    }

    public void removePlayer(Player player) throws InvalidGameActionException {

        if(!players.contains(player)){
            throw new InvalidGameActionException("No such player was in the game");
        }
        players.remove(player);

        if(players.size()==0 || (players.size()==1 && gameStatus.equals(GameStatus.PLAYERS_JOINING))){
            endGame();
        }

    }

    public void startGame(Player player) throws InvalidGameActionException {
        if(!player.equals(leader)){
            throw new InvalidGameActionException("Only the leader can start the game");
        }
        startNewRound();
    }

    private void startNewRound() {
        gameStatus = GameStatus.SUBMITTING_ANSWERS;
        Question question = Utils.getRandomQuestion(gameMode);
        Round round = new Round(this, question,rounds.size()+1);
        if(hasEllen){
            round.setEllenAnswer(Utils.getRandomEllenAnswer(question));
        }

        rounds.add(round);

    }

    private void endGame() {
        gameStatus = GameStatus.ENDED;
    }

    public String getGameState(){
        return "";

    }

    public void submitAnswer(Player player, String answer) throws InvalidGameActionException {

        if(answer.length()==0)
            throw new InvalidGameActionException("Answer cannot be empty");

        if(!players.contains(player))
            throw new InvalidGameActionException("No such player was in the game");

        if(!gameStatus.equals(GameStatus.SUBMITTING_ANSWERS))
            throw new InvalidGameActionException("Game is not accepting answers");

        Round currentRound = getCurrentRound();

        currentRound.submitAnswer(player,answer);

        if(currentRound.allAnswersSubmitted(players.size())){
            gameStatus = GameStatus.SUBMITTING_ANSWERS;
        }
    }

    public  void selectAnswer(Player player , PlayerAnswer selectedAnswer) throws InvalidGameActionException {

        if(!players.contains(player))
            throw new InvalidGameActionException("No such player was in the game");

        if(!gameStatus.equals(GameStatus.SELECTING_ANSWERS))
            throw new InvalidGameActionException("Game is not Selecting answers");

        Round currentRound = getCurrentRound();

        currentRound.selectAnswer(player,selectedAnswer);

        if( currentRound.allAnswersSelected(players.size())){
            if(rounds.size() < numRounds){
                gameStatus = GameStatus.WAITING_FOR_READY;
            }
            else{
                endGame();
            }
        }
    }

    public void playerIsReady(Player player) throws InvalidGameActionException {
        if(!players.contains(player))
            throw new InvalidGameActionException("No such player was in the game");

        if(!gameStatus.equals(GameStatus.WAITING_FOR_READY))
            throw new InvalidGameActionException("Game is not waiting for players");

        readyPlayers.add(player);

        if(readyPlayers.size() == players.size()){
            startNewRound();
        }
    }

    public void playerIsNotReady(Player player) throws InvalidGameActionException {
        if(!players.contains(player))
            throw new InvalidGameActionException("No such player was in the game");

        if(!gameStatus.equals(GameStatus.WAITING_FOR_READY))
            throw new InvalidGameActionException("Game is not waiting for players");

        readyPlayers.remove(player);

    }

    private Round getCurrentRound() throws InvalidGameActionException {
        if(rounds.size() == 0)
            throw new InvalidGameActionException("There are no rounds");
        return rounds.get(rounds.size()-1);

    }
}