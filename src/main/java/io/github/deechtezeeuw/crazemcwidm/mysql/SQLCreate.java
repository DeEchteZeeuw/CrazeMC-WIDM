package io.github.deechtezeeuw.crazemcwidm.mysql;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLCreate {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    public void gameTable() {
        PreparedStatement ps;
        try {
            ps = plugin.getSQL().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS widm_games "
            + "(UUID VARCHAR(128), Map VARCHAR(128), Hosts VARCHAR(256), Theme VARCHAR(32), GameStatus INTEGER(1), GameTime INTEGER(10), PRIMARY KEY (UUID))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void contestantTable() {
        PreparedStatement ps;
        try {
            ps = plugin.getSQL().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS widm_contestants "
                    + "(UUID VARCHAR(128), Game VARCHAR(128), Color INTEGER(2), Player VARCHAR(128), Role Integer(1) DEFAULT 0, Kills INTEGER(3) DEFAULT 0, Death TINYINT(1) DEFAULT 0, Peacekeeper TINYINT(1) DEFAULT 0, PKKills INTEGER(2) DEFAULT 0, Spawn VARCHAR(128), Booklock TINYINT(1), PRIMARY KEY (UUID))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
