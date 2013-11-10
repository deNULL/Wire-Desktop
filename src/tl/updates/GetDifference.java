package tl.updates;

import tl.TL;
import java.nio.ByteBuffer;

public class GetDifference extends tl.TLFunction {
  public int pts;
  public int date;
  public int qts;
  
  public GetDifference(ByteBuffer buffer) throws Exception {
    pts = buffer.getInt();
    date = buffer.getInt();
    qts = buffer.getInt();
  }
  
  public GetDifference(int pts, int date, int qts) {
    this.pts = pts;
    this.date = date;
    this.qts = qts;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xa041495);
    }
    buffer.putInt(pts);
    buffer.putInt(date);
    buffer.putInt(qts);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GetDifference: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 12;
  }
  
  public String toString() {
    return "(updates.getDifference pts:" + pts + " date:" + date + " qts:" + qts + ")";
  }
}
