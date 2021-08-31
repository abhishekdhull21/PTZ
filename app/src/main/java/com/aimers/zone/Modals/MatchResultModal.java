package com.aimers.zone.Modals;

public class MatchResultModal {
    String username,kill,win,position,remarks;



    public MatchResultModal(String username, String kill, String win, String position,String remarks) {
        this.username = username;
        this.kill = kill;
        this.win = win;
        this.position = position;

        this.remarks = remarks;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKill() {
        return kill;
    }

    public void setKill(String kill) {
        this.kill = kill;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
