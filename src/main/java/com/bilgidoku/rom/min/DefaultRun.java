package com.bilgidoku.rom.min;

public class DefaultRun implements IRun {
	
	@Override
	public void runInWorker(Runnable work) {
		work.run();
	}

}
