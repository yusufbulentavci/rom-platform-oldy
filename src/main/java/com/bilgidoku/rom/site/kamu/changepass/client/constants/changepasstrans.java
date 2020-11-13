package com.bilgidoku.rom.site.kamu.changepass.client.constants;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	/home/rompg/rom/phase8/java/rom/src/main/resources/com/bilgidoku/rom/site/kamu/changepass/client/constants/changepasstrans.properties'.
 */
public interface changepasstrans extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "Different passwords".
   * 
   * @return translated "Different passwords"
   */
  @DefaultMessage("Different passwords")
  @Key("differentPasswords")
  String differentPasswords();

  /**
   * Translated "Fill form properly please".
   * 
   * @return translated "Fill form properly please"
   */
  @DefaultMessage("Fill form properly please")
  @Key("emptyForm")
  String emptyForm();

  /**
   * Translated "No token".
   * 
   * @return translated "No token"
   */
  @DefaultMessage("No token")
  @Key("noToken")
  String noToken();

  /**
   * Translated "Password".
   * 
   * @return translated "Password"
   */
  @DefaultMessage("Password")
  @Key("password")
  String password();

  /**
   * Translated "Retype password".
   * 
   * @return translated "Retype password"
   */
  @DefaultMessage("Retype password")
  @Key("retypepassword")
  String retypepassword();

  /**
   * Translated "Set password".
   * 
   * @return translated "Set password"
   */
  @DefaultMessage("Set password")
  @Key("setpassword")
  String setpassword();

  /**
   * Translated "Password is too short (minimum 6 chars)".
   * 
   * @return translated "Password is too short (minimum 6 chars)"
   */
  @DefaultMessage("Password is too short (minimum 6 chars)")
  @Key("tooShortPass")
  String tooShortPass();
}
