package com.bilgidoku.rom.web.maintain;

import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.run.timer.EveryDay;
import com.bilgidoku.rom.run.timer.EveryHour;
import com.bilgidoku.rom.run.timer.EveryMonth;
import com.bilgidoku.rom.run.timer.EveryYear;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.web.maintain.dbop.CronDailyDbOp;
import com.bilgidoku.rom.web.maintain.dbop.CronHourlyDbOp;
import com.bilgidoku.rom.web.maintain.dbop.CronMonthlyDbOp;
import com.bilgidoku.rom.web.maintain.dbop.CronYearlyDbOp;

public class BakimGorevlisi extends GorevliDir implements EveryHour, EveryDay, EveryMonth, EveryYear {
	public static final int NO = 47;

	public static BakimGorevlisi tek() {
		if (tek == null) {
			synchronized (BakimGorevlisi.class) {
				if (tek == null) {
					tek = new BakimGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static BakimGorevlisi tek;

	private BakimGorevlisi() {
		super("Bakim", NO);
	}

	int tickHour = 0, tickDay = 0, tickMonth = 0, tickYear = 0;


	@Override
	public void selfDescribe(JSONObject jo) {
		jo.safePut("tickHour", tickHour).safePut("tickDay", tickDay).safePut("tickMonth", tickMonth).safePut("tickYear",
				tickYear);
	}

	@Override
	public void everyMonth(int year, int month) {
		try {
			tickMonth++;
			new CronMonthlyDbOp().doIt(year, month);
		} catch (KnownError e) {
			Sistem.printStackTrace(e, "Cron monthly");
		}
	}

	@Override
	public void everyDay(int year, int month, int day) {
		try {
			tickDay++;
			new CronDailyDbOp().doIt(year, month, day);
		} catch (KnownError e) {
			Sistem.printStackTrace(e, "Cron daily");
		}
	}


	@Override
	public void everyHour(int year, int month, int day, int hour) {
		try {
			tickHour++;
			new CronHourlyDbOp().doIt(year, month, day, hour);
		} catch (KnownError e) {
			Sistem.printStackTrace(e, "Cron hourly");
		}
	}

	@Override
	public void everyYear(int year) {
		try {
			tickYear++;
			new CronYearlyDbOp().doIt(year);
		} catch (KnownError e) {
			Sistem.printStackTrace(e, "Cron yearly");
		}
	}

}
