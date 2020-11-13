package com.bilgidoku.rom.gwt.client.util.chat.im.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.gwt.client.common.resp.ArrayResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Contacts;
import com.bilgidoku.rom.gwt.araci.client.rom.ContactsResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Dialogs;
import com.bilgidoku.rom.gwt.araci.client.rom.DialogsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.DialogsResponse;
import com.bilgidoku.rom.gwt.araci.client.service.OturumIciCagriDao;
import com.bilgidoku.rom.gwt.araci.client.tepeweb.InitialsDao;
import com.bilgidoku.rom.gwt.client.util.chat.IceCallRt;
import com.bilgidoku.rom.gwt.client.util.chat.RtMsgContactsProcessor;
import com.bilgidoku.rom.gwt.client.util.chat.RtMsgProcessor;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.util.AsyncMethod;
import com.bilgidoku.rom.shared.util.Presence;

public class ContactRepo {

	// private final List<CbContacts cb;
	private String myCid;
	private final Map<String, ContactBlaImpl> contacts = new HashMap<>();
	private List<CbContacts> cbs = new ArrayList<>();

	public ContactRepo(String myCid, CbContacts... cbs) {
		this.myCid=myCid;
		for (CbContacts c : cbs) {
			this.cbs.add(c);
		}
	}

	public void fillOnlines() {
		OturumIciCagriDao.rtonlines(new ArrayResponse<String>() {
			@Override
			public void ready(final String[] listOnlines) {
				contacts.clear();
				for (CbContacts c : cbs) {
					c.resetList();
				}
				
				

				for (int i = 0; i < listOnlines.length; i = i + 2) {
					final String cid = listOnlines[i];
					
					final String online = (String) listOnlines[i + 1];

					get(cid, new AsyncMethod<ContactBla, String>() {

						@Override
						public void run(ContactBla param) {
							param.setPresence(parseCodedPresence(online));
							for (CbContacts c : cbs) {
								c.presenceChanged(param);
							}
						}

						@Override
						public void error(String param) {

						}
					});
				}

			}

		});
	}
	
	public void fillDialog(String dlgUri) {
		Sistem.outln("Filling dialog");
		DialogsDao.get(dlgUri, new DialogsResponse() {

			@Override
			public void ready(Dialogs value) {
				Sistem.outln("Filling dialog ready:"+value.contacts.length);
				for (int i = 0; i < value.contacts.length; i = i + 2) {
					final String cid = value.contacts[i];
					Sistem.outln("Cid:"+cid);
					get(cid, new AsyncMethod<ContactBla, String>() {

						@Override
						public void run(ContactBla param) {
							for (CbContacts c : cbs) {
								c.dlgJoin(param, cid.equals(myCid));
							}
						}

						@Override
						public void error(String param) {

						}
					});
				}

			}

			@Override
			public void err(int statusCode, String statusText, Throwable exception) {
				super.err(statusCode, statusText, exception);
			}

		});
	}

	private Object[] parseCodedPresence(String online) {
		String[] parts = online.split("-");
		Object[] ret;
		if (parts.length != 2) {
			ret = new Object[] { Presence.FREE, Presence.StrFree };
		} else
			ret = new Object[] { Integer.parseInt(parts[0]), parts[1] };
		return ret;
	}

	public void get(final String from, final AsyncMethod<ContactBla, String> future) {
//		String mycid=RomEntryPoint.com().get("cid");
//		if(mycid.equals(from))
//			return;
		
		final ContactBla bla = getContact(from);
		if (bla != null) {
			if (future != null)
				future.run(bla);
			return;
		}
		InitialsDao.getcontact(from, "/_/_initials", new ContactsResponse() {
			@Override
			public void ready(final Contacts value) {
				ContactBlaImpl cba = new ContactBlaImpl(value);
				Sistem.outln(this.toString()+"CONTACT ADDED:"+cba.id+" "+from);
				contacts.put(from, cba);
				for (CbContacts c : cbs) {
					c.contactAdded(cba);
				}
				if (future != null)
					future.run(cba);

			}
		});
	}

	private ContactBla getContact(String contactId) {
		return contacts.get(contactId);
	}

