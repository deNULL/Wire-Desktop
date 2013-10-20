package tl.account;

import tl.TL;
import java.nio.ByteBuffer;

public class UpdateProfile extends tl.TLFunction {
  public String first_name;
  public String last_name;
  
  public UpdateProfile(ByteBuffer buffer) {
    first_name = new String(TL.readString(buffer));
    last_name = new String(TL.readString(buffer));
  }
  
  public UpdateProfile(String first_name, String last_name) {
    this.first_name = first_name;
    this.last_name = last_name;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xf0888d68);
    }
    TL.writeString(buffer, first_name.getBytes(), false);
    TL.writeString(buffer, last_name.getBytes(), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateProfile: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return TL.length(first_name.getBytes()) + TL.length(last_name.getBytes());
  }
  
  public String toString() {
    return "(account.updateProfile first_name:" + "first_name" + " last_name:" + "last_name" + ")";
  }
}
