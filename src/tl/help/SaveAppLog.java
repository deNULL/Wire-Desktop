package tl.help;

import tl.TL;
import java.nio.ByteBuffer;

public class SaveAppLog extends tl.TLFunction {
  public tl.TInputAppEvent[] events;
  
  public SaveAppLog(ByteBuffer buffer) {
    events = TL.readVector(buffer, true, new tl.TInputAppEvent[0]);
  }
  
  public SaveAppLog(tl.TInputAppEvent[] events) {
    this.events = events;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x6f02f748);
    }
    TL.writeVector(buffer, events, true, false);
  	return buffer;
  }
  
  public int length() {
    return 8 + TL.length(events);
  }
  
  public String toString() {
    return "(SaveAppLog events:" + TL.toString(events) + ")";
  }
}
