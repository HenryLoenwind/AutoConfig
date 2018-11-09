package info.loenwind.autoconfig.factory;

import javax.annotation.Nullable;

import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.config.Property;

class IntValue extends AbstractValue<Integer> {

  protected IntValue(IValueFactory owner, String section, String keyname, Integer defaultValue, String text) {
    super(owner, section, keyname, defaultValue, text);
  }

  @Override
  protected @Nullable Integer makeValue() {
    int min = minValue != null ? minValue.intValue() : Integer.MIN_VALUE, max = maxValue != null ? maxValue.intValue() : Integer.MAX_VALUE;
    Property prop = owner.getConfig().get(section, keyname, defaultValue);
    prop.setLanguageKey(keyname);
    prop.setComment(getText() + " [range: " + min + " ~ " + max + ", default: " + defaultValue + "]");
    prop.setMinValue(min);
    prop.setMaxValue(max);
    prop.setRequiresMcRestart(isStartup);
    return MathHelper.clamp(prop.getInt(defaultValue), min, max);
  }

  @Override
  protected IByteBufAdapter<Integer> getDataType() {
    return ByteBufAdapters.INTEGER;
  }

}