package info.loenwind.autoconfig.factory;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import info.loenwind.autoconfig.util.ConfigProperty;
import info.loenwind.autoconfig.util.NullHelper;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.fml.loading.FMLLoader;

class FluidValue extends AbstractValue<Fluid> {

  private final static Fluid defaultFluidPlaceholder = new Fluid("", null, null);

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
    if (!FMLLoader.hasReachedState(LoaderState.INITIALIZATION)) {
      return defaultValue;
    }
    if (defaultFluid == null) {
      defaultFluid = FluidRegistry.getFluid(defaultValueName);
    }
    if (value == null || valueGeneration != owner.getGeneration()) {
      final Map<String, Object> serverConfig = owner.getServerConfig();
      if (serverConfig != null && serverConfig.containsKey(keyname)) {
        value = (Fluid) serverConfig.get(keyname);
      } else {
        value = FluidRegistry.getFluid(valueInConfig);
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