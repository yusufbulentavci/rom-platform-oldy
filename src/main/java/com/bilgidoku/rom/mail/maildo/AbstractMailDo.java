package com.bilgidoku.rom.mail.maildo;

import com.bilgidoku.rom.mail.MailDo;
import com.bilgidoku.rom.mail.MailProcessContext;

public abstract class AbstractMailDo implements MailDo{
	
	protected final MailProcessContext context;
	protected final MailDoConfig config;
	
	AbstractMailDo(MailProcessContext context, MailDoConfig config){
		this.context=context;
		this.config=config;
	}
	

	 /**
     * Utility method for obtaining a string representation of an array of Objects.
     */
    protected final String arrayToString(Object[] array) {
        if (array == null) {
            return "null";
        }
        StringBuffer sb = new StringBuffer(1024);
        sb.append("[");
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(array[i]);
        }
        sb.append("]");
        return sb.toString();
    }

}
