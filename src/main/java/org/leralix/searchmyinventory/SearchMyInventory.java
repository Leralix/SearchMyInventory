package org.leralix.searchmyinventory;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.leralix.searchmyinventory.Lang.Lang;
import org.leralix.searchmyinventory.commands.CommandManager;
import org.leralix.searchmyinventory.util.ConfigUtil;
import org.leralix.searchmyinventory.util.sound.SoundStorage;

import java.util.logging.Logger;

public final class SearchMyInventory extends JavaPlugin {

    private static SearchMyInventory plugin;
    static Logger logger;

    @Override
    public void onEnable() {
        plugin = this;
        logger = this.getLogger();


        getLogger().info("To report a bug or request a feature, please ask on my discord server: https://discord.gg/Q8gZSFUuzb");

        logger.info("[SMI] Loading Plugin");

        logger.info("[SMI] -Loading Lang");

        ConfigUtil.saveAndUpdateResource("lang.yml");
        ConfigUtil.loadCustomConfig("lang.yml");
        String lang = ConfigUtil.getCustomConfig("lang.yml").getString("language");
        Lang.loadTranslations(lang + ".yml");

        getCommand("search").setExecutor(new CommandManager());

        logger.info("[SMI] -Loading Config");

        ConfigUtil.saveAndUpdateResource("config.yml");
        ConfigUtil.loadCustomConfig("config.yml");

        SoundStorage.init();


        logger.info("[SMI] -Plugin Loaded");

    }

    @Override
    public void onDisable() {


    }

    public static SearchMyInventory getPlugin(){
        return plugin;
    }

    public static Logger getPluginLogger() {
        return logger;
    }
}
