package tl;

import java.nio.ByteBuffer;

public class UpdateShort extends tl.TUpdates {
  
  public UpdateShort(ByteBuffer buffer) {
    update = (tl.TUpdate) TL.read(buffer);
    date = buffer.getInt();
  }
  
  public UpdateShort(tl.TUpdate update, int date) {
    this.update = update;
    this.date = date;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x78d4dec1);
    }
    update.writeTo(buffer, false);
    buffer.putInt(date);
  	return buffer;
  }
  
  public int length() {
    return 8 + update.length();
  }
  
  public String toString() {
    return "(UpdateShort update:" + update + " date:" + date + ")";
  }
}
