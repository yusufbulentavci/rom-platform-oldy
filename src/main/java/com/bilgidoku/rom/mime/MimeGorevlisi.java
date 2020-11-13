package com.bilgidoku.rom.mime;

import java.util.HashMap;
import java.util.Map;

import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.shared.err.KnownError;


public class MimeGorevlisi extends GorevliDir {
	public static final int NO=15;
	
	public static MimeGorevlisi tek(){
		if(tek==null) {
			synchronized (MimeGorevlisi.class) {
				if(tek==null) {
					tek=new MimeGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}
	
	static MimeGorevlisi tek;
	private MimeGorevlisi() {
		super("Mime", NO);
		for(int i=0; i<pairs.length;i++){
			String[] p=pairs[i];
			fileToMime.put(p[0], p[1]);
		}
	}
	
	final static private MC mc=new MC(MimeGorevlisi.class);
	private Map<String,String> fileToMime=new HashMap<String, String>();
	
	final static private Astate gmc=mc.c("get-mime");
	
	public String getMime(String fileExtension){
		gmc.more();
		return fileToMime.get(fileExtension);
	}

	private static final Astate _getMimeOfFile=mc.c("getMimeOfFile");

	
	public String getMimeOfFile(String lastPart) throws KnownError{
		_getMimeOfFile.more();
		int ind = lastPart.lastIndexOf('.');
		if (ind < 0) {
			_getMimeOfFile.fail(lastPart);
			throw new KnownError().badRequest();
		}

		String extension = lastPart.substring(ind + 1).toLowerCase();
		String mime = getMime(extension);
		if (mime == null) {
			_getMimeOfFile.fail(lastPart);
			throw new KnownError("Mime not known:"+lastPart).badRequest();
		}
		return mime;
	}
	
	public String[] getMimeById(int id) {
		return pairs[id];
	}

	
	private static String[][] pairs=new String[][]{
			new String[]{"konus","application/javascript"},
			new String[]{"json","application/javascript"},
			new String[]{"htm","text/html"},
			new String[]{"html","text/html"},
			new String[]{"323","text/h323"},
			new String[]{"acx","application/internet-property-stream"},
			new String[]{"ai","application/postscript"},
			new String[]{"aif","audio/x-aiff"},
			new String[]{"aifc","audio/x-aiff"},
			new String[]{"aiff","audio/x-aiff"},
			new String[]{"asf","video/x-ms-asf"},
			new String[]{"asr","video/x-ms-asf"},
			new String[]{"asx","video/x-ms-asf"},
			new String[]{"au","audio/basic"},
			new String[]{"avi","video/x-msvideo"},
			new String[]{"axs","application/olescript"},
			new String[]{"bas","text/plain"},
			new String[]{"sql","text/plain"},
			new String[]{"bcpio","application/x-bcpio"},
			new String[]{"bin","application/octet-stream"},
			new String[]{"bmp","image/bmp"},
			new String[]{"c","text/plain"},
			new String[]{"cat","application/vnd.ms-pkiseccat"},
			new String[]{"cdf","application/x-cdf"},
			new String[]{"cer","application/x-x509-ca-cert"},
			new String[]{"class","application/octet-stream"},
			new String[]{"clp","application/x-msclip"},
			new String[]{"cmx","image/x-cmx"},
			new String[]{"cod","image/cis-cod"},
			new String[]{"cpio","application/x-cpio"},
			new String[]{"crd","application/x-mscardfile"},
			new String[]{"crl","application/pkix-crl"},
			new String[]{"crt","application/x-x509-ca-cert"},
			new String[]{"csh","application/x-csh"},
			new String[]{"css","text/css"},
			new String[]{"dcr","application/x-director"},
			new String[]{"der","application/x-x509-ca-cert"},
			new String[]{"dir","application/x-director"},
			new String[]{"dll","application/x-msdownload"},
			new String[]{"dms","application/octet-stream"},
			new String[]{"doc","application/msword"},
			new String[]{"dot","application/msword"},
			new String[]{"dvi","application/x-dvi"},
			new String[]{"dxr","application/x-director"},
			new String[]{"eps","application/postscript"},
			new String[]{"etx","text/x-setext"},
			new String[]{"evy","application/envoy"},
			new String[]{"exe","application/octet-stream"},
			new String[]{"fif","application/fractals"},
			new String[]{"flr","x-world/x-vrml"},
			new String[]{"gif","image/gif"},
			new String[]{"gtar","application/x-gtar"},
			new String[]{"gz","application/x-gzip"},
			new String[]{"h","text/plain"},
			new String[]{"hdf","application/x-hdf"},
			new String[]{"hlp","application/winhlp"},
			new String[]{"hqx","application/mac-binhex40"},
			new String[]{"hta","application/hta"},
			new String[]{"htc","text/x-component"},
			new String[]{"htt","text/webviewhtml"},
			new String[]{"ico","image/x-icon"},
			new String[]{"ief","image/ief"},
			new String[]{"iii","application/x-iphone"},
			new String[]{"ins","application/x-internet-signup"},
			new String[]{"isp","application/x-internet-signup"},
			new String[]{"jfif","image/pipeg"},
			new String[]{"jpe","image/jpeg"},
			new String[]{"jpeg","image/jpeg"},
			new String[]{"jpg","image/jpeg"},
			new String[]{"js","application/x-javascript"},
			new String[]{"latex","application/x-latex"},
			new String[]{"lha","application/octet-stream"},
			new String[]{"lsf","video/x-la-asf"},
			new String[]{"lsx","video/x-la-asf"},
			new String[]{"lzh","application/octet-stream"},
			new String[]{"m13","application/x-msmediaview"},
			new String[]{"m14","application/x-msmediaview"},
			new String[]{"m3u","audio/x-mpegurl"},
			new String[]{"man","application/x-troff-man"},
			new String[]{"mdb","application/x-msaccess"},
			new String[]{"me","application/x-troff-me"},
			new String[]{"mht","message/rfc822"},
			new String[]{"mhtml","message/rfc822"},
			new String[]{"mid","audio/mid"},
			new String[]{"mny","application/x-msmoney"},
			new String[]{"mov","video/quicktime"},
			new String[]{"movie","video/x-sgi-movie"},
			new String[]{"mp2","video/mpeg"},
			new String[]{"mp3","audio/mpeg"},
			new String[]{"mpa","video/mpeg"},
			new String[]{"mpe","video/mpeg"},
			new String[]{"mpeg","video/mpeg"},
			new String[]{"mpg","video/mpeg"},
			new String[]{"mpp","application/vnd.ms-project"},
			new String[]{"mpv2","video/mpeg"},
			new String[]{"mov","video/quicktime"},
			new String[]{"movie","video/x-sgi-movie"},
			new String[]{"mp4","video/mp4"},
			new String[]{"mpga","audio/mpeg", 
			"ms","application/x-troff-ms"},
			new String[]{"mvb","application/x-msmediaview"},
			new String[]{"nws","message/rfc822"},
			new String[]{"oda","application/oda"},
			new String[]{"p10","application/pkcs10"},
			new String[]{"p12","application/x-pkcs12"},
			new String[]{"p7b","application/x-pkcs7-certificates"},
			new String[]{"p7c","application/x-pkcs7-mime"},
			new String[]{"p7m","application/x-pkcs7-mime"},
			new String[]{"p7r","application/x-pkcs7-certreqresp"},
			new String[]{"p7s","application/x-pkcs7-signature"},
			new String[]{"pbm","image/x-portable-bitmap"},
			new String[]{"pdf","application/pdf"},
			new String[]{"pfx","application/x-pkcs12"},
			new String[]{"pgm","image/x-portable-graymap"},
			new String[]{"pko","application/ynd.ms-pkipko"},
			new String[]{"pma","application/x-perfmon"},
			new String[]{"pmc","application/x-perfmon"},
			new String[]{"pml","application/x-perfmon"},
			new String[]{"pmr","application/x-perfmon"},
			new String[]{"pmw","application/x-perfmon"},
			new String[]{"png","image/png"},
			new String[]{"pnm","image/x-portable-anymap"},
			new String[]{"pot,","application/vnd.ms-powerpoint"},
			new String[]{"ppm","image/x-portable-pixmap"},
			new String[]{"prf","application/pics-rules"},
			new String[]{"ps","application/postscript"},
			new String[]{"pub","application/x-mspublisher"},
			new String[]{"qt","video/quicktime"},
			new String[]{"ra","audio/x-pn-realaudio"},
			new String[]{"ram","audio/x-pn-realaudio"},
			new String[]{"ras","image/x-cmu-raster"},
			new String[]{"rgb","image/x-rgb"},
			new String[]{"rmi","audio/mid"},
			new String[]{"roff","application/x-troff"},
			new String[]{"rtf","application/rtf"},
			new String[]{"rtx","text/richtext"},
			new String[]{"scd","application/x-msschedule"},
			new String[]{"sct","text/scriptlet"},
			new String[]{"setpay","application/set-payment-initiation"},
			new String[]{"setreg","application/set-registration-initiation"},
			new String[]{"sh","application/x-sh"},
			new String[]{"shar","application/x-shar"},
			new String[]{"sit","application/x-stuffit"},
			new String[]{"snd","audio/basic"},
			new String[]{"spc","application/x-pkcs7-certificates"},
			new String[]{"spl","application/futuresplash"},
			new String[]{"src","application/x-wais-source"},
			new String[]{"sst","application/vnd.ms-pkicertstore"},
			new String[]{"stl","application/vnd.ms-pkistl"},
			new String[]{"stm","text/html"},
			new String[]{"sv4cpio","application/x-sv4cpio"},
			new String[]{"sv4crc","application/x-sv4crc"},
			new String[]{"swf","application/x-shockwave-flash"},
			new String[]{"t","application/x-troff"},
			new String[]{"tar","application/x-tar"},
			new String[]{"tcl","application/x-tcl"},
			new String[]{"tex","application/x-tex"},
			new String[]{"texi","application/x-texinfo"},
			new String[]{"texinfo","application/x-texinfo"},
			new String[]{"tgz","application/x-compressed"},
			new String[]{"tif","image/tiff"},
			new String[]{"tiff","image/tiff"},
			new String[]{"tr","application/x-troff"},
			new String[]{"trm","application/x-msterminal"},
			new String[]{"tsv","text/tab-separated-values"},
			new String[]{"txt","text/plain"},
			new String[]{"uls","text/iuls"},
			new String[]{"ustar","application/x-ustar"},
			new String[]{"vcf","text/x-vcard"},
			new String[]{"vrml","x-world/x-vrml"},
			new String[]{"wav","audio/x-wav"},
			new String[]{"wcm","application/vnd.ms-works"},
			new String[]{"wdb","application/vnd.ms-works"},
			new String[]{"wks","application/vnd.ms-works"},
			new String[]{"wmf","application/x-msmetafile"},
			new String[]{"wps","application/vnd.ms-works"},
			new String[]{"wri","application/x-mswrite"},
			new String[]{"wrl","x-world/x-vrml"},
			new String[]{"wrz","x-world/x-vrml"},
			new String[]{"xaf","x-world/x-vrml"},
			new String[]{"xbm","image/x-xbitmap"},
			new String[]{"xof","x-world/x-vrml"},
			new String[]{"xpm","image/x-xpixmap"},
			new String[]{"xwd","image/x-xwindowdump"},
			new String[]{"z","application/x-compress"},
			new String[]{"zip","application/zip"},
			new String[]{"odt","application/vnd.oasis.opendocument.text"},
			new String[]{"oth","application/vnd.oasis.opendocument.text-web"},
			new String[]{"odm","application/vnd.oasis.opendocument.text-master"},
			new String[]{"odg","application/vnd.oasis.opendocument.graphics"},
			new String[]{"odp","application/vnd.oasis.opendocument.presentation"},
			new String[]{"ods","application/vnd.oasis.opendocument.spreadsheet"},
			new String[]{"odc","application/vnd.oasis.opendocument.chart"},
			new String[]{"odf","application/vnd.oasis.opendocument.formula"},
			new String[]{"odb","application/vnd.oasis.opendocument.database"},
			new String[]{"odi","application/vnd.oasis.opendocument.image"},
			new String[]{"doc","application/msword"},
			new String[]{"dot","application/msword"},
			new String[]{"docx","application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
			new String[]{"dotx","application/vnd.openxmlformats-officedocument.wordprocessingml.template"},
			new String[]{"docm","application/vnd.ms-word.document.macroEnabled.12"},
			new String[]{"dotm", "application/vnd.ms-word.template.macroEnabled.12"},
			new String[]{"xls", "application/vnd.ms-excel"},
			new String[]{"xlt","application/vnd.ms-excel"},
			new String[]{"xla", "application/vnd.ms-excel"},
			new String[]{"xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
			new String[]{"xltx", "application/vnd.openxmlformats-officedocument.spreadsheetml.template"},
			new String[]{"xlsm", "application/vnd.ms-excel.sheet.macroEnabled.12"},
			new String[]{"xltm", "application/vnd.ms-excel.template.macroEnabled.12"},
			new String[]{"xlam", "application/vnd.ms-excel.addin.macroEnabled.12"},
			new String[]{"xlsb", "application/vnd.ms-excel.sheet.binary.macroEnabled.12"},
			new String[]{"ppt", "application/vnd.ms-powerpoint"},
			new String[]{"pot", "application/vnd.ms-powerpoint"},
			new String[]{"pps", "application/vnd.ms-powerpoint"},
			new String[]{"ppa", "application/vnd.ms-powerpoint"},
			new String[]{"pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
			new String[]{"potx", "application/vnd.openxmlformats-officedocument.presentationml.template"},
			new String[]{"ppsx", "application/vnd.openxmlformats-officedocument.presentationml.slideshow"},
			new String[]{"ppam", "application/vnd.ms-powerpoint.addin.macroEnabled.12"},
			new String[]{"pptm", "application/vnd.ms-powerpoint.presentation.macroEnabled.12"},
			new String[]{"potm", "application/vnd.ms-powerpoint.template.macroEnabled.12"},
			new String[]{"ppsm", "application/vnd.ms-powerpoint.slideshow.macroEnabled.12"},
			new String[]{"svg","image/svg+xml"},
			new String[]{"woff","application/x-font-woff"},
			new String[]{"xml","application/xml"},
			new String[]{"rgf","application/javascript"}
	};

	
	public void selfDescribe(JSONObject jo) {
		
	}
	
}
