package ru.denull.wire.stub.tl/**namespace**/;
/**import**/
import ru.denull.wire.stub.tl.TLFunction;
import java.nio.ByteBuffer;


public class /**constructor**/ extends /**type**/ {
/**fields**/
  
  public /**constructor**/(ByteBuffer buffer) {
        try {
/**parse_body**/
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
  }
  
  public /**constructor**/(/**params**/) {
/**init_body**/
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
/**write_body**/
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at /**constructor**/: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return /**length**/;
  }
  
  public String toString() {
    return /**string**/;
  }
}
