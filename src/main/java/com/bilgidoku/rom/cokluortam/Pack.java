package com.bilgidoku.rom.cokluortam;


public class Pack {
//
//	private static final String PACKS_DIR=ServiceDiscovery.getService(PropertyService.class).getString("mediastore.packsDir");
//	private final int packId;
//	private final String packDir;
//	private int nextId=0;
//	private final File pdf;
//	Gson gson = new GsonBuilder().create();
//	
//	
//	public Pack(int p){
//		this.packId=p;
//		packDir=PACKS_DIR+p+"/";
//		pdf=new File(packDir);
//		createNotExist();
//		findLastId();
//	}
//	
//	private void createNotExist() {
//		if(pdf.exists())
//			return;
//		pdf.mkdirs();
//	}
//
//	private void findLastId() {
//		String[] afs = pdf.list();
//		for (String s : afs) {
//			try{
//				int n = Integer.parseInt(s);
//				if(n>=nextId){
//					nextId=n+1;
//				}
//			}catch(NumberFormatException e){
//			}
//		}
//	}
//
//
//	public int addImage(Img i, File f) throws IOException{
//		i.pack=this.packId;
//		i.id=nextId++;
//		File p=idPath(i.id);
//		if(p.exists())
//			throw new RuntimeException("Pack Image still exist while adding:"+i.id);
//		
//		p.mkdir();
//
//		File nw=getImgFile(p, i.format);
//		FileUtils.copyFile(f, nw);
//		writeJson(p, i);
//		return i.id;
//	}
//
//	private File getImgFile(File p, String format) throws IOException {
//		return new File(p.getPath()+"/o."+format);
//	}
//
//	private void writeJson(File p, Img i) throws IOException {
//		File f=jsonFile(p);
//		try(JsonWriter jw=new JsonWriter(new FileWriter(f,false))){
//			gson.toJson(i, Img.class, jw);
//		}
//	}
//
//	private File jsonFile(File p) {
//		return new File(p.getPath()+"/a.json");
//	}
//
//	
//	private File idPath(int id) {
//		return new File(packDir+id);
//	}
//	
	
}
