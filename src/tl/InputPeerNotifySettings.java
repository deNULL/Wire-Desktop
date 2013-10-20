package tl;

import java.nio.ByteBuffer;

public class InputPeerNotifySettings extends tl.TInputPeerNotifySettings {
  
  public InputPeerNotifySettings(ByteBuffer buffer) {
    mute_until = buffer.getInt();
    sound = new String(TL.readString(buffer));
    show_previews = (buffer.getInt() == 0x997275b5);
    events_mask = buffer.getInt();
  }
  
  public InputPeerNotifySettings(int mute_until, String sound, boolean show_previews, int events_mask) {
    this.mute_until = mute_until;
    this.sound = sound;
    this.show_previews = show_previews;
    this.events_mask = events_mask;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x46a2ce98);
    }
    buffer.putInt(mute_until);
    TL.writeString(buffer, sound.getBytes(), false);
    buffer.putInt(show_previews ? 0x997275b5 : 0xbc799737);
    buffer.putInt(events_mask);
  	return buffer;
  }
  
  public int length() {
    return 12 + TL.length(sound.getBytes());
  }
  
  public String toString() {
    return "(InputPeerNotifySettings mute_until:" + mute_until + " sound:" + "sound" + " show_previews:" + (show_previews ? "true" : "false") + " events_mask:" + events_mask + ")";
  }
}
