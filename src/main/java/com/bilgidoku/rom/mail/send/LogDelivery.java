package com.bilgidoku.rom.mail.send;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Properties;

import com.bilgidoku.rom.epostatemel.javam.mail.MessagingException;
import com.bilgidoku.rom.epostatemel.javam.mail.SendFailedException;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.InternetAddress;

import com.bilgidoku.rom.epostatemel.mail.smtp.SMTPAddressFailedException;
import com.bilgidoku.rom.epostatemel.mail.smtp.SMTPAddressSucceededException;
import com.bilgidoku.rom.epostatemel.mail.smtp.SMTPSendFailedException;
import com.bilgidoku.rom.dns.HostAddress;
import com.bilgidoku.rom.mail.Email;
import com.bilgidoku.rom.min.Sistem;

public class LogDelivery {
	private Email mail;
	private boolean isDebug;

	public LogDelivery(Email mail, boolean isDebug) {
		this.mail = mail;
		this.isDebug=isDebug;
	}

	void log(String message) {
		Sistem.outln(message);
	}

	void log(String message, Throwable t) {
		Sistem.printStackTrace(t,message);
	}
	
	void logMessaginException(Email mail, MessagingException me) {
		StringBuilder exceptionBuffer = new StringBuilder(256).append("Exception delivering message (")
				.append(mail.getName()).append(") - ").append(me.getMessage());
		log(exceptionBuffer.toString());
	}

	public void logAttempt(HostAddress outgoingMailServer, Object object, InternetAddress[] addr) {
		StringBuilder logMessageBuffer = new StringBuilder(256).append("Attempting delivery of ")
				.append(mail.getName()).append(" to host ").append(outgoingMailServer.getHostName()).append(" at ")
				.append(outgoingMailServer.getHost()).append(" from ").append(object).append(" for addresses ")
				.append(Arrays.asList(addr));
		log(logMessageBuffer.toString());
	}

	public void logCouldntCloseConnection(HostAddress outgoingMailServer, Exception e) {
		log("Warning: could not close the SMTP transport after sending mail (" + mail.getName() + ") to "
				+ outgoingMailServer.getHostName() + " at " + outgoingMailServer.getHost() + " for "
				+ mail.getRecipients() + "; probably the server has already closed the "
				+ "connection. Message is considered to be delivered. Exception: " + e.getMessage());

	}

	public void logSentSuccess(HostAddress outgoingMailServer, Properties props) {
		StringBuilder logMessageBuffer = new StringBuilder(256).append("Mail (").append(mail.getName())
				.append(") sent successfully to ").append(outgoingMailServer.getHostName()).append(" at ")
				.append(outgoingMailServer.getHost()).append(" from ").append(props.get("mail.smtp.from"))
				.append(" for ").append(mail.getRecipients());
		log(logMessageBuffer.toString());
	}

	/*
	 * private method to log the extended SendFailedException introduced in
	 * JavaMail 1.3.2.
	 */
	public void logSendFailedException(SendFailedException sfe) {

		SMTPSendFailedException smtp = getSmtp(sfe);
		if (smtp != null) {
			Integer returnCode = smtp.getReturnCode();
			String command = smtp.getCommand();
			log("SMTP SEND FAILED:");
			log(sfe.toString());
			log("  Command: " + command);
			log("  RetCode: " + returnCode);
			log("  Response: " + sfe.getMessage());
		} else {
			log("Send failed: " + sfe.toString());
		}

		MessagingException me = sfe;

		Exception ne;
		while ((ne = me.getNextException()) != null && ne instanceof MessagingException) {
			me = (MessagingException) ne;

			InternetAddress address = getAddress(me);
			if (address != null) {
				String action = me.getClass().getName().endsWith(".SMTPAddressFailedException") ? "FAILED"
						: "SUCCEEDED";
				String command = getCommand(me);
				Integer returnCode = getReturnCode(me);
				log("ADDRESS " + action + ":");
				log(me.toString());
				log("  Address: " + address);
				log("  Command: " + command);
				log("  RetCode: " + returnCode);
			}
		}
	}

