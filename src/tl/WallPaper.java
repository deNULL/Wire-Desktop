package tl;

import java.nio.ByteBuffer;

public class WallPaper extends tl.TWallPaper {
  
  public WallPaper(ByteBuffer buffer) {
    id = buffer.getInt();
    title = new String(TL.readString(buffer));
    sizes = TL.readVector(buffer, true, new tl.TPhotoSize[0]);
    color = buffer.getInt();
  }
  
  public WallPaper(int id, String title, tl.TPhotoSize[] sizes, int color) {
    this.id = id;
    this.title = title;
    this.sizes = sizes;
    this.color = color;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xccb03657);
    }
    buffer.putInt(id);
    TL.writeString(buffer, title.getBytes(), false);
    TL.writeVector(buffer, sizes, true, false);
    buffer.putInt(color);
  	return buffer;
  }
  
  public int length() {
    return 16 + TL.length(title.getBytes()) + TL.length(sizes);
  }
  
  public String toString() {
    return "(WallPaper id:" + id + " title:" + "title" + " sizes:" + TL.toString(sizes) + " color:" + color + ")";
  }
}
