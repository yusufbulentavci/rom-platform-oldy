package com.bilgidoku.rom.shared.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bilgidoku.rom.min.Sistem;

public abstract class StateMachine {
	
	protected abstract class T {
		private String[] usedIn;

		public T(String usedIn) {
			this.usedIn = usedIn.split("\\|");
		}

		public String[] usedIn() {
			return usedIn;
		}

		public abstract String to(String from, String event, Object... params);
	}

	protected class State {
		final String name;
		// event/transition
		Map<String, T> trans = new HashMap<String, T>();

		public State(String s) {
			this.name = s;
		}

		public void addTrans(String e, T transition) {
			trans.put(e, transition);
		}

		public T getTrans(String event) {
			return trans.get(event);
		}
	}

	protected final Set<String> stateNames = new HashSet<String>();
	protected final Set<String> eventNames = new HashSet<String>();

	protected final List<T> transitions = new ArrayList<T>();

	/**
	 * state:event->transition
	 */
	protected final Map<String, State> states = new HashMap<String, State>();

	protected State cur;
	private List<Runnable> queue = new LinkedList<Runnable>();
	private String error;
	private boolean running;
	private final String name;

	protected StateMachine(String name) {
		this.name=name;
		eventNames.add("close");
		eventNames.add("start");
		eventNames.add("error");

		stateNames.add("initial");
		stateNames.add("run");
		stateNames.add("closed");
	}

	protected void t(T t) {
		transitions.add(t);
	}

	protected void tDone() {

		for (T transition : transitions) {
			for (String usedIn : transition.usedIn()) {
				String[] parts = usedIn.split("/");
				if (!parts[0].equals("*")) {
					String[] ss = parts[0].split(",");
					for (String string : ss) {
						stateNames.add(string);
					}
				}

				if (!parts[1].equals("*")) {
					String[] es = parts[1].split(",");
					for (String string : es) {
						eventNames.add(string);
					}
				}

			}

		}

		for (T transition : transitions) {
			for (String usedIn : transition.usedIn()) {
				String[] snames = usedIn.split("/");
				List<String> ss = stateId(snames[0]);
				List<String> es = eventId(snames[1]);
				for (String s : ss) {
					State st = getState(s);
					for (String e : es) {
						st.addTrans(e, transition);
					}
				}

			}
		}
		this.cur = getState("initial");
	}

	private State getState(String s) {
		State state = states.get(s);
		if (state == null) {
			state = new State(s);
			states.put(s, state);
		}
		return state;
	}

	protected List<String> stateId(String string) {
		return resolve(stateNames, string);
	}

	protected List<String> eventId(String string) {
		return resolve(eventNames, string);
	}

	private List<String> resolve(Set<String> all, String string) {
		List<String> ret = new ArrayList<String>();
		if (string.equals("*")) {
			for (String string2 : all) {
				ret.add(string2);
			}
		} else {
			String[] ps = string.split(",");
			for (String string2 : ps) {
				ret.add(string2);
			}
		}
		return ret;
	}

	protected void event(final String event, final Object... params) {
		addEvent(new Runnable() {

			@Override
			public void run() {
				Sistem.outln(">>>>"+name+" >>>> " + event + " in " + cur.name);
				T t = cur.getTrans(event);
				if (t == null) {
					Sistem.errln("!!!!!!!!! Invalid event:" + event + " in state:" + cur.name);
					return;
				}
				String ret = t.to(cur.name, event, params);
				Sistem.outln("<<"+name+" << " + ret);
				cur = getState(ret);
			}

		});

		if (this.running)
			return;
		try {
			this.running = true;
			while (this.queue.size() > 0) {
				Runnable r = this.queue.remove(0);
				try {
					r.run();
				} catch (Exception e) {
					Sistem.printStackTrace(e);
				}
			}

		} finally {
			this.running = false;
		}

	}

	private void addEvent(Runnable runnable) {
		this.queue.add(runnable);
	}

	protected void err(String reason) {
		this.error = reason;
		if(reason!=null)
			Sistem.errln("!!!!!! "+name+" Error in state:"+cur.name+" Reason: "+reason);
		event("error");
	}

	protected boolean isInState(String state, String... strings) {
		for (String string : strings) {
			if (state.equals(string)) {
				return true;
			}
		}
		return false;
	}

}
