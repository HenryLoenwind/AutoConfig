package info.loenwind.autoconfig.factory;

import javax.annotation.Nullable;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketConfigSync implements IMessage {

  protected @Nullable IValueFactory factory;
  protected @Nullable String modid, section;
  protected @Nullable ByteBuf bufferCopy;

  public PacketConfigSync() {
    this.factory = null;
  }

  PacketConfigSync(IValueFactory factory) {
    this.factory = factory;
  }

  @SuppressWarnings("null")
  @Override
  public void toBytes(@Nullable ByteBuf buf) {
    ByteBufUtils.writeUTF8String(buf, factory.getModid());
    ByteBufUtils.writeUTF8String(buf, factory.getSection());
    ByteBufAdapterRegistry.saveMapping(buf);
    factory.save(buf);
  }

  @SuppressWarnings("null")
  @Override
  public void fromBytes(@Nullable ByteBuf buf) {
    modid = ByteBufUtils.readUTF8String(buf);
    section = ByteBufUtils.readUTF8String(buf);
    ByteBufAdapterRegistry.loadMapping(buf);
    bufferCopy = buf.copy();
  }

  public static class Handler implements IMessageHandler<PacketConfigSync, IMessage> {
    @SuppressWarnings("null")
    @Override
    public IMessage onMessage(@Nullable PacketConfigSync message, @Nullable MessageContext ctx) {
      if (!Minecraft.getMinecraft().isIntegratedServerRunning()) {
        FactoryManager.read(message.modid, message.section, message.bufferCopy);
      }
      if (message.bufferCopy != null) {
        message.bufferCopy.release();
      }
      return null;
    }
  }

}
