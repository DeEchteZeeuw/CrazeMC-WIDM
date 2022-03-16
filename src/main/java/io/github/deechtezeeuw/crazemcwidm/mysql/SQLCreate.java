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
            + "(UUID VARCHAR(128), Map VARCHAR(128), Hosts VARCHAR(256), PRIMARY KEY (UUID))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
