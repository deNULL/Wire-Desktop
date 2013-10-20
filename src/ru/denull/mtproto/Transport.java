package ru.denull.mtproto;

import java.nio.ByteBuffer;


public abstract class Transport {
	public abstract void send(ByteBuffer packet) throws Exception;
	public abstract ByteBuffer receive() throws Exception;
	
	public abstract boolean isOpen();
	public abstract boolean isConnected();
	
	public abstract void close();
}
