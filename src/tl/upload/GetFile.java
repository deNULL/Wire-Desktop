package tl.upload;

import tl.TL;
import java.nio.ByteBuffer;

public class GetFile extends tl.TLFunction {
  public tl.TInputFileLocation location;
  public int offset;
  public int limit;
  
  public GetFile(ByteBuffer buffer) {
    location = (tl.TInputFileLocation) TL.read(buffer);
    offset = buffer.getInt();
    limit = buffer.getInt();
  }
  
  public GetFile(tl.TInputFileLocation location, int offset, int limit) {
    this.location = location;
    this.offset = offset;
    this.limit = limit;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xe3a6cfb5);
    }
    location.writeTo(buffer, false);
    buffer.putInt(offset);
    buffer.putInt(limit);
  	return buffer;
  }
  
  public int length() {
    return 12 + location.length();
  }
  
  public String toString() {
    return "(GetFile location:" + location + " offset:" + offset + " limit:" + limit + ")";
  }
}
