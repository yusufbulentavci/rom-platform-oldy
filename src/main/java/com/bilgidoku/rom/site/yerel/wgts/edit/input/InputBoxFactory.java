package com.bilgidoku.rom.site.yerel.wgts.edit.input;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.ArrayContextBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.BooleanContextBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.BrowseAll;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.BrowseContainers;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.BrowseFiles;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.BrowseLists;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.BrowsePages;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.ContainerContextBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.EnumBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.FileContextBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.FontContextBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.GenericHTMLText;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.GenericTextArea;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.GenericTextBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.HeightContextBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.ImageContextBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.IntegerContextBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.LinkContextBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.ListContextBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.PageContextBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.PercentageBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.RectContextBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.TickBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.TranslationContextBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.WidthContextBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.YoutubeBox;

public class InputBoxFactory {

	private static InputBoxFactory one;
	private static BrowseContainers browseCon;
	private static BrowseFiles browseFiles;
	private static BrowsePages browsePages;
	private static BrowseAll browseAll;
	private static BrowseLists browseLists;
	

	private List<InputBoxRegistry> registry = new ArrayList<InputBoxRegistry>();

	public static InputBox getFor(Att att, String value) {
		ensureOne();

		for (InputBoxRegistry it : one.registry) {
			if (it.iCanInput(att)) {
				return it.create(att, value);
			}
		}

		return null;
	}
	
	public static BrowseBase getBrowseFor(String type) {
		ensureOne();
		
		if (type.equals("page")) 
			return browsePages;
		else if (type.equals("file")) 
			return browseFiles;
		else if (type.equals("container")) 
			return browseCon;
		else if (type.equals("list")) 
			return browseLists;
		else if (type.equals("all")) 
			return browseAll;
		else 
			return null;
		
	}

	private static void ensureOne() {
		if (one == null) {
			one = new InputBoxFactory();
			
			browseCon = new BrowseContainers();
			browseFiles = new BrowseFiles();
			browsePages = new BrowsePages();
			browseAll = new BrowseAll();
			browseLists = new BrowseLists();			
			
		}
	}

	private void register(InputBoxRegistry ibr) {
		registry.add(ibr);
	}

	private InputBoxFactory() {
		register(ArrayContextBox.getRegistry());
		register(TranslationContextBox.getRegistry());
		register(PageContextBox.getRegistry());
		register(LinkContextBox.getRegistry());
		register(YoutubeBox.getRegistry());
		register(TickBox.getRegistry());
		register(WidthContextBox.getRegistry());
		register(HeightContextBox.getRegistry());
		register(FontContextBox.getRegistry());
		register(ContainerContextBox.getRegistry());
		register(ListContextBox.getRegistry());
		register(FileContextBox.getRegistry());
		register(ImageContextBox.getRegistry());
		register(EnumBox.getRegistry());
		register(PercentageBox.getRegistry());
		register(RectContextBox.getRegistry());
		register(IntegerContextBox.getRegistry());
		register(BooleanContextBox.getRegistry());
		register(GenericHTMLText.getRegistry());
		register(GenericTextArea.getRegistry());
		register(GenericTextBox.getRegistry());		

	}

}
