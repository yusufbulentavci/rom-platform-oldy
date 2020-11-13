package com.bilgidoku.rom.dns;

/** 
 * A specialized subclass of javam.mail.URLName, which provides location
 * information for servers.
 * 
 * @since Mailet API v2.2.0a16-unstable
 */
public class HostAddress extends com.bilgidoku.rom.epostatemel.javam.mail.URLName
{
    private String hostname;

    public HostAddress(String hostname, String url)
    {
        super(url);
        this.hostname = hostname;
    }

    public String getHostName()
    {
        return hostname;
    }

/*
    public static void main(String[] args) throws Exception
    {
        HostAddress url;
        try
        {
            url = new HostAddress("mail.devtech.com", "smtp://" + "66.112.202.2" + ":25");
            syso("Hostname: " + url.getHostName());
            syso("The protocol is: " + url.getProtocol());
            syso("The host is: " + url.getHost());
            syso("The port is: " + url.getPort());
            syso("The user is: " + url.getUsername());
            syso("The password is: " + url.getPassword());
            syso("The file is: " + url.getFile());
            syso("The ref is: " + url.getRef());
        }
        catch (Exception e)
        {
            com.bilgidoku.rom.runtime.log.Sistem.errln(e);
        };
    }
*/
}
