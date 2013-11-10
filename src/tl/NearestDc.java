package tl;

import java.nio.ByteBuffer;

public class NearestDc extends tl.TNearestDc {

  
  public NearestDc(ByteBuffer buffer) throws Exception {
    country = new String(TL.readString(buffer), "UTF8");
    this_dc = buffer.getInt();
    nearest_dc = buffer.getInt();
  }
  
  public NearestDc(String country, int this_dc, int nearest_dc) {
    this.country = country;
    this.this_dc = this_dc;
    this.nearest_dc = nearest_dc;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x8e1a1775);
    }
    TL.writeString(buffer, country.getBytes("UTF8"), false);
    buffer.putInt(this_dc);
    buffer.putInt(nearest_dc);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at NearestDc: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8 + TL.length(country.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(nearestDc country:" + "country" + " this_dc:" + this_dc + " nearest_dc:" + nearest_dc + ")";
  }
}
