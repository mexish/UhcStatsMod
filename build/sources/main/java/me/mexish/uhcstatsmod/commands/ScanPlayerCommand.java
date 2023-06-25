package me.mexish.uhcstatsmod.commands;

import lombok.val;
import me.mexish.uhcstatsmod.UhcStatsMod;
import me.mexish.uhcstatsmod.util.ChatUtil;
import me.mexish.uhcstatsmod.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.ClientCommandHandler;

public class ScanPlayerCommand extends CommandBase {

    public ScanPlayerCommand() {
        ClientCommandHandler.instance.registerCommand(this);
    }

    @Override
    public String getCommandName() {
        return "scanplayers";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/scanplayers";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_canCommandSenderUseCommand_1_) {
        return true;
    }

    int countedPlayers = 0;

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        try {
            for (val ent : Minecraft.getMinecraft().theWorld.getEntities(EntityPlayer.class, ($) -> true)) {

                if (countedPlayers > 2) {
                    countedPlayers = 0;
                    return;
                }

                val isBot = !Utils.isPlayer(ent);
                if (ent.getName() != null) {
                    ChatUtil.base(ent.getName() + (isBot ? " - BOT" : ""));
                }

                if (isBot) {
                    continue;
                }


                if (ent != UhcStatsMod.mc.thePlayer) {
                    ChatUtil.base("Your opponent: " + ent.getName());
                    countedPlayers++;
                }


            }



        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
