package com.bilgidoku.rom.site.kamu.tutor.client;

public class Leveler {
	private final int optionCount;
	private final Level[] levels;
	private int confLevel = 0;

	public Leveler(int optionCount, Level[] levels) {
		this.optionCount = optionCount;
		this.levels = levels;
	}

	public void toDefault() {
		setLevel(0);
	}

	void setLevel(int i) {
		confLevel = i;
	}

	public short getLow() {
		return levels[confLevel].lowerBound;
	}

	public int getExcluded() {
		return levels[confLevel].excludedSize;
	}

	public int getUp() {
		return levels[confLevel].getUp(optionCount);
	}

	public int getOptionLength() {
		return optionCount;
	}

	public String getName(int i) {
		return levels[i].name;
	}

	public int getLevel() {
		return confLevel;
	}

//	public int random(int last) {
//		for (int i = 0; i < 10; i++) {
//			Level l = levels[confLevel];
//			int x = (int) (Math.random() * (optionCount - l.excludedSize)) + l.lowerBound;
//
//			Sistem.outln("LevelId:" + confLevel + "slen:" + optionCount + " exc:" + l.excludedSize + " limitLowe:"
//					+ l.lowerBound + " result:" + x);
//			if(x==last)
//				continue;
//			
//			return x;
//		}
//		return 0;
//	}

	public int levelLength() {
		return levels.length;
	}

	public void fill(Circle circle, int[] scores) {
		
//		Sistem.outln("Scores in fill"+Arrays.toString(scores));
//		
//		Sistem.outln("Circle fill before:"+circle.toString());
		
		if (circle.getNumberOfElements() >= 5)
			return;
		
		int toAdd = 5-circle.getNumberOfElements();
		
		for(int j=2; j<100; j++){
			for (short i = getLow(); i < getUp(); i++) {
				if (scores[i] >= j)
					continue;
				if(circle.contains(i))
					continue;
				circle.insertBeforeActual(i);
				if (circle.getNumberOfElements() >= 5)
					break;
			}
			if (circle.getNumberOfElements() >= 5)
				break;
		}
		
		if(circle.getNumberOfElements()<5){
			throw new RuntimeException("Empty circle");
		}
		
//		Sistem.outln("Circle fill before random:"+circle.toString());
//		Sistem.outln("Circle toAdd:"+toAdd);
//		if(toAdd>1)
//			circle.randomize();
		
//		Sistem.outln("Circle after fill:"+circle.toString());
		
	}

}
