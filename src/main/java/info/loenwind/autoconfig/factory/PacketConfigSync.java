package info.loenwind.autoconfig.factory;

import javax.annotation.Nullable;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketConfigSync {

  protected @Nullable IValueFactory factory;
  protected @Nullable String modid, section;
  protected @Nullable ByteBuf bufferCopy;

  public PacketConfigSync() {
    this.factory = null;
  }

  public PacketConfigSync(@Nullable PacketBuffer buf) {
    modid = buf.readString();
    section = buf.readString();
    ByteBufAdapterRegistry.loadMapping(buf);
    bufferCopy = buf.copy();
  }

  PacketConfigSync(IValueFactory factory) {
    this.factory = factory;
  }

  public void toBytes(@Nullable PacketBuffer buf) {
    buf.writeString(factory.getModid());
    buf.writeString(factory.getSection());
    ByteBufAdapterRegistry.saveMapping(buf);
    factory.save(buf);
  }


  public boolean handle(Supplier<NetworkEvent.Context> contextSupplier) {
    if (!Minecraft.getInstance().isIntegratedServerRunning()) {
      FactoryManager.read(modid, section, bufferCopy);
    }
    if (bufferCopy != null) {
      bufferCopy.release();
      return true;
    }
    return false;
  }

}
