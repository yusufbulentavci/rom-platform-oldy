package com.bilgidoku.rom.mail.maildo;

public class SpfInline {
//	   private boolean addHeader = false;
//	    private SPF spf;
//	    public final static String EXPLANATION_ATTRIBUTE = "org.apache.james.transport.mailets.spf.explanation";
//	    public final static String RESULT_ATTRIBUTE = "org.apache.james.transport.mailets.spf.result";
//
//	    /**
//	     * @see org.apache.mailet.base.GenericMailet#init()
//	     */
//	    public void init() {
//	        addHeader = Boolean.valueOf(getInitParameter("addHeader", "false"));
//	        spf = new SPF();
//	    }
//
//	    /**
//	     * @see org.apache.mailet.base.GenericMailet#service(org.apache.mailet.Mail)
//	     */
//	    public void service(Mail mail) throws MessagingException {
//	        String sender = null;
//	        MailAddress senderAddr = mail.getSender();
//	        String remoteAddr = mail.getRemoteAddr();
//	        String helo = mail.getRemoteHost();
//
//	        if (remoteAddr.equals("127.0.0.1") == false) {
//	            if (senderAddr != null) {
//	                sender = senderAddr.toString();
//	            } else {
//	                sender = "";
//	            }
//	            SPFResult result = spf.checkSPF(remoteAddr, sender, helo);
//	            mail.setAttribute(EXPLANATION_ATTRIBUTE, result.getExplanation());
//	            mail.setAttribute(RESULT_ATTRIBUTE, result.getResult());
//
//	            log("ip:" + remoteAddr + " from:" + sender + " helo:" + helo + " = " + result.getResult());
//	            if (addHeader) {
//	                try {
//	                    MimeMessage msg = mail.getMessage();
//	                    msg.addHeader(result.getHeaderName(), result.getHeaderText());
//	                    msg.saveChanges();
//	                } catch (MessagingException e) {
//	                    // Ignore not be able to add headers
//	                }
//	            }
//	        }
//	    }
	
	
}
