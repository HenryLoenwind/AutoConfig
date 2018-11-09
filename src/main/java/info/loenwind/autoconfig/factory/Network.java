package info.loenwind.autoconfig.factory;

import info.loenwind.autoconfig.factory.PacketConfigSync.Handler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

class Network {

  private static final SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper("autoconfig");

  public static void sendTo(IMessage message, EntityPlayerMP player) {
    INSTANCE.sendTo(message, player);
  }

  public static void create() {
    INSTANCE.registerMessage(Handler.class, PacketConfigSync.class, 0, Side.CLIENT);
  }

}
