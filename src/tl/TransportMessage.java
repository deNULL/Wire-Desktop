package tl;

import java.nio.ByteBuffer;

public class TransportMessage extends tl.TTransportMessage {

  
  public TransportMessage(ByteBuffer buffer) throws Exception {
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
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x0);
    }
    buffer.putLong(msg_id);
    buffer.putInt(seqno);
    buffer.putInt(bytes);
    body.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at TransportMessage: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 20 + body.length();
  }
  
  public String toString() {
    return "(transport_message msg_id:" + String.format("0x%016x", msg_id) + " seqno:" + seqno + " bytes:" + bytes + " body:" + body + ")";
  }
}
