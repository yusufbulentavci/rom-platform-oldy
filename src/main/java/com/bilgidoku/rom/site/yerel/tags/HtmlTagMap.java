package com.bilgidoku.rom.site.yerel.tags;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.AttImpl;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.TagImpl;
import com.bilgidoku.rom.shared.cmds.CommandRepo;
import com.bilgidoku.rom.shared.code.Command;

public final class HtmlTagMap extends TagMap {
	
	private static TagMap one;
	
	public static TagMap one(){
		if(one==null)
			one=new HtmlTagMap();
		return one;
	}
	
	private static StyleDef[] STYLE_DEFS = {
			new StyleDef("background", "background",
					"Sets all the background properties in one declaration"),
			new StyleDef("background-attachment", "background",
					"Sets whether a background image is fixed or scrolls with the rest of the page"),
			new StyleDef("background-color", "background",
					"Sets the background color of an element", "color"),
			new StyleDef("background-image", "background",
					"Sets the background image for an element"),
			new StyleDef("background-position", "background",
					"Sets the starting position of a background image"),
			new StyleDef("background-repeat", "background",
					"Sets how a background image will be repeated"),
			new StyleDef("border", "border-outline",
					"Sets all the border properties in one declaration"),
			new StyleDef("border-bottom", "border-outline",
					"Sets all the bottom border properties in one declaration"),
			new StyleDef("border-bottom-color", "border-outline",
					"Sets the color of the bottom border", "color"),
			new StyleDef("border-bottom-style", "border-outline",
					"Sets the style of the bottom border"),
			new StyleDef("border-bottom-width", "border-outline",
					"Sets the width of the bottom border"),
			new StyleDef("border-color", "border-outline",
					"Sets the color of the four borders", "color"),
			new StyleDef("border-left", "border-outline",
					"Sets all the left border properties in one declaration"),
			new StyleDef("border-left-color", "border-outline",
					"Sets the color of the left border", "color"),
			new StyleDef("border-left-style", "border-outline",
					"Sets the style of the left border"),
			new StyleDef("border-left-width", "border-outline",
					"Sets the width of the left border"),
			new StyleDef("border-right", "border-outline",
					"Sets all the right border properties in one declaration "),
			new StyleDef("border-right-color", "border-outline",
					"Sets the color of the right border", "color"),
			new StyleDef("border-right-style", "border-outline",
					"Sets the style of the right border"),
			new StyleDef("border-right-width", "border-outline",
					"Sets the width of the right border"),
			new StyleDef("border-style", "border-outline",
					"Sets the style of the four borders"),
			new StyleDef("border-top", "border-outline",
					"Sets all the top border properties in one declaration"),
			new StyleDef("border-top-color", "border-outline",
					"Sets the color of the top border", "color"),
			new StyleDef("border-top-style ", "border-outline",
					"Sets the style of the top border"),
			new StyleDef("border-top-width", "border-outline",
					"Sets the width of the top border"),
			new StyleDef("border-width", "border-outline",
					"Sets the width of the four borders"),
			new StyleDef("outline", "border-outline",
					"Sets all the outline properties in one declaration"),
			new StyleDef("outline-color", "border-outline",
					"Sets the color of an outline", "color"),
			new StyleDef("outline-style", "border-outline",
					"Sets the style of an outline"),
			new StyleDef("outline-width", "border-outline",
					"Sets the width of an outline"),
			new StyleDef("height", "dimension", "Sets the height of an element"),
			new StyleDef("max-height", "dimension",
					"Sets the maximum height of an element"),
			new StyleDef("max-width", "dimension",
					"Sets the maximum width of an element"),
			new StyleDef("min-height", "dimension",
					"Sets the minimum height of an element"),
			new StyleDef("min-width", "dimension",
					"Sets the minimum width of an element"),
			new StyleDef("width", "dimension", "Sets the width of an element"),
			new StyleDef("font", "font",
					"Sets all the font properties in one declaration"),
			new StyleDef("font-family", "font",
					"Specifies the font family for text"),
			new StyleDef("font-size", "font", "Specifies the font size of text"),
			new StyleDef("font-style", "font",
					"Specifies the font style for text"),
			new StyleDef("font-variant", "font",
					"Specifies whether or not a text should be displayed in a small-caps font"),
			new StyleDef("font-weight", "font",
					"Specifies the weight of a font"),
			new StyleDef("content", "content",
					"Used with the :before and :after pseudo-elements, to insert content"),
			new StyleDef("counter-increment", "content",
					"Increments one or more counters"),
			new StyleDef("counter-reset", "content",
					"Creates or resets one or more counters"),
			new StyleDef("quotes", "content",
					"Sets the type of quotation marks for embedded quotations"),
			new StyleDef("list-style", "list",
					"Sets all the properties for a list in one declaration"),
			new StyleDef("list-style-image", "list",
					"Specifies an image as the list-item marker"),
			new StyleDef(
					"list-style-position",
					"list",
					"Specifies if the list-item markers should appear inside or outside the content flow"),
			new StyleDef("list-style-type", "list",
					"Specifies the type of list-item marker"),
			new StyleDef("margin", "margin",
					"Sets all the margin properties in one declaration"),
			new StyleDef("margin-bottom", "margin",
					"Sets the bottom margin of an element"),
			new StyleDef("margin-left", "margin",
					"Sets the left margin of an element"),
			new StyleDef("margin-right", "margin",
					"Sets the right margin of an element"),
			new StyleDef("margin-top", "margin",
					"Sets the top margin of an element"),
			new StyleDef("padding", "padding",
					"Sets all the padding properties in one declaration"),
			new StyleDef("padding-bottom", "padding",
					"Sets the bottom padding of an element"),
			new StyleDef("padding-left", "padding",
					"Sets the left padding of an element"),
			new StyleDef("padding-right", "padding",
					"Sets the right padding of an element"),
			new StyleDef("padding-top", "padding",
					"Sets the top padding of an element"),
			new StyleDef("bottom", "position",
					"Sets the bottom margin edge for a positioned box"),
			new StyleDef(
					"clear",
					"position",
					"Specifies which sides of an element where other floating elements are not allowed"),
			new StyleDef("clip", "position",
					"Clips an absolutely positioned element"),
			new StyleDef("cursor", "position",
					"Specifies the type of cursor to be displayed"),
			new StyleDef("display", "position",
					"Specifies the type of box an element should generate"),
			new StyleDef("float", "position",
					"Specifies whether or not a box should float"),
			new StyleDef("left", "position",
					"Sets the left margin edge for a positioned box"),
			new StyleDef("overflow", "position",
					"Specifies what happens if content overflows an elements box"),
			new StyleDef("position", "position",
					"Specifies the type of positioning for an element"),
			new StyleDef("right", "position",
					"Sets the right margin edge for a positioned box"),
			new StyleDef("top", "position",
					"Sets the top margin edge for a positioned box"),
			new StyleDef("visibility", "position",
					"Specifies whether or not an element is visible"),
			new StyleDef("z-index", "position",
					"Sets the stack order of an element"),
			new StyleDef(
					"orphans",
					"print",
					" Sets the minimum number of lines that must be left at the bottom of a page when a page break occurs inside an element"),
			new StyleDef("page-break-after", "print",
					"Sets the page-breaking behavior after an element"),
			new StyleDef("page-break-before", "print",
					"Sets the page-breaking behavior before an element"),
			new StyleDef("page-break-inside", "print",
					"Sets the page-breaking behavior inside an element"),
			new StyleDef(
					"widows",
					"print",
					" Sets the minimum number of lines that must be left at the top of a page when a page break occurs inside an element"),
			new StyleDef("border-collapse", "table",
					"Specifies whether or not table borders should be collapsed"),
			new StyleDef("border-spacing", "table",
					"Specifies the distance between the borders of adjacent cells"),
			new StyleDef("caption-side", "table",
					"Specifies the placement of a table caption"),
			new StyleDef(
					"empty-cells",
					"table",
					"Specifies whether or not to display borders and background on empty cells in a table"),
			new StyleDef("table-layout", "table",
					"Sets the layout algorithm to be used for a table"),
			new StyleDef("color", "text", "Sets the color of text", "color"),
			new StyleDef("direction", "text",
					"Specifies the text direction/writing direction"),
			new StyleDef("letter-spacing", "text",
					"Increases or decreases the space between characters in a text"),
			new StyleDef("line-height", "text", "Sets the line height"),
			new StyleDef("text-align", "text",
					"Specifies the horizontal alignment of text"),
			new StyleDef("text-decoration", "text",
					"Specifies the decoration added to text"),
			new StyleDef("text-indent", "text",
					"Specifies the indentation of the first line in a text-block"),
			new StyleDef("text-shadow", "text",
					"Specifies the shadow effect added to text"),
			new StyleDef("text-transform", "text",
					"Controls the capitalization of text"),
			new StyleDef("unicode-bidi", "text", ""),
			new StyleDef("vertical-align", "text",
					"Sets the vertical alignment of an element"),
			new StyleDef("white-space", "text",
					"Specifies how white-space inside an element is handled"),
			new StyleDef("word-spacing", "text",
					"Increases or decreases the space between words in a text"),
			new StyleDef("transform", "text",""),
			new StyleDef("transition", "text",""),
			new StyleDef("column-count", "text",""),
			new StyleDef("column-gap", "text",""),
			new StyleDef("column-rule-style", "text",""),
			new StyleDef("column-rule-width", "text",""),
			new StyleDef("column-rule-color", "text",""),
			new StyleDef("column-rule", "text",""),
			new StyleDef("column-span", "text",""),
			new StyleDef("column-width", "text","")
	};

