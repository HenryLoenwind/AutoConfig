package info.loenwind.autoconfig.factory;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import info.loenwind.autoconfig.util.ConfigProperty;
import info.loenwind.autoconfig.util.NullHelper;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.ModLoadingStage;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.ForgeRegistries;

class FluidValue extends AbstractValue<Fluid> {

  private final static Fluid defaultFluidPlaceholder = Fluids.EMPTY;

  private final String defaultValueName;
  private @Nullable Fluid defaultFluid = null;

  protected FluidValue(IValueFactory owner, String section, String keyname, String defaultValue, String text) {
    super(owner, section, keyname, defaultFluidPlaceholder, text);
    defaultValueName = defaultValue;
  }

  @Nonnull
  @Override
  public Fluid get() {
    final String valueInConfig = getString(); // make sure the config value is registered with the config object
    if (ModLoadingContext.get().getActiveContainer().getCurrentState() != ModLoadingStage.CONSTRUCT) {
      return defaultValue;
    }
    if (defaultFluid == null) {
      defaultFluid = ForgeRegistries.FLUIDS.getValue(ResourceLocation.tryCreate(defaultValueName));
    }
    if (value == null || valueGeneration != owner.getGeneration()) {
      final Map<String, Object> serverConfig = owner.getServerConfig();
      if (serverConfig != null && serverConfig.containsKey(keyname)) {
        value = (Fluid) serverConfig.get(keyname);
      } else {
        value = ForgeRegistries.FLUIDS.getValue(ResourceLocation.tryCreate(valueInConfig));
        if (!owner.isInInit() && owner.getConfig().hasChanged()) {
          owner.getConfig().save();
        }
      }
      valueGeneration = owner.getGeneration();
    }
    return NullHelper.first(value, defaultFluid, defaultValue);
  }

  private @Nullable String getString() {
    ConfigProperty prop = owner.getConfig().get(section, keyname, defaultValueName);
    prop.setLanguageKey(keyname);
    prop.setValidationPattern(null);
    prop.setComment(getText() + " [default: " + defaultValueName + "]");
    prop.setRequiresMcRestart(isStartup);
    return prop.getString();
  }

  @Override
  protected @Nullable Fluid makeValue() {
    getString();
    return null;
  }

  @Override
  protected IByteBufAdapter<Fluid> getDataType() {
    return ByteBufAdapters.FLUID;
  }

}