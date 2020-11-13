package com.bilgidoku.rom.site.kamu.pub.client;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.shared.GwtEvent;

class Ok extends NativeEvent{
	
}

public class TestEvent extends GwtEvent<TestEventHandler> {
	public TestEvent() {
		super();
	}

	public static Type<TestEventHandler> TYPE = new Type<TestEventHandler>();

	public com.google.gwt.event.shared.GwtEvent.Type<TestEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(TestEventHandler handler) {
		handler.test(this);		
	}

}
