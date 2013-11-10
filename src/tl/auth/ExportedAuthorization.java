package tl.auth;

import tl.TL;
import java.nio.ByteBuffer;

public class ExportedAuthorization extends tl.auth.TExportedAuthorization {

  
  public ExportedAuthorization(ByteBuffer buffer) throws Exception {
    id = buffer.getInt();
    bytes = TL.readString(buffer);
  }
  
  public ExportedAuthorization(int id, byte[] bytes) {
    this.id = id;
    this.bytes = bytes;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xdf969c2d);
    }
    buffer.putInt(id);
    TL.writeString(buffer, bytes, false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ExportedAuthorization: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4 + TL.length(bytes);
  }
  
  public String toString() {
    return "(auth.exportedAuthorization id:" + id + " bytes:" + TL.toString(bytes) + ")";
  }
}
