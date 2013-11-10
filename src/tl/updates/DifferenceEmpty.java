package tl.updates;

import tl.TL;
import java.nio.ByteBuffer;

public class DifferenceEmpty extends tl.updates.TDifference {

  
  public DifferenceEmpty(ByteBuffer buffer) throws Exception {
    date = buffer.getInt();
    seq = buffer.getInt();
  }
  
  public DifferenceEmpty(int date, int seq) {
    this.date = date;
    this.seq = seq;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x5d75a138);
    }
    buffer.putInt(date);
    buffer.putInt(seq);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at DifferenceEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8;
  }
  
  public String toString() {
    return "(updates.differenceEmpty date:" + date + " seq:" + seq + ")";
  }
}
