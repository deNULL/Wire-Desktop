package tl;

import java.nio.ByteBuffer;

public class InputAppEvent extends tl.TInputAppEvent {

  
  public InputAppEvent(ByteBuffer buffer) {
    time = buffer.getDouble();
    try {  type = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    peer = buffer.getLong();
    try {  data = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
  }
  
  public InputAppEvent(double time, String type, long peer, String data) {
    this.time = time;
    this.type = type;
    this.peer = peer;
    this.data = data;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x770656a8);
    }
    buffer.putDouble(time);
    try { TL.writeString(buffer, type.getBytes("UTF8"), false); } catch (Exception e) { };
    buffer.putLong(peer);
    try { TL.writeString(buffer, data.getBytes("UTF8"), false); } catch (Exception e) { };
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputAppEvent: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 16 + TL.length(type.getBytes()) + TL.length(data.getBytes());
  }
  
  public String toString() {
    return "(inputAppEvent time:" + time + " type:" + "type" + " peer:" + String.format("0x%016x", peer) + " data:" + "data" + ")";
  }
}
