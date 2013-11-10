package ru.denull.wire.model;

public class Config {
	public static final int api_id = 1220;
	public static final String api_hash = "e9bac3af3eef81acd2d8576c14a9dfd0";
	public static final String app_version = "0.1";
	public static final boolean DEBUG = true;
  public static String getDeviceModel() {
    return System.getProperty("os.name") + ", " + System.getProperty("os.version") + ", " + System.getProperty("os.arch");
  }
  public static String getSystemVersion() {
    return "Java " + System.getProperty("java.runtime.version") + ", " + System.getProperty("java.vendor");
  }
}
