package io.github.deechtezeeuw.crazemcwidm.mysql;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLSelect {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    public boolean playerExists(UUID uuid) {
        try {
            PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("SELECT * FROM players WHERE UUID=?");
            ps.setString(1, uuid.toString());

            ResultSet resultSet = ps.executeQuery();
            return  (resultSet.next() ? true : false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
