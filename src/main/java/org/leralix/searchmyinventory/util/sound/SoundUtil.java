package org.leralix.searchmyinventory.util.sound;

import org.bukkit.entity.Player;
import org.leralix.searchmyinventory.util.ConfigUtil;

/**
 * Utility class for playing sounds
 */
public class SoundUtil {
    /**
     * Play a predefined sound to the player
     * @param player    The player to play the sound to
     * @param soundEnum The sound to play
     */
    public static void playSound(Player player, SoundEnum soundEnum){
        if(ConfigUtil.getCustomConfig("config.yml").getBoolean("enableSounds",true)){
            SoundStorage.getSoundData(soundEnum).playSound(player);
        }
    }
}
