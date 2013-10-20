package tl.updates;

import tl.TL;
import java.nio.ByteBuffer;

public class DifferenceEmpty extends tl.updates.TDifference {
  
  public DifferenceEmpty(ByteBuffer buffer) {
    date = buffer.getInt();
    seq = buffer.getInt();
  }
  
  public DifferenceEmpty(int date, int seq) {
    this.date = date;
    this.seq = seq;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x5d75a138);
    }
    buffer.putInt(date);
    buffer.putInt(seq);
  	return buffer;
  }
  
  public int length() {
    return 8;
  }
  
  public String toString() {
    return "(DifferenceEmpty date:" + date + " seq:" + seq + ")";
  }
}
