package com.bilgidoku.rom.gwt.client.util.com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONObject;


/**
 * Her uygulamanin bir frame'i bir de component manager'i bulunur
 * Bunlar @see RomEntryPoint icerisinde bulunurlar
 * 
 * Kimdir component?
 * CompFactory tarafindan yaratilirlar.
 * Her uygulamanin bir componentfactory listesi vardir @see {@link RomEntryPoint#factory())}
 * Her component @see Comp arayuzunu saglar, @see CompBase sinifindan turer
 * 
 *  
 *  @see FrameCom bunun dispatch metodunu cagirir 
 *  
 *  Hey komponent'in ilgilendigi mesaj cmd listesi vardir. ComponentManager bunlari alir.
 *  Bir mesaj geldiginde interest eden componentlere mesaji dispatch eder
 *  
 *  @see CompFactory 'nin bir adet @see Comp u olabiliyor
 *    
 *  
 *  @see CompInfo
 * 
 * 
 */
public class ComponentManager {

	private final Map<String, CompFactory> factories = new HashMap<>();
	private final List<CompFactory> factoriesByOrder = new ArrayList<>();

	private final Map<String, Comp> allComps = new HashMap<>();

	private final Map<String, List<Comp>> cmdInterestOfComps = new HashMap<>();
//	private final Map<String, List<Comp>> byNeed = new HashMap<>();

	private boolean dirty = true;

	public ComponentManager(ArrayList<CompFactory> list) {
		// list.sort(new Comparator<CompFactory>() {
		//
		// @Override
		// public int compare(CompFactory o1, CompFactory o2) {
		// int x=o1.info().weight;
		// int y=o2.info().weight;
		//
		// return (x < y) ? -1 : ((x == y) ? 0 : 1);
		// }
		// });
		for (CompFactory f : list) {
//			Sistem.outln("Factory:" + f.info().name);
			addFactory(f);
		}

	}

	public void start() {
		check();
	}

	public void addFactory(CompFactory f) {
		factories.put(f.info().name, f);
		// reverse list by multiplying with -1
		factoriesByOrder.add(f);
		dirty = true;
	}

	public void tick(int tick) {
		if (!dirty)
			return;
		check();
	}

	
	/**
	 * Component'ler ihtiyaca gore yaratilir
	 * 
	 * 
	 * 
	 */
	private void check() {
		dirty = false;
//		Sistem.outln("CM:=============Check Changes=====================");
		for (int i = 0; i < 20; i++) {

			List<Comp> toRemove = new ArrayList<Comp>();

			removeloop: for (CompFactory it : factoriesByOrder) {
				
				Comp comp = allComps.get(it.info().name);
				if (comp == null) {
					// Bu component zaten create edilmemis
					continue;
				}
				
				String[] depends = it.info().depends;
				// Bu komponent'in ihtiyaclari saglaniyor mu?
				// Tum ihtiyaclarin saglaniyor olmasi gerekiyor
				// Saglanmiyor 
				if (depends != null && depends.length > 0) {
					for (String need : depends) {
						if (need.charAt(0) == '+') {
							if (!allComps.containsKey(need)) {
								toRemove.add(comp);
								continue removeloop;
							}
						} else {
							if (!RomEntryPoint.com().containsKey(need)) {
								toRemove.add(comp);
								continue removeloop;
							}
						}
					}
				}

			}

			for (Comp comp : toRemove) {
				removeComp(comp);
			}

			List<CompFactory> toAdd = new ArrayList<CompFactory>();

			factoryloop: for (CompFactory it : factoriesByOrder) {
				// Sistem.outln("Check to add "+it.info().name);
				if (allComps.containsKey(it.info().name))
					continue;
				String[] depends = it.info().depends;

				if (depends != null && depends.length > 0) {
					for (String need : depends) {
						if (need.charAt(0) == '+') {
							if (!allComps.containsKey(need)) {
//								Sistem.outln("CM: Pass comp " + it.info().name + " do not need " + need);
								continue factoryloop;
							}
						} else {
							if (!RomEntryPoint.com().containsKey(need)) {
//								Sistem.outln("CM:  Pass comp " + it.info().name + " do not need " + need);
								continue factoryloop;
							}
						}
					}
				}
				Sistem.outln("ADD COMP " + it.info().name);
				toAdd.add(it);
			}
			for (CompFactory compFactory : toAdd) {
				addComp(compFactory.get());
			}

			Set<Comp> notified = new HashSet<>();
			for (Entry<String, String> it : RomEntryPoint.com().change.entrySet()) {
				List<Comp> comps = cmdInterestOfComps.get(it.getKey());
				if (comps != null)
					for (Comp comp : comps) {
//						Sistem.outln("CM: Data changed "+it.getKey()+" for comp:"+comp.compInfo().name);
						comp.dataChanged(it.getKey(), ClientUtil.fromNullStr(it.getValue()));
						notified.add(comp);
					}
			}
			for (Comp comp : notified) {
//				Sistem.outln("CM:  NOTIFY comp:"+comp.compInfo().name);
				comp.processNewState();
			}
			RomEntryPoint.com().change.clear();

			if (toRemove.size() == 0 && toAdd.size() == 0){
//				Sistem.outln("CM:___________end_____________");
				return;
			}

		}
		
//		Sistem.outln("CM:___________end_____________");
	}

