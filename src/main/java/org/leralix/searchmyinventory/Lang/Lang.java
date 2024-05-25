package org.leralix.searchmyinventory.Lang;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.leralix.searchmyinventory.SearchMyInventory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public enum Lang {
    LANGUAGE_SUCCESSFULLY_LOADED,
    SEARCH_DESC,
    PLAYER_NOT_FOUND,
    INVITATION_SENT,
    INVITATION_RECEIVED,
    CLICK_TO_ACCEPT,
    INVALID_ARGUMENTS,


    NO_INVITATION,
    INVITATION_ACCEPTED,


    TARGET_TOO_FAR;


    private static final Map<Lang, String> translations = new HashMap<>();

    public static boolean loadTranslations(String filename) {

        File langFolder = new File(SearchMyInventory.getPlugin().getDataFolder(), "lang");

        if (!langFolder.exists()) {
            langFolder.mkdir();
        }


        File file = new File(langFolder, filename);

        if(!file.exists())
            SearchMyInventory.getPlugin().saveResource("lang/" + filename, true);

        boolean replace =  false;//ConfigUtil.getCustomConfig("lang.yml").getBoolean("autoUpdateLangFiles",true);
        if(replace) {
            SearchMyInventory.getPlugin().saveResource("lang/" + filename, true);
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (Lang key : Lang.values()) {
            String message = config.getString("language." + key.name());
            if (message != null) {
                translations.put(key, message);
            }
        }
        SearchMyInventory.getPluginLogger().info(LANGUAGE_SUCCESSFULLY_LOADED.get());
        return true;
    }

    public String get() {
        String translation = translations.get(this);
        if (translation != null) {
            return ChatColor.translateAlternateColorCodes('§', translation);
        }
        return "Message not found for " + this.name() + " in this language file.";
    }

    public String get(Object... placeholders) {
        String translation = translations.get(this);
        if (translation != null) {
            translation = ChatColor.translateAlternateColorCodes('§', translation);
            for (int i = 0; i < placeholders.length; i++) {
                String val = placeholders[i] == null ? "null" : placeholders[i].toString();

                translation = translation.replace("{" + i + "}",val);
            }
            return translation;
        }
        return "Message not found for " + this.name() + " in this language file.";
    }
}