package io.github.deechtezeeuw.crazemcwidm.managers;

import io.github.deechtezeeuw.crazemcwidm.classes.Vote;

import java.util.ArrayList;
import java.util.UUID;

public class VoteManager {
    private ArrayList<Vote> votes = new ArrayList<>();

    public boolean hasVoted(UUID game, UUID contestant) {
        for (Vote vote : this.votes) {
            if (vote.getGame() == null || vote.getContestant() == null) continue;
            if (vote.getGame().equals(game) && vote.getContestant().equals(contestant)) return true;
        }

        return false;
    }

    public void addVote(Vote vote) {
        this.votes.add(vote);
    }

    public void deleteVote(UUID voteID) {
        for (int i = 0; i < this.votes.size(); i++) {
            Vote vote = this.votes.get(i);
            if (vote.getVoteID() == null) continue;
            if (vote.getVoteID().equals(voteID)) this.votes.remove(i);
        }
    }

    public ArrayList<Vote> gameVotes(UUID game) {
        ArrayList<Vote> votes = new ArrayList<>();
        for (Vote vote : this.votes) {
            if (vote.getGame() == null) continue;
            if (vote.getGame().equals(game)) votes.add(vote);
        }

        return votes;
    }

    public void deleteGameVotes(UUID game) {
        for (int i = 0; i < this.votes.size(); i++) {
            Vote vote = this.votes.get(i);
            if (vote.getGame() == null) continue;
            if (vote.getGame().equals(game)) this.votes.remove(i);
        }
    }

    public int onPlayerVotes(UUID contestant) {
        int i = 0;

        for (Vote vote : this.votes) {
            if (vote.getVotedOn() == null) continue;
            if (vote.getVotedOn().equals(contestant)) i++;
        }

        return i;
    }
}
