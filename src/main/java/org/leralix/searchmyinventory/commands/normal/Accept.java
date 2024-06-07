package org.leralix.searchmyinventory.commands.normal;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.leralix.searchmyinventory.Lang.Lang;
import org.leralix.searchmyinventory.commands.SubCommand;
import org.leralix.searchmyinventory.gui.InvGUI;
import org.leralix.searchmyinventory.storage.InvitationStorage;
import org.leralix.searchmyinventory.util.sound.SoundEnum;
import org.leralix.searchmyinventory.util.sound.SoundUtil;

import java.util.ArrayList;
import java.util.List;


public class Accept extends SubCommand {
    @Override
    public String getName() {
        return "accept";
    }

    @Override
    public String getDescription() {
        return Lang.SEARCH_DESC.get();
    }

    @Override
    public String getSyntax() {
        return "/search accept <player>";
    }

    public int getArguments(){
        return 2;
    }

    @Override
    public List<String> getTabCompleteSuggestions(Player player, String[] args){
        List<String> suggestions = new ArrayList<>();
        if (args.length == 2) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                suggestions.add(p.getName());
            }
        }
        return suggestions;
    }

    @Override
    public void perform(Player player, String[] args){

        if(args.length != getArguments()){
            player.sendMessage(Lang.INVALID_ARGUMENTS.get());
            return;
        }

        String senderName = args[1];

        Player sender = Bukkit.getPlayer(senderName);
        if(sender == null){
            player.sendMessage(Lang.PLAYER_NOT_FOUND.get());
            return;
        }

        if(!InvitationStorage.containsInvitation(player)){
            player.sendMessage(Lang.NO_INVITATION.get(senderName));
            return;
        }
        if(!InvitationStorage.getInvitation(player).contains(sender)){
            player.sendMessage(Lang.NO_INVITATION.get(senderName));
            return;
        }

        InvGUI.openInventory(sender, player);
        InvitationStorage.removeInvitation(player, sender);
        player.sendMessage(Lang.INVITATION_ACCEPTED.get(senderName));
        SoundUtil.playSound(sender, SoundEnum.INVITATION_ACCEPTED);
        SoundUtil.playSound(player, SoundEnum.INVITATION_ACCEPTED);
    }

}



