package tl.photos;

import tl.TL;
import java.nio.ByteBuffer;

public class GetUserPhotos extends tl.TLFunction {
  public tl.TInputUser user_id;
  public int offset;
  public int max_id;
  public int limit;
  
  public GetUserPhotos(ByteBuffer buffer) {
    user_id = (tl.TInputUser) TL.read(buffer);
    offset = buffer.getInt();
    max_id = buffer.getInt();
    limit = buffer.getInt();
  }
  
  public GetUserPhotos(tl.TInputUser user_id, int offset, int max_id, int limit) {
    this.user_id = user_id;
    this.offset = offset;
    this.max_id = max_id;
    this.limit = limit;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xb7ee553c);
    }
    user_id.writeTo(buffer, false);
    buffer.putInt(offset);
    buffer.putInt(max_id);
    buffer.putInt(limit);
  	return buffer;
  }
  
  public int length() {
    return 16 + user_id.length();
  }
  
  public String toString() {
    return "(GetUserPhotos user_id:" + user_id + " offset:" + offset + " max_id:" + max_id + " limit:" + limit + ")";
  }
}
