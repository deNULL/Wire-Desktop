package tl.auth;

import tl.TL;
import java.nio.ByteBuffer;

public class ExportAuthorization extends tl.TLFunction {
  public int dc_id;
  
  public ExportAuthorization(ByteBuffer buffer) {
    dc_id = buffer.getInt();
  }
  
  public ExportAuthorization(int dc_id) {
    this.dc_id = dc_id;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xe5bfffcd);
    }
    buffer.putInt(dc_id);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ExportAuthorization: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(auth.exportAuthorization dc_id:" + dc_id + ")";
  }
}