	private static int serial=0;
	class ContactBlaImpl implements ContactBla {
		int id=serial++;
		
		public final Contacts contact;
		private String presence;
		List<String> chatHistory;
		private CallWrap call;
		private int presenceCode;
		public String imgUrl;

		private long busy;

		private String dlgPresence="online";

		ContactBlaImpl(Contacts contact) {
			this.contact = contact;
			Sistem.outln("CREATED:"+id);
		}

		public void setPresence(Object[] os) {
			setPresence((Integer) os[0], (String) os[1]);
		}

		public void setPresence(int code, String online) {
			this.presenceCode = code;
			this.presence = online;
			if (call != null)
				call.setPresence(code, online);
		}

		public String getPresenceImg() {
			return Presence.img(presenceCode);
		}

		public void addMsg(String html) {
			if (chatHistory == null)
				chatHistory = new ArrayList<String>();
			chatHistory.add(html);

		}

		public List<String> getChatHistory() {
			return chatHistory;
		}

		public CallWrap getCall() {
			if (call == null)
				call = new IceCallRt(contact.first_name, contact.uri);
			return call;
		}

		@Override
		public String contactUri() {
			return contact.uri;
		}

		@Override
		public String getContactName() {
			return ClientUtil.getName(contact);
		}

		@Override
		public String getImgUrl() {
			return imgUrl;
		}

		@Override
		public void setImgUrl(String dataUrl) {
			imgUrl = dataUrl;
		}

		@Override
		public void setBusy() {
			this.busy=System.currentTimeMillis();
		}

		@Override
		public boolean isBusy(long l) {
			return (l-this.busy)<3000;
		}

		@Override
		public void setDlgPresence(String text) {
			dlgPresence=text;
		}

		@Override
		public String getDlgPresence() {
			return dlgPresence;
		}

	}

