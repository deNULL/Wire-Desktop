package tl;

import java.nio.ByteBuffer;

public class Error extends tl.TError {

  
  public Error(ByteBuffer buffer) {
    code = buffer.getInt();
    try {  text = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
  }
  
  public Error(int code, String text) {
    this.code = code;
    this.text = text;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xc4b9f9bb);
    }
    buffer.putInt(code);
    try { TL.writeString(buffer, text.getBytes("UTF8"), false); } catch (Exception e) { };
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at Error: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4 + TL.length(text.getBytes());
  }
  
  public String toString() {
    return "(error code:" + code + " text:" + "text" + ")";
  }
}
