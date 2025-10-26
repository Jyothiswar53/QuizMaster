
package com.quiz_app.quiz_app.dto;

public class LeaderboardEntry {
    private int rank;
    private String username;
    private int score;

    public LeaderboardEntry(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}