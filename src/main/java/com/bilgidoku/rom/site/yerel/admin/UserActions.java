package com.bilgidoku.rom.site.yerel.admin;


public class UserActions {
//	private NavUsers nav;
//
//	public UserActions(NavUsers navWriting) {
//		this.nav = navWriting;
//	}
//
//	public void getUsers() {
//		InitialsDao.getusers("/_/_initials", new ArrayResponse<String>() {
//			@Override
//			public void array(List<String[]> myarr) {
//				for (int i = 0; i < myarr.size(); i++) {
//					addUser(myarr.get(i));
//				}
//			}
//		});
//	}
//
//	private void addUser(String[] arr) {
//		//0 => username, 1=>contact_id, 2 and more=> roles
//		nav.addUser(arr[0], arr[1], Integer.parseInt(arr[2]));		
//	}
//	
//	public void addUser(String userName, String contactId, int roles) {
//		SiteTreeItem node = new SiteTreeItem(userName, img.user(), null, true, "user", "uri");
//		node.setUserObject(new User(userName, contactId, roles));
//		tree.addItem(node);
//		node.addDoubleClickHandler(new DoubleClickHandler() {
//			@Override
//			public void onDoubleClick(DoubleClickEvent event) {
//				wa.openLeaf();
//			}
//		});
//		node.addTreeItemEditHandler(new TreeItemEditHandler() {
//			@Override
//			public void editTreeItem(TreeItemEdit event) {
//				wa.openLeaf();
//			}
//		});
//	}
//
//
//	public void removeLeaf() {
//		final TreeItem toDel = nav.getSelectedItem();
//		if (toDel == null || toDel.getParentItem() != null)
//			return;
//		boolean res = Window.confirm(Ctrl.trans.confirmDelete());
//		if (!res)
//			return;
//		String userName = toDel.getText();
//
//		InitialsDao.removeuser(userName, "/_/_initials", new StringResponse() {
//			@Override
//			public void ready(String value) {
//				toDel.remove();
//			}
//		});
//
//	}
//
//	public void openLeaf() {
//		TreeItem selItem = nav.getSelectedItem();
//		if (selItem == null) {
//			Window.alert("Select app to edit");
//			return;
//		}
//		User u = (User)selItem.getUserObject();
//		openUser(u);
//	}
//
//	private void openUser(User u) {
//		TabUser tw = new TabUser(u);
//		Ctrl.openTab(u.getUserName(), u.getUserName(), tw, "/_local/images/common/user(), Data.ADMIN_COLOR);
//	}
//
//	public void newLeaf() {
//		final String sUser = Window.prompt("Enter User Name", "");
//		if (sUser != null && !sUser.isEmpty()) {
//			InitialsDao.adduser(Ctrl.info().langcodes[0],sUser, Data.INITIAL_PASSWORD, "", "", "", "", "/_/_initials", new StringResponse() {
//				@Override
//				public void ready(String value) {
//					//contact_id bilgisi donuyor					
//					if (nav.getUserCount() <= 0) {
//						//ilk kullanıcı ise admin
//						nav.addUser(sUser, value, 1<<Data.ADMINROLE);
//					}
//					else
//						nav.addUser(sUser, value, 0);
//				}				
//			});
//		}
//	}
//
//	public void addUsers() {
//		nav.addUsers();
//	}
}
