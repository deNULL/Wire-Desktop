package tl;

import java.nio.ByteBuffer;

public class FutureSalts extends tl.TFutureSalts {

  
  public FutureSalts(ByteBuffer buffer) throws Exception {
    req_msg_id = buffer.getLong();
    now = buffer.getInt();
    salts = TL.readVector(buffer, false, new tl.TFutureSalt[0]);
  }
  
  public FutureSalts(long req_msg_id, int now, tl.TFutureSalt[] salts) {
    this.req_msg_id = req_msg_id;
    this.now = now;
    this.salts = salts;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xae500895);
    }
    buffer.putLong(req_msg_id);
    buffer.putInt(now);
    TL.writeVector(buffer, salts, false, false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at FutureSalts: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 16 + TL.length(salts);
  }
  
  public String toString() {
    return "(future_salts req_msg_id:" + String.format("0x%016x", req_msg_id) + " now:" + now + " salts:" + TL.toString(salts) + ")";
  }
}
