package com.aimers.zone.Modals;

import java.io.Serializable;

public class MatchModal implements Serializable {
    final String match_id;
    final String game_id;
    final String match_date;
    final String match_time;
    final String prize_pool;
    final String per_kill;
    final String entry_fee;
    final String type;
    final String version;
    final String map;
    final String total_slot;
    final String alloted_slot;
    final String remaining_slot;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    private String pic;
int pos;
    public String getMatch_id() {
        return match_id;
    }

    public String getGame_id() {
        return game_id;
    }

    public String getMatch_date() {
        return match_date;
    }

    public String getMatch_time() {
        return match_time;
    }

    public String getPrize_pool() {
        return prize_pool;
    }

    public String getPer_kill() {
        return per_kill;
    }

    public String getEntry_fee() {
        return entry_fee;
    }

    public String getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    public String getMap() {
        return map;
    }

    public String getTotal_slot() {
        return total_slot;
    }

    public String getAlloted_slot() {
        return alloted_slot;
    }

    public String getRemaining_slot() {
        return remaining_slot;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public MatchModal(int pos, String match_id, String game_id, String match_date, String match_time, String prize_pool, String per_kill, String entry_fee, String type, String version, String map, String total_slot, String alloted_slot, String remaining_slot, String pic) {
        this.pos = pos;
        this.match_id = match_id;
        this.game_id = game_id;
        this.match_date = match_date;
        this.match_time = match_time;
        this.prize_pool = prize_pool;
        this.per_kill = per_kill;
        this.entry_fee = entry_fee;
        this.type = type;
        this.version = version;
        this.map = map;
        this.total_slot = total_slot;
        this.alloted_slot = alloted_slot;
        this.remaining_slot = remaining_slot;
        this.pic = pic;
    }
}
