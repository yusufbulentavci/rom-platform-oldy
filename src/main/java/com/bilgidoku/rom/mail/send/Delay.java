package com.bilgidoku.rom.mail.send;

import com.bilgidoku.rom.ilk.util.Conv;

public class Delay {
	private static final long DEFAULT_DELAY_TIME = Conv.hourMsec(6) ;

	// String "5m","10m","45m","2h","3h", "6h";
	/** List of Delay Times. Controls frequency of retry attempts. */
	private final long[] delayTimes = { Conv.minMsec(5), Conv.minMsec(10), Conv.minMsec(45), Conv.hourMsec(2),
			Conv.hourMsec(3), Conv.hourMsec(6) };

	/**
	 * This method returns, given a retry-count, the next delay time to use.
	 * 
	 * @param retry_count
	 *            the current retry_count.
	 * @return the next delay time to use, given the retry count
	 **/
	public long getNextDelay(int retry_count) {
		if (retry_count > delayTimes.length) {
			return DEFAULT_DELAY_TIME;
		}
		return delayTimes[retry_count - 1];
	}


}
