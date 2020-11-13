package com.bilgidoku.rom.dns;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.List;

public interface Dns {
	/**
	 * <p>
	 * Return a prioritized unmodifiable list of host handling mail for the
	 * domain.
	 * </p>
	 * 
	 * <p>
	 * First lookup MX hosts, then MX hosts of the CNAME address, and if no
	 * server is found return the IP of the hostname
	 * </p>
	 * 
	 * @param hostname
	 *            domain name to look up
	 * 
	 * @return a unmodifiable list of handling servers corresponding to this
	 *         mail domain name
	 * @throws TemporaryResolutionException
	 *             get thrown on temporary problems
	 */
	Collection<String> findMXRecords(String hostname) throws TemporaryResolutionException;

	/**
	 * Get a collection of DNS TXT Records
	 * 
	 * @param hostname
	 *            The hostname to check
	 * @return collection of strings representing TXT record values
	 */
	Collection<String> findTXTRecords(String hostname);
	
	String getTXTRecord(String hostname);

	/**
	 * Resolve the given hostname to an array of InetAddress based on the DNS
	 * Server. It should not take into account the hostnames defined in the
	 * local host table
	 * 
	 * @return An array of InetAddress
	 */
	InetAddress[] getAllByName(String host) throws UnknownHostException;

	/**
	 * Resolve the given hostname to an InetAddress based on the DNS Server. It
	 * should not take into account the hostnames defined in the local host
	 * table
	 * 
	 * @return The resolved InetAddress or null if not resolved
	 */
	InetAddress getByName(String host) throws UnknownHostException;

	/**
	 * Resolve the local hostname of the machine and returns it. It relies on
	 * the hostname defined in the local host table
	 * 
	 * @return The local InetAddress of the machine.
	 */
	InetAddress getLocalHost() throws UnknownHostException;

	/**
	 * Resolve the given InetAddress to an host name based on the DNS Server. It
	 * should not take into account the hostnames defined in the local host
	 * table
	 * 
	 * @return The resolved hostname String or null if not resolved
	 */
	String getHostName(InetAddress addr);

	/**
	 * Retrieve dns records for the given host
	 * 
	 * @param request
	 *            the dns request
	 * @return an array of Strings representing the records
	 * @throws TimeoutException
	 */
	public List<String> getRecords(DNSRequest request) throws TimeoutException;

	/**
	 * Try to get all domain names for the running host
	 * 
	 * @return names A List contains all domain names which could resolved
	 */
	public List<String> getLocalDomainNames();

	/**
	 * Set the timeout for DNS-Requests
	 * 
	 * @param timeOut
	 *            The timeout in seconds
	 */
	public void setTimeOut(int timeOut);

	/**
	 * @return the current record limit
	 */
	public int getRecordLimit();

	/**
	 * Sets a new limit for the number of records for MX and PTR lookups.
	 * 
	 * @param recordLimit
	 *            the new limit (0 => unlimited)
	 */
	public void setRecordLimit(int recordLimit);
	
	public String[] getAppServices(String name);
 
}
