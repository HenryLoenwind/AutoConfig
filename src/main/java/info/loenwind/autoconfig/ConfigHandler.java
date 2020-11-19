package info.loenwind.autoconfig;

import java.io.File;

import javax.annotation.Nullable;

import info.loenwind.autoconfig.factory.ByteBufAdapters;
import info.loenwind.autoconfig.factory.IRootFactory;
import info.loenwind.autoconfig.util.Configuration;
import info.loenwind.autoconfig.util.Log;
import info.loenwind.autoconfig.util.NullHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.loading.FMLPaths;

public class ConfigHandler {

  protected final File configDirectory;
  protected final Configuration config;

  protected final IRootFactory factory;

  public ConfigHandler(FMLCommonSetupEvent event, IRootFactory factory) {
    this(event, factory, null);
  }

  public ConfigHandler(FMLCommonSetupEvent event, IRootFactory factory, @Nullable String folder) {
    this.factory = factory;
    ByteBufAdapters.NONE.getClass(); // trigger system init
    MinecraftForge.EVENT_BUS.register(this);
    if (folder != null) {
      configDirectory = new File(FMLPaths.CONFIGDIR.get().toFile(), folder);
    } else {
      configDirectory = NullHelper.notnullF(FMLPaths.CONFIGDIR.get().toFile(), "FMLCommonSetupEvent has no config folder");
    }
    if (!configDirectory.exists()) {
      configDirectory.mkdir();
    }

    File configFile = new File(configDirectory, factory.getModid() + ".cfg");
    config = new Configuration(configFile);
    syncConfig();
  }

  public void syncConfig() {
    try {
      factory.setConfig(config);
    } catch (Exception e) {
      Log.error(factory.getModid() + " has a problem loading its configuration:");
      e.printStackTrace();
    } finally {
      if (config.hasChanged()) {
        config.save();
      }
    }
  }

  @SubscribeEvent
  public void onConfigChanged(ModConfig.ModConfigEvent event) {
    if (event.getConfig().getModId().equals(factory.getModid())) {
      Log.info("Updating config...");
      syncConfig();
    }
  }

}
