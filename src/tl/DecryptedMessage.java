package tl;

import java.nio.ByteBuffer;

public class DecryptedMessage extends tl.TDecryptedMessage {
  
  public DecryptedMessage(ByteBuffer buffer) {
    random_id = buffer.getLong();
    random_bytes = TL.readString(buffer);
    message = new String(TL.readString(buffer));
    media = (tl.TDecryptedMessageMedia) TL.read(buffer);
  }
  
  public DecryptedMessage(long random_id, byte[] random_bytes, String message, tl.TDecryptedMessageMedia media) {
    this.random_id = random_id;
    this.random_bytes = random_bytes;
    this.message = message;
    this.media = media;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x1f814f1f);
    }
    buffer.putLong(random_id);
    TL.writeString(buffer, random_bytes, false);
    TL.writeString(buffer, message.getBytes(), false);
    media.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 12 + TL.length(random_bytes) + TL.length(message.getBytes()) + media.length();
  }
  
  public String toString() {
    return "(DecryptedMessage random_id:" + String.format("0x%016x", random_id) + " random_bytes:" + TL.toString(random_bytes) + " message:" + "message" + " media:" + media + ")";
  }
}
