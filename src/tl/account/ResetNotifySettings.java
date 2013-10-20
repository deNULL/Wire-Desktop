package tl.account;

import tl.TL;
import java.nio.ByteBuffer;

public class ResetNotifySettings extends tl.TLFunction {

  
  public ResetNotifySettings(ByteBuffer buffer) {

  }
  
  public ResetNotifySettings() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xdb7e1747);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(ResetNotifySettings)";
  }
}
