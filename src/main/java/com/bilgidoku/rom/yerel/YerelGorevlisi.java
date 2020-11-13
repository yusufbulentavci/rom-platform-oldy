package com.bilgidoku.rom.yerel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bilgidoku.rom.gunluk.GunlukGorevlisi;
import com.bilgidoku.rom.gunluk.RotatingStream;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.file.FromResource;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.run.KosuGorevlisi;

/**
 * Converts IP addresses to country codes using the database available from
 * http://software77.net/geo-ip/
 * <p>
 * This class loads the database into memory, where it is stored in an efficient
 * format for instant lookups. It is capable of automatically updating the
 * database on a given time schedule.
 * <p>
 * A disk folder is required to store the latest database version so that it can
 * be loaded initially without requesting it over HTTP. This is important
 * because the list maintainers restrict the number of requests permitted.
 * <p>
 * Currently the database has about 100,000 entries. The in-memory
 * representation in this class uses three arrays to store two longs and a
 * pointer to a (reused) string for each entry, so typical memory consumption is
 * about 2MB. It would be possible to nearly halve this by using ints instead of
 * longs to store the numbers, but I haven't bothered as 2MB seems a reasonable
 * overhead for most servers.
 * <p>
 * For obvious reasons, the memory requirement temporarily doubles while loading
 * a new database version.
 * <p>
 * At present, this class only supports the IP4 database.
 */
public class YerelGorevlisi extends GorevliDir {
	public static final int NO=7;
	
