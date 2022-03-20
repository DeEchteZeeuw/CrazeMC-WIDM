package io.github.deechtezeeuw.crazemcwidm.mysql;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLCreate {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    public void gameTable() {
        PreparedStatement ps;
        try {
            ps = plugin.getSQL().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS WIDM_Games "
            + "(UUID VARCHAR(128), Map VARCHAR(128), Hosts VARCHAR(256), Theme VARCHAR(32), GameStatus INTEGER(1), PRIMARY KEY (UUID))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void contestantTable() {
        PreparedStatement ps;
        try {
            ps = plugin.getSQL().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS WIDM_Contestants "
                    + "(Game VARCHAR(128), Color VARCHAR(32), ChatColor VARCHAR(32), Player VARCHAR(128), Role VARCHAR(32), Kills INTEGER(3), Death TINYINT(1), Peacekeeper TINYINT(1), PKKills INTEGER(2), Spawn VARCHAR(128))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
