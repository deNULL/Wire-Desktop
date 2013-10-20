package tl.account;

import tl.TL;
import java.nio.ByteBuffer;

public class ResetNotifySettings extends tl.TLFunction {

  
  public ResetNotifySettings(ByteBuffer buffer) {

  }
  
  public ResetNotifySettings() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xdb7e1747);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ResetNotifySettings: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(account.resetNotifySettings)";
  }
}