	public static SMTPSendFailedException getSmtp(SendFailedException sfe) {
		if (sfe instanceof SMTPSendFailedException)
			return (SMTPSendFailedException) sfe;
		return null;
	}

	public static InternetAddress getAddress(MessagingException sfe) {
		if (sfe instanceof SMTPAddressFailedException) {
			return ((SMTPAddressFailedException) sfe).getAddress();
		} else if (sfe instanceof SMTPAddressSucceededException) {
			return ((SMTPAddressSucceededException) sfe).getAddress();
		}
		return null;
	}

	public static String getCommand(MessagingException sfe) {
		if (sfe instanceof SMTPAddressFailedException) {
			return ((SMTPAddressFailedException) sfe).getCommand();
		} else if (sfe instanceof SMTPAddressSucceededException) {
			return ((SMTPAddressSucceededException) sfe).getCommand();
		}
		return null;
	}

	public static Integer getReturnCode(MessagingException sfe) {
		if (sfe instanceof SMTPAddressFailedException) {
			return ((SMTPAddressFailedException) sfe).getReturnCode();
		} else if (sfe instanceof SMTPAddressSucceededException) {
			return ((SMTPAddressSucceededException) sfe).getReturnCode();
		}
		return null;
	}

	public void logToBounce(int retries) {
		StringBuilder logBuffer = new StringBuilder(128).append("Bouncing message ").append(mail.getName())
				.append(" after ").append(retries).append(" retries");
		log(logBuffer.toString());
	}

	public void logRetry(int retries) {
		StringBuilder logBuffer = new StringBuilder(128).append("Storing message ").append(mail.getName())
				.append(" into outgoing after ").append(retries).append(" retries");
		log(logBuffer.toString());
	}

	void logFailMessage(Email mail, Exception ex, boolean permanent) {
		StringWriter sout = new StringWriter();
		PrintWriter out = new PrintWriter(sout, true);
		if (permanent) {
			out.print("Permanent");
		} else {
			out.print("Temporary");
		}

		String exceptionLog = exceptionToLogString(ex);

		StringBuilder logBuffer = new StringBuilder(64).append(" exception delivering mail (").append(mail.getName());

		if (exceptionLog != null) {
			logBuffer.append(". ");
			logBuffer.append(exceptionLog);
		}

		logBuffer.append(": ");
		out.print(logBuffer.toString());
		if (isDebug)
			ex.printStackTrace(out);
		log(sout.toString());
	}

	/**
	 * Try to return a usefull logString created of the Exception which was
	 * given. Return null if nothing usefull could be done
	 * 
	 * @param e
	 *            The MessagingException to use
	 * @return logString
	 */
	String exceptionToLogString(Exception e) {
		if (e.getClass().getName().endsWith(".SMTPSendFailedException")) {
			return "RemoteHost said: " + e.getMessage();
		} else if (e instanceof SendFailedException) {
			SendFailedException exception = (SendFailedException) e;

			// No error
			if (exception.getInvalidAddresses().length == 0 && exception.getValidUnsentAddresses().length == 0)
				return null;

			Exception ex;
			StringBuilder sb = new StringBuilder();
			boolean smtpExFound = false;
			sb.append("RemoteHost said:");

			if (e instanceof MessagingException)
				while ((ex = ((MessagingException) e).getNextException()) != null && ex instanceof MessagingException) {
					e = ex;
					InternetAddress ia = getAddress((MessagingException) e);
					sb.append(" ( " + ia + " - [" + ex.getMessage().replaceAll("\\n", "") + "] )");
					smtpExFound = true;
				}
			if (!smtpExFound) {
				boolean invalidAddr = false;
				sb.append(" ( ");

				if (exception.getInvalidAddresses().length > 0) {
					sb.append(Arrays.toString(exception.getInvalidAddresses()));
					invalidAddr = true;
				}
				if (exception.getValidUnsentAddresses().length > 0) {
					if (invalidAddr == true)
						sb.append(" ");
					sb.append(Arrays.toString(exception.getValidUnsentAddresses()));
				}
				sb.append(" - [");
				sb.append(exception.getMessage().replaceAll("\\n", ""));
				sb.append("] )");
			}
			return sb.toString();
		}
		return null;
	}

}
