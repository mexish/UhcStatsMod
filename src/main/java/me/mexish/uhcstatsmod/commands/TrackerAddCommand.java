package me.mexish.uhcstatsmod.commands;

import me.mexish.uhcstatsmod.UhcStatsMod;
import me.mexish.uhcstatsmod.listener.EventListener;
import me.mexish.uhcstatsmod.requests.HypixelRequests;
import me.mexish.uhcstatsmod.util.ChatUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.client.ClientCommandHandler;

public class TrackerAddCommand extends CommandBase {

    public TrackerAddCommand() {
        ClientCommandHandler.instance.registerCommand(this);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_canCommandSenderUseCommand_1_) {
        return true;
    }

    @Override
    public String getCommandName() {
        return "trackeradd";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/trackeradd <name>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 1) {
            try {
                UhcStatsMod.INSTANCE.configuration.setTrackedPlayer(args[0]);
                UhcStatsMod.INSTANCE.configuration.save();
                ChatUtil.base("Set tracked player to: " + args[0]);
                ChatUtil.base("Current GameType: " + HypixelRequests.getHypixelAPI().getStatus(HypixelRequests.getHypixelAPI().getPlayerByName(UhcStatsMod.INSTANCE.getConfiguration().getTrackedPlayer()).get().getPlayer().getUuid()).get().getSession().getMode());
                if (EventListener.getTicksChecked() == 100 || EventListener.getTicksChecked() > 50) {
                    EventListener.setTicksChecked(101);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                ChatUtil.base("Invalid player, or something else went wrong.");
            }
        }
    }
}
