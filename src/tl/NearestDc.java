package tl;

import java.nio.ByteBuffer;

public class NearestDc extends tl.TNearestDc {

  
  public NearestDc(ByteBuffer buffer) {
    try {  country = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    this_dc = buffer.getInt();
    nearest_dc = buffer.getInt();
  }
  
  public NearestDc(String country, int this_dc, int nearest_dc) {
    this.country = country;
    this.this_dc = this_dc;
    this.nearest_dc = nearest_dc;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x8e1a1775);
    }
    try { TL.writeString(buffer, country.getBytes("UTF8"), false); } catch (Exception e) { };
    buffer.putInt(this_dc);
    buffer.putInt(nearest_dc);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at NearestDc: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8 + TL.length(country.getBytes());
  }
  
  public String toString() {
    return "(nearestDc country:" + "country" + " this_dc:" + this_dc + " nearest_dc:" + nearest_dc + ")";
  }
}
