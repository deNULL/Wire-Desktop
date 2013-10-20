package tl;

import java.nio.ByteBuffer;

public class UserProfilePhotoEmpty extends tl.TUserProfilePhoto {

  
  public UserProfilePhotoEmpty(ByteBuffer buffer) {

  }
  
  public UserProfilePhotoEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x4f11bae1);
    }

  	return buffer;
  }
  
  public int length() {
    return 0;
  }
  
  public String toString() {
    return "(UserProfilePhotoEmpty)";
  }
}
