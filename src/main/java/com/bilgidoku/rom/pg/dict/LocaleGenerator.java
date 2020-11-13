package com.bilgidoku.rom.pg.dict;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

public class LocaleGenerator {

	Locale list[] = NumberFormat.getAvailableLocales();

	Hashtable<String, String> countryToLang = new Hashtable<String, String>();
	Hashtable<String, String> countryToLang2 = new Hashtable<String, String>();
	Hashtable<String, String> countryNames = new Hashtable<String, String>();
	Hashtable<String, String> langNames = new Hashtable<String, String>();
	
	List<String> orderedLangs=new ArrayList<String>();
	
	static final String[] langorder={"en","tr","es","zh","hi","fr","de","ru","bn","ja","ar","vi","ko"};

	static public void main(String[] args) {
		LocaleGenerator lg=new LocaleGenerator();
		lg.initNames();
		lg.initctl();
		lg.langArray();
		
//		lg.printLangsSqlEnum();
	}
	
	
	private void printLangsSqlEnum() {
		
//		syso("create type rom.langs as enum( ");
//		boolean virgul=false;
//		for (String lang: orderedLangs) {
//			if(virgul){
//				System.out.print(",");
//			}
//			virgul=true;
//			System.out.print("'"+lang+"'");
//			
//		}
//		syso("");
//		System.out.print(");");
		
	}


	private void langArray() {
		for (String lang : langorder) {
			orderedLangs.add(lang);
		}
		
		for (String lang: langNames.keySet()) {
			if(orderedLangs.contains(lang)){
				continue;
			}
			orderedLangs.add(lang);
		}
	}
	void initNames(){
		for(int i=0;i<langs.length;i+=2){
			String ln=langs[i];
			String lc=langs[i+1];
			langNames.put(lc, ln);
		}
		for(int i=0;i<codesAndCountries.length;i+=2){
			String lc=codesAndCountries[i];
			String ln=codesAndCountries[i+1];
			countryNames.put(lc, ln);
		}
	}
	
	void initctl(){
		// // String[] list = Locale.getISOCountries();
		// // Locale list[] =Locale.getAvailableLocales();
		for (Locale locale : list) {
			// syso(locale.getDisplayName()+":"+locale.getCountry()+":"+locale.getLanguage());

			if (locale.getCountry().equals("")) {
				continue;
			}
			String lang = countryToLang.get(locale.getCountry());
			if (lang != null) {
				countryToLang2.put(locale.getCountry(), locale.getLanguage());
			} else {
				countryToLang.put(locale.getCountry(), locale.getLanguage());
			}
		}
	}
	
	void printCountryNames(){

//		for (String c : countryNames.keySet()) {
//			String l = countryNames.get(c);
//			System.out
//					.println("countryNames.put(\"" + c + "\",\"" + l + "\");");
//		}
//		syso("");
//		for (String c : langNames.keySet()) {
//			String l = langNames.get(c);
//			syso("langNames.put(\"" + c + "\",\"" + l + "\");");
//		}
//		syso("");
//		for (String c : countryNames.keySet()) {
//			String l = countryToLang.get(c);
//			if (l == null) {
//				l = "en";
//				syso("//auto");
//			}
//			syso("countryToLang.put(\"" + c + "\",\"" + l
//					+ "\");");
//			String l2 = countryToLang2.get(c);
//			if (l2 != null && !l.equals(l2)) {
//				syso("//countryToLang.put(\"" + c + "\",\"" + l2
//						+ "\");");
//			}
//		}		
	}
	

