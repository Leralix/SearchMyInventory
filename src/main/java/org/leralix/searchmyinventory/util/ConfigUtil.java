package org.leralix.searchmyinventory.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.leralix.searchmyinventory.SearchMyInventory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used for config related utilities.
 */
public class ConfigUtil {
    /**
     * This map is used to store the custom configs.
     */
    private static final Map<String, FileConfiguration> configs = new HashMap<>();

    /**
     * Get a custom config by its name.
     * @param fileName  The name of the config file.
     * @return          The {@link FileConfiguration } object.
     */
    public static FileConfiguration getCustomConfig(final String fileName) {
        return configs.get(fileName);
    }

    /**
     * Save a resource file.
     * @param fileName the name of the ressource file.
     */
    public static void saveResource(final String fileName) {
        File file = new File(SearchMyInventory.getPlugin().getDataFolder(),fileName);
        if (!file.exists()) {
            SearchMyInventory.getPlugin().saveResource(fileName, false);
        }
    }

    /**
     * Save and update a resource file. If some lines are missing in the current file, they will be added at the end
     * @param fileName  The name of the resource file.
     */
    public static void saveAndUpdateResource(final String fileName) {


        File currentFile = new File(SearchMyInventory.getPlugin().getDataFolder(),fileName);
        if (!currentFile.exists()) {
            SearchMyInventory.getPlugin().saveResource(fileName, false);
        }

        InputStream baseFile = SearchMyInventory.getPlugin().getResource(fileName);

        List<String> baseFileLines = new ArrayList<>();
        List<String> currentFileLines = new ArrayList<>();

        //Read base file
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(currentFile)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                currentFileLines.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Read current file
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(baseFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                baseFileLines.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateCurrentFileWithBaseFile(currentFile, baseFileLines, currentFileLines);
    }


    /**
     * Update the current file with the base file. If some lines are missing in the current file, they will be added at the end
     * @param file              The file to update
     * @param baseFileLines     The lines of the base file
     * @param currentFileLines  The lines of the current file
     */
    public static void updateCurrentFileWithBaseFile(final File file, final  List<String> baseFileLines, final List<String> currentFileLines) {
        Map<String, String> currentFileMap = new HashMap<>();
        for (String line : currentFileLines) {
            String key = line.contains(":") ? line.split(":")[0].trim() : line;
            currentFileMap.put(key, line);
        }

        for (String baseLine : baseFileLines) {
            String baseKey = baseLine.contains(":") ? baseLine.split(":")[0].trim() : baseLine;
            if (!currentFileMap.containsKey(baseKey)) {
                // Add line if not already present
                SearchMyInventory.getPluginLogger().warning("Adding config line : " + baseLine + " in config file");
                currentFileLines.add(baseLine);
            }
        }
        writeToFile(currentFileLines, file);
    }

    /**
     * Write a list of lines to complete an updated config file
     * @param lines         The list of new lines to add to the config file
     * @param fileToWrite   The file to write
     */
    public static void writeToFile(List<String> lines, File fileToWrite) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToWrite, false))) { // false pour écraser le contenu existant
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load a custom config file into the memory
     * @param fileName  The name of the file to load
     */
    public static void loadCustomConfig(String fileName) {

        File configFile = new File(SearchMyInventory.getPlugin().getDataFolder(), fileName);
        if (!configFile.exists()) {
            SearchMyInventory.getPluginLogger().severe(fileName + " does not exist!");
            return;
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        configs.put(fileName, config);
    }



}
