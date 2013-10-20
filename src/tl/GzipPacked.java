package tl;

import java.nio.ByteBuffer;

public class GzipPacked extends tl.TLObject {
  public byte[] packed_data;
  
  public GzipPacked(ByteBuffer buffer) {
    packed_data = TL.readString(buffer);
  }
  
  public GzipPacked(byte[] packed_data) {
    this.packed_data = packed_data;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x3072cfa1);
    }
    TL.writeString(buffer, packed_data, false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GzipPacked: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return TL.length(packed_data);
  }
  
  public String toString() {
    return "(gzip_packed packed_data:" + TL.toString(packed_data) + ")";
  }
}
