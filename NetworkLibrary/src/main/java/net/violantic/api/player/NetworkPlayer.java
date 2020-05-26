package net.violantic.api.player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class NetworkPlayer {

    public int id;
    public UUID uuid;
    public String rank;
    public String tag;
    public int xp;
    public int gold;

    public NetworkPlayer(ResultSet set) throws SQLException {
        this.id = set.getInt("id");
        this.uuid = UUID.fromString(set.getString("uuid"));
        this.rank = set.getString("rank");
        this.tag = set.getString("tag");
        this.xp = set.getInt("xp");
    }

    public NetworkPlayer(int id, UUID uuid) {
        this.uuid = uuid;
        this.rank = NetworkRank.DEFAULT.name;
    }

    public NetworkPlayer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public enum NetworkRank {

        ADMIN(5, "admin", "[Admin]", 'c'),
        MOD(4, "mod", "[Mod]", 'e'),
        BUILD(3, "builder", "[Builder] ", '3'),
        YOUTUBE(2, "youtuber", "[YouTube]", '6'),
        VIP(1, "vip", "[VIP]", 'd'),
        DEFAULT(0, "default", "", '7');

        private int weight;
        private String name;
        private String tag;
        private char color;

        NetworkRank(int weight, String name, String tag, char color) {
            this.weight = weight;
            this.name = name;
            this.tag = tag;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public String getTag() {
            return tag;
        }

        public char getColor() {
            return color;
        }
    }
}
