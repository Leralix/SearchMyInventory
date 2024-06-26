package org.leralix.searchmyinventory.commands.normal;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.leralix.searchmyinventory.Lang.Lang;
import org.leralix.searchmyinventory.commands.SubCommand;
import org.leralix.searchmyinventory.storage.InvitationStorage;
import org.leralix.searchmyinventory.util.ChatUtil;
import org.leralix.searchmyinventory.util.ConfigUtil;
import org.leralix.searchmyinventory.util.PositionUtil;
import org.leralix.searchmyinventory.util.sound.SoundEnum;
import org.leralix.searchmyinventory.util.sound.SoundUtil;

import java.util.ArrayList;
import java.util.List;


public class SearchPlayer extends SubCommand {
    @Override
    public String getName() {
        return "player";
    }

    @Override
    public String getDescription() {
        return Lang.SEARCH_DESC.get();
    }

    @Override
    public String getSyntax() {
        return "/search player <player name>";
    }

    public int getArguments(){
        return 2;
    }

    @Override
    public List<String> getTabCompleteSuggestions(Player player, String[] args){
        List<String> suggestions = new ArrayList<>();
        if (args.length == 2) {
            for (org.bukkit.entity.Player p : Bukkit.getOnlinePlayers()) {
                if(p != player)
                    suggestions.add(p.getName());
            }
        }
        return suggestions;
    }

    @Override
    public void perform(org.bukkit.entity.Player player, String[] args){

        if(args.length != getArguments()){
            player.sendMessage(Lang.INVALID_ARGUMENTS.get());
            return;
        }

        String playerName = args[1];
        Player target = Bukkit.getPlayer(playerName);

        if(target == null){
            player.sendMessage(Lang.PLAYER_NOT_FOUND.get());
            return;
        }

        int maxDistance = ConfigUtil.getCustomConfig("config.yml").getInt("maxDistance", 15);
        if(PositionUtil.getPositionBetween(player, target) > maxDistance){
            player.sendMessage(Lang.TARGET_TOO_FAR.get(target.getName()));
            return;
        }

        sendInvitation(target, player);

    }

    private void sendInvitation(Player target, Player sender) {

        InvitationStorage.addInvitation(target, sender);

        sender.sendMessage(Lang.INVITATION_SENT.get(target.getName()));
        SoundUtil.playSound(sender, SoundEnum.INVITATION_SENT);

        target.sendMessage(Lang.INVITATION_RECEIVED.get(sender.getName()));
        ChatUtil.sendClickableCommand(target, Lang.CLICK_TO_ACCEPT.get(), "search accept " + sender.getName());
        SoundUtil.playSound(target, SoundEnum.INVITATION_RECEIVED);

    }

}



