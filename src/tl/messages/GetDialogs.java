package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class GetDialogs extends tl.TLFunction {
  public int offset;
  public int max_id;
  public int limit;
  
  public GetDialogs(ByteBuffer buffer) throws Exception {
    offset = buffer.getInt();
    max_id = buffer.getInt();
    limit = buffer.getInt();
  }
  
  public GetDialogs(int offset, int max_id, int limit) {
    this.offset = offset;
    this.max_id = max_id;
    this.limit = limit;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xeccf1df6);
    }
    buffer.putInt(offset);
    buffer.putInt(max_id);
    buffer.putInt(limit);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GetDialogs: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 12;
  }
  
  public String toString() {
    return "(messages.getDialogs offset:" + offset + " max_id:" + max_id + " limit:" + limit + ")";
  }
}
