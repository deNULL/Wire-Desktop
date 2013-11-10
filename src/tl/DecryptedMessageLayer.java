package tl;

import java.nio.ByteBuffer;

public class DecryptedMessageLayer extends tl.TLObject {
  public int layer;
  public tl.TLObject message;
  
  public DecryptedMessageLayer(ByteBuffer buffer) throws Exception {
    layer = buffer.getInt();
    message = (tl.TLObject) TL.read(buffer);
  }
  
  public DecryptedMessageLayer(int layer, tl.TLObject message) {
    this.layer = layer;
    this.message = message;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x99a438cf);
    }
    buffer.putInt(layer);
    message.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at DecryptedMessageLayer: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8 + message.length();
  }
  
  public String toString() {
    return "(decryptedMessageLayer layer:" + layer + " message:" + message + ")";
  }
}
