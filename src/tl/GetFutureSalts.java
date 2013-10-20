package tl;

import java.nio.ByteBuffer;

public class GetFutureSalts extends tl.TFutureSalts {
  
  public GetFutureSalts(ByteBuffer buffer) {
    num = buffer.getInt();
  }
  
  public GetFutureSalts(int num) {
    this.num = num;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xb921bd04);
    }
    buffer.putInt(num);
  	return buffer;
  }
  
  public int length() {
    return 4;
  }
  
  public String toString() {
    return "(GetFutureSalts num:" + num + ")";
  }
}