	static final String[] codesAndCountries = { "AC", "Ascension Island", "AD",
			"Andorra", "AE", "United Arab Emirates", "AF", "Afghanistan", "AG",
			"Antigua and Barbuda", "AI", "Anguilla", "AL", "Albania", "AM",
			"Armenia", "AN", "Netherlands Antilles", "AO", "Angola", "AQ",
			"Antarctica", "AR", "Argentina", "AS", "American Samoa", "AT",
			"Austria", "AU", "Australia", "AW", "Aruba", "AX", "Aland Islands",
			"AZ", "Azerbaijan", "BA", "Bosnia and Herzegovina", "BB",
			"Barbados", "BD", "Bangladesh", "BE", "Belgium", "BF",
			"Burkina Faso", "BG", "Bulgaria", "BH", "Bahrain", "BI", "Burundi",
			"BJ", "Benin", "BM", "Bermuda", "BN", "Brunei Darussalam", "BO",
			"Bolivia", "BR", "Brazil", "BS", "Bahamas", "BT", "Bhutan", "BV",
			"Bouvet Island", "BW", "Botswana", "BY", "Belarus", "BZ", "Belize",
			"CA", "Canada", "CC", "Cocos (Keeling) Islands", "CD",
			"Congo, Democratic Republic", "CF", "Central African Republic",
			"CG", "Congo", "CH", "Switzerland", "CI",
			"Cote D'Ivoire (Ivory Coast)", "CK", "Cook Islands", "CL", "Chile",
			"CM", "Cameroon", "CN", "China", "CO", "Colombia", "CR",
			"Costa Rica", "CS", "Czechoslovakia (former)", "CU", "Cuba", "CV",
			"Cape Verde", "CX", "Christmas Island", "CY", "Cyprus", "CZ",
			"Czech Republic", "DE", "Germany", "DJ", "Djibouti", "DK",
			"Denmark", "DM", "Dominica", "DO", "Dominican Republic", "DZ",
			"Algeria", "EC", "Ecuador", "EE", "Estonia", "EG", "Egypt", "EH",
			"Western Sahara", "ER", "Eritrea", "ES", "Spain", "ET", "Ethiopia",
			"EU", "European Union", "FI", "Finland", "FJ", "Fiji", "FK",
			"Falkland Islands (Malvinas)", "FM", "Micronesia", "FO",
			"Faroe Islands", "FR", "France", "FX", "France, Metropolitan",
			"GA", "Gabon", "GB", "Great Britain (UK)", "GD", "Grenada", "GE",
			"Georgia", "GF", "French Guiana", "GG", "Guernsey", "GH", "Ghana",
			"GI", "Gibraltar", "GL", "Greenland", "GM", "Gambia", "GN",
			"Guinea", "GP", "Guadeloupe", "GQ", "Equatorial Guinea", "GR",
			"Greece", "GS", "S. Georgia and S. Sandwich Isls.", "GT",
			"Guatemala", "GU", "Guam", "GW", "Guinea-Bissau", "GY", "Guyana",
			"HK", "Hong Kong", "HM", "Heard and McDonald Islands", "HN",
			"Honduras", "HR", "Croatia (Hrvatska)", "HT", "Haiti", "HU",
			"Hungary", "ID", "Indonesia", "IE", "Ireland", "IL", "Israel",
			"IM", "Isle of Man", "IN", "India", "IO",
			"British Indian Ocean Territory", "IQ", "Iraq", "IR", "Iran", "IS",
			"Iceland", "IT", "Italy", "JE", "Jersey", "JM", "Jamaica", "JO",
			"Jordan", "JP", "Japan", "KE", "Kenya", "KG", "Kyrgyzstan", "KH",
			"Cambodia", "KI", "Kiribati", "KM", "Comoros", "KN",
			"Saint Kitts and Nevis", "KP", "Korea (North)", "KR",
			"Korea (South)", "KW", "Kuwait", "KY", "Cayman Islands", "KZ",
			"Kazakhstan", "LA", "Laos", "LB", "Lebanon", "LC", "Saint Lucia",
			"LI", "Liechtenstein", "LK", "Sri Lanka", "LR", "Liberia", "LS",
			"Lesotho", "LT", "Lithuania", "LU", "Luxembourg", "LV", "Latvia",
			"LY", "Libya", "MA", "Morocco", "MC", "Monaco", "MD", "Moldova",
			"ME", "Montenegro", "MF", "Saint Martin", "MG", "Madagascar", "MH",
			"Marshall Islands", "MK", "F.Y.R.O.M. (Macedonia)", "ML", "Mali",
			"MM", "Myanmar", "MN", "Mongolia", "MO", "Macau", "MP",
			"Northern Mariana Islands", "MQ", "Martinique", "MR", "Mauritania",
			"MS", "Montserrat", "MT", "Malta", "MU", "Mauritius", "MV",
			"Maldives", "MW", "Malawi", "MX", "Mexico", "MY", "Malaysia", "MZ",
			"Mozambique", "NA", "Namibia", "NC", "New Caledonia", "NE",
			"Niger", "NF", "Norfolk Island", "NG", "Nigeria", "NI",
			"Nicaragua", "NL", "Netherlands", "NO", "Norway", "NP", "Nepal",
			"NR", "Nauru", "NT", "Neutral Zone", "NU", "Niue", "NZ",
			"New Zealand (Aotearoa)", "OM", "Oman", "PA", "Panama", "PE",
			"Peru", "PF", "French Polynesia", "PG", "Papua New Guinea", "PH",
			"Philippines", "PK", "Pakistan", "PL", "Poland", "PM",
			"St. Pierre and Miquelon", "PN", "Pitcairn", "PR", "Puerto Rico",
			"PS", "Palestinian Territory, Occupied", "PT", "Portugal", "PW",
			"Palau", "PY", "Paraguay", "QA", "Qatar", "RE", "Reunion", "RS",
			"Serbia", "RO", "Romania", "RU", "Russian Federation", "RW",
			"Rwanda", "SA", "Saudi Arabia", "SB", "Solomon Islands", "SC",
			"Seychelles", "SD", "Sudan", "SE", "Sweden", "SG", "Singapore",
			"SH", "St. Helena", "SI", "Slovenia", "SJ",
			"Svalbard & Jan Mayen Islands", "SK", "Slovak Republic", "SL",
			"Sierra Leone", "SM", "San Marino", "SN", "Senegal", "SO",
			"Somalia", "SR", "Suriname", "ST", "Sao Tome and Principe", "SU",
			"USSR (former)", "SV", "El Salvador", "SY", "Syria", "SZ",
			"Swaziland", "TC", "Turks and Caicos Islands", "TD", "Chad", "TF",
			"French Southern Territories", "TG", "Togo", "TH", "Thailand",
			"TJ", "Tajikistan", "TK", "Tokelau", "TM", "Turkmenistan", "TN",
			"Tunisia", "TO", "Tonga", "TP", "East Timor", "TR", "Turkey", "TT",
			"Trinidad and Tobago", "TV", "Tuvalu", "TW", "Taiwan", "TZ",
			"Tanzania", "UA", "Ukraine", "UG", "Uganda", "UK",
			"United Kingdom", "UM", "US Minor Outlying Islands", "US",
			"United States", "UY", "Uruguay", "UZ", "Uzbekistan", "VA",
			"Vatican City State", "VC", "Saint Vincent & the Grenadines", "VE",
			"Venezuela", "VG", "British Virgin Islands", "VI",
			"Virgin Islands (U.S.)", "VN", "Viet Nam", "VU", "Vanuatu", "WF",
			"Wallis and Futuna Islands", "WS", "Samoa", "XK", "Kosovo*", "YE",
			"Yemen", "YT", "Mayotte", "YU",
			"Serbia and Montenegro (former Yugoslavia)", "ZA", "South Africa",
			"ZM", "Zambia", "ZR", "Zaire", "ZW", "Zimbabwe", "AP",
			"Asia/Pacific Region", "TL", "Timor-Leste" };
	
	

