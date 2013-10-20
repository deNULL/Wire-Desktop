package tl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import ru.denull.mtproto.Base64;

public abstract class TLObject {  
  abstract public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed);
  public ByteBuffer writeTo(ByteBuffer buffer) {
    return writeTo(buffer, true);
  }
  
  public byte[] writeToByteArray() {
    ByteBuffer buffer = ByteBuffer.allocateDirect(length(true));
    buffer.order(ByteOrder.LITTLE_ENDIAN);
    writeTo(buffer, true);
    buffer.rewind();
    byte[] bytes = new byte[buffer.capacity()];
    buffer.get(bytes);
    return bytes;
  }
  
  public String writeToBase64() {
    return Base64.encodeToString(writeToByteArray(), Base64.NO_WRAP);
  }
  
  abstract public int length(); 
  public int length(boolean boxed) {
    return length() + (boxed ? 4 : 0);
  }
}
