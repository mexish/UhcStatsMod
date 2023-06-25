package me.mexish.uhcstatsmod.listener;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import me.mexish.uhcstatsmod.UhcStatsMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EventListener {

    @Getter @Setter static int ticksChecked = 0;

    @Getter static boolean inDuel = false;

    EntityPlayer player;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();



    public EventListener() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void onTick(TickEvent e) {
        /*if (ticksChecked > 100) {
            ticksChecked = 0;
            return;
        }*/
        /*if (ticksChecked > 1) {
            if (Utils.isOnHypixel()) {
                try {
                    ChatUtil.base("Checking player: " + UhcStatsMod.INSTANCE.getConfiguration().getTrackedPlayer());
                    Executors.newFixedThreadPool(3).submit(() -> {
                        System.out.println("Reijrse");
                        String lastGameType = "";
                        if (UhcStatsMod.getLastGameType() != null) {
                            lastGameType = UhcStatsMod.getLastGameType();
                        }
                        if (!lastGameType.equalsIgnoreCase("")) {
                            try {
                                if (!HypixelRequests.getHypixelAPI().getStatus(HypixelRequests.getHypixelAPI().getPlayerByName(UhcStatsMod.INSTANCE.getConfiguration().getTrackedPlayer()).get().getPlayer().getUuid()).get().getSession().getMode().equalsIgnoreCase(UhcStatsMod.getLastGameType())) {
                                    try {
                                        ChatUtil.base("GameType changed to: " + HypixelRequests.getHypixelAPI().getStatus(HypixelRequests.getHypixelAPI().getPlayerByName(UhcStatsMod.INSTANCE.getConfiguration().getTrackedPlayer()).get().getPlayer().getUuid()).get().getSession().getMode());
                                        UhcStatsMod.setLastGameType(HypixelRequests.getHypixelAPI().getStatus(HypixelRequests.getHypixelAPI().getPlayerByName(UhcStatsMod.INSTANCE.getConfiguration().getTrackedPlayer()).get().getPlayer().getUuid()).get().getSession().getMode());
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            if (!UhcStatsMod.INSTANCE.getConfiguration().getTrackedPlayer().equalsIgnoreCase("")) {
                                try {
                                    UhcStatsMod.setLastGameType(HypixelRequests.getHypixelAPI().getStatus(HypixelRequests.getHypixelAPI().getPlayerByName(UhcStatsMod.INSTANCE.getConfiguration().getTrackedPlayer()).get().getPlayer().getUuid()).get().getSession().getMode());
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }

                        }
                        //ticksChecked++;
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }*/


        // Splitter

        if (UhcStatsMod.mc.thePlayer != null && UhcStatsMod.mc.theWorld != null) {
            val scoreObjNames = UhcStatsMod.mc.theWorld.getScoreboard().getObjectiveNames();
            inDuel = scoreObjNames.contains("Mode: ");
        }



    }

    int called = 0;
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void playerLogIn(EntityJoinWorldEvent e) {
        World world = e.world;
        if (e.entity instanceof EntityPlayer) {
            player = (EntityPlayer) e.entity;


            if (player.getName().equalsIgnoreCase(UhcStatsMod.mc.thePlayer.getName())) {
                Future<?> future = scheduler.schedule(() -> {
                    // Execute the command
                    if (!(called > 0)) {
                        ClientCommandHandler.instance.executeCommand(player, "/scanplayers");
                        //ChatUtil.base("Executed command");
                        ++called;
                    }
                }, 2, TimeUnit.SECONDS);
            }



            // Keep this down here so that it keeps working
            Future<?> future = scheduler.schedule(() -> {
                // Execute the command
                if (called > 0) {
                    called = 0;
                }

            }, 6, TimeUnit.SECONDS);
            //
        }
    }

}
