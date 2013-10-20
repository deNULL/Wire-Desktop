package tl;

import java.nio.ByteBuffer;

public class InputAppEvent extends tl.TInputAppEvent {

  
  public InputAppEvent(ByteBuffer buffer) {
    time = buffer.getDouble();
    type = new String(TL.readString(buffer));
    peer = buffer.getLong();
    data = new String(TL.readString(buffer));
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
    TL.writeString(buffer, type.getBytes(), false);
    buffer.putLong(peer);
    TL.writeString(buffer, data.getBytes(), false);
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
