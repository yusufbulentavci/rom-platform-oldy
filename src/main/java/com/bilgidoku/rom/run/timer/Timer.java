package com.bilgidoku.rom.run.timer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.run.KosuGorevlisi;

class GetTime {
	int min;
	int quaterHour;
	int halfHour;
	int hour;
	int twoHours;
	int sixHours;
	int twelveHours;
	int day;
	int mon;
	int year;

	public GetTime() {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(System.currentTimeMillis());
		min = gc.get(Calendar.MINUTE);
		quaterHour=15*((int)(min/15));
		halfHour=30*((int)(min/30));
		hour = gc.get(Calendar.HOUR);
		twoHours=2*((int)(hour/2));
		sixHours=6*((int)(hour/6));
		twelveHours=12*((int)(hour/12));
		day = gc.get(Calendar.DAY_OF_MONTH);
		mon = gc.get(Calendar.MONTH);
		year = gc.get(Calendar.YEAR);
	}
}

public class Timer implements Runnable {
	List<EveryMinute> waitMin = new ArrayList<EveryMinute>();
	List<EveryQuaterHour> waitQuaterHours = new ArrayList<EveryQuaterHour>();
	List<EveryHalfHour> waitHalfHours = new ArrayList<EveryHalfHour>();
	List<EveryHour> waitHour = new ArrayList<EveryHour>();
	List<EveryTwoHours> waitTwoHours = new ArrayList<EveryTwoHours>();
	List<EverySixHours> waitSixHours = new ArrayList<EverySixHours>();
	List<EveryTwelveHours> waitTwelveHours = new ArrayList<EveryTwelveHours>();
	List<EveryDay> waitDay = new ArrayList<EveryDay>();
	List<EveryMonth> waitMonth = new ArrayList<EveryMonth>();
	List<EveryYear> waitYear = new ArrayList<EveryYear>();

	final GetTime reported;
	
	final ZamanlayiciYardim yardim;

	public Timer(ZamanlayiciYardim yardim) {
		this.yardim=yardim;
		reported = new GetTime();
		yardim.scheduleAtFixedRate(this, 1, 1, TimeUnit.MINUTES);
	}

	public JSONObject describe() {
		JSONObject jo = new JSONObject();
		if (waitMin.size() > 0)
			jo.safePut("min", waitMin.size());
		if (waitHour.size() > 0)
			jo.safePut("hour", waitHour.size());
		if (waitDay.size() > 0)
			jo.safePut("day", waitDay.size());
		if (waitMonth.size() > 0)
			jo.safePut("mon", waitMonth.size());
		if (waitYear.size() > 0)
			jo.safePut("year", waitYear.size());

		return jo;
	}

