package com.bilgidoku.rom.site.yerel.mail.core;

import java.util.HashMap;
import java.util.Map;

public class MimeContentType {

	private Map<String, String> fileToMime = new HashMap<String, String>();

	public MimeContentType() {
		for (int i = 0; i < pairs.length; i += 2) {
			String file = pairs[i];
			String mime = pairs[i + 1];
			fileToMime.put(file, mime);
		}
	}


	public String getMime(String fileExtension) {
		return fileToMime.get(fileExtension);
	}


	public String getMimeOfFile(String lastPart) {
		int ind = lastPart.lastIndexOf('.');
		if (ind < 0) {
			return "application/octet-stream";
		}

		String extension = lastPart.substring(ind + 1).toLowerCase();
		String mime = getMime(extension);
		if (mime == null) {
			return "application/octet-stream";
		}
		return mime;
	}

	private static String[] pairs = new String[] { "323", "text/h323", "acx", "application/internet-property-stream",
			"ai", "application/postscript", "aif", "audio/x-aiff", "aifc", "audio/x-aiff", "aiff", "audio/x-aiff",
			"asf", "video/x-ms-asf", "asr", "video/x-ms-asf", "asx", "video/x-ms-asf", "au", "audio/basic", "avi",
			"video/x-msvideo", "axs", "application/olescript", "bas", "text/plain", "sql", "text/plain", "bcpio",
			"application/x-bcpio", "bin", "application/octet-stream", "bmp", "image/bmp", "c", "text/plain", "cat",
			"application/vnd.ms-pkiseccat", "cdf", "application/x-cdf", "cer", "application/x-x509-ca-cert", "class",
			"application/octet-stream", "clp", "application/x-msclip", "cmx", "image/x-cmx", "cod", "image/cis-cod",
			"cpio", "application/x-cpio", "crd", "application/x-mscardfile", "crl", "application/pkix-crl", "crt",
			"application/x-x509-ca-cert", "csh", "application/x-csh", "css", "text/css", "dcr",
			"application/x-director", "der", "application/x-x509-ca-cert", "dir", "application/x-director", "dll",
			"application/x-msdownload", "dms", "application/octet-stream", "doc", "application/msword", "dot",
			"application/msword", "dvi", "application/x-dvi", "dxr", "application/x-director", "eps",
			"application/postscript", "etx", "text/x-setext", "evy", "application/envoy", "exe",
			"application/octet-stream", "fif", "application/fractals", "flr", "x-world/x-vrml", "gif", "image/gif",
			"gtar", "application/x-gtar", "gz", "application/x-gzip", "h", "text/plain", "hdf", "application/x-hdf",
			"hlp", "application/winhlp", "hqx", "application/mac-binhex40", "hta", "application/hta", "htc",
			"text/x-component", "htm", "text/html", "html", "text/html", "htt", "text/webviewhtml", "ico",
			"image/x-icon", "ief", "image/ief", "iii", "application/x-iphone", "ins", "application/x-internet-signup",
			"isp", "application/x-internet-signup", "jfif", "image/pipeg", "jpe", "image/jpeg", "jpeg", "image/jpeg",
			"jpg", "image/jpeg", "js", "application/x-javascript", "latex", "application/x-latex", "lha",
			"application/octet-stream", "lsf", "video/x-la-asf", "lsx", "video/x-la-asf", "lzh",
			"application/octet-stream", "m13", "application/x-msmediaview", "m14", "application/x-msmediaview", "m3u",
			"audio/x-mpegurl", "man", "application/x-troff-man", "mdb", "application/x-msaccess", "me",
			"application/x-troff-me", "mht", "message/rfc822", "mhtml", "message/rfc822", "mid", "audio/mid", "mny",
			"application/x-msmoney", "mov", "video/quicktime", "movie", "video/x-sgi-movie", "mp2", "video/mpeg",
			"mp3", "audio/mpeg", "mpa", "video/mpeg", "mpe", "video/mpeg", "mpeg", "video/mpeg", "mpg", "video/mpeg",
			"mpp", "application/vnd.ms-project", "mpv2", "video/mpeg", "ms", "application/x-troff-ms", "mvb",
			"application/x-msmediaview", "nws", "message/rfc822", "oda", "application/oda", "p10",
			"application/pkcs10", "p12", "application/x-pkcs12", "p7b", "application/x-pkcs7-certificates", "p7c",
			"application/x-pkcs7-mime", "p7m", "application/x-pkcs7-mime", "p7r", "application/x-pkcs7-certreqresp",
			"p7s", "application/x-pkcs7-signature", "pbm", "image/x-portable-bitmap", "pdf", "application/pdf", "pfx",
			"application/x-pkcs12", "pgm", "image/x-portable-graymap", "pko", "application/ynd.ms-pkipko", "pma",
			"application/x-perfmon", "pmc", "application/x-perfmon", "pml", "application/x-perfmon", "pmr",
			"application/x-perfmon", "pmw", "application/x-perfmon", "png", "image/png", "pnm",
			"image/x-portable-anymap", "pot,", "application/vnd.ms-powerpoint", "ppm", "image/x-portable-pixmap",
			"prf", "application/pics-rules", "ps", "application/postscript", "pub", "application/x-mspublisher", "qt",
			"video/quicktime", "ra", "audio/x-pn-realaudio", "ram", "audio/x-pn-realaudio", "ras",
			"image/x-cmu-raster", "rgb", "image/x-rgb", "rmi", "audio/mid", "roff", "application/x-troff", "rtf",
			"application/rtf", "rtx", "text/richtext", "scd", "application/x-msschedule", "sct", "text/scriptlet",
			"setpay", "application/set-payment-initiation", "setreg", "application/set-registration-initiation", "sh",
			"application/x-sh", "shar", "application/x-shar", "sit", "application/x-stuffit", "snd", "audio/basic",
			"spc", "application/x-pkcs7-certificates", "spl", "application/futuresplash", "src",
			"application/x-wais-source", "sst", "application/vnd.ms-pkicertstore", "stl", "application/vnd.ms-pkistl",
			"stm", "text/html", "sv4cpio", "application/x-sv4cpio", "sv4crc", "application/x-sv4crc", "t",
			"application/x-troff", "tar", "application/x-tar", "tcl", "application/x-tcl", "tex", "application/x-tex",
			"texi", "application/x-texinfo", "texinfo", "application/x-texinfo", "tgz", "application/x-compressed",
			"tif", "image/tiff", "tiff", "image/tiff", "tr", "application/x-troff", "trm", "application/x-msterminal",
			"tsv", "text/tab-separated-values", "txt", "text/plain", "uls", "text/iuls", "ustar",
			"application/x-ustar", "vcf", "text/x-vcard", "vrml", "x-world/x-vrml", "wav", "audio/x-wav", "wcm",
			"application/vnd.ms-works", "wdb", "application/vnd.ms-works", "wks", "application/vnd.ms-works", "wmf",
			"application/x-msmetafile", "wps", "application/vnd.ms-works", "wri", "application/x-mswrite", "wrl",
			"x-world/x-vrml", "wrz", "x-world/x-vrml", "xaf", "x-world/x-vrml", "xbm", "image/x-xbitmap", "xof",
			"x-world/x-vrml", "xpm", "image/x-xpixmap", "xwd", "image/x-xwindowdump", "z", "application/x-compress",
			"zip", "application/zip", "odt", "application/vnd.oasis.opendocument.text", "oth",
			"application/vnd.oasis.opendocument.text-web", "odm", "application/vnd.oasis.opendocument.text-master",
			"odg", "application/vnd.oasis.opendocument.graphics", "odp",
			"application/vnd.oasis.opendocument.presentation", "ods", "application/vnd.oasis.opendocument.spreadsheet",
			"odc", "application/vnd.oasis.opendocument.chart", "odf", "application/vnd.oasis.opendocument.formula",
			"odb", "application/vnd.oasis.opendocument.database", "odi", "application/vnd.oasis.opendocument.image",

			"doc", "application/msword", "dot", "application/msword", "docx",
			"application/vnd.openxmlformats-officedocument.wordprocessingml.document", "dotx",
			"application/vnd.openxmlformats-officedocument.wordprocessingml.template", "docm",
			"application/vnd.ms-word.document.macroEnabled.12", "dotm",
			"application/vnd.ms-word.template.macroEnabled.12", "xls", "application/vnd.ms-excel", "xlt",
			"application/vnd.ms-excel", "xla", "application/vnd.ms-excel", "xlsx",
			"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xltx",
			"application/vnd.openxmlformats-officedocument.spreadsheetml.template", "xlsm",
			"application/vnd.ms-excel.sheet.macroEnabled.12", "xltm",
			"application/vnd.ms-excel.template.macroEnabled.12", "xlam",
			"application/vnd.ms-excel.addin.macroEnabled.12", "xlsb",
			"application/vnd.ms-excel.sheet.binary.macroEnabled.12", "ppt", "application/vnd.ms-powerpoint", "pot",
			"application/vnd.ms-powerpoint", "pps", "application/vnd.ms-powerpoint", "ppa",
			"application/vnd.ms-powerpoint", "pptx",
			"application/vnd.openxmlformats-officedocument.presentationml.presentation", "potx",
			"application/vnd.openxmlformats-officedocument.presentationml.template", "ppsx",
			"application/vnd.openxmlformats-officedocument.presentationml.slideshow", "ppam",
			"application/vnd.ms-powerpoint.addin.macroEnabled.12", "pptm",
			"application/vnd.ms-powerpoint.presentation.macroEnabled.12", "potm",
			"application/vnd.ms-powerpoint.template.macroEnabled.12", "ppsm",
			"application/vnd.ms-powerpoint.slideshow.macroEnabled.12" };
}
