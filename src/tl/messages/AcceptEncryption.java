package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class AcceptEncryption extends tl.TLFunction {
  public tl.TInputEncryptedChat peer;
  public byte[] g_b;
  public long key_fingerprint;
  
  public AcceptEncryption(ByteBuffer buffer) throws Exception {
    peer = (tl.TInputEncryptedChat) TL.read(buffer);
    g_b = TL.readString(buffer);
    key_fingerprint = buffer.getLong();
  }
  
  public AcceptEncryption(tl.TInputEncryptedChat peer, byte[] g_b, long key_fingerprint) {
    this.peer = peer;
    this.g_b = g_b;
    this.key_fingerprint = key_fingerprint;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x3dbc0415);
    }
    peer.writeTo(buffer, true);
    TL.writeString(buffer, g_b, false);
    buffer.putLong(key_fingerprint);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at AcceptEncryption: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 12 + peer.length() + TL.length(g_b);
  }
  
  public String toString() {
    return "(messages.acceptEncryption peer:" + peer + " g_b:" + TL.toString(g_b) + " key_fingerprint:" + String.format("0x%016x", key_fingerprint) + ")";
  }
}
