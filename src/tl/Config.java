package tl;

import java.nio.ByteBuffer;

public class Config extends tl.TConfig {

  
  public Config(ByteBuffer buffer) throws Exception {
    date = buffer.getInt();
    test_mode = (buffer.getInt() == 0x997275b5);
    this_dc = buffer.getInt();
    dc_options = TL.readVector(buffer, true, new tl.TDcOption[0]);
    chat_size_max = buffer.getInt();
  }
  
  public Config(int date, boolean test_mode, int this_dc, tl.TDcOption[] dc_options, int chat_size_max) {
    this.date = date;
    this.test_mode = test_mode;
    this.this_dc = this_dc;
    this.dc_options = dc_options;
    this.chat_size_max = chat_size_max;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x232d5905);
    }
    buffer.putInt(date);
    buffer.putInt(test_mode ? 0x997275b5 : 0xbc799737);
    buffer.putInt(this_dc);
    TL.writeVector(buffer, dc_options, true, true);
    buffer.putInt(chat_size_max);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at Config: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 24 + TL.length(dc_options);
  }
  
  public String toString() {
    return "(config date:" + date + " test_mode:" + (test_mode ? "true" : "false") + " this_dc:" + this_dc + " dc_options:" + TL.toString(dc_options) + " chat_size_max:" + chat_size_max + ")";
  }
}
