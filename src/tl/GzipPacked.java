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
    if (boxed) {
      buffer.putInt(0x3072cfa1);
    }
    TL.writeString(buffer, packed_data, false);
  	return buffer;
  }
  
  public int length() {
    return TL.length(packed_data);
  }
  
  public String toString() {
    return "(GzipPacked packed_data:" + TL.toString(packed_data) + ")";
  }
}
