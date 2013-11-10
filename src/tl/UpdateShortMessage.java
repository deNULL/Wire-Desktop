package tl;

import java.nio.ByteBuffer;

public class UpdateShortMessage extends tl.TUpdates {

  
  public UpdateShortMessage(ByteBuffer buffer) throws Exception {
    id = buffer.getInt();
    from_id = buffer.getInt();
    message = new String(TL.readString(buffer), "UTF8");
    pts = buffer.getInt();
    date = buffer.getInt();
    seq = buffer.getInt();
  }
  
  public UpdateShortMessage(int id, int from_id, String message, int pts, int date, int seq) {
    this.id = id;
    this.from_id = from_id;
    this.message = message;
    this.pts = pts;
    this.date = date;
    this.seq = seq;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xd3f45784);
    }
    buffer.putInt(id);
    buffer.putInt(from_id);
    TL.writeString(buffer, message.getBytes("UTF8"), false);
    buffer.putInt(pts);
    buffer.putInt(date);
    buffer.putInt(seq);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateShortMessage: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 20 + TL.length(message.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(updateShortMessage id:" + id + " from_id:" + from_id + " message:" + "message" + " pts:" + pts + " date:" + date + " seq:" + seq + ")";
  }
}
