package info.loenwind.autoconfig.factory;

import java.util.Random;

import javax.annotation.Nullable;

import info.loenwind.autoconfig.util.ConfigProperty;
import net.minecraft.util.math.MathHelper;

class FloatValue extends AbstractValue<Float> {

  protected FloatValue(IValueFactory owner, String section, String keyname, Float defaultValue, String text) {
    super(owner, section, keyname, defaultValue, text);
  }

  @Override
  protected @Nullable Float makeValue() {
    float min = minValue != null ? minValue.floatValue() : Float.NEGATIVE_INFINITY, max = maxValue != null ? maxValue.floatValue() : Float.MAX_VALUE;
    @SuppressWarnings("cast")
    ConfigProperty prop = owner.getConfig().get(section, keyname, (double) defaultValue);
    prop.setLanguageKey(keyname);
    prop.setComment(getText() + " [range: " + min + " ~ " + max + ", default: " + defaultValue + "]");
    prop.setMinValue(min);
    prop.setMaxValue(max);
    prop.setRequiresMcRestart(isStartup);
    return (float) MathHelper.clamp(prop.getDouble(defaultValue), min, max);
  }

  @Override
  protected IByteBufAdapter<Float> getDataType() {
    return ByteBufAdapters.FLOAT;
  }

  @Override
  public int getChance(Random rand) {
    float chance = get();
    int result = (int) chance;
    if ((result + rand.nextFloat()) < chance) {
      result++;
    }
    return result;
  }

}