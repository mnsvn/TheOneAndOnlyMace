package ru.mnsvn.theoneandonlymace;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CrafterCraftEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TheOneAndOnlyMace extends JavaPlugin implements Listener {

    private boolean alreadyCrafted;
    private final ItemStack AIR = new ItemStack(Material.AIR);

    @EventHandler
    public void onMaceCraftBanFurtherCrafts(PrepareItemCraftEvent e) {
        Recipe recipe = e.getRecipe();
        if (recipe == null)
            return;

        ItemStack result = recipe.getResult();
        Material resultType = result.getType();

        if (resultType != Material.MACE)
            return;

        if (alreadyCrafted)
            e.getInventory().setResult(AIR);
        else
            alreadyCrafted = true;
    }

    @EventHandler
    public void onMaceAutoCraftBanFurtherCrafts(CrafterCraftEvent e) {
        ItemStack result = e.getResult();
        Material resultType = result.getType();

        if (resultType != Material.MACE)
            return;

        if (alreadyCrafted)
            e.setResult(AIR);
        else
            alreadyCrafted = true;
    }

    @Override
    public void onEnable() {
        this.getConfig().addDefault("alreadyCrafted", false);
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();

        alreadyCrafted = this.getConfig().getBoolean("alreadyCrafted", false);
        getLogger().info("Enabled? " + alreadyCrafted);

        Bukkit.getPluginManager().registerEvents(this, this);

        getCommand("theoneandonlymace").setExecutor(this);
        getCommand("theoneandonlymace").setTabCompleter(this);
    }

    @Override
    public void onDisable() {
        this.getConfig().set("alreadyCrafted", alreadyCrafted);
        this.saveConfig();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (args.length > 0 && args[0].equals("craft")) {
            alreadyCrafted = true;
        } else if (args.length > 0 && args[0].equals("uncraft")) {
            alreadyCrafted = false;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length > 0) return List.of("craft", "uncraft");
        return List.of();
    }
}
