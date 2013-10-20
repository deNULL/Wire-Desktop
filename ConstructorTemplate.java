package tl/**namespace**/;
/**import**/
import java.nio.ByteBuffer;

public class /**constructor**/ extends /**type**/ {
/**fields**/
  
  public /**constructor**/(ByteBuffer buffer) {
/**parse_body**/
  }
  
  public /**constructor**/(/**params**/) {
/**init_body**/
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
/**write_body**/
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at /**constructor**/: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return /**length**/;
  }
  
  public String toString() {
    return /**string**/;
  }
}
