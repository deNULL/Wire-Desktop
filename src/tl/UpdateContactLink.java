package tl;

import java.nio.ByteBuffer;

public class UpdateContactLink extends tl.TUpdate {
  
  public UpdateContactLink(ByteBuffer buffer) {
    user_id = buffer.getInt();
    my_link = (tl.contacts.TMyLink) TL.read(buffer);
    foreign_link = (tl.contacts.TForeignLink) TL.read(buffer);
  }
  
  public UpdateContactLink(int user_id, tl.contacts.TMyLink my_link, tl.contacts.TForeignLink foreign_link) {
    this.user_id = user_id;
    this.my_link = my_link;
    this.foreign_link = foreign_link;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x51a48a9a);
    }
    buffer.putInt(user_id);
    my_link.writeTo(buffer, false);
    foreign_link.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 12 + my_link.length() + foreign_link.length();
  }
  
  public String toString() {
    return "(UpdateContactLink user_id:" + user_id + " my_link:" + my_link + " foreign_link:" + foreign_link + ")";
  }
}
