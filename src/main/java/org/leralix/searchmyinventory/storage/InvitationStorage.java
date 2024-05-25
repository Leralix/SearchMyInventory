package org.leralix.searchmyinventory.storage;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InvitationStorage {


    private static final HashMap<Player, ArrayList<Player>> invitations = new HashMap<>();

    public static void addInvitation(Player receiver, Player sender) {

        if(!invitations.containsKey(receiver))
            invitations.put(receiver, new ArrayList<>());

        invitations.get(receiver).add(sender);
    }

    public static void removeInvitation(Player receiver, Player sender) {
        invitations.get(receiver).remove(sender);
    }
    public static boolean containsInvitation(Player player) {
        return invitations.containsKey(player);
    }

    public static ArrayList<Player> getInvitation(Player player) {
        return invitations.get(player);
    }




}
