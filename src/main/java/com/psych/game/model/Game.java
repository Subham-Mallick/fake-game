package com.psych.game.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

}