	private final static Att a_onreset_40 = new AttImpl("onreset", true, 1,
			new String[] {}, 2, "");
	private final static Att a_content_69 = new AttImpl("content", false, 1,
			new String[] {}, 1, "");
	private final static Att a_colspan_91 = new AttImpl("colspan", false, 1,
			new String[] {}, 4, "");
	private final static Att a_codetype_96 = new AttImpl("codetype", false, 1,
			new String[] {}, 2, "");
	private final static Att a_for_49 = new AttImpl("for", false, 1,
			new String[] {}, 2, "");
	private final static Att a_border_81 = new AttImpl("border", false, 1,
			new String[] {}, 2, "");
	private final static Att a_onkeypress_14 = new AttImpl("onkeypress", true, 1,
			new String[] {}, 2, "");
	private final static Att a_accesskey_17 = new AttImpl("accesskey", false, 1,
			new String[] {}, 2, "");
	private final static Att a_align_63 = new AttImpl("align", false, 1,
			new String[] { "left", "center", "right", "justify", "char" }, 2,
			"");
	private final static Att a_onload_55 = new AttImpl("onload", true, 1,
			new String[] {}, 2, "");
	private final static Att a_onselect_28 = new AttImpl("onselect", true, 1,
			new String[] {}, 2, "");
	private final static Att a_title_3 = new AttImpl("title", false, 1,
			new String[] {}, 2, "");
	private final static Att a_onkeydown_15 = new AttImpl("onkeydown", true, 1,
			new String[] {}, 2, "");
	private final static Att a_lang_4 = new AttImpl("lang", false, 1,
			new String[] {}, 2, "");
	private final static Att a_ondblclick_8 = new AttImpl("ondblclick", true, 1,
			new String[] {}, 2, "");
	private final static Att a_onchange_29 = new AttImpl("onchange", true, 1,
			new String[] {}, 2, "");
	private final static Att a_valuetype_79 = new AttImpl("valuetype", false, 1,
			new String[] { "data", "ref", "object" }, 4, "");
	private final static Att a_abbr_86 = new AttImpl("abbr", false, 1,
			new String[] {}, 2, "");
	private final static Att a_classid_93 = new AttImpl("classid", false, 1,
			new String[] {}, 2, "");
	private final static Att a_onblur_20 = new AttImpl("onblur", true, 1,
			new String[] {}, 2, "");
	private final static Att a_checked_103 = new AttImpl("checked", false, 1,
			new String[] { "checked" }, 2, "");
	private final static Att a_src_74 = new AttImpl("src", false, 1,
			new String[] {}, 1, "");
	private final static Att a_codebase_94 = new AttImpl("codebase", false, 1,
			new String[] {}, 2, "");
	private final static Att a_onfocus_19 = new AttImpl("onfocus", true, 1,
			new String[] {}, 2, "");
	private final static Att a_onmouseout_13 = new AttImpl("onmouseout", true, 1,
			new String[] {}, 2, "");
	private final static Att a_onmousedown_9 = new AttImpl("onmousedown", true, 1,
			new String[] {}, 2, "");
	private final static Att a_disabled_24 = new AttImpl("disabled", false, 1,
			new String[] { "disabled" }, 2, "");
	private final static Att a_usemap_77 = new AttImpl("usemap", false, 1,
			new String[] {}, 2, "");
	private final static Att a_onkeyup_16 = new AttImpl("onkeyup", true, 1,
			new String[] {}, 2, "");
	private final static Att a_alt_105 = new AttImpl("alt", false, 1,
			new String[] {}, 2, "");
	private final static Att a_accept_41 = new AttImpl("accept", false, 1,
			new String[] {}, 2, "");
	private final static Att a_coords_31 = new AttImpl("coords", false, 1,
			new String[] {}, 2, "");
	private final static Att a_axis_87 = new AttImpl("axis", false, 1,
			new String[] {}, 2, "");
	private final static Att a_width_62 = new AttImpl("width", false, 1,
			new String[] {}, 2, "");
	private final static Att a_rules_83 = new AttImpl("rules", false, 1,
			new String[] { "none", "groups", "rows", "cols", "all" }, 2, "");
	
