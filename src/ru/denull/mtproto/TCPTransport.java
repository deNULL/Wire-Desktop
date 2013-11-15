package ru.denull.mtproto;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.zip.CRC32;

public class TCPTransport extends Transport {
  private static final String TAG = "TCPTransport";
	SocketChannel channel;
	private int packetIndex;
	
	public TCPTransport(String address, int port) throws IOException {
		channel = SocketChannel.open(new InetSocketAddress(address, port));
		packetIndex = 0;
	}

	synchronized public void send(ByteBuffer packet) throws Exception {
		ByteBuffer buffer = ByteBuffer.allocateDirect(packet.capacity() + 12);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
    
		buffer.putInt(packet.capacity() + 12);
		buffer.putInt(packetIndex);
    packetIndex++;
    
    packet.rewind();
    buffer.put(packet);
    
    CRC32 crc = new CRC32();
    for (int i = 0; i < buffer.capacity() - 4; i++) {
      crc.update(buffer.get(i));
    }
    buffer.putInt((int) crc.getValue());
    buffer.rewind();
    
    /*for (int i = 0; i < buffer.capacity(); i++) {
      System.out.print(String.format("%02x ", buffer.get(i)));
      if (i % 16 == 15) {
        System.out.println();
      }
    }
    System.out.println();*/
    
    channel.write(buffer);
	}
	
	public ByteBuffer receive() throws IOException {
		ByteBuffer head = ByteBuffer.allocate(8);
    head.order(ByteOrder.LITTLE_ENDIAN);
    
		if (channel.read(head) < 8) {
			return null;
		}
    
    int size = head.getInt(0);
    int index = head.getInt(4);
    
    if (size > 0x00ffffff) {
      Log.e(TAG, "Size of the packet is too big, something went wrong");
    }
    
    ByteBuffer buffer = ByteBuffer.allocate(size - 12);
    buffer.order(ByteOrder.LITTLE_ENDIAN);
    while (buffer.position() < size - 12) {
      channel.read(buffer);
    }
    buffer.rewind();
    
    ByteBuffer tail = ByteBuffer.allocate(4);
    tail.order(ByteOrder.LITTLE_ENDIAN);
    channel.read(tail);
    
    /*int _crc = tail.getInt(0);
    CRC32 crc = new CRC32();
    for (int i = 0; i < head.capacity(); i++) {
      crc.update(head.get(i));
    }
    for (int i = 0; i < buffer.capacity(); i++) {
      crc.update(buffer.get(i));
    }

    if (_crc != (int) crc.getValue()) {
      Log.w(TAG, "Invalid checksum, received " + _crc + ", expected " + crc.getValue());
    }*/

    return buffer;
	}

  public boolean isOpen() {
    return channel.isOpen();
  }

  public boolean isConnected() {
    return channel.isConnected();
  }

  @Override
  public void close() {
    try {
      channel.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
