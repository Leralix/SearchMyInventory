package org.leralix.searchmyinventory.util;

import org.bukkit.entity.Player;

public class PositionUtil {

    public static float getPositionBetween(Player player1, Player player2) {
        return (float) Math.sqrt(
                Math.pow(player1.getLocation().getX() - player2.getLocation().getX(), 2) +
                Math.pow(player1.getLocation().getY() - player2.getLocation().getY(), 2) +
                Math.pow(player1.getLocation().getZ() - player2.getLocation().getZ(), 2));
    }
}
