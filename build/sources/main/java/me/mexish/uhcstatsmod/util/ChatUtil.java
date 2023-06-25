package me.mexish.uhcstatsmod.util;

import me.mexish.uhcstatsmod.UhcStatsMod;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class ChatUtil {

    public static void base(String message) {
        IChatComponent msg = new ChatComponentText(UhcStatsMod.PREFIX + " " + message);
        UhcStatsMod.mc.thePlayer.addChatMessage(msg);
    }

}
