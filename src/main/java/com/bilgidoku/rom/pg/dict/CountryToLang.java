package com.bilgidoku.rom.pg.dict;


import java.util.Hashtable;

public class CountryToLang {
	final Hashtable<String, String> countryToLang=new Hashtable<String, String>();
	final Hashtable<String, String> countryNames=new Hashtable<String, String>();
	final Hashtable<String, String> langNames=new Hashtable<String, String>();
	
	final Hashtable<String, Short> countryCodes=new Hashtable<String, Short>();
	final Hashtable<String, Short> langCodes=new Hashtable<String, Short>();
	

	
	public CountryToLang(){
		init();
		initCodes();
	}
	
	

	private void initCodes() {
		short i=0;
		for (String lang:langNames.keySet()) {
			langCodes.put(lang, i++);
		}
		i=0;
		for (String c:countryNames.keySet()) {
			countryCodes.put(c, i++);
		}
	}

	private void init(){
		countryNames.put("TL","Timor-Leste");
		countryNames.put("TK","Tokelau");
		countryNames.put("TJ","Tajikistan");
		countryNames.put("TH","Thailand");
		countryNames.put("TG","Togo");
		countryNames.put("TF","French Southern Territories");
		countryNames.put("GY","Guyana");
		countryNames.put("TD","Chad");
		countryNames.put("TC","Turks and Caicos Islands");
		countryNames.put("GW","Guinea-Bissau");
		countryNames.put("GU","Guam");
		countryNames.put("GT","Guatemala");
		countryNames.put("GS","S. Georgia and S. Sandwich Isls.");
		countryNames.put("GR","Greece");
		countryNames.put("GQ","Equatorial Guinea");
		countryNames.put("GP","Guadeloupe");
		countryNames.put("SZ","Swaziland");
		countryNames.put("GN","Guinea");
		countryNames.put("SY","Syria");
		countryNames.put("GM","Gambia");
		countryNames.put("GL","Greenland");
		countryNames.put("SV","El Salvador");
		countryNames.put("SU","USSR (former)");
		countryNames.put("ST","Sao Tome and Principe");
		countryNames.put("GI","Gibraltar");
		countryNames.put("GH","Ghana");
		countryNames.put("SR","Suriname");
		countryNames.put("GG","Guernsey");
		countryNames.put("GF","French Guiana");
		countryNames.put("GE","Georgia");
		countryNames.put("SO","Somalia");
		countryNames.put("GD","Grenada");
		countryNames.put("SN","Senegal");
		countryNames.put("SM","San Marino");
		countryNames.put("GB","United Kingdom");
		countryNames.put("SL","Sierra Leone");
		countryNames.put("GA","Gabon");
		countryNames.put("SK","Slovakia");
		countryNames.put("SJ","Svalbard & Jan Mayen Islands");
		countryNames.put("SI","Slovenia");
		countryNames.put("SH","St. Helena");
		countryNames.put("SG","Singapore");
		countryNames.put("SE","Sweden");
		countryNames.put("FX","France, Metropolitan");
		countryNames.put("SD","Sudan");
		countryNames.put("SC","Seychelles");
		countryNames.put("SB","Solomon Islands");
		countryNames.put("SA","Saudi Arabia");
		countryNames.put("FR","France");
		countryNames.put("FO","Faroe Islands");
		countryNames.put("FM","Micronesia");
		countryNames.put("RW","Rwanda");
		countryNames.put("FK","Falkland Islands (Malvinas)");
		countryNames.put("FJ","Fiji");
		countryNames.put("RU","Russia");
		countryNames.put("FI","Finland");
		countryNames.put("RS","Serbia");
		countryNames.put("RO","Romania");
		countryNames.put("RE","Reunion");
		countryNames.put("EU","European Union");
		countryNames.put("ET","Ethiopia");
		countryNames.put("ES","Spain");
		countryNames.put("ER","Eritrea");
		countryNames.put("EH","Western Sahara");
		countryNames.put("EG","Egypt");
		countryNames.put("EE","Estonia");
		countryNames.put("EC","Ecuador");
		countryNames.put("DZ","Algeria");
		countryNames.put("QA","Qatar");
		countryNames.put("DO","Dominican Republic");
		countryNames.put("PY","Paraguay");
		countryNames.put("DM","Dominica");
		countryNames.put("PW","Palau");
		countryNames.put("DK","Denmark");
		countryNames.put("DJ","Djibouti");
		countryNames.put("PT","Portugal");
		countryNames.put("PS","Palestinian Territory, Occupied");
		countryNames.put("PR","Puerto Rico");
		countryNames.put("DE","Germany");
		countryNames.put("PN","Pitcairn");
		countryNames.put("PM","St. Pierre and Miquelon");
		countryNames.put("PL","Poland");
		countryNames.put("PK","Pakistan");
		countryNames.put("PH","Philippines");
		countryNames.put("PG","Papua New Guinea");
		countryNames.put("PF","French Polynesia");
		countryNames.put("CZ","Czech Republic");
		countryNames.put("CY","Cyprus");
		countryNames.put("PE","Peru");
		countryNames.put("CX","Christmas Island");
		countryNames.put("CV","Cape Verde");
		countryNames.put("CU","Cuba");
		countryNames.put("PA","Panama");
		countryNames.put("CS","Serbia and Montenegro");
		countryNames.put("CR","Costa Rica");
		countryNames.put("CO","Colombia");
		countryNames.put("CN","China");
		countryNames.put("CM","Cameroon");
		countryNames.put("CL","Chile");
		countryNames.put("CK","Cook Islands");
		countryNames.put("CI","Cote D'Ivoire (Ivory Coast)");
		countryNames.put("CH","Switzerland");
		countryNames.put("CG","Congo");
		countryNames.put("CF","Central African Republic");
		countryNames.put("CD","Congo, Democratic Republic");
		countryNames.put("CC","Cocos (Keeling) Islands");
		countryNames.put("OM","Oman");
		countryNames.put("CA","Canada");
		countryNames.put("BZ","Belize");
		countryNames.put("BY","Belarus");
		countryNames.put("BW","Botswana");
		countryNames.put("BV","Bouvet Island");
		countryNames.put("BT","Bhutan");
		countryNames.put("BS","Bahamas");
		countryNames.put("BR","Brazil");
		countryNames.put("BO","Bolivia");
		countryNames.put("NZ","New Zealand");
		countryNames.put("BN","Brunei Darussalam");
		countryNames.put("BM","Bermuda");
		countryNames.put("NU","Niue");
		countryNames.put("BJ","Benin");
		countryNames.put("NT","Neutral Zone");
		countryNames.put("BI","Burundi");
		countryNames.put("BH","Bahrain");
		countryNames.put("NR","Nauru");
		countryNames.put("BG","Bulgaria");
		countryNames.put("BF","Burkina Faso");
		countryNames.put("NP","Nepal");
		countryNames.put("BE","Belgium");
		countryNames.put("BD","Bangladesh");
		countryNames.put("NO","Norway");
		countryNames.put("BB","Barbados");
		countryNames.put("ZW","Zimbabwe");
		countryNames.put("BA","Bosnia and Herzegovina");
		countryNames.put("NL","Netherlands");
		countryNames.put("NI","Nicaragua");
		countryNames.put("ZR","Zaire");
		countryNames.put("NG","Nigeria");
		countryNames.put("NF","Norfolk Island");
		countryNames.put("AZ","Azerbaijan");
		countryNames.put("NE","Niger");
		countryNames.put("AX","Aland Islands");
		countryNames.put("NC","New Caledonia");
		countryNames.put("AW","Aruba");
		countryNames.put("ZM","Zambia");
		countryNames.put("NA","Namibia");
		countryNames.put("AU","Australia");
		countryNames.put("AT","Austria");
		countryNames.put("AS","American Samoa");
		countryNames.put("AR","Argentina");
		countryNames.put("AQ","Antarctica");
		countryNames.put("AP","Asia/Pacific Region");
		countryNames.put("MZ","Mozambique");
		countryNames.put("AO","Angola");
		countryNames.put("AN","Netherlands Antilles");
		countryNames.put("MY","Malaysia");
		countryNames.put("AM","Armenia");
		countryNames.put("MX","Mexico");
		countryNames.put("MW","Malawi");
		countryNames.put("AL","Albania");
		countryNames.put("MV","Maldives");
		countryNames.put("MU","Mauritius");
		countryNames.put("ZA","South Africa");
		countryNames.put("AI","Anguilla");
		countryNames.put("MT","Malta");
		countryNames.put("MS","Montserrat");
		countryNames.put("MR","Mauritania");
		countryNames.put("AG","Antigua and Barbuda");
		countryNames.put("MQ","Martinique");
		countryNames.put("AF","Afghanistan");
		countryNames.put("MP","Northern Mariana Islands");
		countryNames.put("AE","United Arab Emirates");
		countryNames.put("MO","Macau");
		countryNames.put("AD","Andorra");
		countryNames.put("MN","Mongolia");
		countryNames.put("AC","Ascension Island");
		countryNames.put("MM","Myanmar");
		countryNames.put("ML","Mali");
		countryNames.put("MK","Macedonia");
		countryNames.put("YU","Serbia and Montenegro (former Yugoslavia)");
		countryNames.put("YT","Mayotte");
		countryNames.put("MH","Marshall Islands");
		countryNames.put("MG","Madagascar");
		countryNames.put("MF","Saint Martin");
		countryNames.put("ME","Montenegro");
		countryNames.put("MD","Moldova");
		countryNames.put("MC","Monaco");
		countryNames.put("MA","Morocco");
		countryNames.put("LY","Libya");
		countryNames.put("YE","Yemen");
		countryNames.put("LV","Latvia");
		countryNames.put("LU","Luxembourg");
		countryNames.put("LT","Lithuania");
		countryNames.put("LS","Lesotho");
		countryNames.put("LR","Liberia");
		countryNames.put("LK","Sri Lanka");
		countryNames.put("LI","Liechtenstein");
		countryNames.put("LC","Saint Lucia");
		countryNames.put("LB","Lebanon");
		countryNames.put("LA","Laos");
		countryNames.put("XK","Kosovo");
		countryNames.put("KZ","Kazakhstan");
		countryNames.put("KY","Cayman Islands");
		countryNames.put("KW","Kuwait");
		countryNames.put("KR","South Korea");
		countryNames.put("KP","Korea (North)");
		countryNames.put("KN","Saint Kitts and Nevis");
		countryNames.put("KM","Comoros");
		countryNames.put("KI","Kiribati");
		countryNames.put("WS","Samoa");
		countryNames.put("KH","Cambodia");
		countryNames.put("KG","Kyrgyzstan");
		countryNames.put("KE","Kenya");
		countryNames.put("WF","Wallis and Futuna Islands");
		countryNames.put("JP","Japan");
		countryNames.put("JO","Jordan");
		countryNames.put("JM","Jamaica");
		countryNames.put("VU","Vanuatu");
		countryNames.put("JE","Jersey");
		countryNames.put("VN","Vietnam");
		countryNames.put("VI","Virgin Islands (U.S.)");
		countryNames.put("VG","British Virgin Islands");
		countryNames.put("VE","Venezuela");
		countryNames.put("VC","Saint Vincent & the Grenadines");
		countryNames.put("VA","Vatican City State");
		countryNames.put("IT","Italy");
		countryNames.put("IS","Iceland");
		countryNames.put("IR","Iran");
		countryNames.put("IQ","Iraq");
		countryNames.put("UZ","Uzbekistan");
		countryNames.put("IO","British Indian Ocean Territory");
		countryNames.put("IN","India");
		countryNames.put("UY","Uruguay");
		countryNames.put("IM","Isle of Man");
		countryNames.put("IL","Israel");
		countryNames.put("US","United States");
		countryNames.put("IE","Ireland");
		countryNames.put("ID","Indonesia");
		countryNames.put("UM","US Minor Outlying Islands");
		countryNames.put("UK","United Kingdom");
		countryNames.put("UG","Uganda");
		countryNames.put("HU","Hungary");
		countryNames.put("UA","Ukraine");
		countryNames.put("HT","Haiti");
		countryNames.put("HR","Croatia");
		countryNames.put("TZ","Tanzania");
		countryNames.put("HN","Honduras");
		countryNames.put("HM","Heard and McDonald Islands");
		countryNames.put("TW","Taiwan");
		countryNames.put("TV","Tuvalu");
		countryNames.put("HK","Hong Kong");
		countryNames.put("TT","Trinidad and Tobago");
		countryNames.put("TR","Turkey");
		countryNames.put("TP","East Timor");
		countryNames.put("TO","Tonga");
		countryNames.put("TN","Tunisia");
		countryNames.put("TM","Turkmenistan");

		langNames.put("th","Thai");
		langNames.put("zh","Chinese");
		langNames.put("ar","Arabic");
		langNames.put("mt","Maltese");
		langNames.put("ms","Malay");
		langNames.put("sv","Swedish");
		langNames.put("pt","Portuguese");
		langNames.put("sr","Serbian");
		langNames.put("sq","Albanian");
		langNames.put("de","German");
		langNames.put("mk","Macedonian");
		langNames.put("pl","Polish");
		langNames.put("sl","Slovenian");
		langNames.put("da","Danish");
		langNames.put("sk","Slovak");
		langNames.put("ga","Irish");
		langNames.put("vi","Vietnamese");
		langNames.put("ja","Japanese");
		langNames.put("cs","Czech");
		langNames.put("iw","Hebrew");
		langNames.put("fr","French");
		langNames.put("it","Italian");
		langNames.put("lv","Latvian");
		langNames.put("is","Icelandic");
		langNames.put("lt","Lithuanian");
		langNames.put("ru","Russian");
		langNames.put("in","Indonesian");
		langNames.put("fi","Finnish");
		langNames.put("ro","Romanian");
		langNames.put("ca","Catalan");
		langNames.put("uk","Ukrainian");
		langNames.put("et","Estonian");
		langNames.put("es","Spanish");
		langNames.put("hu","Hungarian");
		langNames.put("hr","Croatian");
		langNames.put("en","English");
		langNames.put("el","Greek");
		langNames.put("ko","Korean");
		langNames.put("bg","Bulgarian");
		langNames.put("no","Norwegian");
		langNames.put("tr","Turkish");
		langNames.put("be","Belarusian");
		langNames.put("hi","Hindi");
		langNames.put("nl","Dutch");

		//auto
		countryToLang.put("TL","en");
		//auto
		countryToLang.put("TK","en");
		//auto
		countryToLang.put("TJ","en");
		countryToLang.put("TH","th");
		//auto
		countryToLang.put("TG","en");
		//auto
		countryToLang.put("TF","en");
		//auto
		countryToLang.put("GY","en");
		//auto
		countryToLang.put("TD","en");
		//auto
		countryToLang.put("TC","en");
		//auto
		countryToLang.put("GW","en");
		//auto
		countryToLang.put("GU","en");
		countryToLang.put("GT","es");
		//auto
		countryToLang.put("GS","en");
		countryToLang.put("GR","el");
		//auto
		countryToLang.put("GQ","en");
		//auto
		countryToLang.put("GP","en");
		//auto
		countryToLang.put("SZ","en");
		//auto
		countryToLang.put("GN","en");
		countryToLang.put("SY","ar");
		//auto
		countryToLang.put("GM","en");
		//auto
		countryToLang.put("GL","en");
		countryToLang.put("SV","es");
		//auto
		countryToLang.put("SU","en");
		//auto
		countryToLang.put("ST","en");
		//auto
		countryToLang.put("GI","en");
		//auto
		countryToLang.put("GH","en");
		//auto
		countryToLang.put("SR","en");
		//auto
		countryToLang.put("GG","en");
		//auto
		countryToLang.put("GF","en");
		//auto
		countryToLang.put("GE","en");
		//auto
		countryToLang.put("SO","en");
		//auto
		countryToLang.put("GD","en");
		//auto
		countryToLang.put("SN","en");
		//auto
		countryToLang.put("SM","en");
		countryToLang.put("GB","en");
		//auto
		countryToLang.put("SL","en");
		//auto
		countryToLang.put("GA","en");
		countryToLang.put("SK","sk");
		//auto
		countryToLang.put("SJ","en");
		countryToLang.put("SI","sl");
		//auto
		countryToLang.put("SH","en");
//		countryToLang.put("SG","zh");
		countryToLang.put("SG","en");
		countryToLang.put("SE","sv");
		//auto
		countryToLang.put("FX","en");
		countryToLang.put("SD","ar");
		//auto
		countryToLang.put("SC","en");
		//auto
		countryToLang.put("SB","en");
		countryToLang.put("SA","ar");
		countryToLang.put("FR","fr");
		//auto
		countryToLang.put("FO","en");
		//auto
		countryToLang.put("FM","en");
		//auto
		countryToLang.put("RW","en");
		//auto
		countryToLang.put("FK","en");
		//auto
		countryToLang.put("FJ","en");
		countryToLang.put("RU","ru");
		countryToLang.put("FI","fi");
		countryToLang.put("RS","sr");
		countryToLang.put("RO","ro");
		//auto
		countryToLang.put("RE","en");
		//auto
		countryToLang.put("EU","en");
		//auto
		countryToLang.put("ET","en");
		countryToLang.put("ES","es");
		//countryToLang.put("ES","ca");
		//auto
		countryToLang.put("ER","en");
		//auto
		countryToLang.put("EH","en");
		countryToLang.put("EG","ar");
		countryToLang.put("EE","et");
		countryToLang.put("EC","es");
		countryToLang.put("DZ","ar");
		countryToLang.put("QA","ar");
		countryToLang.put("DO","es");
		countryToLang.put("PY","es");
		//auto
		countryToLang.put("DM","en");
		//auto
		countryToLang.put("PW","en");
		countryToLang.put("DK","da");
		//auto
		countryToLang.put("DJ","en");
		countryToLang.put("PT","pt");
		//auto
		countryToLang.put("PS","en");
		countryToLang.put("PR","es");
		countryToLang.put("DE","de");
		//auto
		countryToLang.put("PN","en");
		//auto
		countryToLang.put("PM","en");
		countryToLang.put("PL","pl");
		//auto
		countryToLang.put("PK","en");
		countryToLang.put("PH","en");
		//auto
		countryToLang.put("PG","en");
		//auto
		countryToLang.put("PF","en");
		countryToLang.put("CZ","cs");
		countryToLang.put("CY","el");
		countryToLang.put("PE","es");
		//auto
		countryToLang.put("CX","en");
		//auto
		countryToLang.put("CV","en");
		//auto
		countryToLang.put("CU","en");
		countryToLang.put("PA","es");
		countryToLang.put("CS","sr");
		countryToLang.put("CR","es");
		countryToLang.put("CO","es");
		countryToLang.put("CN","zh");
		//auto
		countryToLang.put("CM","en");
		countryToLang.put("CL","es");
		//auto
		countryToLang.put("CK","en");
		//auto
		countryToLang.put("CI","en");
		countryToLang.put("CH","de");
		//countryToLang.put("CH","it");
		//auto
		countryToLang.put("CG","en");
		//auto
		countryToLang.put("CF","en");
		//auto
		countryToLang.put("CD","en");
		//auto
		countryToLang.put("CC","en");
		countryToLang.put("OM","ar");
//		countryToLang.put("CA","fr");
		countryToLang.put("CA","en");
		//auto
		countryToLang.put("BZ","en");
		countryToLang.put("BY","be");
		//auto
		countryToLang.put("BW","en");
		//auto
		countryToLang.put("BV","en");
		//auto
		countryToLang.put("BT","en");
		//auto
		countryToLang.put("BS","en");
		countryToLang.put("BR","pt");
		countryToLang.put("BO","es");
		countryToLang.put("NZ","en");
		//auto
		countryToLang.put("BN","en");
		//auto
		countryToLang.put("BM","en");
		//auto
		countryToLang.put("NU","en");
		//auto
		countryToLang.put("BJ","en");
		//auto
		countryToLang.put("NT","en");
		//auto
		countryToLang.put("BI","en");
		countryToLang.put("BH","ar");
		//auto
		countryToLang.put("NR","en");
		countryToLang.put("BG","bg");
		//auto
		countryToLang.put("BF","en");
		//auto
		countryToLang.put("NP","en");
		countryToLang.put("BE","fr");
		//countryToLang.put("BE","nl");
		//auto
		countryToLang.put("BD","en");
		countryToLang.put("NO","no");
		//auto
		countryToLang.put("BB","en");
		//auto
		countryToLang.put("ZW","en");
		countryToLang.put("BA","sr");
		countryToLang.put("NL","nl");
		countryToLang.put("NI","es");
		//auto
		countryToLang.put("ZR","en");
		//auto
		countryToLang.put("NG","en");
		//auto
		countryToLang.put("NF","en");
		//auto
		countryToLang.put("AZ","en");
		//auto
		countryToLang.put("NE","en");
		//auto
		countryToLang.put("AX","en");
		//auto
		countryToLang.put("NC","en");
		//auto
		countryToLang.put("AW","en");
		//auto
		countryToLang.put("ZM","en");
		//auto
		countryToLang.put("NA","en");
		countryToLang.put("AU","en");
		countryToLang.put("AT","de");
		//auto
		countryToLang.put("AS","en");
		countryToLang.put("AR","es");
		//auto
		countryToLang.put("AQ","en");
		//auto
		countryToLang.put("AP","en");
		//auto
		countryToLang.put("MZ","en");
		//auto
		countryToLang.put("AO","en");
		//auto
		countryToLang.put("AN","en");
		countryToLang.put("MY","ms");
		//auto
		countryToLang.put("AM","en");
		countryToLang.put("MX","es");
		//auto
		countryToLang.put("MW","en");
		countryToLang.put("AL","sq");
		//auto
		countryToLang.put("MV","en");
		//auto
		countryToLang.put("MU","en");
		countryToLang.put("ZA","en");
		//auto
		countryToLang.put("AI","en");
		countryToLang.put("MT","en");
		//countryToLang.put("MT","mt");
		//auto
		countryToLang.put("MS","en");
		//auto
		countryToLang.put("MR","en");
		//auto
		countryToLang.put("AG","en");
		//auto
		countryToLang.put("MQ","en");
		//auto
		countryToLang.put("AF","en");
		//auto
		countryToLang.put("MP","en");
		countryToLang.put("AE","ar");
		//auto
		countryToLang.put("MO","en");
		//auto
		countryToLang.put("AD","en");
		//auto
		countryToLang.put("MN","en");
		//auto
		countryToLang.put("AC","en");
		//auto
		countryToLang.put("MM","en");
		//auto
		countryToLang.put("ML","en");
		countryToLang.put("MK","mk");
		//auto
		countryToLang.put("YU","en");
		//auto
		countryToLang.put("YT","en");
		//auto
		countryToLang.put("MH","en");
		//auto
		countryToLang.put("MG","en");
		//auto
		countryToLang.put("MF","en");
		countryToLang.put("ME","sr");
		//auto
		countryToLang.put("MD","en");
		//auto
		countryToLang.put("MC","en");
		countryToLang.put("MA","ar");
		countryToLang.put("LY","ar");
		countryToLang.put("YE","ar");
		countryToLang.put("LV","lv");
		countryToLang.put("LU","fr");
		//countryToLang.put("LU","de");
		countryToLang.put("LT","lt");
		//auto
		countryToLang.put("LS","en");
		//auto
		countryToLang.put("LR","en");
		//auto
		countryToLang.put("LK","en");
		//auto
		countryToLang.put("LI","en");
		//auto
		countryToLang.put("LC","en");
		countryToLang.put("LB","ar");
		//auto
		countryToLang.put("LA","en");
		//auto
		countryToLang.put("XK","en");
		//auto
		countryToLang.put("KZ","en");
		//auto
		countryToLang.put("KY","en");
		countryToLang.put("KW","ar");
		countryToLang.put("KR","ko");
		//auto
		countryToLang.put("KP","en");
		//auto
		countryToLang.put("KN","en");
		//auto
		countryToLang.put("KM","en");
		//auto
		countryToLang.put("KI","en");
		//auto
		countryToLang.put("WS","en");
		//auto
		countryToLang.put("KH","en");
		//auto
		countryToLang.put("KG","en");
		//auto
		countryToLang.put("KE","en");
		//auto
		countryToLang.put("WF","en");
		countryToLang.put("JP","ja");
		countryToLang.put("JO","ar");
		//auto
		countryToLang.put("JM","en");
		//auto
		countryToLang.put("VU","en");
		//auto
		countryToLang.put("JE","en");
		countryToLang.put("VN","vi");
		//auto
		countryToLang.put("VI","en");
		//auto
		countryToLang.put("VG","en");
		countryToLang.put("VE","es");
		//auto
		countryToLang.put("VC","en");
		//auto
		countryToLang.put("VA","en");
		countryToLang.put("IT","it");
		countryToLang.put("IS","is");
		//auto
		countryToLang.put("IR","en");
		countryToLang.put("IQ","ar");
		//auto
		countryToLang.put("UZ","en");
		//auto
		countryToLang.put("IO","en");
//		countryToLang.put("IN","hi");
		countryToLang.put("IN","en");
		countryToLang.put("UY","es");
		//auto
		countryToLang.put("IM","en");
		countryToLang.put("IL","iw");
		countryToLang.put("US","en");
		//countryToLang.put("US","es");
//		countryToLang.put("IE","ga");
		countryToLang.put("IE","en");
		countryToLang.put("ID","in");
		//auto
		countryToLang.put("UM","en");
		//auto
		countryToLang.put("UK","en");
		//auto
		countryToLang.put("UG","en");
		countryToLang.put("HU","hu");
		countryToLang.put("UA","uk");
		//auto
		countryToLang.put("HT","en");
		countryToLang.put("HR","hr");
		//auto
		countryToLang.put("TZ","en");
		countryToLang.put("HN","es");
		//auto
		countryToLang.put("HM","en");
		countryToLang.put("TW","zh");
		//auto
		countryToLang.put("TV","en");
		countryToLang.put("HK","zh");
		//auto
		countryToLang.put("TT","en");
		countryToLang.put("TR","tr");
		//auto
		countryToLang.put("TP","en");
		//auto
		countryToLang.put("TO","en");
		countryToLang.put("TN","ar");
		//auto
		countryToLang.put("TM","en");
	}

	public Short getCountryCode(String code) {
		return this.countryCodes.get(code);
	}

	public short getLanguageCode(String string) {
		return langCodes.get(string);
	}



	public String getLangOfCountry(String country) {
		return countryToLang.get(country);
	}


}
