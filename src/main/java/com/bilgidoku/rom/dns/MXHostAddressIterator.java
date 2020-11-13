/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/
package com.bilgidoku.rom.dns;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 *
 */
public class MXHostAddressIterator implements Iterator<HostAddress> {

	private Iterator<HostAddress> addresses = null;
	private Iterator<String> hosts;
	private boolean useSingleIP;
	private int defaultPort;

	public MXHostAddressIterator(Iterator<String> hosts, boolean useSingleIP) {
		this(hosts, 25, useSingleIP);
	}

	public MXHostAddressIterator(Iterator<String> hosts, int defaultPort, boolean useSingleIP) {
		this.hosts = hosts;
		this.useSingleIP = useSingleIP;
		this.defaultPort = defaultPort;

		init();
	}

	/**
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {

		return addresses.hasNext();
	}


	private void init() {
		final List<HostAddress> hAddresses = new ArrayList<HostAddress>();
		while (hosts.hasNext()) {
			String nextHostname = (String) hosts.next();
			final String hostname;
			final String port;

			int idx = nextHostname.indexOf(':');
			if (idx > 0) {
				port = nextHostname.substring(idx + 1);
				hostname = nextHostname.substring(0, idx);
			} else {
				hostname = nextHostname;
				port = defaultPort + "";
			}

			InetAddress[] addrs = null;
			try {
				if (useSingleIP) {
					if(hostname.equals("localhost")){
						addrs=new InetAddress[] { InetAddress.getByName("localhost") };
					}else{
						addrs = new InetAddress[] { DnsGorevlisi.tek().getByName(hostname) };
					}
				} else {
					addrs = DnsGorevlisi.tek().getAllByName(hostname);
				}
				for (int i = 0; i < addrs.length; i++) {
					hAddresses.add(new HostAddress(hostname, "smtp://" + addrs[i].getHostAddress() + ":" + port));
				}
			} catch (UnknownHostException uhe) {
				uhe.printStackTrace();
				// this should never happen, since we just got
				// this host from mxHosts, which should have
				// already done this check.
			}

		}
		addresses = hAddresses.iterator();
	}

	/**
	 * @see java.util.Iterator#next()
	 */
	public HostAddress next() {
		return addresses.next();
	}

	/**
	 * Not supported.
	 */
	public void remove() {
		throw new UnsupportedOperationException("remove not supported by this iterator");
	}

}
