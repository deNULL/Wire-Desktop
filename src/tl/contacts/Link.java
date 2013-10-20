package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class Link extends tl.contacts.TLink {
  
  public Link(ByteBuffer buffer) {
    my_link = (tl.contacts.TMyLink) TL.read(buffer);
    foreign_link = (tl.contacts.TForeignLink) TL.read(buffer);
    user = (tl.TUser) TL.read(buffer);
  }
  
  public Link(tl.contacts.TMyLink my_link, tl.contacts.TForeignLink foreign_link, tl.TUser user) {
    this.my_link = my_link;
    this.foreign_link = foreign_link;
    this.user = user;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xeccea3f5);
    }
    my_link.writeTo(buffer, false);
    foreign_link.writeTo(buffer, false);
    user.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 12 + my_link.length() + foreign_link.length() + user.length();
  }
  
  public String toString() {
    return "(Link my_link:" + my_link + " foreign_link:" + foreign_link + " user:" + user + ")";
  }
}
