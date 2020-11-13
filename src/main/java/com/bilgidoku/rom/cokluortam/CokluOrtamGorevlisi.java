package com.bilgidoku.rom.cokluortam;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.bilgidoku.rom.cokluortam.twod.ResimGorevlisi;
import com.bilgidoku.rom.gwt.shared.ImageResp;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.internetapi.RichWebApi;
import com.bilgidoku.rom.internetapi.api.SocialOne;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

public class CokluOrtamGorevlisi extends GorevliDir {

	public static final int NO=21;
	
	public static CokluOrtamGorevlisi tek(){
		if(tek==null) {
			synchronized (CokluOrtamGorevlisi.class) {
				if(tek==null) {
					tek=new CokluOrtamGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}
	
	static CokluOrtamGorevlisi tek;
	private CokluOrtamGorevlisi() {
		super("CokluOrtam", NO);
		dir=dataDir() + "/conts/";
	}
	
	private final String dir;
	
	

	public Img getMediaDb(int pr, String pid) throws KnownError {
		destur();
		
		try (DbThree db3 = new DbThree("select * from tepeweb.mediastore_byprovider(?,?)")) {
			db3.setInt(pr);
			db3.setString(pid);
			db3.executeQuery();
			if (!db3.next())
				return null;

			return new Img(db3);
		}
	}
	

	public Img getMedia(int pr, String pid) throws KnownError {
		destur();
		
		File p = imgDir(pr, pid);
		File jsonFile=jsonFile(p);
		try {
			return Img.fromJsonFile(jsonFile);
		} catch ( IOException e) {
			throw err(e);
		}
	}
	


	public boolean mediaExists(int pr, String pid) {
		File p = imgDir(pr, pid);
		File jsonFile=jsonFile(p);
		return jsonFile.exists();
	}


	public void addImage(Img img, File downloaded) throws KnownError {
		destur();
		
		try {
			File p = imgDir(img);
			if (p.exists())
				throw new RuntimeException("Image still exist while adding:" + img.pr + "-" + img.pid);

			p.mkdirs();
			//
			 File nw=getImgFile(p, img.format);
			 FileUtils.copyFile(downloaded, nw);
			 writeJson(p, img);

			// tepeweb.mediastore_new(p_pr int, p_pid text, p_tags text[], p_uses int, p_weight int, p_format text) 
//			imgToDb(img);
		} catch (IOException e) {
			throw err(e);
		}
	}

	private void imgToDb(Img img) throws KnownError {
		try (DbThree db3 = new DbThree("select * from tepeweb.mediastore_new(?,?,?,?,?,?)")) {
			img.set(db3);
			db3.execute();
		}
	}

	private File imgDir(Img img) {
		return imgDir(img.pr,img.pid);
	}

	private File prDir(int pr) {
		return new File(dir + pr);
	}
	private File imgDir(int pr, String pid) {
		return new File(dir + pr + "/" + pid);
	}
	
	private File getImgFile(File p, String format) throws IOException {
		return new File(p.getPath() + "/o." + format);
	}

	private void writeJson(File p, Img i) throws IOException {
		Gson gson = new GsonBuilder().create();
		File f = jsonFile(p);
		try (JsonWriter jw = new JsonWriter(new FileWriter(f, false))) {
			gson.toJson(i, Img.class, jw);
		}
	}

	private File jsonFile(File p) {
		return new File(p.getPath() + "/a.json");
	}


	public File sketchFile(Integer pr, String pid, String format) throws KnownError {
		destur();
		return new File(imgDir(pr, pid) + "/p."+format);
	}


	public File imageFile(Integer pr, String pid, String format, char size) throws KnownError{
			File f=new File(imgDir(pr, pid) + "/"+size+"."+format);
			if(size=='o' || f.exists())
				return f;
			
			File o=new File(imgDir(pr, pid) + "/o."+format);
			if(!o.exists())
				return o;
			
			if(format.equals("svg")){
				try {
					Files.createSymbolicLink(f.toPath(), o.toPath());
				} catch (IOException e) {
					throw new KnownError("Creating symbolic link failed while scaling/ f:"+f.getPath()+" orig:"+o.getPath());
				}
				return f;
			}
			
			ResimGorevlisi.tek().scale(o, f, size);
			return f;
		
	}


	public List<Img> listMedia(int pr) throws KnownError {
		File f = prDir(pr);
		List<Img> ret=new ArrayList<Img>();
		String[] pids=f.list();
		for (String pid : pids) {
			Img img=getMedia(pr, pid);
			ret.add(img);
		}
		return ret;
	}


	public RichWebApi local(Integer pr) {
		return new RichWebApiImpl(pr);
	}

	


	class RichWebApiImpl implements RichWebApi{
		Integer pr;
		RichWebApiImpl(int p){
			this.pr=p;
		}

		@Override
		public ImageResp[] searchImage(Integer p_limit, Integer p_offset, String p_phrase, String p_size,
				String p_aspect, String p_style, String p_colors, String p_face) throws KnownError {

			List<ImageResp> ret=new ArrayList<ImageResp>();
			
			int use=Integer.parseInt(p_phrase);
			List<Img> limg = CokluOrtamGorevlisi.this.listMedia(pr);
			for (Img img : limg) {
				if((use == img.uses)){
					ret.add(img.toResult());
				}
			}
			return ret.toArray(new ImageResp[ret.size()]);
		}
		@Override
		public ImageResp infoMedia(String p_id) {
			Img m;
			try {
				m = CokluOrtamGorevlisi.this.getMedia(pr, p_id);
				if(m==null)
					return null;
				return m.toResult();
			} catch (KnownError e) {
				Sistem.printStackTrace(e, "infomedia:"+pr+"-"+p_id);
				return null;
			}
		}
		@Override
		public void buyMedia(String p_id) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public SocialOne socialOneAccessToken(String accessToken) {
			return null;
		}
		
	}




	public void selfDescribe(JSONObject jo) {
		
	}

}
