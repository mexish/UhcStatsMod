package me.mexish.uhcstatsmod.util;

import lombok.Getter;
import lombok.NonNull;
import me.mexish.uhcstatsmod.UhcStatsMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.regex.Pattern;

public class Utils {

    public Utils() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Getter static boolean isOnHypixel;

    @SubscribeEvent
    public void onConnect(FMLNetworkEvent.ClientConnectedToServerEvent e) {
        ServerData data = UhcStatsMod.mc.getCurrentServerData();
        if (data != null && data.serverIP.contains("hypixel.net")) {
            isOnHypixel = true;
        }
    }

    @SubscribeEvent
    public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        isOnHypixel = false;
    }

    public static String removeFormatting(String input) {
        return input.replaceAll("§[0-9a-fk-or]", "");
    }

    /**
     * Some anti-bot implementation :P
     * @param player    the entity that we are checking.
     * @return          if
     */
    public static boolean isPlayer(final @NonNull EntityPlayer player) {
        return Pattern.compile("\\w{3,16}").matcher(player.getName()).matches()
                && Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap()
                .stream()
                .anyMatch($pl ->
                        $pl.getDisplayName() != null
                                && $pl.getDisplayName().toString().contains(player.getName())
                );
    }
}
