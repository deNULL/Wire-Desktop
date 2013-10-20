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
    if (boxed) {
      buffer.putInt(0xd9915325);
    }
    buffer.putDouble(crop_left);
    buffer.putDouble(crop_top);
    buffer.putDouble(crop_width);
  	return buffer;
  }
  
  public int length() {
    return 24;
  }
  
  public String toString() {
    return "(InputPhotoCrop crop_left:" + crop_left + " crop_top:" + crop_top + " crop_width:" + crop_width + ")";
  }
}
