package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class SetEncryptedTyping extends tl.TLFunction {
  public tl.TInputEncryptedChat peer;
  public boolean typing;
  
  public SetEncryptedTyping(ByteBuffer buffer) throws Exception {
    peer = (tl.TInputEncryptedChat) TL.read(buffer);
    typing = (buffer.getInt() == 0x997275b5);
  }
  
  public SetEncryptedTyping(tl.TInputEncryptedChat peer, boolean typing) {
    this.peer = peer;
    this.typing = typing;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x791451ed);
    }
    peer.writeTo(buffer, true);
    buffer.putInt(typing ? 0x997275b5 : 0xbc799737);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at SetEncryptedTyping: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8 + peer.length();
  }
  
  public String toString() {
    return "(messages.setEncryptedTyping peer:" + peer + " typing:" + (typing ? "true" : "false") + ")";
  }
}
