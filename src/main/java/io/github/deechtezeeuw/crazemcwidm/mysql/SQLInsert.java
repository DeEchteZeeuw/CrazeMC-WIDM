package io.github.deechtezeeuw.crazemcwidm.mysql;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;
import io.github.deechtezeeuw.crazemcwidm.classes.Game;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class SQLInsert {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    public void insertGame(Game game) {
        if (game == null) return;
        if (game.getUuid() == null || game.getHosts() == null || game.getMap() == null) return;

        // Insert into db
        try {
            if (!plugin.getSQL().sqlSelect.gameExists(game.getUuid())) {
                PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("INSERT INTO widm_games"
                        + " (UUID, Map, Hosts, Theme, GameStatus) VALUES (?,?,?,?,?)");
                ps.setString(1, game.getUuid().toString());
                ps.setString(2, game.getMap().toString());
                ps.setString(3, game.getHosts().toString());
                ps.setString(4, game.getTheme());
                ps.setInt(5, game.getGameStatus());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertContestant(Contestant contestant) {
        if (contestant == null) return;
        if (contestant.getGame() == null || contestant.getUuid() == null) return;

        // Insert into db
        try {
            if (!plugin.getSQL().sqlSelect.contestantExists(contestant.getGame(), contestant.getColor())) {
                PreparedStatement ps = plugin.getSQL().getConnection().prepareStatement("INSERT INTO widm_contestants"
                        + " (UUID, Game, Color) VALUES (?,?,?)");
                ps.setString(1, contestant.getUuid().toString());
                ps.setString(2, contestant.getGame().toString());
                ps.setInt(3, contestant.getColor());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
