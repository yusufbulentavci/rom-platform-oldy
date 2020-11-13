package com.bilgidoku.rom.haber;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.err.KnownError;

public class UdpServer implements Runnable{

	private DatagramSocket serverSocket;

	public UdpServer() throws KnownError {
		try {
			serverSocket = new DatagramSocket(9876);
		} catch (SocketException e) {
			throw new KnownError("TalkService udpserver couldnt be binded to port 9876", e);
		}
	}

	public void run() {

		byte[] receiveData = new byte[1024 * 20];
		while (HaberGorevlisi.tek().mayi()) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				serverSocket.receive(receivePacket);

				String sentence = new String(receivePacket.getData());
//				System.out.println("RECEIVED: " + sentence);
//				InetAddress IPAddress = receivePacket.getAddress();
//				int port = receivePacket.getPort();

				JSONObject msg = new JSONObject(sentence);
				HaberGorevlisi.tek().newMsg(msg, null);
			} catch (IOException e) {
				Sistem.printStackTrace(e);
			} catch (JSONException e) {
				Sistem.printStackTrace(e);
			}
		}
	}

}
