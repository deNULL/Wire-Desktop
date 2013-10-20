package tl;

import java.nio.ByteBuffer;

public class UpdatesTooLong extends tl.TUpdates {

  
  public UpdatesTooLong(ByteBuffer buffer) {

  }
  
  public UpdatesTooLong() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xe317af7e);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(UpdatesTooLong)";
  }
}
