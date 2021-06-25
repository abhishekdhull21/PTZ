package com.example.myapplication.Modals;

public class MatchModal {
    String match_id,
            game_id,
            match_date,match_time,prize_pool,per_kill,entry_fee,type,version,map,total_slot,alloted_slot,remaining_slot;

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

    public MatchModal(String match_id, String game_id, String match_date, String match_time, String prize_pool, String per_kill, String entry_fee, String type, String version, String map, String total_slot, String alloted_slot, String remaining_slot) {
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
    }
}
