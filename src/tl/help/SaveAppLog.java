package tl.help;

import tl.TL;
import java.nio.ByteBuffer;

public class SaveAppLog extends tl.TLFunction {
  public tl.TInputAppEvent[] events;
  
  public SaveAppLog(ByteBuffer buffer) throws Exception {
    events = TL.readVector(buffer, true, new tl.TInputAppEvent[0]);
  }
  
  public SaveAppLog(tl.TInputAppEvent[] events) {
    this.events = events;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x6f02f748);
    }
    TL.writeVector(buffer, events, true, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at SaveAppLog: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8 + TL.length(events);
  }
  
  public String toString() {
    return "(help.saveAppLog events:" + TL.toString(events) + ")";
  }
}
