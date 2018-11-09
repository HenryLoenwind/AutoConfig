package info.loenwind.autoconfig.util;

import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;

public enum Lang {
  NETWORK_BAD_CONFIG("autoconfig.network.bad_config"),
  NETWORK_CONFIG_CONNECTED("autoconfig.network.autosync.connected"),
  NETWORK_CONFIG_OFFLINE("autoconfig.network.autosync.offline"),
  NETWORK_CONFIG_SYNC("autoconfig.network.manusync"),

  ;

  private final String key;

  private Lang(String key) {
    this.key = key;
  }

  public String get() {
    return I18n.translateToLocal(key);
  }

  public String get(Object... params) {
    return I18n.translateToLocalFormatted(key, params);
  }

  public TextComponentTranslation toChatServer() {
    return new TextComponentTranslation(key);
  }

  public TextComponentTranslation toChatServer(Object... params) {
    return new TextComponentTranslation(key, params);
  }

}