	@Override
	public void run() {
		final GetTime now = new GetTime();

		if (now.year != reported.year) {
			reported.year = now.year;
			if (waitYear.size() > 0)
				yardim.exec(new Runnable() {
					@Override
					public void run() {
						for (EveryYear it : waitYear) {
							try {
								it.everyYear(now.year);
							} catch (Throwable t) {
								Sistem.printStackTrace(t, "Timer every-year");
							}
						}
					}
				});
		}

		if (now.mon != reported.mon) {
			reported.mon = now.mon;
			if (waitMonth.size() > 0)
				yardim.exec(new Runnable() {
					@Override
					public void run() {
						for (EveryMonth it : waitMonth) {
							try {
								it.everyMonth(now.year, now.mon);
							} catch (Throwable t) {
								Sistem.printStackTrace(t, "Timer every-month");
							}
						}
					}
				});
		}

		if (now.day != reported.day) {
			reported.day = now.day;
			if (waitDay.size() > 0)
				yardim.exec(new Runnable() {
					@Override
					public void run() {
						for (EveryDay it : waitDay) {
							try {
								it.everyDay(now.year, now.mon, now.day);
							} catch (Throwable t) {
								Sistem.printStackTrace(t, "Timer every-day");
							}
						}
					}
				});
		}

		
		if (now.twelveHours != reported.twelveHours) {
			reported.twelveHours = now.twelveHours;
			if (waitTwelveHours.size() > 0)
				yardim.exec(new Runnable() {
					@Override
					public void run() {

						for (EveryTwelveHours it : waitTwelveHours) {
							try {
								it.everyTwelveHours(now.year, now.mon, now.day,
										now.hour);
							} catch (Throwable t) {
								Sistem.printStackTrace(t, "Timer every-twelve hour");
							}
						}
					}
				});
		}
		
		if (now.sixHours != reported.sixHours) {
			reported.sixHours = now.sixHours;
			if (waitSixHours.size() > 0)
				yardim.exec(new Runnable() {
					@Override
					public void run() {

						for (EverySixHours it : waitSixHours) {
							try {
								it.everySixHours(now.year, now.mon, now.day,
										now.hour);
							} catch (Throwable t) {
								Sistem.printStackTrace(t, "Timer every-six hour");
							}
						}
					}
				});
		}
		
		if (now.twoHours != reported.twoHours) {
			reported.twoHours = now.twoHours;
			if (waitTwoHours.size() > 0)
				yardim.exec(new Runnable() {
					@Override
					public void run() {

						for (EveryTwoHours it : waitTwoHours) {
							try {
								it.everyTwoHours(now.year, now.mon, now.day,
										now.hour);
							} catch (Throwable t) {
								Sistem.printStackTrace(t, "Timer every-two hour");
							}
						}
					}
				});
		}
		
		if (now.hour != reported.hour) {
			reported.hour = now.hour;
			if (waitHour.size() > 0)
				yardim.exec(new Runnable() {
					@Override
					public void run() {

						for (EveryHour it : waitHour) {
							try {
								it.everyHour(now.year, now.mon, now.day,
										now.hour);
							} catch (Throwable t) {
								Sistem.printStackTrace(t, "Timer every-hour");
							}
						}
					}
				});
		}

		if (now.halfHour != reported.halfHour) {
			reported.halfHour = now.halfHour;
			if (waitHalfHours.size() > 0)
				yardim.exec(new Runnable() {
					@Override
					public void run() {

						for (EveryHalfHour it : waitHalfHours) {
							try {
								it.everyHalfHour(now.year, now.mon, now.day,
										now.hour, now.min);
							} catch (Throwable t) {
								Sistem.printStackTrace(t, "Timer every-half hour");
							}
						}
					}
				});
		}
		
		if (now.quaterHour != reported.quaterHour) {
			reported.quaterHour = now.quaterHour;
			if (waitQuaterHours.size() > 0)
				yardim.exec(new Runnable() {
					@Override
					public void run() {

						for (EveryQuaterHour it : waitQuaterHours) {
							try {
								it.everyQuaterHour(now.year, now.mon, now.day,
										now.hour, now.min);
							} catch (Throwable t) {
								Sistem.printStackTrace(t, "Timer every-quater hour");
							}
						}
					}
				});
		}
		

		if (now.min != reported.min) {
			reported.min = now.min;
			if (waitMin.size() > 0)
				yardim.exec(new Runnable() {
					@Override
					public void run() {
						synchronized (waitMin) {
							for (EveryMinute it : waitMin) {
								try {
									it.everyMinute(now.year, now.mon, now.day,
											now.hour, now.min);
								} catch (Throwable t) {
									Sistem.printStackTrace(t, "Timer every-min");
								}
							}
						}
					}
				});
		}

	}

	public void waitMin(EveryMinute e) {
		waitMin.add(e);
	}

	public void waitHour(EveryHour e) {
		waitHour.add(e);
	}

	public void waitDay(EveryDay e) {
		waitDay.add(e);
	}

	public void waitMonth(EveryMonth e) {
		waitMonth.add(e);
	}

	public void waitYear(EveryYear e) {
		waitYear.add(e);
	}

	public void waitQuaterHour(EveryQuaterHour e) {
		waitQuaterHours.add(e);
	}

	public void waitHalfHour(EveryHalfHour e) {
		waitHalfHours.add(e);
	}

	public void waitTwoHours(EveryTwoHours e) {
		waitTwoHours.add(e);
	}

	public void waitSixHours(EverySixHours e) {
		waitSixHours.add(e);
	}

	public void waitTwelveHours(EveryTwelveHours e) {
		waitTwelveHours.add(e);
	}
	
	public void waitMinRemove(EveryMinute waiting) {
		synchronized (waitMin) {
			waitMin.remove(waiting);
		}
	}
}