	private final static Att a_value_22 = new AttImpl("value", false, 1,
			new String[] {}, 2, "");
	private final static Att a_multiple_101 = new AttImpl("multiple", false, 1,
			new String[] { "multiple" }, 2, "");
	private final static Att a_media_48 = new AttImpl("media", false, 1,
			new String[] {}, 2, "");
	private final static Att a_ismap_78 = new AttImpl("ismap", false, 1,
			new String[] { "ismap" }, 2, "");
	private final static Att a_declare_92 = new AttImpl("declare", false, 1,
			new String[] { "declare" }, 2, "");
	private final static Att a_onmouseup_10 = new AttImpl("onmouseup", true, 1,
			new String[] {}, 2, "");
	private final static Att a_scheme_70 = new AttImpl("scheme", false, 1,
			new String[] {}, 2, "");
	private final static Att a_height_76 = new AttImpl("height", false, 1,
			new String[] {}, 2, "");
	private final static Att a_longdesc_75 = new AttImpl("longdesc", false, 1,
			new String[] {}, 2, "");
	private final static Att a_dir_35 = new AttImpl("dir", false, 1, new String[] {
			"ltr", "rtl" }, 1, "");
	private final static Att a_name_21 = new AttImpl("name", false, 1,
			new String[] {}, 2, "");
	private final static Att a_enctype_38 = new AttImpl("enctype", false, 1,
			new String[] {}, 4, "");
	private final static Att a_cols_26 = new AttImpl("cols", false, 1,
			new String[] {}, 1, "");
	private final static Att a_type_50 = new AttImpl("type", false, 1,
			new String[] {}, 1, "");
	private final static Att a_span_61 = new AttImpl("span", false, 1,
			new String[] {}, 4, "");
	private final static Att a_summary_80 = new AttImpl("summary", false, 1,
			new String[] {}, 2, "");
	private final static Att a_dir_6 = new AttImpl("dir", false, 1, new String[] {
			"ltr", "rtl" }, 2, "");
	private final static Att a_rowspan_90 = new AttImpl("rowspan", false, 1,
			new String[] {}, 4, "");
	private final static Att a_archive_97 = new AttImpl("archive", false, 1,
			new String[] {}, 2, "");
	private final static Att a_cite_54 = new AttImpl("cite", false, 1,
			new String[] {}, 2, "");
	private final static Att a_href_67 = new AttImpl("href", false, 1,
			new String[] {}, 1, "");
	private final static Att a_href_32 = new AttImpl("href", false, 1,
			new String[] {}, 2, "");
	private final static Att a_alt_34 = new AttImpl("alt", false, 1,
			new String[] {}, 1, "");
	private final static Att a_type_45 = new AttImpl("type", false, 1,
			new String[] {}, 2, "");
	private final static Att a_class_1 = new AttImpl("class", false, 1,
			new String[] {}, 2, "");
	private final static Att a_profile_60 = new AttImpl("profile", false, 1,
			new String[] {}, 2, "");
	private final static Att a_label_72 = new AttImpl("label", false, 1,
			new String[] {}, 2, "");
	private final static Att a_label_73 = new AttImpl("label", false, 1,
			new String[] {}, 1, "");
	private final static Att a_maxlength_104 = new AttImpl("maxlength", false, 1,
			new String[] {}, 2, "");
	private final static Att a_onsubmit_39 = new AttImpl("onsubmit", true, 1,
			new String[] {}, 2, "");
	private final static Att a_cellspacing_84 = new AttImpl("cellspacing", false,
			1, new String[] {}, 2, "");
	private final static Att a_accept_charset_42 = new AttImpl("accept-charset",
			false, 1, new String[] {}, 2, "");
	private final static Att a_datetime_57 = new AttImpl("datetime", false, 1,
			new String[] {}, 2, "");
	private final static Att a_onmousemove_12 = new AttImpl("onmousemove", true, 1,
			new String[] {}, 2, "");
	private final static Att a_action_36 = new AttImpl("action", false, 1,
			new String[] {}, 1, "");
	private final static Att a_name_59 = new AttImpl("name", false, 1,
			new String[] {}, 2, "");
	private final static Att a_onmouseover_11 = new AttImpl("onmouseover", true, 1,
			new String[] {}, 2, "");
	private final static Att a_data_95 = new AttImpl("data", false, 1,
			new String[] {}, 2, "");
	private final static Att a_char_64 = new AttImpl("char", false, 1,
			new String[] {}, 2, "");
	private final static Att a_cellpadding_85 = new AttImpl("cellpadding", false,
			1, new String[] {}, 2, "");
	private final static Att a_headers_88 = new AttImpl("headers", false, 1,
			new String[] {}, 2, "");
	private final static Att a_size_100 = new AttImpl("size", false, 1,
			new String[] {}, 2, "");
	private final static Att a_shape_30 = new AttImpl("shape", false, 1,
			new String[] { "rect", "circle", "poly", "default" }, 1, "");
	
