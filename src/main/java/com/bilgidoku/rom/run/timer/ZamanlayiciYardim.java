package com.bilgidoku.rom.run.timer;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public interface ZamanlayiciYardim {

	void exec(Runnable runnable);

	ScheduledFuture<?> scheduleAtFixedRate(Runnable runn, long i, long j, TimeUnit minutes);

}
