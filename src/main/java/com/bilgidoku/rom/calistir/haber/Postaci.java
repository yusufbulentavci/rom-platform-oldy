package com.bilgidoku.rom.calistir.haber;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.bilgidoku.rom.haber.Msg;
import com.bilgidoku.rom.shared.err.KnownError;

public class Postaci {
	private final InetAddress address;
	final Msg msg;

	protected Postaci(String cmd) throws KnownError {

		this.msg = new Msg(cmd);
		try {
			address = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			throw new KnownError(e);
		}
	}

	public void send() throws KnownError {
		try {
			DatagramSocket socket = new DatagramSocket();
			byte[] buf = msg.str().getBytes();
			DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 9876);
			socket.send(packet);
		} catch (IOException e) {
			throw new KnownError(e);
		}

	}

}
