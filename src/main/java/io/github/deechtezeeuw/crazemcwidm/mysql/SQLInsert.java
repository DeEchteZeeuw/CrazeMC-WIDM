package io.github.deechtezeeuw.crazemcwidm.mysql;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class SQLInsert {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    public void insertPlayer(Player player) {
        try {
            UUID uuid = player.getUniqueId();
            if (!plugin.getSQL().sqlSelect.playerExists(uuid)) {
                PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("INSERT INTO players"
                + " (UUID) VALUES (?)");
                ps.setString(1, uuid.toString());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
