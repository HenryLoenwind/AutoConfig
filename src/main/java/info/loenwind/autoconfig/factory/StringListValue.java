package info.loenwind.autoconfig.factory;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import info.loenwind.autoconfig.util.ConfigProperty;

class StringListValue extends AbstractValue<List<String>> {

  protected StringListValue(IValueFactory owner, String section, String keyname, List<String> defaultValue, String text) {
    super(owner, section, keyname, defaultValue, text);
  }

  @Override
  protected @Nullable List<String> makeValue() {
    ConfigProperty prop = owner.getConfig().get(section, keyname, defaultValue.toArray(new String[0]));
    prop.setLanguageKey(keyname);
    prop.setValidationPattern(null);
    prop.setComment(getText() + " [default: " + defaultValue.toArray(new String[0]) + "]");
    prop.setRequiresMcRestart(isStartup);
    return Arrays.asList(prop.getStringList());
  }

  @Override
  protected IByteBufAdapter<List<String>> getDataType() {
    return ByteBufAdapters.STRINGLIST;
  }

}