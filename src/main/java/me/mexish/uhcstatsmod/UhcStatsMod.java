package me.mexish.uhcstatsmod;

import lombok.Getter;
import lombok.NonNull;
import me.mexish.uhcstatsmod.commands.UhcStatCommand;
import me.mexish.uhcstatsmod.config.UhcConfiguration;
import me.mexish.uhcstatsmod.listener.ChatListener;
import me.mexish.uhcstatsmod.requests.HypixelRequests;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(modid = UhcStatsMod.MODID, name="UhcStats", version = UhcStatsMod.VERSION)
public class UhcStatsMod
{
    public static final String MODID = "uhcstatsmod";
    public static final String VERSION = "1.0";

    public static final String PREFIX = "§7[§6UHCSTATS§7]";

    @Mod.Instance
    public static UhcStatsMod INSTANCE;

    @Getter File suggestedConfigurationFile;

    @Getter public UhcConfiguration configuration;

    public static Minecraft mc = Minecraft.getMinecraft();
    
    @EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        this.suggestedConfigurationFile = event.getSuggestedConfigurationFile();
        new ChatListener();
        registerCommands();
        reloadConfiguration();
    }

    public void registerCommands() {
        new UhcStatCommand();
    }

    private void loadFromFile(final @NonNull File file) {
        try {
            configuration = new UhcConfiguration(new Configuration(file)).load();
        } catch (final Throwable e) {
            System.out.println("Unable to load configuration: " + e.getMessage());
            e.printStackTrace();
        }

        HypixelRequests.setKey(configuration.getApiKey());
    }

    public void reloadConfiguration() {
        loadFromFile(suggestedConfigurationFile);
    }


}