	private final static Att a_method_37 = new AttImpl("method", false, 1,
			new String[] { "get", "post" }, 1, "");
	
	private final static Att a_rows_25 = new AttImpl("rows", false, 1,
			new String[] {}, 1, "");
	private final static Att a_id_58 = new AttImpl("id", false, 1, new String[] {},
			1, "");
	private final static Att a_http_equiv_68 = new AttImpl("http-equiv", false, 1,
			new String[] {}, 2, "");
	private final static Att a_readonly_27 = new AttImpl("readonly", false, 1,
			new String[] { "readonly" }, 2, "");
	private final static Att a_charoff_65 = new AttImpl("charoff", false, 1,
			new String[] {}, 2, "");
	private final static Att a_scope_89 = new AttImpl("scope", false, 1,
			new String[] { "row", "col", "rowgroup", "colgroup" }, 2, "");
	private final static Att a_xml_lang_5 = new AttImpl("xml:lang", false, 1,
			new String[] {}, 2, "");
	private final static Att a_charset_43 = new AttImpl("charset", false, 1,
			new String[] {}, 2, "");
	private final static Att a_onclick_7 = new AttImpl("onclick", true, 1,
			new String[] {}, 2, "");
	private final static Att a_standby_98 = new AttImpl("standby", false, 1,
			new String[] {}, 2, "");
	private final static Att a_src_51 = new AttImpl("src", false, 1,
			new String[] {}, 2, "");
	private final static Att a_defer_52 = new AttImpl("defer", false, 1,
			new String[] { "defer" }, 2, "");
	private final static Att a_id_0 = new AttImpl("id", false, 1, new String[] {},
			2, "");
	private final static Att a_style_2 = new AttImpl("style", false, 1,
			new String[] {}, 2, "");
	private final static Att a_selected_71 = new AttImpl("selected", false, 1,
			new String[] {}, 2, "");
	private final static Att a_valign_66 = new AttImpl("valign", false, 1,
			new String[] { "top", "middle", "bottom", "baseline" }, 2, "");
	private final static Att a_rev_47 = new AttImpl("rev", false, 1,
			new String[] {}, 2, "");
	private final static Att a_rel_46 = new AttImpl("rel", false, 1,
			new String[] {}, 2, "");
	private final static Att a_type_102 = new AttImpl("type", false, 1,
			new String[] { "text", "password", "checkbox", "radio", "submit",
					"reset", "file", "hidden", "image", "button" }, 4, "");
	private final static Att a_type_23 = new AttImpl("type", false, 1,
			new String[] { "button", "submit", "reset" }, 4, "");
	private final static Att a_onunload_56 = new AttImpl("onunload", true, 1,
			new String[] {}, 2, "");
	private final static Att a_frame_82 = new AttImpl("frame", false, 1,
			new String[] { "void", "above", "below", "hsides", "lhs", "rhs",
					"vsides", "box", "border" }, 2, "");
	private final static Att a_tabindex_18 = new AttImpl("tabindex", false, 1,
			new String[] {}, 2, "");
	
	private final static Att custom_target = new AttImpl("target", false, 1,
			new String[] {"_blank","_self","_parent","_top"}, 2, "");
	
