package info.loenwind.autoconfig.factory;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

class Network {

  private static final String PROTOCOL_VERSION = "1";
  private static int ID = 0;
  private static SimpleChannel INSTANCE;

  public static void sendTo(Object packet, ServerPlayerEntity player) {
    INSTANCE.sendTo(packet, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
  }

  public static void create() {
    INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("autoconfig", "confighandler"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    INSTANCE.messageBuilder(PacketConfigSync.class, ID++)
            .encoder(PacketConfigSync::toBytes)
            .decoder(PacketConfigSync::new)
            .consumer(PacketConfigSync::handle)
            .add();
  }

}
