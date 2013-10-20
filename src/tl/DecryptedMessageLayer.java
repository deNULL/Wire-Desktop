package tl;

import java.nio.ByteBuffer;

public class DecryptedMessageLayer extends tl.TLObject {
  public int layer;
  public tl.TLObject message;
  
  public DecryptedMessageLayer(ByteBuffer buffer) {
    layer = buffer.getInt();
    message = (tl.TLObject) TL.read(buffer);
  }
  
  public DecryptedMessageLayer(int layer, tl.TLObject message) {
    this.layer = layer;
    this.message = message;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x99a438cf);
    }
    buffer.putInt(layer);
    message.writeTo(buffer, true);
  	return buffer;
  }
  
  public int length() {
    return 8 + message.length();
  }
  
  public String toString() {
    return "(DecryptedMessageLayer layer:" + layer + " message:" + message + ")";
  }
}
