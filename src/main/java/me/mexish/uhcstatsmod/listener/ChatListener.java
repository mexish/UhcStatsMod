package me.mexish.uhcstatsmod.listener;

import lombok.val;
import me.mexish.uhcstatsmod.UhcStatsMod;
import me.mexish.uhcstatsmod.util.ChatUtil;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

public class ChatListener {

    public ChatListener() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static final String NEW_KEY_PATTERN = "Your new API key is ";

    @SubscribeEvent(receiveCanceled = true)
    public void onChatMessageReceived(final ClientChatReceivedEvent event) {
        if (event.type != 0) {
            return;
        }

        val text = event.message.getUnformattedText();

        if (text.startsWith(ChatListener.NEW_KEY_PATTERN)) {
            val key = text.substring(ChatListener.NEW_KEY_PATTERN.length());
            UhcStatsMod.INSTANCE.getConfiguration().setApiKey(UUID.fromString(key));
            UhcStatsMod.INSTANCE.getConfiguration().save();
            ChatUtil.base("Automatically applied your API key to: " + key);
            UhcStatsMod.INSTANCE.reloadConfiguration();
        }
    }
}
