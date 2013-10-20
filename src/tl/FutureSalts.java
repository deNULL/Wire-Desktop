package tl;

import java.nio.ByteBuffer;

public class FutureSalts extends tl.TFutureSalts {
  
  public FutureSalts(ByteBuffer buffer) {
    req_msg_id = buffer.getLong();
    now = buffer.getInt();
    salts = TL.readVector(buffer, false, new tl.TFutureSalt[0]);
  }
  
  public FutureSalts(long req_msg_id, int now, tl.TFutureSalt[] salts) {
    this.req_msg_id = req_msg_id;
    this.now = now;
    this.salts = salts;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xae500895);
    }
    buffer.putLong(req_msg_id);
    buffer.putInt(now);
    TL.writeVector(buffer, salts, false, false);
  	return buffer;
  }
  
  public int length() {
    return 16 + TL.length(salts);
  }
  
  public String toString() {
    return "(FutureSalts req_msg_id:" + String.format("0x%016x", req_msg_id) + " now:" + now + " salts:" + TL.toString(salts) + ")";
  }
}
