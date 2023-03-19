package me.mexish.uhcstatsmod.commands;

import com.google.gson.JsonObject;
import kong.unirest.json.JSONObject;
import lombok.val;
import me.mexish.uhcstatsmod.UhcStatsMod;
import me.mexish.uhcstatsmod.requests.HypixelRequests;
import me.mexish.uhcstatsmod.requests.MojangRequest;
import me.mexish.uhcstatsmod.util.ChatUtil;
import net.hypixel.api.HypixelAPI;
import net.hypixel.api.reply.PlayerReply;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.ClientCommandHandler;

import java.text.DecimalFormat;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static net.hypixel.api.data.type.GameType.UHC;

public class UhcStatCommand extends CommandBase {

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_canCommandSenderUseCommand_1_) {
        return true;
    }

    public UhcStatCommand() {
        ClientCommandHandler.instance.registerCommand(this);
    }

    @Override
    public String getCommandName() {
        return "uhcstat";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/uhcstat <player>";
    }


    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 1) {
            String playerName = args[0];
            UUID playerUuid = MojangRequest.getUUID(playerName);
            if (playerUuid == null) {
                ChatUtil.base("§cCouldn't find player uuid!");
                return;
            } else {
                //ChatUtil.base("Found player: " + playerUuid);
            }

            try {
                Executors.newFixedThreadPool(3).submit(() -> {
                    val playerReply = HypixelRequests.getHypixelAPI().getPlayerByUuid(playerUuid);
                    JsonObject stats = null;
                    try {
                        stats = playerReply.get().getPlayer().getProperty("stats").getAsJsonObject().get("UHC").getAsJsonObject();
                    } catch (Exception e) {
                        return;
                    }
                    int kills = 0;
                    if (stats.get("kills") != null) {
                        kills = stats.get("kills").getAsInt();
                    }
                    int killsSolo = 0;
                    if (stats.get("kills_solo") != null) {
                        killsSolo = stats.get("kills_solo").getAsInt();
                    }
                    int totalKills = kills + killsSolo;
                    int soloWins = 0;
                    if (stats.get("wins_solo") != null) {
                        soloWins = stats.get("wins_solo").getAsInt();
                    }
                    int teamWins = 0;
                    if (stats.get("wins") != null) {
                        teamWins = stats.get("wins").getAsInt();
                    }
                    int totalWins = soloWins + teamWins;
                    int soloDeaths = 0;
                    if (stats.get("deaths_solo") != null) {
                        soloDeaths = stats.get("deaths_solo").getAsInt();
                    }
                    int teamDeaths = 0;
                    if (stats.get("deaths") != null) {
                        teamDeaths = stats.get("deaths").getAsInt();
                    }
                    int totalDeaths = soloDeaths + teamDeaths;

                    float KDR = 0;
                    if (totalKills > 0 && totalDeaths > 0) {
                        KDR = (float) totalKills / totalDeaths;
                    } else if (totalDeaths <= 1 && totalKills > 0) {
                        KDR = totalKills;
                    }
                    DecimalFormat df = new DecimalFormat("#.##");
                    String formattedKdNumber = df.format(KDR);
                    int score = stats.get("score").getAsInt();
                    String finalStats =
                            "§2Total Kills: §a" + totalKills + " §3(KD: " + formattedKdNumber + ")" + "\n" +
                                    "§2Total Wins: §a" + totalWins + "\n" +
                                    "§2Score: §a" + score;
                    //ChatUtil.base("Team Kills: " + kills);
                    //ChatUtil.base("Solo Kills: " + killsSolo);
                    IChatComponent finalStatMessage = new ChatComponentText(UhcStatsMod.PREFIX + "\n" + "§bPlayer stats for §3" + playerName + ":\n" + finalStats);
                    UhcStatsMod.mc.thePlayer.addChatMessage(finalStatMessage);
                });


            } catch (Exception ex) {
                ChatUtil.base("§cCouldn't find player stats for UHC!");
                return;
            }
        }
    }
}
