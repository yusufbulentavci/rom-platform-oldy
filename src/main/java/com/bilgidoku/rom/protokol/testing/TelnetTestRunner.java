package com.bilgidoku.rom.protokol.testing;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.apache.commons.net.telnet.TelnetClient;

import com.bilgidoku.rom.ilk.file.FromResource;
import com.bilgidoku.rom.ilk.util.Genel;


public class TelnetTestRunner {
	private final String[] dlg;
	private TelnetClient telnet = new TelnetClient();
	private InputStream in;
	private PrintStream out;
	private String prompt = "%";

	private int port;

	public TelnetTestRunner(int port, String res) throws IOException {
		this.port = port;
		String all = FromResource.loadString(res);
		this.dlg = all.split("---\n");
	}

	public void run() {
		try {
			telnet.connect(Genel.getHostName(), port);
			in = telnet.getInputStream();
			out = new PrintStream(telnet.getOutputStream());

			boolean sending = false;
			for (int i = 0; i < dlg.length; i++) {
				String text = dlg[i].trim();
				if (text.length() == 0) {
					throw new RuntimeException("empty at index:" + i);

				}
				if (sending)
					write(text);
				else{
					String read=readWritten().trim();
					if(!read.matches(text)){
						throw new RuntimeException("Pattern not matched;\nExpected:"+text+"\nReceived:"+read);
					}
				}
				sending = !sending;
			}
			System.out.println("DONE");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (telnet != null)
				try {
					telnet.disconnect();
				} catch (IOException e) {
				}
		}
	}

	public void su(String password) {
		try {
			write("su");
			readUntil("Password: ");
			write(password);
			prompt = "#";
			readUntil(prompt + " ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String readUntil(String pattern) {
		try {
			char lastChar = pattern.charAt(pattern.length() - 1);
			StringBuffer sb = new StringBuffer();
			boolean found = false;
			char ch = (char) in.read();
			while (true) {
				System.out.print(ch);
				sb.append(ch);
				if (ch == lastChar) {
					if (sb.toString().endsWith(pattern)) {
						return sb.toString();
					}
				}
				ch = (char) in.read();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String readWritten() {
		try {
			
			for(int i=0; i<20; i++){
				if(in.available()>0){
					byte[] all=new byte[2000];
					in.read(all);
					String str = new String(all);
					System.out.println("<<<"+str);
					return str;
				}
				Thread.sleep(1000);
			}
		}catch(IOException | InterruptedException e){
			e.printStackTrace();
		}
			
		throw new RuntimeException("Couldnt be read");
	}

	public void write(String value) {
		try {
			if(value.endsWith("<END>")){
				value=value.replaceFirst("<END>", "\r\n.\r\n");
				out.print(value);
				out.println(".");
				out.println("");
				System.out.println(">>>END"+value);
			}else{
				out.println(value);
				System.out.println(">>>"+value);
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String sendCommand(String command) {
		try {
			write(command);
			return readUntil(prompt + " ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void disconnect() {
		try {
			telnet.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		//	    	try {
		//	    		AutomatedTelnetClient telnet = new AutomatedTelnetClient(
		//	    				"myserver", "userId", "Password");
		//	    		System.out.println("Got Connection...");
		//	    		telnet.sendCommand("ps -ef ");
		//	    		System.out.println("run command");
		//	    		telnet.sendCommand("ls ");
		//	    		System.out.println("run command 2");
		//	    		telnet.disconnect();
		//	    		System.out.println("DONE");
		//	    	} catch (Exception e) {
		//	    		e.printStackTrace();
		//	    	}
	}

}
