package io.github.deechtezeeuw.crazemcwidm.classes;

import java.util.UUID;

public class Vote {
    protected UUID voteID;
    protected UUID game;
    protected UUID contestant;
    protected UUID votedOn;
    protected int votes;

    public void setVoteID(UUID voteID) {
        this.voteID = voteID;
    }

    public UUID getVoteID() {
        return voteID;
    }

    public void setGame(UUID game) {
        this.game = game;
    }

    public UUID getGame() {
        return game;
    }

    public void setContestant(UUID contestant) {
        this.contestant = contestant;
    }

    public UUID getContestant() {
        return contestant;
    }

    public void setVotedOn(UUID votedOn) {
        this.votedOn = votedOn;
    }

    public UUID getVotedOn() {
        return votedOn;
    }

    public void setVotes(int i) {
        this.votes = i;
    }

    public int getVotes() {
        return votes;
    }
}
