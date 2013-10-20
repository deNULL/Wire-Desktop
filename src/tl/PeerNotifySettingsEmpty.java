package tl;

import java.nio.ByteBuffer;

public class PeerNotifySettingsEmpty extends tl.TPeerNotifySettings {

  
  public PeerNotifySettingsEmpty(ByteBuffer buffer) {

  }
  
  public PeerNotifySettingsEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x70a68512);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(PeerNotifySettingsEmpty)";
  }
}
