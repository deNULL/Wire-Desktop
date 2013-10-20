package tl.updates;

import tl.TL;
import java.nio.ByteBuffer;

public class GetDifference extends tl.TLFunction {
  public int pts;
  public int date;
  public int qts;
  
  public GetDifference(ByteBuffer buffer) {
    pts = buffer.getInt();
    date = buffer.getInt();
    qts = buffer.getInt();
  }
  
  public GetDifference(int pts, int date, int qts) {
    this.pts = pts;
    this.date = date;
    this.qts = qts;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xa041495);
    }
    buffer.putInt(pts);
    buffer.putInt(date);
    buffer.putInt(qts);
  	return buffer;
  }
  
  public int length() {
    return 12;
  }
  
  public String toString() {
    return "(GetDifference pts:" + pts + " date:" + date + " qts:" + qts + ")";
  }
}
