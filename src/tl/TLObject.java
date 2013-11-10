package tl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import ru.denull.mtproto.Base64;

public abstract class TLObject {  
  abstract public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception;
  public ByteBuffer writeTo(ByteBuffer buffer) throws Exception {
    return writeTo(buffer, true);
  }
  
  public byte[] writeToByteArray() throws Exception {
    ByteBuffer buffer = ByteBuffer.allocateDirect(length(true));
    buffer.order(ByteOrder.LITTLE_ENDIAN);
    writeTo(buffer, true);
    buffer.rewind();
    byte[] bytes = new byte[buffer.capacity()];
    buffer.get(bytes);
    return bytes;
  }
  
  public String writeToBase64() throws Exception {
    return Base64.encodeToString(writeToByteArray(), Base64.NO_WRAP);
  }
  
  abstract public int length() throws Exception; 
  public int length(boolean boxed) throws Exception {
    return length() + (boxed ? 4 : 0);
  }
}
