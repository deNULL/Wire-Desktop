package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class SetEncryptedTyping extends tl.TLFunction {
  public tl.TInputEncryptedChat peer;
  public boolean typing;
  
  public SetEncryptedTyping(ByteBuffer buffer) {
    peer = (tl.TInputEncryptedChat) TL.read(buffer);
    typing = (buffer.getInt() == 0x997275b5);
  }
  
  public SetEncryptedTyping(tl.TInputEncryptedChat peer, boolean typing) {
    this.peer = peer;
    this.typing = typing;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x791451ed);
    }
    peer.writeTo(buffer, false);
    buffer.putInt(typing ? 0x997275b5 : 0xbc799737);
  	return buffer;
  }
  
  public int length() {
    return 8 + peer.length();
  }
  
  public String toString() {
    return "(SetEncryptedTyping peer:" + peer + " typing:" + (typing ? "true" : "false") + ")";
  }
}
