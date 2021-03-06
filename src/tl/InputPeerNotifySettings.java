package tl;

import java.nio.ByteBuffer;

public class InputPeerNotifySettings extends tl.TInputPeerNotifySettings {

  
  public InputPeerNotifySettings(ByteBuffer buffer) throws Exception {
    mute_until = buffer.getInt();
    sound = new String(TL.readString(buffer), "UTF8");
    show_previews = (buffer.getInt() == 0x997275b5);
    events_mask = buffer.getInt();
  }
  
  public InputPeerNotifySettings(int mute_until, String sound, boolean show_previews, int events_mask) {
    this.mute_until = mute_until;
    this.sound = sound;
    this.show_previews = show_previews;
    this.events_mask = events_mask;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x46a2ce98);
    }
    buffer.putInt(mute_until);
    TL.writeString(buffer, sound.getBytes("UTF8"), false);
    buffer.putInt(show_previews ? 0x997275b5 : 0xbc799737);
    buffer.putInt(events_mask);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputPeerNotifySettings: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 12 + TL.length(sound.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(inputPeerNotifySettings mute_until:" + mute_until + " sound:" + "sound" + " show_previews:" + (show_previews ? "true" : "false") + " events_mask:" + events_mask + ")";
  }
}
