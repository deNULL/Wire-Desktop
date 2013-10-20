package tl.auth;

import tl.TL;
import java.nio.ByteBuffer;

public class ExportedAuthorization extends tl.auth.TExportedAuthorization {
  
  public ExportedAuthorization(ByteBuffer buffer) {
    id = buffer.getInt();
    bytes = TL.readString(buffer);
  }
  
  public ExportedAuthorization(int id, byte[] bytes) {
    this.id = id;
    this.bytes = bytes;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xdf969c2d);
    }
    buffer.putInt(id);
    TL.writeString(buffer, bytes, false);
  	return buffer;
  }
  
  public int length() {
    return 4 + TL.length(bytes);
  }
  
  public String toString() {
    return "(ExportedAuthorization id:" + id + " bytes:" + TL.toString(bytes) + ")";
  }
}
