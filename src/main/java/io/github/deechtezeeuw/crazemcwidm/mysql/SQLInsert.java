package io.github.deechtezeeuw.crazemcwidm.mysql;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
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

    public void insertGame(Game game) {
        if (game == null) return;
        if (game.getUuid() == null || game.getHosts() == null || game.getMap() == null) return;

        // Insert into db
        try {
            if (!plugin.getSQL().sqlSelect.gameExists(game.getUuid())) {
                PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("INSERT INTO widm_games"
                        + " (UUID, Map, Hosts) VALUES (?,?,?)");
                ps.setString(1, game.getUuid().toString());
                ps.setString(2, game.getMap().toString());
                ps.setString(3, game.getHosts().toString());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
