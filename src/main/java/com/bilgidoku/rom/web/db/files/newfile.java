package com.bilgidoku.rom.web.db.files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.kurum.KurumDosyaGorevlisi;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.pg.dict.BeforeHook;
import com.bilgidoku.rom.pg.dict.HookScope;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.NotInlineMethodException;
import com.bilgidoku.rom.shared.err.ParameterError;

import io.netty.handler.codec.http.multipart.FileUpload;

public class newfile extends BeforeHook {
	private static final MC mc = new MC(newfile.class);

	private static final Astate uc = mc.c("undo");
	private static final Astate ue = mc.c("undo-failed");
	
	

	@Override
	public void undo(HookScope scope) throws KnownError, ParameterError {
		uc.more();
		String downloadedUri;
		downloadedUri = scope.getParam("uri", 1, 400, true);
		if (downloadedUri == null)
			return;
		KurumDosyaGorevlisi.tek().deleteFile(scope.getHostId(), downloadedUri, true);

	}

	private static final Astate doc = mc.c("do");
	private static final Astate doe = mc.c("do-failed");
	private static final Astate upc = mc.c("upload");
	private static final Astate upe = mc.c("upload-failed");

	@Override
	public boolean hook(HookScope scope) throws KnownError, KnownError, NotInlineMethodException, ParameterError {
		doc.more();
		
		
		String tc = scope.request.getParam("textcontent", 2, 5000000, false);
		if(tc!=null){
			String fileName = scope.request.getParam("filename", 5, 100, true);
			fileName=KurumDosyaGorevlisi.tek().nameFix(fileName);
			
			String fileUri = scope.getUri() + "/" + fileName;
			scope.paramOverride("uri", fileUri);
			
			String realPath=KurumDosyaGorevlisi.tek().realPath(scope.getHostId(), fileUri);
			KurumDosyaGorevlisi.tek().realFileChanged(realPath);
			try(FileWriter fw=new FileWriter(realPath)){
				fw.write(tc);
			} catch (IOException e) {
				throw new KnownError("Cant create text content file, path:"+realPath, e).badRequest();
			}finally{
				
			}
			
			return true;
		}
		

		String du = scope.request.getParam("download_uri", 5, 300, false);
		if (du != null) {
			int li = du.lastIndexOf('/');
			if (li <= 0) {
				doe.more();
				throw new KnownError("Bad syntax:" + du).badRequest();
			}

			String fileName = du.substring(li + 1, du.length());
			fileName = KurumDosyaGorevlisi.tek().nameFix(fileName);

			String fileUri = scope.getUri() + "/" + fileName;
			scope.paramOverride("uri", fileUri);
			
			String realPath = KurumDosyaGorevlisi.tek().realPath(scope.getHostId(), fileUri);
			
			if (!Downloader.download(du, realPath))
				throw new KnownError().notFound(fileUri);
			
			KurumDosyaGorevlisi.tek().realFileChanged(realPath);
			
			return true;
		}

		upc.more();
		
		List<FileUpload> items = scope.request.getFilesParam("fn", true);
		String[] fileUris=new String[items.size()];
		
		for(int i=0; i<items.size(); i++){
			FileUpload item = items.get(i);
			String fixedName = KurumDosyaGorevlisi.tek().nameFix(item.getFilename());
			String fileUri = scope.getUri() + "/" + fixedName;
			String realPath = KurumDosyaGorevlisi.tek().realPath(scope.getHostId(), fileUri);
			KurumDosyaGorevlisi.tek().realFileChanged(realPath);
			fileUris[i]=fileUri;
//			Sistem.errln("realpath:" + realPath);
//			Sistem.errln("fileUri:" + fileUri);
			try {
				item.renameTo(new File(realPath));
			} catch (IOException e) {
				upe.more(realPath);
				throw new KnownError(item.getFilename()+"->"+realPath, e).badRequest();
			}
		}
		
		
		
		
		
		
//		FileUpload item = scope.request.getFileParam("fn", true);

		if(fileUris.length==1){
			scope.paramOverride("uri", fileUris[0]);
		}else{
			
			JSONArray arr;
			try {
				arr = new JSONArray(fileUris);
				scope.paramOverride("mulfn", arr.toString());
			} catch (JSONException e) {
				Sistem.printStackTrace(e);
			}
			
		}
		

		//
		// String du = getDownloadUri(scope);
		// if (du != null) {
		// int li = du.lastIndexOf('/');
		// if (li <= 0) {
		// throw new KnownError(false, "BAD_REQUEST",
		// "download_uri parameter has no file:" + du);
		// }
		//
		// String fileName = du.substring(li + 1, du.length());
		// fileName=KurumDosyaGorevlisi.tek().nameFix(fileName);
		//
		// String fileUri=scope.getUri() + "/"+ fileName;
		// scope.addParam("uri", fileUri);
		//
		// return download(scope, du,
		// KurumDosyaGorevlisi.tek().realPath(scope.getHostId(),fileUri));
		// }
		//
		// // Create a factory for disk-based file items
		// DiskFileItemFactory factory = new DiskFileItemFactory();
		//
		// // Set factory constraints
		// factory.setSizeThreshold(30 * 1024 * 1024);
		//
		// factory.setRepository(new File(serverEnvo.getTmpFolder()));
		//
		// // Create a new file upload handler
		// ServletFileUpload upload = new ServletFileUpload(factory);
		//
		// // Set overall request size constraint
		// upload.setSizeMax(1 * 1024 * 1024);
		//
		// // Parse the request
		// List<FileItem> items;
		// try {
		// items = upload.parseRequest(scope.request);
		// Iterator<FileItem> itr = items.iterator();
		// while (itr.hasNext()) {
		// FileItem item = (FileItem) itr.next();
		// if (!item.isFormField()
		// && item.getFieldName().equals("file_name")) {
		//
		// String fixedName = KurumDosyaGorevlisi.tek().nameFix(item.getName());
		// String fileUri = scope.getUri() + "/" + fixedName;
		// String realPath = KurumDosyaGorevlisi.tek().realPath(scope.getHostId(), fileUri);
		// writeFile(scope, realPath,
		// item);
		// scope.addParam("uri", fileUri);
		// break;
		// }
		// }
		// } catch (FileUploadException e) {
		// // TODO Auto-generated catch block
		// Sistem.printStackTrace(e);
		// }

		return true;
	}

	// private void writeFile(HookScope scope, String filePath, FileItem
	// uploadFile) {
	// File nf = new File(filePath);
	// try {
	// boolean e = nf.exists();
	// uploadFile.write(nf);
	// log.debug("Added new file:" + filePath);
	// } catch (Exception e) {
	// throw new KnownError(false, "FILE_WRITE_FAILED", e.getMessage());
	// }
	//
	// }
	//
	// private String getDownloadUri(HookScope scope){
	// return scope.outUriParam("download_uri", false);
	// }
}
