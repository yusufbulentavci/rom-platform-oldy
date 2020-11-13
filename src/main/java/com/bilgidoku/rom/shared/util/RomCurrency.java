package com.bilgidoku.rom.shared.util;

import java.util.HashMap;
import java.util.Map;

import com.bilgidoku.rom.shared.RunException;


public class RomCurrency {
	
	private static final String CURRENCYDATA="AED-2-\u062f\u002e\u0625\u002e\u200f-JOD-3-\u062f\u002e\u0623\u002e\u200f-SYP-2-\u0644\u002e\u0633\u002e\u200f-HRK-2-\u004b\u006e-EUR-2-\u20ac-PAB-2-\u0042-VEF-2-\u0042\u0073\u002e\u0046\u002e-TWD-2-\u004e\u0054\u0024-DKK-2-\u006b\u0072-USD-2-\u0024-VND-0-\u0111-SEK-2-\u006b\u0072-BOB-2-\u0042\u0024-SGD-2-\u0024-BHD-3-\u062f\u002e\u0628\u002e\u200f-SAR-2-\u0631\u002e\u0633\u002e\u200f-YER-2-\u0631\u002e\u064a\u002e\u200f-INR-2-\u0930\u0942-BAM-2-\u004b\u004d-UAH-2-\u0433\u0440\u043d\u002e-CHF-2-\u0053\u0046\u0072\u002e-ARS-2-\u0024-EGP-2-\u062c\u002e\u0645\u002e\u200f-JPY-0-\uffe5-SVC-2-\u0043-BRL-2-\u0052\u0024-ISK-0-\u006b\u0072\u002e-CZK-2-\u004b\u010d-PLN-2-\u007a\u0142-CSD-2-\u0043\u0053\u0044-MYR-2-\u0052\u004d-COP-2-\u0024-BGN-2-\u043b\u0432\u002e-PYG-0-\u0047-SDG-2-\u062c\u002e\u0633\u002e\u200f-RON-2-\u004c\u0045\u0049-PHP-2-\u0050\u0068\u0070-TND-3-\u062f\u002e\u062a\u002e\u200f-GTQ-2-\u0051-KRW-0-\uffe6-MXN-2-\u0024-RUB-2-\u0440\u0443\u0431\u002e-HNL-2-\u004c-HKD-2-\u0048\u004b\u0024-NOK-2-\u006b\u0072-HUF-2-\u0046\u0074-THB-2-\u0e3f-IQD-3-\u062f\u002e\u0639\u002e\u200f-CLP-0-\u0043\u0068\u0024-MAD-2-\u062f\u002e\u0645\u002e\u200f-TRY-2-\u0054\u004c-QAR-2-\u0631\u002e\u0642\u002e\u200f-OMR-3-\u0631\u002e\u0639\u002e\u200f-ALL-2-\u004c\u0065\u006b-DOP-2-\u0052\u0044\u0024-CUP-2-\u0043\u0055\u0024-NZD-2-\u0024-RSD-2-\u0434\u0438\u043d\u002e-UYU-2-\u004e\u0055\u0024-ILS-2-ILS-ZAR-2-\u0052-AUD-2-\u0024-CAD-2-\u0024-CRC-2-\u0043-KWD-3-\u062f\u002e\u0643\u002e\u200f-LYD-3-\u062f\u002e\u0644\u002e\u200f-DZD-2-\u062f\u002e\u062c\u002e\u200f-LTL-2-\u004c\u0074-CNY-2-\uffe5-LBP-2-\u0644\u002e\u0644\u002e\u200f-NIO-2-\u0024\u0043-MKD-2-\u0044\u0065\u006e-BYR-0-\u0420\u0443\u0431-PEN-2-\u0053\u002f\u002e-IDR-2-\u0052\u0070-GBP-2-\u00a3";
	private static Map<String,RomCurrency> map;

	private final String code;
	private final int decimal;
	private final String symbol;
	
	public RomCurrency(String code, int decimal, String symbol) {
		super();
		this.code = code;
		this.decimal = decimal;
		this.symbol = symbol;
	}

	public static String toText(long am, String currencyCode) throws RunException{
		if(map==null)
			init();

		RomCurrency rc=map.get(currencyCode);
		if(rc==null){
			throw new RunException("Unknown currency code:"+currencyCode);
		}
		
		int f=rc.getDecimal();
		if(f==0)
			return am+rc.getSymbol();
		int pow=(int) Math.pow(10, f);
		
		long kurus = (am%pow);
		
		if(currencyCode.equals("TRY")){
			return (int)(am/pow)+","+(am%pow)+rc.getSymbol();
		}
		
//		if(kurus==0)
//			return (int)(am/pow)+c.getSymbol(Locale.US);
		
		return (int)(am/pow)+"."+(am%pow)+rc.getSymbol();
	}

	public static String toAmount(long am, String currencyCode) {
		if(map==null)
			init();

		RomCurrency rc=map.get(currencyCode);
		if(rc==null){
			return null;
		}
		
		int f=rc.getDecimal();
		if(f==0)
			return am+rc.getSymbol();
		int pow=(int) Math.pow(10, f);
		
		long kurus = (am%pow);
		
		return (int)(am/pow)+"."+(am%pow);
	}

	private String getSymbol() {
		return symbol;
	}

	private int getDecimal() {
		return decimal;
	}

	private static void init() {
		String[] cc = CURRENCYDATA.split("-");
		map=new HashMap<String,RomCurrency>();
		for(int i=0; i<cc.length; i+=3){
			String c = cc[i];
			int d=Integer.parseInt(cc[i+1]);
			String s=cc[i+2];
			map.put(c, new RomCurrency(c, d, s));
		}
	}

	
//	
//	
//	public static void main(String[] args) {
////		Locale[] locales = Locale.getAvailableLocales();
////		Set<Currency> w = new HashSet<>();
////		for (Locale l : locales) {
////			if (null == l.getCountry() || l.getCountry().isEmpty())
////				continue;
////			Currency c = Currency.getInstance(l);
////			if (w.contains(c))
////				continue;
////			w.add(c);
////
////			System.out.print("-"+c.getCurrencyCode() + "-");
////			System.out.print(c.getDefaultFractionDigits() + "-");
////			System.out.print( print(c.getSymbol(l)));
//////			System.out.println(c.getSymbol(l));
////
////		}
//		//
//		//        for (Currency c: Currency.getAvailableCurrencies()) {
//		//        	System.out.println(c.getCurrencyCode() + "-" + c.getSymbol() +"-"+c.getDefaultFractionDigits()+"-"+c.toString() ); 
//		//		}
//	}
//
////	private static String print(String symbol) {
////		StringBuilder b = new StringBuilder();
////
////		for( char c : symbol.toCharArray() ){
//////		    if( c>999  ){
//////		        b.append( "\\u" ).append( Integer.toHexString(c) );
//////		    }else{
//////		        b.append( c );
//////		    }
////		    b.append(unicodeEscaped(c));
////		}
////
////		return b.toString();
////	}
////	
////	  private static String unicodeEscaped(char ch) {
////	      if (ch < 0x10) {
////	          return "\\u000" + Integer.toHexString(ch);
////	      } else if (ch < 0x100) {
////	          return "\\u00" + Integer.toHexString(ch);
////	      } else if (ch < 0x1000) {
////	          return "\\u0" + Integer.toHexString(ch);
////	      }
////	      return "\\u" + Integer.toHexString(ch);
////	  }

}
