package io.github.deechtezeeuw.crazemcwidm.mysql;

import io.github.deechtezeeuw.crazemcwidm.CrazeMCWIDM;
import io.github.deechtezeeuw.crazemcwidm.classes.Contestant;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUpdate {
    private final CrazeMCWIDM plugin = CrazeMCWIDM.getInstance();

    // Update contestant
    public void updateContestant(Contestant contestant, String what) {
        try
        {
            PreparedStatement ps = null;
            switch (what) {
                case "player":
                    ps = plugin.getSQL().getConnection().prepareStatement("UPDATE widm_contestants SET Player = ? WHERE UUID = ?");
                    ps.setString(1, contestant.getPlayer().toString());
                    ps.setString(2, contestant.getUuid().toString());
                    break;
                case "role":
                    ps = plugin.getSQL().getConnection().prepareStatement("UPDATE widm_contestants SET Role = ? WHERE UUID = ?");
                    ps.setInt(1, contestant.getRole());
                    ps.setString(2, contestant.getUuid().toString());
                    break;
                case "kills":
                    ps = plugin.getSQL().getConnection().prepareStatement("UPDATE widm_contestants SET Kills = ? WHERE UUID = ?");
                    ps.setInt(1, contestant.getKills());
                    ps.setString(2, contestant.getUuid().toString());
                    break;
                case "death":
                    ps = plugin.getSQL().getConnection().prepareStatement("UPDATE widm_contestants SET Death = ? WHERE UUID = ?");
                    ps.setBoolean(1, contestant.getDeath());
                    ps.setString(2, contestant.getUuid().toString());
                    break;
                case "peacekeeper":
                    ps = plugin.getSQL().getConnection().prepareStatement("UPDATE widm_contestants SET Peacekeeper = ? WHERE UUID = ?");
                    ps.setBoolean(1, contestant.getPeacekeeper());
                    ps.setString(2, contestant.getUuid().toString());
                    break;
                case "pkkills":
                    ps = plugin.getSQL().getConnection().prepareStatement("UPDATE widm_contestants SET PKKills = ? WHERE UUID = ?");
                    ps.setInt(1, contestant.getPeacekeeperKills());
                    ps.setString(2, contestant.getUuid().toString());
                    break;
                case "spawn":
                    ps = plugin.getSQL().getConnection().prepareStatement("UPDATE widm_contestants SET Spawn = ? WHERE UUID = ?");
                    ps.setString(1, contestant.getSpawn().getWorld().getName() + "#" + contestant.getSpawn().getBlockX() + "#" + contestant.getSpawn().getBlockY() + "#" + contestant.getSpawn().getBlockZ() + "#" + contestant.getSpawn().getPitch() + "#" + contestant.getSpawn().getYaw() );
                    ps.setString(2, contestant.getUuid().toString());
                    break;
            }

            if (ps == null) return;

            // call executeUpdate to execute our sql update statement
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
