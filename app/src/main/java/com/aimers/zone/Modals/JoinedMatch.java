package com.aimers.zone.Modals;

import java.util.ArrayList;

public class JoinedMatch {
    public JoinedMatch(ArrayList<String> matchId) {
        this.matchId = matchId;
    }

    ArrayList<String> matchId;

    public ArrayList<String> getMatchId() {
        return matchId;
    }

    public void setMatchId(ArrayList<String> matchId) {
        this.matchId = matchId;
    }
}