	RtMsgContactsProcessor msgProc = new RtMsgContactsProcessor() {

		@Override
		public void xmmsPresence(final String from, final int code, final String presence) {

			if (!Presence.can(code, Presence.Visible)) {
				Sistem.outln("CONTACT REMOVE:"+from);
//				ContactBla rem = contacts.remove(from);
//				if (rem != null) {
//					for (CbContacts c : cbs) {
//						c.contactRemove(rem);
//					}
//				}
				return;
			}

			get(from, new AsyncMethod<ContactBla, String>() {

				@Override
				public void run(ContactBla param) {
					param.setPresence(code, presence);
					for (CbContacts c : cbs) {
						c.presenceChanged(param);
					}
				}

				@Override
				public void error(String param) {
				}
			});

		}

		@Override
		public void dlgJoin(final String from, final boolean mine) {
			get(from, new AsyncMethod<ContactBla, String>() {

				@Override
				public void run(ContactBla param) {
					for (CbContacts c : cbs) {
						c.dlgJoin(param, mine);
					}
				}

				@Override
				public void error(String param) {
					
				}
				
			});

		}

		@Override
		public void dlgSay(final String from, final boolean mine, final String text) {
			get(from, new AsyncMethod<ContactBla, String>() {

				@Override
				public void run(ContactBla param) {
					param.setBusy();
					for (CbContacts c : cbs) {
						c.dlgSay(param, mine, text);
					}
				}

				@Override
				public void error(String param) {
					
				}
				
			});
			
		}

		@Override
		public void dlgPhoto(final String from, final boolean mine, final String dataUrl) {
			get(from, new AsyncMethod<ContactBla, String>() {

				@Override
				public void run(ContactBla param) {
					param.setImgUrl(dataUrl);
					for (CbContacts c : cbs) {
						c.dlgContactPhotoChanged(param, mine);
					}
				}

				@Override
				public void error(String param) {
					
				}
				
			});
			
		}

		@Override
		public void dlgVideo(String from, final boolean mine, final String src, final String docmd) {
			get(from, new AsyncMethod<ContactBla, String>() {

				@Override
				public void run(ContactBla param) {
					param.setBusy();
					for (CbContacts c : cbs) {
						c.dlgVideo(param, mine, src, docmd);
					}
				}

				@Override
				public void error(String param) {
					
				}
				
			});	
		}

		@Override
		public void dlgTalking(String from, boolean mine) {
			get(from, new AsyncMethod<ContactBla, String>() {

				@Override
				public void run(ContactBla param) {
					param.setBusy();
				}

				@Override
				public void error(String param) {
					
				}
				
			});	
			
		}

		@Override
		public void dlgTvImg(final String from, final boolean mine, final String src) {
			
			get(from, new AsyncMethod<ContactBla, String>() {

				@Override
				public void run(ContactBla param) {
					param.setBusy();
					for (CbContacts c : cbs) {
						c.dlgTvImg(param, mine, src);
					}
				}

				@Override
				public void error(String param) {
					
				}
				
			});	
		}

		@Override
		public void dlgTvVideo(final String from, final boolean mine, final String src) {
			get(from, new AsyncMethod<ContactBla, String>() {

				@Override
				public void run(ContactBla param) {
					param.setBusy();
					for (CbContacts c : cbs) {
						c.dlgTvVideo(param, mine, src);
					}
				}

				@Override
				public void error(String param) {
					
				}
				
			});	
		}

		@Override
		public void dlgTvMark(final String from, final boolean mine, final int markx, final int marky) {
			get(from, new AsyncMethod<ContactBla, String>() {

				@Override
				public void run(ContactBla param) {
					param.setBusy();
					for (CbContacts c : cbs) {
						c.dlgTvMark(param, mine, markx, marky);
					}
				}

				@Override
				public void error(String param) {
					
				}
				
			});
		}

		@Override
		public void dlgTvVideoCtrl(final String from, final boolean mine, final Integer secs, final String ctrl) {
			get(from, new AsyncMethod<ContactBla, String>() {

				@Override
				public void run(ContactBla param) {
					param.setBusy();
					for (CbContacts c : cbs) {
						c.dlgTvVideoCtrl(param, mine, secs, ctrl);
					}
				}

				@Override
				public void error(String param) {
					
				}
				
			});	
		}
		
		@Override
		public void dlgTvText(final String from, final boolean mine, final String str) {
			get(from, new AsyncMethod<ContactBla, String>() {

				@Override
				public void run(ContactBla param) {
					param.setBusy();
					for (CbContacts c : cbs) {
						c.dlgTvText(param, mine, str);
					}
				}

				@Override
				public void error(String param) {
					
				}
				
			});	
		}
		
		@Override
		public void dlgTvHeader(final String from, final boolean mine, final String str) {
			get(from, new AsyncMethod<ContactBla, String>() {

				@Override
				public void run(ContactBla param) {
					param.setBusy();
					for (CbContacts c : cbs) {
						c.dlgTvHeader(param, mine, str);
					}
				}

				@Override
				public void error(String param) {
					
				}
				
			});	
		}

		
		@Override
		public void dlgTvShow(final String from, final boolean mine, final char str) {
			get(from, new AsyncMethod<ContactBla, String>() {

				@Override
				public void run(ContactBla param) {
					param.setBusy();
					for (CbContacts c : cbs) {
						c.dlgTvShow(param, mine, str);
					}
				}

				@Override
				public void error(String param) {
					
				}
				
			});	
		}

		@Override
		public void dlgPresence(final String from, final boolean mine, final String text) {
			get(from, new AsyncMethod<ContactBla, String>() {

				@Override
				public void run(ContactBla param) {
					param.setDlgPresence(text);
					for (CbContacts c : cbs) {
						c.dlgTvPresence(param, mine, text);
					}
				}

				@Override
				public void error(String param) {
					
				}
				
			});	
		}

	};


	public RtMsgProcessor getDialogMsgProcessor(String from) {
		CallWrap c = getDialog(from);
		if (c == null)
			return null;

		return c.getMsgProcessor();
	}

	CallWrap getDialog(String from) {
		ContactBla bla = getBla(from);
		if (bla == null)
			return null;

		return bla.getCall();
	}

	public ContactBla getBla(String uri) {
		return contacts.get(uri);
	}

	public RtMsgContactsProcessor getMsgProc() {
		return msgProc;
	}

	public void addCb(CbContacts cb) {
		cbs.add(cb);
	}

	public void setMyCid(String myCid) {
		this.myCid=myCid;
	}

}
