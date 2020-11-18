package info.loenwind.autoconfig.factory;

import java.util.HashMap;
import java.util.Map;

import info.loenwind.autoconfig.util.Log;
import info.loenwind.autoconfig.util.NullHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FactoryManager {

  private static boolean registered = false;

  private static void create() {
    if (!registered) {
      MinecraftForge.EVENT_BUS.register(FactoryManager.class);
      Network.create();
      registered = true;
    }
  }

  public static final String SERVER_OVERRIDE = " (synced from server)";
  public static final String SERVER_SYNC = " (must be kept in sync with server)";
  private static final Map<String, IValueFactory> factories = new HashMap<>();

  private static int overrides = 0;

  public static boolean hasOverrides() {
    return overrides > 0;
  }

  static void read(String mod, String section, final ByteBuf buf) {
    Log.debug("Read " + factories.get(mod + "." + section).read(buf) + " config values from server packet for " + mod + " (" + section + ")");
    overrides++;
  }

  static void registerFactory(IValueFactory factory) {
    synchronized (factories) {
      create();
      factories.put(factory.getModid() + "." + factory.getSection(), factory);
    }
  }

  @SubscribeEvent
  public static void onPlayerLoggon(final PlayerEvent.PlayerLoggedInEvent evt) {
    for (IValueFactory factory : factories.values()) {
      if (factory.needsSyncing()) {
        Network.sendTo(new PacketConfigSync(factory), (PlayerEntity) NullHelper.notnullF(evt.getPlayer(), "PlayerLoggedInEvent without player"));
        Log.debug("Sent config to player " + evt.getPlayer().getDisplayName() + " for " + factory.getModid() + " (" + factory.getSection() + ")");
      }
    }
  }

  @SubscribeEvent
  public static void onPlayerLogout(final ClientDisconnectionFromServerEvent event) {
    for (IValueFactory factory : factories.values()) {
      factory.endServerOverride();
      Log.debug("Removed server config override for " + factory.getModid() + " (" + factory.getSection() + ")");
    }
    overrides = 0;
  }

}
