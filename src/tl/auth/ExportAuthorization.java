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
    if (boxed) {
      buffer.putInt(0xe5bfffcd);
    }
    buffer.putInt(dc_id);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(ExportAuthorization dc_id:" + dc_id + ")";
  }
}
