/**
 * Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

// This file contains style definitions that can be used by CKEditor plugins.
//
// The most common use for it is the "stylescombo" plugin, which shows a combo
// in the editor toolbar, containing all styles. Other plugins instead, like
// the div plugin, use a subset of the styles on their feature.
//
// If you don't have plugins that depend on this file, you can simply ignore it.
// Otherwise it is strongly recommended to customize this file to match your
// website requirements and design properly.

CKEDITOR.stylesSet.add( 'default', [
	/* Block Styles */

	{ name: 'h_1',		element: 'h2', attributes: { 'class': 'h_1' } },
	{ name: 'h_2',		element: 'h2', attributes: { 'class': 'h_2' } },
	{ name: 'h_3',		element: 'h2', attributes: { 'class': 'h_3' } },
	{ name: 'h_4',		element: 'h2', attributes: { 'class': 'h_4' } },
	
	{ name: 'ul_arrow',	element: 'ul',	attributes: { 'class': 'ul_arrow' } },
	{ name: 'ul_square',	element: 'ul',	attributes: { 'class': 'ul_square' } },
	{ name: 'ul_rounded',	element: 'ul',	attributes: { 'class': 'ul_rounded' } },	
	
	{
		name: 'Special Container',
		element: 'div',
		styles: {
			padding: '5px 10px',
			background: '#eee',
			border: '1px solid #ccc'
		}
	},

	/* Inline Styles */

//	{ name: 'Big',				element: 'big' },
//	{ name: 'Small',			element: 'small' },
//	{ name: 'Typewriter',		element: 'tt' },
//
//	{ name: 'Computer Code',	element: 'code' },
//	{ name: 'Keyboard Phrase',	element: 'kbd' },
//	{ name: 'Sample Text',		element: 'samp' },
//	{ name: 'Variable',			element: 'var' },
//
//	{ name: 'Deleted Text',		element: 'del' },
//	{ name: 'Inserted Text',	element: 'ins' },
//
//	{ name: 'Cited Work',		element: 'cite' },
//	{ name: 'Inline Quotation',	element: 'q' },

	{
		name: 'Compact table',
		element: 'table',
		attributes: {
			cellpadding: '5',
			cellspacing: '0',
			border: '1',
			bordercolor: '#ccc'
		},
		styles: {
			'border-collapse': 'collapse'
		}
	},

	{ name: 'Borderless Table',		element: 'table',	styles: { 'border-style': 'hidden', 'background-color': '#E6E6FA' } }

] );