	static final String[] langs = { "Abkhazian", "ab", "Afar", "aa",
			"Afrikaans", "af", "Albanian", "sq", "Amharic", "am", "Arabic",
			"ar", "Armenian", "hy", "Assamese", "as", "Aymara", "ay",
			"Azerbaijani", "az", "Bashkir", "ba", "Basque", "eu",
			"Bengali (Bangla)", "bn", "Bhutani", "dz", "Bihari", "bh",
			"Bislama", "bi", "Breton", "br", "Bulgarian", "bg", "Burmese",
			"my", "Byelorussian (Belarusian)", "be", "Cambodian", "km",
			"Catalan", "ca", "Chinese", "zh", "Corsican", "co", "Croatian",
			"hr", "Czech", "cs", "Danish", "da", "Dutch", "nl", "English",
			"en", "Esperanto", "eo", "Estonian", "et", "Faeroese", "fo",
			"Farsi", "fa", "Fiji", "fj", "Finnish", "fi", "French", "fr",
			"Frisian", "fy", "Galician", "gl", "Gaelic (Scottish)", "gd",
			"Gaelic (Manx)", "gv", "Georgian", "ka", "German", "de", "Greek",
			"el", "Greenlandic", "kl", "Guarani", "gn", "Gujarati", "gu",
			"Hebrew", "he, iw", "Hindi", "hi", "Hungarian", "hu", "Icelandic",
			"is", "Indonesian", "id, in", "Interlingua", "ia", "Interlingue",
			"ie", "Inuktitut", "iu", "Inupiak", "ik", "Irish", "ga", "Italian",
			"it", "Japanese", "ja", "Javanese", "jv", "Kannada", "kn",
			"Kashmiri", "ks", "Kazakh", "kk", "Kinyarwanda (Ruanda)", "rw",
			"Kirghiz", "ky", "Kirundi (Rundi)", "rn", "Korean", "ko",
			"Kurdish", "ku", "Laothian", "lo", "Latin", "la",
			"Latvian (Lettish)", "lv", "Limburgish ( Limburger)", "li",
			"Lingala", "ln", "Lithuanian", "lt", "Macedonian", "mk",
			"Malagasy", "mg", "Malay", "ms", "Malayalam", "ml", "Maltese",
			"mt", "Maori", "mi", "Marathi", "mr", "Moldavian", "mo",
			"Mongolian", "mn", "Nauru", "na", "Nepali", "ne", "Norwegian",
			"no", "Occitan", "oc", "Oriya", "or", "Oromo (Afan, Galla)", "om",
			"Papiamentu", " ", "Pashto (Pushto)", "ps", "Polish", "pl",
			"Portuguese", "pt", "Punjabi", "pa", "Quechua", "qu",
			"Rhaeto-Romance", "rm", "Romanian", "ro", "Russian", "ru",
			"Samoan", "sm", "Sangro", "sg", "Sanskrit", "sa", "Serbian", "sr",
			"Serbo-Croatian", "sh", "Sesotho", "st", "Setswana", "tn", "Shona",
			"sn", "Sindhi", "sd", "Sinhalese", "si", "Siswati", "ss", "Slovak",
			"sk", "Slovenian", "sl", "Somali", "so", "Spanish", "es",
			"Sundanese", "su", "Swahili (Kiswahili)", "sw", "Swedish", "sv",
			"Tagalog", "tl", "Tajik", "tg", "Tamil", "ta", "Tatar", "tt",
			"Telugu", "te", "Thai", "th", "Tibetan", "bo", "Tigrinya", "ti",
			"Tonga", "to", "Tsonga", "ts", "Turkish", "tr", "Turkmen", "tk",
			"Twi", "tw", "Uighur", "ug", "Ukrainian", "uk", "Urdu", "ur",
			"Uzbek", "uz", "Vietnamese", "vi", "Volapük", "vo", "Welsh", "cy",
			"Wolof", "wo", "Xhosa", "xh", "Yiddish", "yi, ji", "Yoruba", "yo",
			"Zulu", "zu" };
}