	public void dispatch(JSONObject cjo) throws RunException {
		String cmd = cjo.getString("cmd");
		Sistem.outln("CM:==>> RUN CMD:"+cmd);
		List<Comp> hl = cmdInterestOfComps.get(cmd);
		if (hl == null)
			return;

		for (Comp commandHandler : hl) {
			commandHandler.handle(cmd, cjo);
		}
	}

	private void addComp(Comp h) {
		// Sistem.outln("Component added:"+h.compInfo().name);

		dirty = true;

		Sistem.outln("CM: Resolve comp:"+h.compInfo().name);
		h.resolve();
		allComps.put(h.compInfo().name, h);

		if (h.compInfo().cmds == null)
			return;
		for (String cmd : h.compInfo().cmds) {
			List<Comp> hl = cmdInterestOfComps.get(cmd);
			if (hl == null) {
				hl = new ArrayList<Comp>();
				cmdInterestOfComps.put(cmd, hl);
			}
			hl.add(h);
			if (cmd.charAt(0) == '*') {
				h.dataChanged(cmd, allComps.containsKey(cmd) ? "true" : null);
			} else {
				String val = RomEntryPoint.com().get(cmd);
				h.dataChanged(cmd, ClientUtil.fromNullStr(val));
			}
		}
		h.processNewState();
		for (Comp comp : allComps.values()) {
			comp.compAdded(h);
		}
	}

	public void removeComp(Comp h) {
		dirty = true;

		Sistem.outln("CM: Freeze comp:"+h.compInfo().name);
		h.freeze();
		allComps.remove(h.compInfo().name);

		if (h.compInfo().cmds == null)
			return;
		for (String cmd : h.compInfo().cmds) {
			List<Comp> hl = cmdInterestOfComps.get(cmd);
			if (hl == null) {
				return;
			}
			hl.remove(h);
			if (hl.size() == 0)
				cmdInterestOfComps.remove(cmd);
		}
		
		for (Comp comp : allComps.values()) {
			comp.compRemoved(h);
		}

	}


	public void setDirty() {
		this.dirty = true;
	}

	public Object comp(String name, String face) {
		Comp o = allComps.get(name);
		if (o == null)
			return null;
		return o.getInterface(face);
	}
	
	public List<String> askFeature(String key){
		List<String> ret=new ArrayList<String>();
		for (CompFactory f : factories.values()) {
			String r=f.info().askFeature(key);
			if(r==null)
				continue;
			ret.add(f.info().name);
			ret.add(r);
		}
		return ret;
	}
}
