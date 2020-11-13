package com.bilgidoku.rom.site.yerel;

public interface SessionFeedback {
	void noSession();
	void hasSession(String userName);
	void loginFailed();
	void avoidAttack();
	void loggedIn(String userName);
	void logoutFailed();
	void loggedOut();
}
