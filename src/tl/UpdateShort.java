package tl;

import java.nio.ByteBuffer;

public class UpdateShort extends tl.TUpdates {

  
  public UpdateShort(ByteBuffer buffer) throws Exception {
    update = (tl.TUpdate) TL.read(buffer);
    date = buffer.getInt();
  }
  
  public UpdateShort(tl.TUpdate update, int date) {
    this.update = update;
    this.date = date;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x78d4dec1);
    }
    update.writeTo(buffer, true);
    buffer.putInt(date);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateShort: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8 + update.length();
  }
  
  public String toString() {
    return "(updateShort update:" + update + " date:" + date + ")";
  }
}
