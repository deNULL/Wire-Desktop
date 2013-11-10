package tl;

import java.nio.ByteBuffer;

public class GetFutureSalts extends tl.TFutureSalts {

  
  public GetFutureSalts(ByteBuffer buffer) throws Exception {
    num = buffer.getInt();
  }
  
  public GetFutureSalts(int num) {
    this.num = num;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xb921bd04);
    }
    buffer.putInt(num);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GetFutureSalts: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4;
  }
  
  public String toString() {
    return "(get_future_salts num:" + num + ")";
  }
}
