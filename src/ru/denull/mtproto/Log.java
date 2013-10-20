package ru.denull.mtproto;

public class Log {
  public static void i(String tag, String message) {
    System.out.println(tag + ":\t " + message);
  }
  public static void e(String tag, String message) {
    System.err.println(tag + ":\t" + message);
  }
  public static void w(String tag, String message) {
    System.err.println(tag + ":\t" + message);
  }
}