	public static YerelGorevlisi tek(){
		if(tek==null) {
			synchronized (YerelGorevlisi.class) {
				if(tek==null) {
					tek=new YerelGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}
	
	static YerelGorevlisi tek;
	private YerelGorevlisi() {
		super("Yerel", NO);
	}
	
	
	private static final MC mc = new MC(YerelGorevlisi.class);
	private final static Pattern CSV_LINE = Pattern
			.compile("^\"([0-9]{1,10})\",\"([0-9]{1,10})\",\"[^\"]*\",\"[0-9]*\",\"([A-Z]+)\"");
	private final static int COPY_BUFFER = 65536;
	private final static int INITIAL_ENTRIES = 65536;

	private long[] entryFrom, entryTo;
	private String[] entryCode;
	private Throwable lastError;
	private Set<String> langs = new HashSet<String>();
	private JSONObject countryToLang;


	
	@Override
	protected void kur() throws KnownError {
		JSONObject conf;
		try {
			conf = FromResource.loadJsonObject("/rom-locales");

			String[] ls = conf.getStringArray("langs");
			for (String s : ls) {
				langs.add(s);
			}
			countryToLang = conf.getJSONObject("countryLangs");
		} catch (IOException e1) {
			throw confError("/rom-locales", e1);
		} catch (JSONException e1) {
			throw confError(e1);
		}

		try {
			loadFile();
		} catch (IOException e) {
			throw confError("IptoCountry couldnt be loaded", e);
		}

	}

	@Override
	protected void bitir(boolean dostca) {
		langs=null;
		countryToLang=null;
		entryFrom = null;
		entryTo = null;
		entryCode = null;
	}

	final private InetAddress getInetAddress(String addrString) throws UnknownHostException {
		InetAddress clientAddress = null;
		clientAddress = InetAddress.getByName(addrString);
		return clientAddress;
	}

	private static final Astate _getCountryCode = mc.c("getCountryCode");

	final public String getCountryCode(String address) throws KnownError {
		destur();
		_getCountryCode.more();
		if (address.startsWith("/127.0.0.1") || address.startsWith("/192.168."))
			return "TR";
		try {
			return getCountryCode(this.getInetAddress(address));
		} catch (UnknownHostException e) {
			_getCountryCode.failed(e, address);
			throw new KnownError(e);
		}

	}

	final public String getCountryCode(InetSocketAddress address) throws KnownError {
		destur();
		_getCountryCode.more();
		String hs = address.getHostString();
		if (hs.startsWith("127.0.0.1") || hs.startsWith("192.168."))
			return "TR";
		try {
			return getCountryCode(this.getInetAddress(hs));
		} catch (UnknownHostException e) {
			_getCountryCode.failed(e, address);
			throw new KnownError(e);
		}

	}

	/**
	 * Gets the country code for an IP address.
	 * <p>
	 * Based on the list documentation, this is the ISO 3166 2-letter code of
	 * the organisation to which the allocation or assignment was made, with the
	 * following differences or unusual cases:
	 * <ul>
	 * <li>AP - non-specific Asia-Pacific location</li>
	 * <li>CS - Serbia and Montenegro</li>
	 * <li>YU - Serbia and Montenegro (Formally Yugoslavia) (Being phased out)
	 * </li>
	 * <li>EU - non-specific European Union location</li>
	 * <li>FX - France, Metropolitan</li>
	 * <li>PS - Palestinian Territory, Occupied</li>
	 * <li>UK - United Kingdom (standard says GB)</li>
	 * <li>ZZ - IETF RESERVED address space.</li>
	 * <li>.. - Unmatched address (specific to this system, not in list)</li>
	 * </ul>
	 * 
	 * @param address
	 *            Internet address
	 * @return Country code
	 * @throws IllegalArgumentException
	 *             If the address is an IPv6 address that can't be expressed in
	 *             4 bytes, or something else
	 */
	final private String getCountryCode(InetAddress address) {
		long addressLong = getAddressAsLong(get4ByteAddress(address));
		return getCountryCode(addressLong, entryFrom, entryTo, entryCode);
	}

	/**
	 * Get a country code given the arrays which hold the details. (This is a
	 * static method to make unit testing easier.)
	 * <p>
	 * This is a separate method with default access so it can be used in unit
	 * testing.
	 * 
	 * @param addressLong
	 *            Address as long
	 * @param entryFrom
	 *            Array of 'from' values for each entry
	 * @param entryTo
	 *            Array of 'to' values for each entry
	 * @param entryCode
	 *            Array of country codes for each entry'
	 * @return Corresponding country code, or .. if not known
	 */
	private static String getCountryCode(long addressLong, long[] entryFrom, long[] entryTo, String[] entryCode) {
		// Binary search for the highest entryFrom that's less than or equal to
		// the specified address.
		int min = 0, max = entryFrom.length == 0 ? 0 : entryFrom.length - 1;
		while (min != max) {
			int half = (min + max) / 2;
			if (half == min) {
				// This special case handles the situation where e.g. min=10,
				// max=11;
				// there half=10 and we could get an endless loop.
				if (entryFrom[max] <= addressLong) {
					min = max;
				} else {
					max = min;
				}
			} else {
				// Standard case; check whether the halfway position is bigger
				// or
				// smaller
				if (entryFrom[half] <= addressLong) {
					min = half;
				} else {
					max = half - 1;
				}
			}
		}

		if (min >= entryFrom.length || entryTo[min] < addressLong || entryFrom[min] > addressLong) {
//			return "..";
			return "TR";
		} else {
			return entryCode[min];
		}
	}

	/**
	 * Given an internet address in network byte order, converts it into an
	 * unsigned long.
	 * <p>
	 * This is a separate method with default access so it can be used in unit
	 * testing.
	 * 
	 * @param bytes
	 *            Bytes
	 * @return Long value
	 * @throws IllegalArgumentException
	 *             If there aren't 4 bytes of address
	 */
	private static long getAddressAsLong(byte[] bytes) throws IllegalArgumentException {
		if (bytes.length != 4) {
			throw new IllegalArgumentException("Input must be 4 bytes");
		}
		int i0 = (int) bytes[0] & 0xff, i1 = (int) bytes[1] & 0xff, i2 = (int) bytes[2] & 0xff,
				i3 = (int) bytes[3] & 0xff;
		return (((long) i0) << 24) | (((long) i1) << 16) | (((long) i2) << 8) | ((long) i3);
	}

	/**
	 * Converts an {@link InetAddress} into a 4-byte address.
	 * <p>
	 * This is a separate method with default access so it can be used in unit
	 * testing.
	 * 
	 * @param address
	 *            Address
	 * @return 4 bytes in network byte order
	 * @throws IllegalArgumentException
	 *             If the address is an IPv6 address that can't be expressed in
	 *             4 bytes, or something else
	 */
	private static byte[] get4ByteAddress(InetAddress address) throws IllegalArgumentException {
		byte[] actual = address.getAddress();
		if (actual.length == 4) {
			return actual;
		}

		if (address instanceof Inet6Address) {
			if (((Inet6Address) address).isIPv4CompatibleAddress()) {
				// For compatible addresses, use last 4 bytes
				byte[] bytes = new byte[4];
				System.arraycopy(actual, actual.length - 4, bytes, 0, 4);
				return bytes;
			} else {
				throw new IllegalArgumentException(
						"IPv6 addresses not supported " + "unless IP4 compatible): " + address.getHostAddress());
			}
		}

		throw new IllegalArgumentException("Unknown address type: " + address.getHostAddress());
	}

	/**
	 * Loads the existing file from disk into memory.
	 * <p>
	 * This is a separate method with default access so it can be used in unit
	 * testing.
	 * 
	 * @param file
	 *            File to load
	 * @throws IOException
	 *             Any problem loading file
	 */
	private static final Astate _loadFile = mc.c("loadFile");

	private void loadFile() throws IOException {
		_loadFile.more();
		BufferedReader reader = null;
		try {
			// String-sharing buffer
			HashMap<String, String> countryCodes = new HashMap<String, String>();

			// Prepare new index buffers
			long[] newEntryFrom = new long[INITIAL_ENTRIES];
			long[] newEntryTo = new long[INITIAL_ENTRIES];
			String[] newEntryCode = new String[INITIAL_ENTRIES];
			int entries = 0;

			InputStream is = this.getClass().getClassLoader().getResourceAsStream("IpToCountry.csv");

			reader = new BufferedReader(new InputStreamReader(is, "US-ASCII"));
			while (true) {
				// Read next line
				String line = reader.readLine();
				if (line == null) {
					// No more lines
					break;
				}
				// Skip comments (lines beginning # or whitespace) and empty
				// lines
				if (line.isEmpty() || line.startsWith("#") || Character.isWhitespace(line.charAt(0))) {
					continue;
				}

				// Match line
				Matcher m = CSV_LINE.matcher(line);
				if (!m.find()) {
					_loadFile.fail(line);
					continue;
				}

				// Line matches!

				// Expand entry arrays if required
				if (entries == newEntryFrom.length) {
					long[] temp = new long[entries * 2];
					System.arraycopy(newEntryFrom, 0, temp, 0, entries);
					newEntryFrom = temp;
					temp = new long[entries * 2];
					System.arraycopy(newEntryTo, 0, temp, 0, entries);
					newEntryTo = temp;
					String[] tempStr = new String[entries * 2];
					System.arraycopy(newEntryCode, 0, tempStr, 0, entries);
					newEntryCode = tempStr;
				}

				// Share country strings, as they are likely to be repeated many
				// times
				String code = m.group(3);
				if (countryCodes.containsKey(code)) {
					code = countryCodes.get(code);
				} else {
					countryCodes.put(code, code);
				}

				// Store new entry
				newEntryFrom[entries] = Long.parseLong(m.group(1));
				newEntryTo[entries] = Long.parseLong(m.group(2));
				newEntryCode[entries] = code;
				entries++;
			}

			// Reallocate arrays to precise length
			long[] temp = new long[entries];
			System.arraycopy(newEntryFrom, 0, temp, 0, entries);
			newEntryFrom = temp;
			temp = new long[entries];
			System.arraycopy(newEntryTo, 0, temp, 0, entries);
			newEntryTo = temp;
			String[] tempStr = new String[entries];
			System.arraycopy(newEntryCode, 0, tempStr, 0, entries);
			newEntryCode = tempStr;

			entryFrom = newEntryFrom;
			entryTo = newEntryTo;
			entryCode = newEntryCode;
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	public boolean isLang(String lang) {
		return langs.contains(lang);
	}

	private static final Astate langOfCountry=mc.c("lang-of-country");
	public String langOfCountry(String country) {
		langOfCountry.more();
		String ret = countryToLang.optString(country);
		if (ret == null || ret.trim().length()==0){
			langOfCountry.failed("Empty or null", country, ret);
			return "en";
		}
		return ret;
	}

	public String[] resolveLocale(String locale) {
		String[] lret = locale.split("_");

		if (lret == null || lret.length != 2)
			return new String[2];

		return lret;
	}

	@Override
	public void selfDescribe(com.bilgidoku.rom.shared.json.JSONObject jo) {

	}

}