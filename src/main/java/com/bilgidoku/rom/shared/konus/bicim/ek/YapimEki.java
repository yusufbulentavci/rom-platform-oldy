package com.bilgidoku.rom.shared.konus.bicim.ek;

/**
 * Sözcüklerin çekimlenerek değişik yerlerde ve görevlerde kullanılmasını sağlayan eklere çekim eki denir. 
 * Çekim ekleri, kelimenin biçimini değiştirir; anlamını değiştirmez. Eklendiği sözcüğe yeni bir anlam kazandırmaz.
 * 
 * Çekim ekleri, sözcüklerin diğer sözcüklerle bağ kurmasını; sözcüklerinin cümlede görev almasını; 
 * hâlini, sayısını, zamanını, şahsını belirtir. 
 * Kısaca çekim ekleri sözcüklerin cümle kuruluşunu gerçekleştirmesini sağlar.
 * 
 * 2. tekil kişi emir kipiyle kurulan tümcelerin (Atla. Koş. Bak. Ye. …) haricinde hiçbir tümce, çekim eki olmadan kurulamaz. 
 * 
 * @author rompg
 *
 */
public abstract class YapimEki extends Ek{
	YapimEki(char kodu, int kucukOnce) {
		super(kodu, kucukOnce);
		// TODO Auto-generated constructor stub
	}

	abstract boolean eylemdemi();

	
	

}
