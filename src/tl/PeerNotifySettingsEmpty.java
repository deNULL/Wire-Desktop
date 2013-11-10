package tl;

import java.nio.ByteBuffer;

public class PeerNotifySettingsEmpty extends tl.TPeerNotifySettings {

  
  public PeerNotifySettingsEmpty(ByteBuffer buffer) throws Exception {

  }
  
  public PeerNotifySettingsEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x70a68512);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at PeerNotifySettingsEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(peerNotifySettingsEmpty)";
  }
}