	private final static Att[] l_31 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3, a_lang_4, a_xml_lang_5, a_dir_6, a_onclick_7,
			a_ondblclick_8, a_onmousedown_9, a_onmouseup_10, a_onmouseover_11,
			a_onmousemove_12, a_onmouseout_13, a_onkeypress_14, a_onkeydown_15,
			a_onkeyup_16, a_name_21, a_size_100, a_multiple_101, a_disabled_24,
			a_tabindex_18, a_onfocus_19, a_onblur_20, a_onchange_29 };
	private final static Att[] l_16 = new Att[] { a_href_67, a_id_0 };
	private final static Att[] l_17 = new Att[] { a_lang_4, a_xml_lang_5,
			a_dir_6, a_id_0, a_http_equiv_68, a_name_21, a_content_69,
			a_scheme_70 };
	private final static Att[] l_20 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3, a_lang_4, a_xml_lang_5, a_dir_6, a_onclick_7,
			a_ondblclick_8, a_onmousedown_9, a_onmouseup_10, a_onmouseover_11,
			a_onmousemove_12, a_onmouseout_13, a_onkeypress_14, a_onkeydown_15,
			a_onkeyup_16 };
	private final static Att[] l_24 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3 };
	private final static Att[] l_1 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3, a_lang_4, a_xml_lang_5, a_dir_6, a_onclick_7,
			a_ondblclick_8, a_onmousedown_9, a_onmouseup_10, a_onmouseover_11,
			a_onmousemove_12, a_onmouseout_13, a_onkeypress_14, a_onkeydown_15,
			a_onkeyup_16, a_accesskey_17, a_tabindex_18, a_onfocus_19,
			a_onblur_20, a_name_21, a_rows_25, a_cols_26, a_disabled_24,
			a_readonly_27, a_onselect_28, a_onchange_29 };
	private final static Att[] l_32 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3, a_lang_4, a_xml_lang_5, a_dir_6, a_onclick_7,
			a_ondblclick_8, a_onmousedown_9, a_onmouseup_10, a_onmouseover_11,
			a_onmousemove_12, a_onmouseout_13, a_onkeypress_14, a_onkeydown_15,
			a_onkeyup_16, a_accesskey_17 };
	private final static Att[] l_18 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3, a_lang_4, a_xml_lang_5, a_dir_6, a_onclick_7,
			a_ondblclick_8, a_onmousedown_9, a_onmouseup_10, a_onmouseover_11,
			a_onmousemove_12, a_onmouseout_13, a_onkeypress_14, a_onkeydown_15,
			a_onkeyup_16, a_align_63, a_char_64, a_charoff_65, a_valign_66 };
	private final static Att[] l_25 = new Att[] { a_lang_4, a_xml_lang_5,
			a_dir_6, a_id_0, a_type_50, a_media_48, a_title_3 };
	private final static Att[] l_8 = new Att[] { a_lang_4, a_xml_lang_5,
			a_dir_6, a_id_0 };
	private final static Att[] l_12 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3, a_lang_4, a_xml_lang_5, a_dir_6, a_onclick_7,
			a_ondblclick_8, a_onmousedown_9, a_onmouseup_10, a_onmouseover_11,
			a_onmousemove_12, a_onmouseout_13, a_onkeypress_14, a_onkeydown_15,
			a_onkeyup_16, a_cite_54, a_datetime_57 };
	private final static Att[] l_30 = new Att[] { a_lang_4, a_xml_lang_5,
			a_dir_6, a_id_0 };
	private final static Att[] l_27 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3, a_lang_4, a_xml_lang_5, a_dir_6, a_onclick_7,
			a_ondblclick_8, a_onmousedown_9, a_onmouseup_10, a_onmouseover_11,
			a_onmousemove_12, a_onmouseout_13, a_onkeypress_14, a_onkeydown_15,
			a_onkeyup_16, a_summary_80, a_width_62, a_border_81, a_frame_82,
			a_rules_83, a_cellspacing_84, a_cellpadding_85 };
	private final static Att[] l_4 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3, a_onclick_7, a_ondblclick_8, a_onmousedown_9,
			a_onmouseup_10, a_onmouseover_11, a_onmousemove_12,
			a_onmouseout_13, a_onkeypress_14, a_onkeydown_15, a_onkeyup_16,
			a_lang_4, a_xml_lang_5, a_dir_35 };
	private final static Att[] l_7 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3, a_lang_4, a_xml_lang_5, a_dir_6, a_onclick_7,
			a_ondblclick_8, a_onmousedown_9, a_onmouseup_10, a_onmouseover_11,
			a_onmousemove_12, a_onmouseout_13, a_onkeypress_14, a_onkeydown_15,
			a_onkeyup_16, a_for_49, a_accesskey_17, a_onfocus_19, a_onblur_20 };
	private final static Att[] l_22 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3, a_lang_4, a_xml_lang_5, a_dir_6, a_onclick_7,
			a_ondblclick_8, a_onmousedown_9, a_onmouseup_10, a_onmouseover_11,
			a_onmousemove_12, a_onmouseout_13, a_onkeypress_14, a_onkeydown_15,
			a_onkeyup_16, a_src_74, a_alt_34, a_longdesc_75, a_height_76,
			a_width_62, a_usemap_77, a_ismap_78 };
	private final static Att[] l_0 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3, a_lang_4, a_xml_lang_5, a_dir_6, a_onclick_7,
			a_ondblclick_8, a_onmousedown_9, a_onmouseup_10, a_onmouseover_11,
			a_onmousemove_12, a_onmouseout_13, a_onkeypress_14, a_onkeydown_15,
			a_onkeyup_16, a_accesskey_17, a_tabindex_18, a_onfocus_19,
			a_onblur_20, a_name_21, a_value_22, a_type_23, a_disabled_24 };
	private final static Att[] l_23 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3, a_lang_4, a_xml_lang_5, a_dir_6, a_onclick_7,
			a_ondblclick_8, a_onmousedown_9, a_onmouseup_10, a_onmouseover_11,
			a_onmousemove_12, a_onmouseout_13, a_onkeypress_14, a_onkeydown_15,
			a_onkeyup_16, a_accesskey_17, a_tabindex_18, a_onfocus_19,
			a_onblur_20, a_charset_43, a_type_45, a_name_59, a_href_32,
			a_rel_46, a_rev_47, a_shape_30, a_coords_31,custom_target};
	private final static Att[] l_6 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3, a_lang_4, a_xml_lang_5, a_dir_6, a_onclick_7,
			a_ondblclick_8, a_onmousedown_9, a_onmouseup_10, a_onmouseover_11,
			a_onmousemove_12, a_onmouseout_13, a_onkeypress_14, a_onkeydown_15,
			a_onkeyup_16, a_charset_43, a_href_32,  a_type_45,
			a_rel_46, a_rev_47, a_media_48 };
	private final static Att[] l_14 = new Att[] { a_lang_4, a_xml_lang_5,
			a_dir_6, a_id_0, a_profile_60 };
	private final static Att[] l_19 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3, a_lang_4, a_xml_lang_5, a_dir_6, a_onclick_7,
			a_ondblclick_8, a_onmousedown_9, a_onmouseup_10, a_onmouseover_11,
			a_onmousemove_12, a_onmouseout_13, a_onkeypress_14, a_onkeydown_15,
			a_onkeyup_16, a_selected_71, a_disabled_24, a_label_72, a_value_22 };
	private final static Att[] l_5 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3, a_lang_4, a_xml_lang_5, a_dir_6, a_onclick_7,
			a_ondblclick_8, a_onmousedown_9, a_onmouseup_10, a_onmouseover_11,
			a_onmousemove_12, a_onmouseout_13, a_onkeypress_14, a_onkeydown_15,
			a_onkeyup_16, a_action_36, a_method_37, a_enctype_38,
			a_onsubmit_39, a_onreset_40, a_accept_41, a_accept_charset_42 };
	private final static Att[] l_21 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3, a_lang_4, a_xml_lang_5, a_dir_6, a_onclick_7,
			a_ondblclick_8, a_onmousedown_9, a_onmouseup_10, a_onmouseover_11,
			a_onmousemove_12, a_onmouseout_13, a_onkeypress_14, a_onkeydown_15,
			a_onkeyup_16, a_disabled_24, a_label_73 };
	private final static Att[] l_29 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3, a_lang_4, a_xml_lang_5, a_dir_6, a_onclick_7,
			a_ondblclick_8, a_onmousedown_9, a_onmouseup_10, a_onmouseover_11,
			a_onmousemove_12, a_onmouseout_13, a_onkeypress_14, a_onkeydown_15,
			a_onkeyup_16, a_declare_92, a_classid_93, a_codebase_94, a_data_95,
			a_type_45, a_codetype_96, a_archive_97, a_standby_98, a_height_76,
			a_width_62, a_usemap_77, a_name_59, a_tabindex_18 };
	private final static Att[] l_33 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3, a_lang_4, a_xml_lang_5, a_dir_6, a_onclick_7,
			a_ondblclick_8, a_onmousedown_9, a_onmouseup_10, a_onmouseover_11,
			a_onmousemove_12, a_onmouseout_13, a_onkeypress_14, a_onkeydown_15,
			a_onkeyup_16, a_accesskey_17, a_tabindex_18, a_onfocus_19,
			a_onblur_20, a_type_102, a_name_21, a_value_22, a_checked_103,
			a_disabled_24, a_readonly_27, a_size_100, a_maxlength_104,
			a_src_51, a_alt_105, a_usemap_77, a_onselect_28, a_onchange_29,
			a_accept_41 };
	private final static Att[] l_9 = new Att[] { a_id_0, a_charset_43,
			a_type_50, a_src_51, a_defer_52 };
	private final static Att[] l_10 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3, a_lang_4, a_xml_lang_5, a_dir_6, a_onclick_7,
			a_ondblclick_8, a_onmousedown_9, a_onmouseup_10, a_onmouseover_11,
			a_onmousemove_12, a_onmouseout_13, a_onkeypress_14, a_onkeydown_15,
			a_onkeyup_16, a_cite_54 };
	private final static Att[] l_28 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3, a_lang_4, a_xml_lang_5, a_dir_6, a_onclick_7,
			a_ondblclick_8, a_onmousedown_9, a_onmouseup_10, a_onmouseover_11,
			a_onmousemove_12, a_onmouseout_13, a_onkeypress_14, a_onkeydown_15,
			a_onkeyup_16, a_abbr_86, a_axis_87, a_headers_88, a_scope_89,
			a_rowspan_90, a_colspan_91, a_align_63, a_char_64, a_charoff_65,
			a_valign_66 };
	private final static Att[] l_3 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3, a_lang_4, a_xml_lang_5, a_dir_6, a_onclick_7,
			a_ondblclick_8, a_onmousedown_9, a_onmouseup_10, a_onmouseover_11,
			a_onmousemove_12, a_onmouseout_13, a_onkeypress_14, a_onkeydown_15,
			a_onkeyup_16, a_accesskey_17, a_tabindex_18, a_onfocus_19,
			a_onblur_20, a_shape_30, a_coords_31, a_href_32,
			a_alt_34 };
	private final static Att[] l_15 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3, a_lang_4, a_xml_lang_5, a_dir_6, a_onclick_7,
			a_ondblclick_8, a_onmousedown_9, a_onmouseup_10, a_onmouseover_11,
			a_onmousemove_12, a_onmouseout_13, a_onkeypress_14, a_onkeydown_15,
			a_onkeyup_16, a_span_61, a_width_62, a_align_63, a_char_64,
			a_charoff_65, a_valign_66 };
	private final static Att[] l_11 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3, a_lang_4, a_xml_lang_5, a_dir_6, a_onclick_7,
			a_ondblclick_8, a_onmousedown_9, a_onmouseup_10, a_onmouseover_11,
			a_onmousemove_12, a_onmouseout_13, a_onkeypress_14, a_onkeydown_15,
			a_onkeyup_16, a_onload_55, a_onunload_56 };
	private final static Att[] l_26 = new Att[] { a_id_0, a_name_21,
			a_value_22, a_valuetype_79, a_type_45 };
	private final static Att[] l_13 = new Att[] { a_lang_4, a_xml_lang_5,
			a_dir_6, a_onclick_7, a_ondblclick_8, a_onmousedown_9,
			a_onmouseup_10, a_onmouseover_11, a_onmousemove_12,
			a_onmouseout_13, a_onkeypress_14, a_onkeydown_15, a_onkeyup_16,
			a_id_58, a_class_1, a_style_2, a_title_3, a_name_59 };
	private final static Att[] l_2 = new Att[] { a_id_0, a_class_1, a_style_2,
			a_title_3, a_lang_4, a_xml_lang_5, a_dir_6, a_onclick_7,
			a_ondblclick_8, a_onmousedown_9, a_onmouseup_10, a_onmouseover_11,
			a_onmousemove_12, a_onmouseout_13, a_onkeypress_14, a_onkeydown_15,
			a_onkeyup_16 };
	private final static String[] t_3 = new String[] {};
	private final static String[] t_21 = new String[] { "optgroup", "option", };
	private final static String[] t_19 = new String[] { "#pcdata", "param",
			"p", "h1", "h2", "h3", "h4", "h5", "h6", "div", "ul", "ol", "dl",
			"pre", "hr", "blockquote", "address", "fieldset", "table", "form",
			"a", "br", "span", "bdo", "map", "object", "img", "tt", "i", "b",
			"big", "small", "em", "strong", "dfn", "code", "q", "samp", "kbd",
			"var", "cite", "abbr", "acronym", "sub", "sup", "input", "select",
			"textarea", "label", "button", "noscript", "ins", "del", "script", };
	private final static String[] t_1 = new String[] { "#pcdata", };
	private final static String[] t_13 = new String[] { "#pcdata", "a", "tt",
			"i", "b", "big", "small", "em", "strong", "dfn", "code", "q",
			"samp", "kbd", "var", "cite", "abbr", "acronym", "sub", "sup",
			"br", "span", "bdo", "map", "ins", "del", "script", "input",
			"select", "textarea", "label", "button", };
	private final static String[] t_5 = new String[] { "#pcdata", "p", "h1",
			"h2", "h3", "h4", "h5", "h6", "div", "ul", "ol", "dl", "pre", "hr",
			"blockquote", "address", "fieldset", "table", "form", "a", "br",
			"span", "bdo", "map", "object", "img", "tt", "i", "b", "big",
			"small", "em", "strong", "dfn", "code", "q", "samp", "kbd", "var",
			"cite", "abbr", "acronym", "sub", "sup", "input", "select",
			"textarea", "label", "button", "noscript", "ins", "del", "script", };
	private final static String[] t_20 = new String[] { "head", "body", };
	private final static String[] t_10 = new String[] { "script", "style",
			"meta", "link", "object", "title", "script", "style", "meta",
			"link", "object", "base", "script", "style", "meta", "link",
			"object", "base", "script", "style", "meta", "link", "object",
			"title", "script", "style", "meta", "link", "object", };
	private final static String[] t_15 = new String[] { "#pcdata", "br",
			"span", "bdo", "map", "object", "img", "tt", "i", "b", "big",
			"small", "em", "strong", "dfn", "code", "q", "samp", "kbd", "var",
			"cite", "abbr", "acronym", "sub", "sup", "input", "select",
			"textarea", "label", "button", "ins", "del", "script", };
	private final static String[] t_17 = new String[] { "th", "td", };
	private final static String[] t_0 = new String[] { "#pcdata", "p", "h1",
			"h2", "h3", "h4", "h5", "h6", "div", "ul", "ol", "dl", "pre", "hr",
			"blockquote", "address", "table", "br", "span", "bdo", "map",
			"object", "img", "tt", "i", "b", "big", "small", "em", "strong",
			"dfn", "code", "q", "samp", "kbd", "var", "cite", "abbr",
			"acronym", "sub", "sup", "noscript", "ins", "del", "script", };
	private final static String[] t_18 = new String[] { "col", };
	private final static String[] t_9 = new String[] { "#pcdata", "legend",
			"p", "h1", "h2", "h3", "h4", "h5", "h6", "div", "ul", "ol", "dl",
			"pre", "hr", "blockquote", "address", "fieldset", "table", "form",
			"a", "br", "span", "bdo", "map", "object", "img", "tt", "i", "b",
			"big", "small", "em", "strong", "dfn", "code", "q", "samp", "kbd",
			"var", "cite", "abbr", "acronym", "sub", "sup", "input", "select",
			"textarea", "label", "button", "noscript", "ins", "del", "script", };
	private final static String[] t_2 = new String[] { "#pcdata", "a", "br",
			"span", "bdo", "map", "object", "img", "tt", "i", "b", "big",
			"small", "em", "strong", "dfn", "code", "q", "samp", "kbd", "var",
			"cite", "abbr", "acronym", "sub", "sup", "input", "select",
			"textarea", "label", "button", "ins", "del", "script", };
	private final static String[] t_11 = new String[] { "tr", };
	private final static String[] t_12 = new String[] { "li", };
	private final static String[] t_14 = new String[] { "option", };
	private final static String[] t_7 = new String[] { "p", "h1", "h2", "h3",
			"h4", "h5", "h6", "div", "ul", "ol", "dl", "pre", "hr",
			"blockquote", "address", "fieldset", "table", "form", "noscript",
			"ins", "del", "script", };
	private final static String[] t_8 = new String[] { "p", "h1", "h2", "h3",
			"h4", "h5", "h6", "div", "ul", "ol", "dl", "pre", "hr",
			"blockquote", "address", "fieldset", "table", "form", "noscript",
			"ins", "del", "script", "area", };
	private final static String[] t_4 = new String[] { "p", "h1", "h2", "h3",
			"h4", "h5", "h6", "div", "ul", "ol", "dl", "pre", "hr",
			"blockquote", "address", "fieldset", "table", "noscript", "ins",
			"del", "script", };
	private final static String[] t_16 = new String[] { "caption", "col",
			"colgroup", "thead", "tfoot", "tbody", "tr", };
	private final static String[] t_6 = new String[] { "dt", "dd", };
	private final static char[] c_3 = new char[] {};
	private final static char[] c_7 = new char[] { '-', '-', '-', '-', '-',
			'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-',
			'-', '-', '-', '-' };
	private final static char[] c_5 = new char[] { '-', '-', '-', '-', '-',
			'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-',
			'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-',
			'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-',
			'-', '-', '-', '-', '-', '-', '-', '-', '-' };
	private final static char[] c_0 = new char[] { '-', '-', '-', '-', '-',
			'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-',
			'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-',
			'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-',
			'-' };
	private final static char[] c_2 = new char[] { '-', '-', '-', '-', '-',
			'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-',
			'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-',
			'-', '-', '-' };
	private final static char[] c_9 = new char[] { '-', '-', '-', '-', '-',
			'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-',
			'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-',
			'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-',
			'-', '-', '-', '-', '-', '-', '-', '-', '-', '-' };
	private final static char[] c_6 = new char[] { '-', '-' };
	private final static char[] c_8 = new char[] { '-', '-', '-', '-', '-',
			'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-',
			'-', '-', '-', '-', '+' };
	private final static char[] c_12 = new char[] { '-', '-', '-', '-', '-',
			'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-',
			'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-',
			'-', '-' };
	private final static char[] c_11 = new char[] { '-', '-', '-', '-', '-',
			'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-',
			'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-',
			'-' };
	private final static char[] c_1 = new char[] { '-' };
	private final static char[] c_4 = new char[] { '-', '-', '-', '-', '-',
			'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-',
			'-', '-', '-' };
	private final static char[] c_13 = new char[] { '?', '*', '*', '?', '?',
			'+', '+' };
	private final static char[] c_10 = new char[] { '-', '-', '-', '-', '-',
			'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-',
			'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-' };

	public HtmlTagMap() {
		super(STYLE_DEFS,new Tag[] { new TagImpl("button", false, "", l_0, t_0, c_0, ""),
				new TagImpl("textarea", false, "", l_1, t_1, c_1, ""),
				new TagImpl("em", false, "", l_2, t_2, c_2, ""),
				new TagImpl("small", false, "", l_2, t_2, c_2, ""),
				new TagImpl("area", false, "", l_3, t_3, c_3, ""),
				new TagImpl("bdo", false, "", l_4, t_2, c_2, ""),
				new TagImpl("form", false, "", l_5, t_4, c_4, ""),
				new TagImpl("link", false, "", l_6, t_3, c_3, ""),
				new TagImpl("label", false, "", l_7, t_2, c_2, ""),
				new TagImpl("dt", false, "", l_2, t_2, c_2, ""),
				new TagImpl("span", false, "", l_2, t_2, c_2, ""),
				new TagImpl("title", false, "", l_8, t_1, c_1, ""),
				new TagImpl("strong", false, "", l_2, t_2, c_2, ""),
				new TagImpl("script", false, "", l_9, t_1, c_1, ""),
				new TagImpl("div", false, "", l_2, t_5, c_5, ""),
				new TagImpl("dl", false, "", l_2, t_6, c_6, ""),
				new TagImpl("blockquote", false, "", l_10, t_7, c_7, ""),
				new TagImpl("kbd", false, "", l_2, t_2, c_2, ""),
				new TagImpl("body", false, "", l_11, t_7, c_7, ""),
				new TagImpl("ins", false, "", l_12, t_5, c_5, ""),
				new TagImpl("map", false, "", l_13, t_8, c_8, ""),
				new TagImpl("dd", false, "", l_2, t_5, c_5, ""),
				new TagImpl("fieldset", false, "", l_2, t_9, c_9, ""),
				new TagImpl("head", false, "", l_14, t_10, c_10, ""),
				new TagImpl("col", false, "", l_15, t_3, c_3, ""),
				new TagImpl("base", false, "", l_16, t_3, c_3, ""),
				new TagImpl("big", false, "", l_2, t_2, c_2, ""),
				new TagImpl("meta", false, "", l_17, t_3, c_3, ""),
				new TagImpl("code", false, "", l_2, t_2, c_2, ""),
				new TagImpl("tbody", false, "", l_18, t_11, c_1, ""),
				new TagImpl("option", false, "", l_19, t_1, c_1, ""),
				new TagImpl("q", false, "", l_10, t_2, c_2, ""),
				new TagImpl("p", false, "", l_2, t_2, c_2, ""),
				new TagImpl("ol", false, "", l_2, t_12, c_1, ""),
				new TagImpl("thead", false, "", l_18, t_11, c_1, ""),
				new TagImpl("ul", false, "", l_2, t_12, c_1, ""),
				new TagImpl("i", false, "", l_2, t_2, c_2, ""),
				new TagImpl("pre", false, "", l_20, t_13, c_11, ""),
				new TagImpl("optgroup", false, "", l_21, t_14, c_1, ""),
				new TagImpl("img", false, "", l_22, t_3, c_3, ""),
				new TagImpl("caption", false, "", l_2, t_2, c_2, ""),
				new TagImpl("b", false, "", l_2, t_2, c_2, ""),
				new TagImpl("a", false, "", l_23, t_15, c_12, ""),
				new TagImpl("br", false, "", l_24, t_3, c_3, ""),
				new TagImpl("style", false, "", l_25, t_1, c_1, ""),
				new TagImpl("hr", false, "", l_2, t_3, c_3, ""),
				new TagImpl("param", false, "", l_26, t_3, c_3, ""),
				new TagImpl("table", false, "", l_27, t_16, c_13, ""),
				new TagImpl("tt", false, "", l_2, t_2, c_2, ""),
				new TagImpl("tr", false, "", l_18, t_17, c_6, ""),
				new TagImpl("th", false, "", l_28, t_5, c_5, ""),
				new TagImpl("td", false, "", l_28, t_5, c_5, ""),
				new TagImpl("samp", false, "", l_2, t_2, c_2, ""),
				new TagImpl("tfoot", false, "", l_18, t_11, c_1, ""),
				new TagImpl("dfn", false, "", l_2, t_2, c_2, ""),
				new TagImpl("noscript", false, "", l_2, t_7, c_7, ""),
				new TagImpl("colgroup", false, "", l_15, t_18, c_1, ""),
				new TagImpl("object", false, "", l_29, t_19, c_9, ""),
				new TagImpl("sup", false, "", l_2, t_2, c_2, ""),
				new TagImpl("html", true, "", l_30, t_20, c_6, ""),
				new TagImpl("h6", false, "", l_2, t_2, c_2, ""),
				new TagImpl("h5", false, "", l_2, t_2, c_2, ""),
				new TagImpl("h4", false, "", l_2, t_2, c_2, ""),
				new TagImpl("h3", false, "", l_2, t_2, c_2, ""),
				new TagImpl("h2", false, "", l_2, t_2, c_2, ""),
				new TagImpl("h1", false, "", l_2, t_2, c_2, ""),
				new TagImpl("sub", false, "", l_2, t_2, c_2, ""),
				new TagImpl("acronym", false, "", l_2, t_2, c_2, ""),
				new TagImpl("select", false, "", l_31, t_21, c_6, ""),
				new TagImpl("del", false, "", l_12, t_5, c_5, ""),
				new TagImpl("li", false, "", l_2, t_5, c_5, ""),
				new TagImpl("cite", false, "", l_2, t_2, c_2, ""),
				new TagImpl("var", false, "", l_2, t_2, c_2, ""),
				new TagImpl("legend", false, "", l_32, t_2, c_2, ""),
				new TagImpl("abbr", false, "", l_2, t_2, c_2, ""),
				new TagImpl("input", false, "", l_33, t_3, c_3, ""),
				new TagImpl("address", false, "", l_2, t_2, c_2, "") ,
				
				new TagImpl("iframe", false, "", new Att[]{a_src_51, a_width_62, a_height_76,a_frameborder,a_allowfullscreen, a_id_0}, null, null, "") ,
				
				
				new TagImpl("video", false, "", new Att[]{a_controls,a_autoplay,a_loop,a_muted,a_poster,a_src_51,a_height_76,a_width_62, a_id_0}, null, null, "") });
		
		
				for (Command cmd : CommandRepo.one().getCommands()) {
					Tag def = cmd.getDef();
					if(def==null)
						continue;
					tagDefs.put(cmd.getCmdStr(), def);
				}
	}

	
	private final static Att a_allowfullscreen= new AttImpl("allowfullscreen", false, 1,
			new String[] {}, 2, "");
	private final static Att a_frameborder= new AttImpl("frameborder", false, 1,
			new String[] {}, 2, "");
	
	private final static Att a_controls = new AttImpl("controls", false, 1,
			new String[] {}, 2, "");
	private final static Att a_autoplay = new AttImpl("autoplay", false, 1,
			new String[] {}, 2, "");
	private final static Att a_loop = new AttImpl("loop", false, 1,
			new String[] {}, 2, "");
	private final static Att a_muted = new AttImpl("muted", false, 1,
			new String[] {}, 2, "");
	private final static Att a_poster = new AttImpl("poster", false, 1,
			new String[] {}, 2, "");

}
