package tl;

import java.nio.ByteBuffer;

public class TransportMessage extends tl.TTransportMessage {
  
  public TransportMessage(ByteBuffer buffer) {
    msg_id = buffer.getLong();
    seqno = buffer.getInt();
    bytes = buffer.getInt();
    body = (tl.TLObject) TL.read(buffer);
  }
  
  public TransportMessage(long msg_id, int seqno, int bytes, tl.TLObject body) {
    this.msg_id = msg_id;
    this.seqno = seqno;
    this.bytes = bytes;
    this.body = body;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x0);
    }
    buffer.putLong(msg_id);
    buffer.putInt(seqno);
    buffer.putInt(bytes);
    body.writeTo(buffer, true);
  	return buffer;
  }
  
  public int length() {
    return 20 + body.length();
  }
  
  public String toString() {
    return "(TransportMessage msg_id:" + String.format("0x%016x", msg_id) + " seqno:" + seqno + " bytes:" + bytes + " body:" + body + ")";
  }
}
