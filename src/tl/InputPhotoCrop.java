package tl;

import java.nio.ByteBuffer;

public class InputPhotoCrop extends tl.TInputPhotoCrop {

  
  public InputPhotoCrop(ByteBuffer buffer) {
    crop_left = buffer.getDouble();
    crop_top = buffer.getDouble();
    crop_width = buffer.getDouble();
  }
  
  public InputPhotoCrop(double crop_left, double crop_top, double crop_width) {
    this.crop_left = crop_left;
    this.crop_top = crop_top;
    this.crop_width = crop_width;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xd9915325);
    }
    buffer.putDouble(crop_left);
    buffer.putDouble(crop_top);
    buffer.putDouble(crop_width);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputPhotoCrop: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 24;
  }
  
  public String toString() {
    return "(inputPhotoCrop crop_left:" + crop_left + " crop_top:" + crop_top + " crop_width:" + crop_width + ")";
  }
}
