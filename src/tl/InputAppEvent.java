package tl;

import java.nio.ByteBuffer;

public class InputAppEvent extends tl.TInputAppEvent {

  
  public InputAppEvent(ByteBuffer buffer) throws Exception {
    time = buffer.getDouble();
    type = new String(TL.readString(buffer), "UTF8");
    peer = buffer.getLong();
    data = new String(TL.readString(buffer), "UTF8");
  }
  
  public InputAppEvent(double time, String type, long peer, String data) {
    this.time = time;
    this.type = type;
    this.peer = peer;
    this.data = data;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x770656a8);
    }
    buffer.putDouble(time);
    TL.writeString(buffer, type.getBytes("UTF8"), false);
    buffer.putLong(peer);
    TL.writeString(buffer, data.getBytes("UTF8"), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputAppEvent: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 16 + TL.length(type.getBytes("UTF8")) + TL.length(data.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(inputAppEvent time:" + time + " type:" + "type" + " peer:" + String.format("0x%016x", peer) + " data:" + "data" + ")";
  }
}
