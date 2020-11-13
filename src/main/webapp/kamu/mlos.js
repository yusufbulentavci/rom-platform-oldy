{
	"codes": {
		"w:searchpage": {
			"runzone": false,
			"title": "w:searchpage",
			"params": {},
			"childs": {
				"a": {
					"class": "body-inner"
				},
				"c": [{
					"t": "w:head",
					"m": true
				}, {
					"t": "w:searchcontent",
					"m": true
				}, {
					"t": "w:footer",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": ".hide"
		},
		"r:19": [{
			"a": {
				"class": "commentbutton"
			},
			"t": "div",
			"tx": "Yorum yazabilmek için giriş yapmalısınız.",
			"m": true
		}],
		"r:300": [],
		"r:420": [{
			"c": [{
				"a": {
					"class": "list_passive_anchor"
				},
				"c": [{
					"p": {
						"val1": "${_fe_nav_page_id2}",
						"name": "show",
						"then": "r:419",
						"var1": "_fe_pageid2"
					},
					"t": "c:onclick",
					"m": true
				}],
				"t": "span",
				"tx": "${_fe_nav_page_id2+1}",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:302": [],
		"r:301": [],
		"r:304": [],
		"r:303": [],
		"r:306": [],
		"r:305": [],
		"r:308": [],
		"r:307": [],
		"r:309": [],
		"r:11": [{
			"p": {
				"addr": "http://${_langdomain}.${note.topd}${note.uri}"
			},
			"t": "c:redirect",
			"m": true
		}],
		"r:12": [{
			"c": [{
				"a": {
					"value": "${_fe_item3}"
				},
				"c": [{
					"p": {
						"val": "true",
						"var": "selected",
						"when": "${_fe_key3==note.lang}"
					},
					"t": "c:setproperty",
					"m": true
				}],
				"t": "option",
				"tx": "${trns _fe_key3}",
				"m": true
			}],
			"t": "div",
			"tx": "${_fe_key3}-${_fe_val3}",
			"m": true
		}],
		"r:890": [],
		"r:892": [],
		"r:891": [],
		"r:894": [],
		"r:893": [],
		"w:comments": {
			"runzone": true,
			"title": "w:comments",
			"params": {
				"_maxcol": {
					"t": 0,
					"d": true,
					"v": "1"
				},
				"_view": {
					"t": 1,
					"d": true,
					"v": "weak"
				},
				"_maxpage": {
					"t": 0,
					"d": true,
					"v": "50"
				},
				"_maxrow": {
					"t": 0,
					"d": true,
					"v": "500"
				},
				"_fe_pageid2": {
					"t": 0,
					"d": true,
					"v": ""
				}
			},
			"childs": {
				"a": {
					"class": "inner wbasket"
				},
				"c": [{
					"a": {
						"class": "widget_header"
					},
					"c": [{
						"t": "h3",
						"tx": "Yorumlar",
						"m": true
					}],
					"t": "div",
					"m": true
				}, {
					"c": [{
						"c": [{
							"p": {
								"var": "_ref",
								"uri": "${item.dialog_uri}/comments.rom"
							},
							"t": "c:load",
							"m": true
						}, {
							"p": {
								"firstnavafter": "r:310",
								"rowbefore": "r:303",
								"pageout": "r:301",
								"rowin": "r:304",
								"styleprefix": "comm_${_view}",
								"secondnavin": "r:314",
								"firstnavout": "r:309",
								"pagein": "r:300",
								"maxcol": "1",
								"array": "${_ref}",
								"secondnavafter": "r:316",
								"item": "r:1254",
								"secondnavbefore": "r:313",
								"var": "2",
								"firstnavpassive": "r:312",
								"rowout": "r:305",
								"pageafter": "r:302",
								"firstnavin": "r:308",
								"maxpage": "1",
								"secondnavout": "r:315",
								"secondnavpassive": "r:318",
								"rowafter": "r:306",
								"secondnavactive": "r:317",
								"pagebefore": "r:299",
								"maxrow": "5000",
								"firstnavactive": "r:311",
								"firstnavbefore": "r:307"
							},
							"t": "c:foreach",
							"m": true
						}],
						"t": "div",
						"m": true
					}],
					"t": "div",
					"m": true
				}, {
					"a": {
						"class": "newcomment"
					},
					"c": [{
						"p": {
							"name": "loginwarn",
							"then": "r:19",
							"when": "${empty cid}",
							"likes": "cid"
						},
						"t": "c:changeable",
						"m": true
					}, {
						"a": {
							"class": "site-btn"
						},
						"c": [{
							"p": {
								"then": "r:1252"
							},
							"t": "c:onclick",
							"m": true
						}],
						"t": "button",
						"tx": "${trns \"writecomment\"}",
						"m": true
					}],
					"t": "div",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": ".hide"
		},
		"r:412": [],
		"r:896": [],
		"w:pageposter": {
			"runzone": false,
			"title": "w:pageposter",
			"params": {},
			"childs": {
				"s": {
					"defaultstyle": {
						"overflow": "hidden",
						"width": "100%",
						"height": "100%"
					}
				},
				"c": [{
					"a": {
						"src": "${item.large_icon}"
					},
					"s": {
						"defaultstyle": {
							"width": "100%",
							"height": "100%"
						}
					},
					"t": "img",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": "middle"
		},
		"r:895": [],
		"r:898": [],
		"r:414": [],
		"r:897": [],
		"r:413": [{
			"a": {
				"class": "car_before_anchor"
			},
			"c": [{
				"p": {
					"val1": "${(_fe_pagecount2+_fe_pageid2-1)%_fe_pagecount2}",
					"then": "r:412",
					"var1": "_fe_pageid2"
				},
				"t": "c:onclick",
				"m": true
			}],
			"t": "div",
			"tx": "${(_fe_pageid2-1+_fe_pagecount2)%_fe_pagecount2+1}",
			"m": true
		}],
		"r:415": [{
			"a": {
				"class": "car_after_anchor"
			},
			"c": [{
				"p": {
					"val1": "${(_fe_pageid2+1)%_fe_pagecount2}",
					"then": "r:414",
					"var1": "_fe_pageid2"
				},
				"t": "c:onclick",
				"m": true
			}],
			"t": "div",
			"tx": "${(_fe_pageid2+1)%_fe_pagecount2+1}",
			"m": true
		}],
		"r:899": [],
		"r:418": [{
			"c": [{
				"a": {
					"class": "list_active_anchor"
				},
				"c": [{
					"p": {
						"val1": "${_fe_nav_page_id2  + 1}",
						"name": "show",
						"then": "r:417",
						"var1": "_fe_pageid2"
					},
					"t": "c:onclick",
					"m": true
				}],
				"t": "span",
				"tx": "${_fe_nav_page_id2+1}",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:417": [],
		"r:419": [],
		"r:1200": [{
			"c": [{
				"a": {
					"method": "post",
					"action": "/_auth/logout.rom",
					"id": "logout_form",
					"class": "wauth_logout"
				},
				"c": [{
					"t": "c:forminline",
					"m": true
				}, {
					"a": {
						"name": "n",
						"type": "submit",
						"value": "${trns 'logout'}",
						"class": "wauth_logout_submit"
					},
					"t": "input",
					"m": true
				}],
				"t": "form",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:1201": [{
			"c": [{
				"a": {
					"class": "wauth_login"
				},
				"c": [{
					"p": {
						"goal": "login",
						"then": "r:986"
					},
					"t": "c:onclick",
					"m": true
				}],
				"t": "a",
				"tx": "${trns 'login'}",
				"m": true
			}],
			"t": "div",
			"m": true
		}, {
			"c": [{
				"a": {
					"class": "wauth_register"
				},
				"c": [{
					"p": {
						"goal": "register",
						"then": "r:987"
					},
					"t": "c:onclick",
					"m": true
				}],
				"t": "a",
				"tx": "${trns 'register'}",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:881": [],
		"w:searchblock": {
			"runzone": true,
			"title": "w:searchblock",
			"params": {
				"_pagesize": {
					"t": 0,
					"v": "20"
				},
				"_view": {
					"t": 1,
					"e": ["weak", "strong"],
					"v": "weak"
				},
				"_searchphrase": {
					"t": 1,
					"d": true,
					"v": ""
				}
			},
			"childs": {
				"a": {
					"class": "inner wsearchresult"
				},
				"c": [{
					"p": {
						"val": "${o_searchphrase}",
						"var": "_searchphrase"
					},
					"t": "c:set",
					"m": true
				}, {
					"a": {
						"class": "searchform"
					},
					"c": [{
						"a": {
							"type": "text",
							"value": "${_searchphrase}",
							"class": "winput"
						},
						"c": [{
							"p": {
								"val1": "\\${event.value}",
								"then": "r:1223",
								"var1": "_searchphrase"
							},
							"t": "c:onchange",
							"m": true
						}],
						"t": "input",
						"m": true
					}, {
						"a": {
							"name": "${trns 'search'}",
							"type": "button",
							"title": "${trns 'search'}",
							"class": "wbutton",
							"value": "${trns 'search'}"
						},
						"c": [{
							"p": {
								"val1": "searchnow",
								"then": "r:1224",
								"var1": "_goal"
							},
							"t": "c:onclick",
							"m": true
						}],
						"t": "input",
						"m": true
					}],
					"t": "div",
					"m": true
				}, {
					"p": {
						"then": "r:207",
						"when": "${_goal=='start' || _goal=='searchnow'}",
						"likes": "_goal"
					},
					"c": [],
					"t": "c:changeable",
					"m": true
				}, {
					"p": {
						"guardgoal": "searchshow",
						"then": "r:215"
					},
					"c": [],
					"t": "c:changeable",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": "middle"
		},
		"r:883": [],
		"r:882": [],
		"r:885": [],
		"r:884": [],
		"r:887": [],
		"r:886": [],
		"r:889": [],
		"r:768": [{
			"p": {
				"var": "_ref",
				"list": "${_listuri}"
			},
			"t": "c:list",
			"m": true
		}, {
			"a": {
				"class": "wlist_top"
			},
			"c": [{
				"p": {
					"firstnavafter": "r:131",
					"rowbefore": "r:124",
					"pageout": "r:122",
					"rowin": "r:125",
					"styleprefix": "pricelist",
					"secondnavin": "r:135",
					"firstnavout": "r:130",
					"pagein": "r:121",
					"maxcol": "${_maxcol}",
					"array": "${_ref}",
					"secondnavafter": "r:137",
					"item": "r:767",
					"secondnavbefore": "r:134",
					"var": "2",
					"firstnavpassive": "r:133",
					"rowout": "r:126",
					"pageafter": "r:123",
					"firstnavin": "r:129",
					"maxpage": "${_maxpage}",
					"secondnavout": "r:136",
					"secondnavpassive": "r:420",
					"rowafter": "r:127",
					"secondnavactive": "r:418",
					"pagebefore": "r:120",
					"maxrow": "${_maxrow}",
					"firstnavactive": "r:132",
					"firstnavbefore": "r:128"
				},
				"t": "c:foreach",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:888": [],
		"r:767": [{
			"a": {
				"class": "content"
			},
			"c": [{
				"a": {
					"href": "${_fe_item2.uri}",
					"class": "img_wrapper"
				},
				"c": [{
					"a": {
						"src": "${_fe_item2.medium_icon}",
						"class": "item_img"
					},
					"t": "img",
					"m": true
				}],
				"t": "a",
				"m": true
			}, {
				"a": {
					"class": "wlistitem_text"
				},
				"c": [{
					"a": {
						"href": "${_fe_item2.uri}"
					},
					"c": [{
						"a": {
							"class": "wlistitem_title"
						},
						"c": [{
							"p": {
								"strlen": "50",
								"threedots": "...",
								"clmn": "title",
								"wiki": "false",
								"symbl": "_fe_item2",
								"tbl": "site.writings"
							},
							"t": "c:text",
							"m": true
						}],
						"t": "span",
						"tx": "",
						"m": true
					}],
					"t": "a",
					"m": true
				}, {
					"a": {
						"class": "wlistitem_spot"
					},
					"c": [{
						"p": {
							"strlen": "150",
							"threedots": "...",
							"clmn": "summary",
							"wiki": "false",
							"symbl": "_fe_item2",
							"tbl": "site.contents"
						},
						"t": "c:text",
						"m": true
					}],
					"t": "span",
					"m": true
				}, {
					"p": {
						"goal": "addtocartsuccess",
						"then": "r:710"
					},
					"c": [],
					"t": "c:changeable",
					"m": true
				}, {
					"c": [{
						"a": {
							"class": "price"
						},
						"t": "span",
						"tx": "${money _fe_item2.nesting.stockprice} \n",
						"m": true
					}, {
						"p": {
							"when": "${_fe_item2.nesting.stockonsale == 'true'}"
						},
						"c": [{
							"a": {
								"method": "post",
								"action": "/_/_cart/add.rom"
							},
							"c": [{
								"p": {
									"var": "cartchanged"
								},
								"t": "c:forminline",
								"m": true
							}, {
								"a": {
									"name": "stock",
									"type": "hidden",
									"value": "${_fe_item2.nesting.stockuri}"
								},
								"t": "input",
								"m": true
							}, {
								"a": {
									"name": "diff",
									"type": "hidden",
									"value": "1"
								},
								"t": "input",
								"m": true
							}, {
								"a": {
									"name": "submitcart",
									"type": "submit",
									"value": "${trns 'addtocart'}",
									"class": "site-btn cart-btn"
								},
								"t": "input",
								"m": true
							}],
							"t": "form",
							"m": true
						}],
						"t": "c:if",
						"m": true
					}],
					"t": "div",
					"m": true
				}],
				"t": "div",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:31": [{
			"c": [{
				"a": {
					"class": "slider_active_anchor"
				},
				"t": "span",
				"tx": "${_fe_nav_page_id2}",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:38": [{
			"a": {
				"class": "item"
			},
			"c": [{
				"a": {
					"href": "${_fe_item2.uri}",
					"class": "${ item.uri == _fe_item2.uri ? 'selected' : '' }"
				},
				"t": "a",
				"tx": "${_fe_item2.title[0] uppercase  _fe_item2.langcodes[0]}",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:39": [{
			"a": {
				"class": ""
			},
			"c": [{
				"a": {
					"href": "${_fe_item2.uri}"
				},
				"c": [{
					"a": {
						"src": "${_fe_item2.uri}"
					},
					"t": "img",
					"m": true
				}, {
					"a": {
						"class": "wlistitem_text"
					},
					"c": [{
						"a": {
							"class": "wlistitem_title"
						},
						"t": "span",
						"tx": "${_fe_item2.title[0]}",
						"m": true
					}, {
						"a": {
							"class": "wlistitem_spot"
						},
						"t": "span",
						"tx": "tip...",
						"m": true
					}],
					"t": "div",
					"m": true
				}],
				"t": "a",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"w:breadcrumbs": {
			"runzone": true,
			"w": 315,
			"h": 25,
			"title": "w:breadcrumbs",
			"params": {},
			"childs": {
				"c": [{
					"p": {
						"var": "_ref",
						"uri": "${item.container}/breadcrumbs.rom?outform=json"
					},
					"t": "c:load",
					"m": true
				}, {
					"p": {
						"firstnavafter": "r:563",
						"rowbefore": "r:556",
						"pageout": "r:554",
						"rowin": "r:557",
						"styleprefix": "bcrumb_",
						"secondnavin": "r:567",
						"firstnavout": "r:562",
						"pagein": "r:553",
						"maxcol": "1",
						"array": "${_ref}",
						"secondnavafter": "r:569",
						"item": "r:551",
						"secondnavbefore": "r:566",
						"var": "2",
						"firstnavpassive": "r:565",
						"rowout": "r:558",
						"pageafter": "r:555",
						"firstnavin": "r:561",
						"maxpage": "1",
						"secondnavout": "r:568",
						"secondnavpassive": "r:571",
						"rowafter": "r:559",
						"secondnavactive": "r:570",
						"pagebefore": "r:552",
						"maxrow": "100",
						"firstnavactive": "r:564",
						"firstnavbefore": "r:560"
					},
					"c": [],
					"t": "c:foreach",
					"m": true
				}, {
					"t": "span",
					"tx": "${item.title[0]}",
					"m": true
				}],
				"t": "div",
				"tx": "",
				"m": true
			},
			"group": "header"
		},
		"w:noheaderfooter": {
			"runzone": false,
			"title": "w:noheaderfooter",
			"params": {},
			"childs": {
				"a": {
					"class": "body-inner"
				},
				"c": [{
					"a": {
						"class": "whead"
					},
					"c": [{
						"a": {
							"class": "inner"
						},
						"c": [{
							"p": {
								"when": "${info.login}"
							},
							"c": [{
								"a": {
									"class": "wheader_top"
								},
								"c": [{
									"p": {
										"when": "${info.ecommerce}"
									},
									"c": [{
										"t": "w:basket",
										"m": true
									}],
									"t": "c:if",
									"m": true
								}, {
									"t": "w:auth",
									"m": true
								}],
								"t": "div",
								"m": true
							}],
							"t": "c:if",
							"m": true
						}, {
							"a": {
								"class": "wheader_bottom"
							},
							"c": [{
								"a": {
									"class": "top"
								},
								"t": "div",
								"m": true
							}, {
								"a": {
									"class": "bottom"
								},
								"t": "div",
								"m": true
							}],
							"t": "div",
							"m": true
						}],
						"t": "div",
						"m": true
					}],
					"t": "div",
					"m": true
				}, {
					"t": "w:content",
					"m": true
				}, {
					"a": {
						"class": "wfooter"
					},
					"t": "div",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": ".hide"
		},
		"w:noheader": {
			"runzone": false,
			"title": "w:noheader",
			"params": {},
			"childs": {
				"a": {
					"class": "body-inner"
				},
				"c": [{
					"a": {
						"class": "whead"
					},
					"c": [{
						"a": {
							"class": "inner"
						},
						"c": [{
							"p": {
								"when": "${info.login}"
							},
							"c": [{
								"a": {
									"class": "wheader_top"
								},
								"c": [{
									"p": {
										"when": "${info.ecommerce}"
									},
									"c": [{
										"t": "w:basket",
										"m": true
									}],
									"t": "c:if",
									"m": true
								}, {
									"t": "w:auth",
									"m": true
								}],
								"t": "div",
								"m": true
							}],
							"t": "c:if",
							"m": true
						}, {
							"a": {
								"class": "wheader_bottom"
							},
							"c": [{
								"a": {
									"class": "top"
								},
								"t": "div",
								"m": true
							}, {
								"a": {
									"class": "bottom"
								},
								"t": "div",
								"m": true
							}],
							"t": "div",
							"m": true
						}],
						"t": "div",
						"m": true
					}],
					"t": "div",
					"m": true
				}, {
					"t": "w:content",
					"m": true
				}, {
					"t": "w:footer",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": ".hide"
		},
		"r:191": [],
		"r:190": [],
		"r:193": [],
		"r:192": [],
		"r:195": [],
		"r:194": [],
		"r:197": [],
		"r:196": [],
		"r:199": [],
		"r:198": [],
		"r:990": [{
			"a": {
				"method": "post",
				"action": "/_auth/register.rom",
				"id": "registerform",
				"class": "wauth_registerform"
			},
			"c": [{
				"p": {
					"sucgoal": "start"
				},
				"t": "c:forminline",
				"m": true
			}, {
				"a": {
					"class": "wauth_form_close"
				},
				"c": [{
					"p": {
						"goal": "start",
						"then": "r:989"
					},
					"t": "c:onclick",
					"m": true
				}],
				"t": "a",
				"m": true
			}, {
				"c": [{
					"c": [{
						"c": [{
							"t": "label",
							"tx": "${trns 'firstname'}:",
							"m": true
						}],
						"t": "td",
						"m": true
					}, {
						"c": [{
							"a": {
								"name": "firstname",
								"type": "text"
							},
							"t": "input",
							"m": true
						}],
						"t": "td",
						"m": true
					}],
					"t": "tr",
					"m": true
				}, {
					"c": [{
						"c": [{
							"t": "label",
							"tx": "${trns 'lastname'}:",
							"m": true
						}],
						"t": "td",
						"m": true
					}, {
						"c": [{
							"a": {
								"name": "lastname",
								"type": "text"
							},
							"t": "input",
							"m": true
						}],
						"t": "td",
						"m": true
					}],
					"t": "tr",
					"m": true
				}, {
					"c": [{
						"c": [{
							"t": "label",
							"tx": "${trns 'email'}:",
							"m": true
						}],
						"t": "td",
						"m": true
					}, {
						"c": [{
							"a": {
								"name": "email",
								"type": "text"
							},
							"t": "input",
							"m": true
						}],
						"t": "td",
						"m": true
					}],
					"t": "tr",
					"m": true
				}, {
					"c": [{
						"c": [{
							"t": "label",
							"tx": "${trns 'password'}:",
							"m": true
						}],
						"t": "td",
						"m": true
					}, {
						"c": [{
							"a": {
								"name": "credential",
								"type": "password"
							},
							"t": "input",
							"m": true
						}],
						"t": "td",
						"m": true
					}],
					"t": "tr",
					"m": true
				}, {
					"c": [{
						"a": {
							"colspan": "4"
						},
						"c": [{
							"a": {
								"name": "ss",
								"type": "submit",
								"value": "${trns 'register'}",
								"class": ""
							},
							"t": "input",
							"m": true
						}],
						"t": "td",
						"m": true
					}],
					"t": "tr",
					"m": true
				}],
				"t": "table",
				"m": true
			}],
			"t": "form",
			"m": true
		}],
		"r:630": [{
			"a": {
				"href": "${ not empty _selected_item.large_icon ? _selected_item.large_icon : _selected_item.uri }",
				"target": "_blank"
			},
			"c": [{
				"a": {
					"class": "galeri_img"
				},
				"c": [{
					"a": {
						"src": "${ not empty _selected_item.large_icon ? _selected_item.large_icon : _selected_item.uri }",
						"width": "100%",
						"class": "",
						"height": "100%"
					},
					"t": "img",
					"m": true
				}],
				"t": "div",
				"m": true
			}],
			"t": "a",
			"m": true
		}],
		"r:751": [],
		"r:750": [],
		"r:753": [],
		"r:752": [],
		"w:pricelist": {
			"runzone": true,
			"title": "w:pricelist",
			"params": {
				"_maxcol": {
					"r": true,
					"t": 0,
					"e": ["1", "2", "3", "4"],
					"v": "3"
				},
				"_view": {
					"t": 1,
					"e": ["weak", "strong"],
					"v": "weak"
				},
				"cartchanged": {
					"t": 1,
					"d": true,
					"v": ""
				},
				"_listuri": {
					"r": true,
					"t": 1,
					"v": ""
				},
				"_maxpage": {
					"t": 0,
					"d": true,
					"v": "100"
				},
				"_maxrow": {
					"r": true,
					"t": 0,
					"e": ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"],
					"v": "10"
				},
				"_fe_pageid2": {
					"t": 0,
					"d": true,
					"v": "0"
				}
			},
			"childs": {
				"a": {
					"class": "wpricelist inner"
				},
				"c": [{
					"p": {
						"immediate": "true",
						"var": "_fe_pageid2",
						"effect": "fade",
						"name": "show",
						"then": "r:768",
						"when": "${true}",
						"append": "false",
						"likes": "_fe_pageid2"
					},
					"t": "c:changeable",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": "ecommerce"
		},
		"r:755": [],
		"r:754": [],
		"w:gallery": {
			"runzone": true,
			"title": "w:gallery",
			"params": {
				"_selected_item": {
					"t": 4,
					"d": true,
					"v": ""
				},
				"_maxcol": {
					"r": true,
					"t": 0,
					"e": ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"],
					"v": "3"
				},
				"_listitems": {
					"c": 16,
					"t": 5
				},
				"_view": {
					"r": true,
					"t": 1,
					"e": ["weak", " strong"],
					"v": "weak"
				},
				"_listuri": {
					"c": 2,
					"t": 1
				},
				"_maxpage": {
					"r": true,
					"t": 0,
					"d": true,
					"v": "100"
				},
				"_maxrow": {
					"r": true,
					"t": 0,
					"d": true,
					"e": ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"],
					"v": "10"
				},
				"_fe_pageid2": {
					"t": 0,
					"d": true,
					"v": ""
				},
				"_arritems": {
					"t": 5,
					"d": true
				}
			},
			"childs": {
				"a": {
					"class": "wgaleri inner"
				},
				"c": [{
					"p": {
						"when": "${_listuri}"
					},
					"c": [{
						"p": {
							"var": "_arritems",
							"list": "${_listuri}"
						},
						"t": "c:list",
						"m": true
					}],
					"t": "c:if",
					"m": true
				}, {
					"c": [{
						"p": {
							"arr": "${_listitems}",
							"var": "_arritems"
						},
						"t": "c:load",
						"m": true
					}],
					"t": "c:else",
					"m": true
				}, {
					"p": {
						"when": "${(len _arritems)>0}"
					},
					"c": [{
						"p": {
							"val": "${_arritems[0]}",
							"var": "_selected_item"
						},
						"t": "c:set",
						"m": true
					}],
					"t": "c:if",
					"m": true
				}, {
					"p": {
						"then": "r:630",
						"when": "${not empty _selected_item}",
						"likes": "_selected_item"
					},
					"t": "c:changeable",
					"m": true
				}, {
					"p": {
						"var": "_fe_pageid2",
						"effect": "fade",
						"then": "r:1251",
						"when": "${not empty _fe_pageid2}",
						"likes": "_fe_pageid2"
					},
					"t": "c:changeable",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": "listing"
		},
		"r:757": [],
		"r:756": [],
		"r:758": [],
		"w:files": {
			"runzone": false,
			"title": "w:files",
			"params": {
				"_maxcol": {
					"t": 0
				},
				"_view": {
					"t": 1
				},
				"_maxpage": {
					"t": 0
				},
				"_maxrow": {
					"t": 0
				},
				"_width": {
					"t": 0
				}
			},
			"childs": {
				"c": [{
					"p": {
						"immediate": "true",
						"var": "_fe_pageid2",
						"effect": "fade",
						"name": "show",
						"then": "r:42",
						"when": "${true}",
						"append": "false",
						"likes": "_fe_pageid2"
					},
					"t": "c:changeable",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": ".hide"
		},
		"r:340": [],
		"r:100": [],
		"r:463": [],
		"r:341": [],
		"r:465": [],
		"r:586": [{
			"a": {
				"class": "slider_before"
			},
			"c": [{
				"p": {
					"val1": "${(_fe_pagecount2+_fe_pageid2-1)%_fe_pagecount2}",
					"then": "r:585",
					"var1": "_fe_pageid2"
				},
				"t": "c:onclick",
				"m": true
			}],
			"t": "div",
			"tx": "once",
			"m": true
		}],
		"r:102": [],
		"r:344": [{
			"c": [{
				"a": {
					"class": "slider_passive_anchor"
				},
				"c": [{
					"p": {
						"val2": "${true}",
						"val1": "${_fe_nav_page_id2}",
						"then": "r:343",
						"var2": "_selected",
						"var1": "_fe_pageid2"
					},
					"t": "c:onclick",
					"m": true
				}],
				"t": "span",
				"tx": "${_fe_nav_page_id2}",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:585": [],
		"r:464": [],
		"r:101": [],
		"r:343": [],
		"r:467": [],
		"r:104": [],
		"r:466": [],
		"r:103": [],
		"r:469": [],
		"r:106": [],
		"r:468": [],
		"r:105": [],
		"r:108": [],
		"r:51": [],
		"r:900": [],
		"r:52": [],
		"r:107": [],
		"r:53": [],
		"r:109": [],
		"r:54": [],
		"w:youtube": {
			"runzone": false,
			"title": "w:youtube",
			"params": {
				"_autoplay": {
					"t": 0,
					"e": ["0", "1"],
					"v": "0"
				},
				"_videoid": {
					"r": true,
					"c": 12,
					"t": 1,
					"v": ""
				},
				"_loop": {
					"t": 0,
					"e": ["0", "1"],
					"v": "0"
				},
				"_controls": {
					"t": 0,
					"e": ["0", "1", "2"],
					"v": "1"
				}
			},
			"childs": {
				"a": {
					"allowfullscreen": "true",
					"src": "https://www.youtube.com/embed/${_videoid}?modestbranding=1&controls=${_controls}&autoplay=${_autoplay}&loop=${_loop}",
					"frameborder": "0"
				},
				"t": "iframe",
				"m": true
			},
			"group": "middle"
		},
		"r:50": [],
		"r:1199": [{
			"a": {
				"method": "post",
				"action": "/_auth/login.rom",
				"id": "loginform",
				"class": "wauth_loginform"
			},
			"c": [{
				"p": {
					"sucgoal": "start"
				},
				"t": "c:forminline",
				"m": true
			}, {
				"c": [{
					"c": [{
						"a": {
							"colspan": "2",
							"align": "right"
						},
						"c": [{
							"a": {
								"class": "wauth_form_close"
							},
							"c": [{
								"p": {
									"goal": "start",
									"then": "r:984"
								},
								"t": "c:onclick",
								"m": true
							}],
							"t": "a",
							"m": true
						}],
						"t": "td",
						"m": true
					}],
					"t": "tr",
					"m": true
				}, {
					"c": [{
						"c": [{
							"t": "label",
							"tx": "${trns 'username'}:",
							"m": true
						}],
						"t": "td",
						"m": true
					}, {
						"c": [{
							"a": {
								"name": "user",
								"type": "text"
							},
							"t": "input",
							"m": true
						}],
						"t": "td",
						"m": true
					}],
					"t": "tr",
					"m": true
				}, {
					"c": [{
						"c": [{
							"t": "label",
							"tx": "${trns 'password'}:",
							"m": true
						}],
						"t": "td",
						"m": true
					}, {
						"c": [{
							"a": {
								"name": "credential",
								"type": "password"
							},
							"t": "input",
							"m": true
						}],
						"t": "td",
						"m": true
					}],
					"t": "tr",
					"m": true
				}, {
					"c": [{
						"a": {
							"align": "right"
						},
						"c": [{
							"a": {
								"name": "s",
								"type": "submit",
								"value": "login",
								"class": ""
							},
							"c": [],
							"t": "input",
							"m": true
						}],
						"t": "td",
						"m": true
					}],
					"t": "tr",
					"m": true
				}],
				"t": "table",
				"m": true
			}],
			"t": "form",
			"m": true
		}, {
			"c": [{
				"a": {
					"class": "wauth_register"
				},
				"c": [{
					"p": {
						"goal": "register",
						"then": "r:987"
					},
					"t": "c:onclick",
					"m": true
				}],
				"t": "a",
				"tx": "${trns 'register'}",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:59": [],
		"r:55": [],
		"r:56": [],
		"r:57": [],
		"r:58": [],
		"w:auth": {
			"runzone": false,
			"title": "w:auth",
			"params": {},
			"childs": {
				"a": {
					"class": "inner wauth_box"
				},
				"c": [{
					"p": {
						"goal": "start",
						"name": "in",
						"then": "r:1216",
						"when": "${not empty cid}",
						"likes": "cid"
					},
					"t": "c:changeable",
					"m": true
				}, {
					"p": {
						"name": "out",
						"then": "r:1221",
						"when": "${empty cid}",
						"likes": "cid"
					},
					"t": "c:changeable",
					"m": true
				}, {
					"p": {
						"goal": "login",
						"name": "login",
						"then": "r:1222",
						"when": "${empty cid}",
						"likes": "cid"
					},
					"t": "c:changeable",
					"m": true
				}, {
					"p": {
						"goal": "register",
						"name": "register",
						"then": "r:990",
						"when": "${empty cid}",
						"likes": "cid"
					},
					"t": "c:changeable",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": ".hide"
		},
		"r:571": [],
		"w:onlybody": {
			"runzone": false,
			"title": "w:onlybody",
			"params": {},
			"childs": {
				"a": {
					"class": "body-inner"
				},
				"c": [{
					"a": {
						"class": "whead"
					},
					"t": "div",
					"m": true
				}, {
					"t": "w:body",
					"m": true
				}, {
					"a": {
						"class": "wfooter"
					},
					"t": "div",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": ".hide"
		},
		"r:570": [],
		"w:nofooter": {
			"runzone": false,
			"title": "w:nofooter",
			"params": {},
			"childs": {
				"a": {
					"class": "body-inner"
				},
				"c": [{
					"t": "w:head",
					"m": true
				}, {
					"t": "w:content",
					"m": true
				}, {
					"a": {
						"class": "wfooter"
					},
					"t": "div",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": ".hide"
		},
		"r:572": [{
			"a": {
				"src": "/_public/images/flags/${_fe_key3}.png",
				"title": "${trns _fe_key3} - ${_fe_item3}",
				"class": "${ _fe_key3 == item.langcodes[0] ? 'selected' : '' }"
			},
			"c": [{
				"p": {
					"then": "r:485"
				},
				"t": "c:onclick",
				"m": true
			}],
			"t": "img",
			"m": true
		}],
		"r:214": [{
			"a": {
				"class": "searchitem"
			},
			"c": [{
				"a": {
					"class": "image"
				},
				"c": [{
					"a": {
						"href": "${_fe_item2.uri}",
						"class": "img_wrapper"
					},
					"c": [{
						"a": {
							"src": "${_fe_item2.medium_icon}",
							"width": "80px",
							"height": "60px"
						},
						"t": "img",
						"m": true
					}],
					"t": "a",
					"m": true
				}],
				"t": "div",
				"m": true
			}, {
				"a": {
					"class": "text"
				},
				"c": [{
					"a": {
						"href": "${_fe_item2.uri}"
					},
					"c": [{
						"t": "h5",
						"tx": "${_fe_item2.title[0]}",
						"m": true
					}],
					"t": "a",
					"m": true
				}, {
					"p": {
						"strlen": "150",
						"threedots": "...",
						"clmn": "summary",
						"wiki": "false",
						"symbl": "_fe_item2",
						"tbl": "site.contents"
					},
					"t": "c:text",
					"m": true
				}],
				"t": "div",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:215": [{
			"c": [{
				"p": {
					"firstnavafter": "r:198",
					"rowbefore": "r:191",
					"pageout": "r:189",
					"rowin": "r:192",
					"styleprefix": "search_${_view}",
					"secondnavin": "r:202",
					"firstnavout": "r:197",
					"pagein": "r:188",
					"maxcol": "1",
					"array": "${_searchres}",
					"secondnavafter": "r:204",
					"item": "r:214",
					"secondnavbefore": "r:201",
					"var": "2",
					"firstnavpassive": "r:200",
					"rowout": "r:193",
					"pageafter": "r:190",
					"firstnavin": "r:196",
					"maxpage": "10",
					"secondnavout": "r:203",
					"secondnavpassive": "r:206",
					"rowafter": "r:194",
					"secondnavactive": "r:205",
					"pagebefore": "r:187",
					"maxrow": "${_pagesize}",
					"firstnavactive": "r:199",
					"firstnavbefore": "r:195"
				},
				"t": "c:foreach",
				"m": true
			}],
			"t": "div",
			"m": true
		}, {
			"p": {
				"val": "searchseen",
				"var": "_goal"
			},
			"t": "c:set",
			"m": true
		}],
		"r:40": [{
			"c": [{
				"a": {
					"class": "list_active_anchor"
				},
				"c": [{
					"p": {
						"val1": "${_fe_nav_page_id2}",
						"name": "show",
						"var1": "_fe_page_id2"
					},
					"t": "c:onclick",
					"m": true
				}],
				"t": "span",
				"tx": "${_fe_nav_page_id2}",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:41": [{
			"c": [{
				"a": {
					"class": "list_passive_anchor"
				},
				"c": [{
					"p": {
						"val1": "${_fe_nav_page_id2}",
						"name": "show",
						"var1": "_fe_page_id2"
					},
					"t": "c:onclick",
					"m": true
				}],
				"t": "span",
				"tx": "${_fe_nav_page_id2}",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:42": [{
			"p": {
				"val": "${item.list}",
				"var": "_ref"
			},
			"t": "c:set",
			"m": true
		}, {
			"a": {
				"class": "wlist_top"
			},
			"c": [{
				"p": {
					"secondnavpassive": "r:41",
					"item": "r:39",
					"maxcol": "${_maxcol}",
					"array": "_ref",
					"secondnavactive": "r:40",
					"var": "2",
					"styleprefix": "list_${_view}",
					"maxrow": "${_maxrow}",
					"maxpage": "${_maxpage}"
				},
				"t": "c:foreach",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:43": [{
			"a": {
				"class": "contactmsg"
			},
			"t": "div",
			"tx": "${trns 'contactsuccess'}",
			"m": true
		}],
		"w:basket": {
			"runzone": true,
			"title": "w:basket",
			"params": {
				"_switchshow": {
					"r": true,
					"t": 3,
					"d": true,
					"v": "false"
				},
				"_showcheckout": {
					"r": true,
					"t": 0,
					"d": true,
					"v": "-1"
				}
			},
			"childs": {
				"a": {
					"class": "inner wbasket_inner"
				},
				"s": {
					"defaultstyle": {
						"display": "inline-block"
					}
				},
				"c": [{
					"p": {
						"name": "addcartsuc",
						"then": "r:1143",
						"when": "${true}",
						"likes": "cartchanged"
					},
					"c": [],
					"t": "c:changeable",
					"m": true
				}, {
					"p": {
						"tickperiod": "20",
						"immediate": "false",
						"name": "showcheckout",
						"then": "r:1152",
						"when": "${_showcheckout>0}",
						"likes": "_switchshow"
					},
					"c": [],
					"t": "c:changeable",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": ".hide"
		},
		"w:body": {
			"runzone": false,
			"title": "w:body",
			"params": {},
			"childs": {
				"a": {
					"class": "wbody_inner"
				},
				"c": [{
					"p": {
						"clmn": "body",
						"wiki": "true",
						"symbl": "item",
						"tbl": "site.writings"
					},
					"t": "c:text",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": ".hide"
		},
		"r:48": [],
		"w:logo": {
			"runzone": false,
			"title": "w:logo",
			"params": {
				"_font": {
					"c": 9,
					"t": 4,
					"v": "{\"fontfamily\":\"Cambria,Palatino Linotype,Book Antiqua,URW Palladio L,serif\", \"fontsize\":\"40px\", \"fontweight\":\"bold\", \"fontstyle\":\"normal\", \"color\":\"#3F4041\"}"
				},
				"_imgheight": {
					"c": 17,
					"t": 1
				},
				"_image": {
					"c": 5,
					"t": 1,
					"v": "/_public/images/check.png"
				},
				"_text": {
					"c": 11,
					"t": 1,
					"v": ""
				}
			},
			"childs": {
				"a": {
					"class": "inner wlogo_top"
				},
				"c": [{
					"a": {
						"href": "/",
						"class": ""
					},
					"c": [{
						"c": [{
							"c": [{
								"c": [{
									"a": {
										"src": "${_image}",
										"width": "",
										"class": "",
										"height": "${_imgheight}"
									},
									"s": {
										"defaultstyle": {
											"vertical-align": "middle"
										}
									},
									"t": "img",
									"m": true
								}],
								"t": "td",
								"m": true
							}, {
								"c": [{
									"s": {
										"defaultstyle": {
											"color": "${_font.color}",
											"font-weight": "${_font.fontweight}",
											"display": "inline-block",
											"font-size": "${_font.fontsize}",
											"font-family": "${_font.fontfamily}",
											"line-height": "${_font.lineheight}",
											"font-style": "${_font.fontstyle}"
										}
									},
									"t": "div",
									"tx": "${_text}",
									"m": true
								}],
								"t": "td",
								"m": true
							}],
							"t": "tr",
							"m": true
						}],
						"t": "table",
						"m": true
					}],
					"t": "a",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": "header"
		},
		"r:44": [{
			"a": {
				"method": "post",
				"action": "/_guest/contactform.rom"
			},
			"c": [{
				"p": {
					"sucgoal": "contactsuccess"
				},
				"t": "c:forminline",
				"m": true
			}, {
				"c": [{
					"c": [{
						"c": [{
							"t": "label",
							"tx": "${trns 'subject'}:",
							"m": true
						}],
						"t": "td",
						"m": true
					}, {
						"c": [{
							"a": {
								"name": "title",
								"type": "text",
								"class": "winput"
							},
							"t": "input",
							"m": true
						}],
						"t": "td",
						"m": true
					}],
					"t": "tr",
					"m": true
				}, {
					"c": [{
						"c": [{
							"t": "label",
							"tx": "${trns 'email'}:",
							"m": true
						}],
						"t": "td",
						"m": true
					}, {
						"c": [{
							"a": {
								"name": "email",
								"type": "text",
								"class": "winput"
							},
							"t": "input",
							"m": true
						}],
						"t": "td",
						"m": true
					}],
					"t": "tr",
					"m": true
				}, {
					"c": [{
						"c": [{
							"t": "label",
							"tx": "${trns 'phone'}:",
							"m": true
						}],
						"t": "td",
						"m": true
					}, {
						"c": [{
							"a": {
								"name": "phone",
								"type": "text",
								"class": "winput"
							},
							"t": "input",
							"m": true
						}],
						"t": "td",
						"m": true
					}],
					"t": "tr",
					"m": true
				}, {
					"s": {
						"defaultstyle": {
							"display": "none"
						}
					},
					"c": [{
						"c": [{
							"t": "label",
							"tx": "${trns 'address'}:",
							"m": true
						}],
						"t": "td",
						"m": true
					}, {
						"c": [{
							"a": {
								"name": "addr",
								"type": "text",
								"class": "winput"
							},
							"t": "input",
							"m": true
						}],
						"t": "td",
						"m": true
					}],
					"t": "tr",
					"m": true
				}, {
					"c": [{
						"c": [{
							"t": "label",
							"tx": "${trns 'message'}:",
							"m": true
						}],
						"t": "td",
						"m": true
					}, {
						"c": [{
							"a": {
								"name": "body",
								"rows": "8",
								"cols": "60",
								"class": "winput"
							},
							"t": "textarea",
							"m": true
						}],
						"t": "td",
						"m": true
					}],
					"t": "tr",
					"m": true
				}, {
					"c": [{
						"a": {
							"colspan": "4"
						},
						"c": [{
							"a": {
								"name": "ss",
								"type": "submit",
								"value": "${trns 'send'}",
								"class": "wbutton"
							},
							"t": "input",
							"m": true
						}],
						"t": "td",
						"m": true
					}],
					"t": "tr",
					"m": true
				}],
				"t": "table",
				"m": true
			}],
			"t": "form",
			"m": true
		}],
		"r:47": [],
		"w:pageimage": {
			"runzone": false,
			"title": "w:pageimage",
			"params": {},
			"childs": {
				"s": {
					"defaultstyle": {
						"overflow": "hidden",
						"width": "100%",
						"height": "100%"
					}
				},
				"c": [{
					"a": {
						"src": "${item.medium_icon}"
					},
					"s": {
						"defaultstyle": {
							"width": "100%",
							"height": "100%"
						}
					},
					"t": "img",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": "middle"
		},
		"r:560": [],
		"r:562": [],
		"w:auth_combo": {
			"runzone": false,
			"title": "w:auth_combo",
			"params": {},
			"childs": {
				"a": {
					"class": "wauth_box"
				},
				"c": [{
					"c": [{
						"p": {
							"name": "usertitle",
							"then": "r:1028",
							"when": "${not empty cid}",
							"likes": "cid"
						},
						"t": "c:changeable",
						"m": true
					}, {
						"p": {
							"name": "guesttitle",
							"then": "r:1025",
							"when": "${empty cid}",
							"likes": "cid"
						},
						"t": "c:changeable",
						"m": true
					}, {
						"p": {
							"goal": "login",
							"name": "loginform",
							"then": "r:1199",
							"when": "${_showguestoptions and empty cid}",
							"likes": "cid"
						},
						"t": "c:changeable",
						"m": true
					}, {
						"p": {
							"name": "usermenu",
							"then": "r:1200",
							"when": "${_showuseroptions and not empty cid }",
							"likes": "cid, _showuseroptions"
						},
						"t": "c:changeable",
						"m": true
					}, {
						"p": {
							"goal": "register",
							"name": "registerform",
							"then": "r:990",
							"when": "${_showguestoptions and empty cid}",
							"likes": "cid"
						},
						"t": "c:changeable",
						"m": true
					}, {
						"p": {
							"name": "guestmenu",
							"then": "r:1201",
							"when": "${_showguestoptions and empty cid}",
							"likes": "cid, _showguestoptions }"
						},
						"t": "c:changeable",
						"m": true
					}],
					"t": "div",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": ".hide"
		},
		"r:561": [],
		"r:201": [],
		"r:322": [],
		"r:564": [],
		"w:gmap": {
			"runzone": false,
			"title": "w:gmap",
			"params": {
				"_googlemapsrc": {
					"t": 1,
					"v": "https://maps.google.com/maps?q=Hawaii&hl=tr&sll=19.901054,-155.577393&sspn=10.132801,13.392334&hnear=Hawaii&t=m&z=7"
				}
			},
			"childs": {
				"a": {
					"src": "${_googlemapsrc}&output=embed",
					"width": "100%",
					"frameborder": "0",
					"class": "inner",
					"height": "99%"
				},
				"t": "iframe",
				"m": true
			},
			"group": "middle"
		},
		"r:563": [],
		"r:200": [],
		"r:324": [],
		"r:203": [],
		"r:566": [],
		"r:323": [{
			"a": {
				"class": "car_before_anchor"
			},
			"c": [{
				"p": {
					"val1": "${(_fe_pagecount2+_fe_pageid2-1)%_fe_pagecount2}",
					"then": "r:322",
					"var1": "_fe_pageid2"
				},
				"t": "c:onclick",
				"m": true
			}],
			"t": "div",
			"tx": "${(_fe_pageid2-1+_fe_pagecount2)%_fe_pagecount2+1}",
			"m": true
		}],
		"r:202": [],
		"r:565": [],
		"r:568": [],
		"r:205": [],
		"r:567": [],
		"r:325": [{
			"a": {
				"class": "car_after_anchor"
			},
			"c": [{
				"p": {
					"val1": "${(_fe_pageid2+1)%_fe_pagecount2}",
					"then": "r:324",
					"var1": "_fe_pageid2"
				},
				"t": "c:onclick",
				"m": true
			}],
			"t": "div",
			"tx": "${(_fe_pageid2+1)%_fe_pagecount2+1}",
			"m": true
		}],
		"r:204": [],
		"r:207": [{
			"p": {
				"var": "_searchres",
				"uri": "/textsearch.rom?outform=json&search=${urlencode _searchphrase}"
			},
			"t": "c:load",
			"m": true
		}, {
			"p": {
				"val": "searchshow",
				"var": "_goal"
			},
			"t": "c:set",
			"m": true
		}],
		"r:569": [],
		"r:206": [],
		"w:carousel": {
			"runzone": true,
			"title": "w:carousel",
			"params": {
				"_maxcol": {
					"r": true,
					"c": 15,
					"t": 0,
					"v": "3"
				},
				"_fe_pagecount2": {
					"r": true,
					"t": 0,
					"d": true,
					"v": "100"
				},
				"_listitems": {
					"c": 16,
					"t": 5
				},
				"_view": {
					"r": true,
					"t": 1,
					"e": ["weak", "strong"],
					"v": "weak"
				},
				"_listuri": {
					"c": 2,
					"t": 1
				},
				"_maxpage": {
					"r": true,
					"t": 0,
					"d": true,
					"v": "1"
				},
				"_header": {
					"t": 1,
					"v": ""
				},
				"_maxrow": {
					"r": true,
					"t": 0,
					"d": true,
					"v": "1"
				},
				"_fe_pageid2": {
					"t": 0,
					"d": true,
					"v": "0"
				},
				"_arritems": {
					"t": 5,
					"d": true
				}
			},
			"childs": {
				"a": {
					"class": "inner wcarousel"
				},
				"c": [{
					"a": {
						"class": "widget_header"
					},
					"c": [{
						"p": {
							"when": "${_listuri}"
						},
						"c": [{
							"p": {
								"var": "_arritems",
								"list": "${_listuri}"
							},
							"t": "c:list",
							"m": true
						}],
						"t": "c:if",
						"m": true
					}, {
						"c": [{
							"p": {
								"arr": "${_listitems}",
								"var": "_arritems"
							},
							"t": "c:load",
							"m": true
						}],
						"t": "c:else",
						"m": true
					}, {
						"p": {
							"when": "${not empty _header}"
						},
						"c": [{
							"t": "h2",
							"tx": "${trns _header}",
							"m": true
						}],
						"t": "c:if",
						"m": true
					}],
					"t": "div",
					"m": true
				}, {
					"p": {
						"var": "_fe_pageid2",
						"effect": "fade",
						"then": "r:366",
						"when": "${not empty _fe_pageid2}",
						"likes": "_fe_pageid2"
					},
					"t": "c:changeable",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": "listing"
		},
		"r:1052": [{
			"p": {
				"var": "_wriref",
				"uri": "${_fe_item2.wuri}"
			},
			"t": "c:load",
			"m": true
		}, {
			"c": [{
				"a": {
					"class": "primg"
				},
				"c": [{
					"a": {
						"src": "${_wriref.medium_icon}",
						"width": "100px",
						"height": "100px"
					},
					"t": "img",
					"m": true
				}],
				"t": "div",
				"m": true
			}, {
				"a": {
					"class": "title"
				},
				"t": "div",
				"tx": "${_fe_item2.wtitle}",
				"m": true
			}, {
				"a": {
					"class": "price"
				},
				"t": "div",
				"tx": "${money _fe_item2.tariff.price}",
				"m": true
			}, {
				"a": {
					"class": "amount"
				},
				"c": [{
					"a": {
						"method": "post",
						"action": "/_/_cart/add.rom"
					},
					"c": [{
						"p": {
							"sucgoal": "start",
							"var": "cartchanged"
						},
						"t": "c:forminline",
						"m": true
					}, {
						"a": {
							"name": "stock",
							"type": "hidden",
							"value": "${_fe_key2}"
						},
						"t": "input",
						"m": true
					}, {
						"a": {
							"name": "diff",
							"type": "hidden",
							"value": "-1"
						},
						"t": "input",
						"m": true
					}, {
						"a": {
							"name": "decrease",
							"type": "submit",
							"value": "decrease",
							"class": "btn minus"
						},
						"t": "input",
						"m": true
					}],
					"t": "form",
					"m": true
				}, {
					"a": {
						"readonly": "readonly",
						"name": "diff",
						"type": "text",
						"value": "${int _fe_item2.amount}"
					},
					"s": {
						"defaultstyle": {
							"width": "30px"
						}
					},
					"t": "input",
					"m": true
				}, {
					"a": {
						"method": "post",
						"action": "/_/_cart/add.rom"
					},
					"c": [{
						"p": {
							"sucgoal": "start",
							"var": "cartchanged"
						},
						"t": "c:forminline",
						"m": true
					}, {
						"a": {
							"name": "stock",
							"type": "hidden",
							"value": "${_fe_key2}"
						},
						"t": "input",
						"m": true
					}, {
						"a": {
							"name": "diff",
							"type": "hidden",
							"value": "1"
						},
						"t": "input",
						"m": true
					}, {
						"a": {
							"name": "increase",
							"type": "submit",
							"value": "increase",
							"class": "btn plus"
						},
						"t": "input",
						"m": true
					}],
					"t": "form",
					"m": true
				}],
				"t": "div",
				"m": true
			}, {
				"a": {
					"class": "price"
				},
				"t": "div",
				"tx": "${money _fe_item2.price}",
				"m": true
			}],
			"t": "div",
			"tx": "",
			"m": true
		}],
		"w:price": {
			"runzone": false,
			"w": 200,
			"h": 120,
			"title": "w:price",
			"params": {},
			"childs": {
				"a": {
					"class": "inner"
				},
				"c": [{
					"p": {
						"var": "_refpr",
						"uri": "${item.stock_uri}"
					},
					"t": "c:load",
					"m": true
				}, {
					"a": {
						"class": "lbl"
					},
					"t": "span",
					"tx": "${trns 'price'}:",
					"m": true
				}, {
					"a": {
						"class": "price"
					},
					"t": "span",
					"tx": "${money _refpr.tariff.price} \n",
					"m": true
				}, {
					"p": {
						"when": "${_refpr.onsale}"
					},
					"c": [{
						"a": {
							"class": "site-btn"
						},
						"c": [{
							"p": {
								"then": "r:1255"
							},
							"t": "c:onclick",
							"m": true
						}],
						"t": "button",
						"tx": "${trns \"addtocart\"}",
						"m": true
					}],
					"t": "c:if",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": "ecommerce"
		},
		"w:warnout": {
			"runzone": false,
			"title": "w:warnout",
			"params": {},
			"childs": {
				"a": {
					"id": "warnout",
					"class": "inner warnout"
				},
				"c": [{
					"a": {
						"id": "warnoutin",
						"class": "warnoutin"
					},
					"t": "div",
					"m": true
				}, {
					"a": {
						"class": "wwarnout_close"
					},
					"c": [{
						"p": {
							"hideitem": "warnout",
							"eventname": "onclick"
						},
						"t": "c:show",
						"m": true
					}],
					"t": "a",
					"m": true
				}, {
					"c": [{
						"p": {
							"clearitem": "warnoutin",
							"eventname": "onclick"
						},
						"t": "c:show",
						"m": true
					}],
					"t": "a",
					"tx": "x",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": ".hide"
		},
		"w:submenu": {
			"runzone": true,
			"title": "w:submenu",
			"params": {
				"_listitems": {
					"c": 16,
					"t": 5
				},
				"_view": {
					"r": true,
					"t": 1,
					"e": ["weak", "strong", "hrzntl"],
					"v": "weak"
				},
				"_listuri": {
					"c": 2,
					"t": 1
				},
				"_header": {
					"t": 1,
					"v": ""
				},
				"_itemcount": {
					"r": true,
					"c": 15,
					"t": 0,
					"v": "10"
				},
				"_arritems": {
					"t": 5,
					"d": true
				}
			},
			"childs": {
				"a": {
					"class": "wsubmenu inner"
				},
				"c": [{
					"a": {
						"class": "widget_header"
					},
					"c": [{
						"t": "h3",
						"tx": "${trns _header}",
						"m": true
					}],
					"t": "div",
					"m": true
				}, {
					"p": {
						"when": "${_listuri}"
					},
					"c": [{
						"p": {
							"var": "_arritems",
							"list": "${_listuri}"
						},
						"t": "c:list",
						"m": true
					}],
					"t": "c:if",
					"m": true
				}, {
					"c": [{
						"p": {
							"arr": "${_listitems}",
							"var": "_arritems"
						},
						"t": "c:load",
						"m": true
					}],
					"t": "c:else",
					"m": true
				}, {
					"c": [{
						"p": {
							"firstnavafter": "r:110",
							"rowbefore": "r:103",
							"pageout": "r:101",
							"rowin": "r:104",
							"styleprefix": "sm_${_view}_",
							"secondnavin": "r:114",
							"firstnavout": "r:109",
							"pagein": "r:100",
							"maxcol": "1",
							"array": "${_arritems}",
							"secondnavafter": "r:116",
							"item": "r:626",
							"secondnavbefore": "r:113",
							"var": "2",
							"firstnavpassive": "r:112",
							"rowout": "r:105",
							"pageafter": "r:102",
							"firstnavin": "r:108",
							"maxpage": "1",
							"secondnavout": "r:115",
							"secondnavpassive": "r:118",
							"rowafter": "r:106",
							"secondnavactive": "r:117",
							"pagebefore": "r:99",
							"maxrow": "${_itemcount}",
							"firstnavactive": "r:111",
							"firstnavbefore": "r:107"
						},
						"t": "c:foreach",
						"m": true
					}],
					"t": "div",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": "listing"
		},
		"r:551": [{
			"a": {
				"class": "${ item.uri == _fe_item2.uri ? 'selected' : '' }"
			},
			"c": [{
				"a": {
					"href": "${_fe_item2.uri}"
				},
				"t": "a",
				"tx": "${_fe_item2.title[0]}",
				"m": true
			}, {
				"s": {
					"defaultstyle": {
						"padding": "0 8px"
					}
				},
				"t": "span",
				"tx": "/",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:553": [],
		"r:311": [],
		"r:552": [],
		"r:310": [],
		"r:555": [],
		"r:313": [],
		"r:554": [],
		"r:312": [],
		"r:557": [],
		"r:315": [],
		"r:314": [],
		"r:556": [],
		"r:317": [],
		"r:559": [],
		"r:62": [],
		"r:63": [],
		"r:316": [],
		"r:558": [],
		"r:1061": [{
			"p": {
				"addr": "/_public/contact.html?tab=orders"
			},
			"t": "c:redirect",
			"m": true
		}],
		"r:318": [],
		"r:1062": [{
			"p": {
				"addr": "/_public/contact.html?tab=orders"
			},
			"t": "c:redirect",
			"m": true
		}],
		"r:1063": [{
			"p": {
				"val": "''",
				"var": "_text"
			},
			"t": "c:set",
			"m": true
		}],
		"r:60": [],
		"r:61": [],
		"r:261": [],
		"r:260": [],
		"r:263": [],
		"r:262": [],
		"r:265": [],
		"r:264": [],
		"w:cafetv": {
			"runzone": false,
			"w": 480,
			"h": 375,
			"title": "w:cafetv",
			"params": {},
			"childs": {
				"a": {
					"id": "cafetv",
					"class": "inner"
				},
				"s": {
					"defaultstyle": {
						"background-repeat": "no-repeat",
						"background-size": "98% 98%",
						"width": "99%",
						"background-image": "url(\"/_static/img/etc/tv.jpeg\")",
						"height": "99%"
					}
				},
				"c": [],
				"t": "div",
				"m": true
			},
			"group": "middle"
		},
		"r:267": [],
		"r:266": [],
		"r:269": [],
		"w:welcome": {
			"runzone": false,
			"title": "w:welcome",
			"params": {},
			"childs": {
				"a": {
					"class": "inner"
				},
				"s": {
					"defaultstyle": {
						"overflow": "hidden",
						"width": "100%",
						"height": "100%"
					}
				},
				"c": [{
					"a": {
						"src": "http://home.mlos.net/_public/welcome.html",
						"width": "100%",
						"frameborder": "0",
						"height": "100%"
					},
					"t": "iframe",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": "other"
		},
		"r:268": [],
		"r:95": [],
		"r:96": [],
		"r:97": [],
		"r:91": [],
		"r:1152": [{
			"a": {
				"class": "site-msg"
			},
			"c": [{
				"a": {
					"class": "site-fancywarn"
				},
				"c": [{
					"a": {
						"class": "site-warnarrow"
					},
					"c": [{
						"a": {
							"class": "line3"
						},
						"t": "div",
						"m": true
					}, {
						"a": {
							"class": "line4"
						},
						"t": "div",
						"m": true
					}, {
						"a": {
							"class": "line5"
						},
						"t": "div",
						"m": true
					}, {
						"a": {
							"class": "line6"
						},
						"t": "div",
						"m": true
					}, {
						"a": {
							"class": "line7"
						},
						"t": "div",
						"m": true
					}, {
						"a": {
							"class": "line8"
						},
						"t": "div",
						"m": true
					}, {
						"a": {
							"class": "line9"
						},
						"t": "div",
						"m": true
					}, {
						"a": {
							"class": "line10"
						},
						"t": "div",
						"m": true
					}],
					"t": "div",
					"m": true
				}, {
					"a": {
						"class": "site-warncontent"
					},
					"c": [{
						"a": {
							"href": "/cart"
						},
						"c": [{
							"a": {
								"class": "site-link"
							},
							"t": "div",
							"tx": "${trns 'gotoCart'}",
							"m": true
						}],
						"t": "a",
						"m": true
					}],
					"t": "div",
					"tx": "",
					"m": true
				}],
				"t": "div",
				"tx": "",
				"m": true
			}],
			"t": "div",
			"m": true
		}, {
			"p": {
				"val": "${_showcheckout-1}",
				"var": "_showcheckout"
			},
			"t": "c:set",
			"m": true
		}, {
			"p": {
				"val": "${false}",
				"var": "_switchshow"
			},
			"t": "c:set",
			"m": true
		}],
		"r:92": [],
		"r:93": [],
		"r:1274": [{
			"p": {
				"val": "${_fe_pageid2}"
			},
			"t": "c:log",
			"m": true
		}, {
			"p": {
				"val": "${false}",
				"var": "_selected"
			},
			"t": "c:set",
			"m": true
		}],
		"w:video": {
			"runzone": false,
			"title": "w:video",
			"params": {
				"_autoplay": {
					"t": 0,
					"e": ["0", "1"],
					"v": "0"
				},
				"_link": {
					"c": 3,
					"t": 1
				},
				"_videoid": {
					"c": 12,
					"t": 1
				},
				"_loop": {
					"t": 0,
					"e": ["0", "1"],
					"v": "0"
				},
				"_controls": {
					"t": 0,
					"e": ["0", "1", "2"],
					"v": "1"
				}
			},
			"childs": {
				"a": {
					"class": "inner"
				},
				"c": [{
					"a": {
						"controls": "true",
						"src": "${_link}",
						"width": "100%",
						"height": "100%"
					},
					"s": {
						"defaultstyle": {
							"display": "${(empty _videoid)?block:none}"
						}
					},
					"t": "video",
					"m": true
				}, {
					"a": {
						"allowfullscreen": "true",
						"src": "https://www.youtube.com/embed/${_videoid}?modestbranding=1&controls=${_controls}&autoplay=${_autoplay}&loop=${_loop}",
						"frameborder": "0"
					},
					"s": {
						"defaultstyle": {
							"display": "${(empty _videoid)?none:block}"
						}
					},
					"t": "iframe",
					"m": true
				}],
				"t": "div",
				"tx": "${_companyinfo}",
				"m": true
			},
			"group": "middle"
		},
		"r:1275": [{
			"p": {
				"val": "${(_fe_pageid2+1)%(len _arritems)}",
				"var": "_fe_pageid2"
			},
			"t": "c:set",
			"m": true
		}],
		"r:94": [],
		"w:page_title": {
			"runzone": false,
			"w": 450,
			"h": 45,
			"title": "w:page_title",
			"params": {},
			"childs": {
				"c": [{
					"c": [],
					"t": "h1",
					"tx": "${item.title[0]}",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": "header"
		},
		"w:main_menu": {
			"runzone": true,
			"title": "w:main_menu",
			"params": {
				"_view": {
					"t": 1,
					"d": true,
					"e": ["hrzntl", "vrtcl"],
					"v": "hrzntl"
				},
				"_listuri": {
					"c": 2,
					"t": 1,
					"v": "/_/lists/public/main"
				}
			},
			"childs": {
				"a": {
					"class": "wmain"
				},
				"c": [{
					"p": {
						"var": "_ref",
						"list": "${_listuri}"
					},
					"t": "c:list",
					"m": true
				}, {
					"p": {
						"firstnavafter": "r:267",
						"rowbefore": "r:260",
						"pageout": "r:258",
						"rowin": "r:261",
						"styleprefix": "menu_${_view}_",
						"secondnavin": "r:271",
						"firstnavout": "r:266",
						"pagein": "r:257",
						"maxcol": "21",
						"array": "${_ref}",
						"secondnavafter": "r:273",
						"item": "r:38",
						"secondnavbefore": "r:270",
						"var": "2",
						"firstnavpassive": "r:269",
						"rowout": "r:262",
						"pageafter": "r:259",
						"firstnavin": "r:265",
						"maxpage": "1",
						"secondnavout": "r:272",
						"secondnavpassive": "r:275",
						"rowafter": "r:263",
						"secondnavactive": "r:274",
						"pagebefore": "r:256",
						"maxrow": "1",
						"firstnavactive": "r:268",
						"firstnavbefore": "r:264"
					},
					"c": [],
					"t": "c:foreach",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": "header"
		},
		"r:99": [],
		"r:131": [],
		"r:130": [],
		"r:1": [{
			"a": {
				"method": "post",
				"action": "/_sesfuncs/rtdlgsay"
			},
			"c": [{
				"t": "c:forminline",
				"m": true
			}, {
				"a": {
					"name": "msg"
				},
				"c": [{
					"t": "c:formfieldreset",
					"m": true
				}],
				"t": "textarea",
				"m": true
			}, {
				"a": {
					"name": "dlgid",
					"type": "hidden",
					"value": "${_dlgvar}"
				},
				"t": "input",
				"m": true
			}, {
				"a": {
					"name": "s",
					"type": "submit",
					"value": "Send",
					"class": "gwt-Button site-bold"
				},
				"t": "input",
				"tx": " ",
				"m": true
			}],
			"t": "form",
			"m": true
		}, {
			"p": {
				"item": "chatdialog",
				"toshow": "${true}"
			},
			"t": "c:show",
			"m": true
		}, {
			"p": {
				"item": "paintdiv",
				"toshow": "${true}"
			},
			"t": "c:show",
			"m": true
		}],
		"r:133": [],
		"r:0": [{
			"c": [{
				"c": [{
					"t": "p",
					"m": true
				}],
				"t": "c:text",
				"tx": "${event.msg} ${event.from}: ${event.param1}",
				"m": true
			}],
			"t": "p",
			"m": true
		}],
		"r:132": [],
		"r:3": [{
			"a": {
				"title": "begin",
				"type": "button",
				"class": "gwt-Button"
			},
			"c": [{
				"p": {
					"goal": "beonline"
				},
				"t": "c:onclick",
				"m": true
			}],
			"t": "button",
			"tx": "${_connectstr}",
			"m": true
		}],
		"r:135": [],
		"r:256": [],
		"r:134": [],
		"r:2": [{
			"p": {
				"sucgoal": "online",
				"warngoal": "deskoffline",
				"subject": "welcome",
				"name": "desk",
				"errgoal": "deskoffline",
				"dlgvar": "_dlgvar"
			},
			"t": "c:rtdesk",
			"m": true
		}],
		"r:90": [],
		"r:5": [{
			"a": {
				"src": "${event.param1}"
			},
			"t": "img",
			"m": true
		}],
		"r:137": [],
		"r:258": [],
		"r:257": [],
		"r:136": [],
		"r:4": [{
			"t": "p",
			"tx": "DESK OFFLINE",
			"m": true
		}],
		"r:259": [],
		"r:6": [{
			"a": {
				"src": "/_local/images/cursor_icon.png"
			},
			"t": "img",
			"m": true
		}],
		"r:84": [],
		"r:85": [],
		"r:86": [],
		"r:87": [],
		"r:80": [],
		"w:oldstandart": {
			"runzone": false,
			"title": "w:oldstandart",
			"params": {},
			"childs": {
				"a": {
					"class": "body-inner"
				},
				"c": [{
					"t": "w:head",
					"m": true
				}, {
					"a": {
						"class": "wcontent"
					},
					"c": [{
						"a": {
							"class": "wcontent_inner"
						},
						"c": [{
							"a": {
								"class": "body_top"
							},
							"c": [{
								"a": {
									"class": "top"
								},
								"t": "div",
								"m": true
							}, {
								"a": {
									"class": "bottom"
								},
								"t": "div",
								"m": true
							}],
							"t": "div",
							"m": true
						}, {
							"t": "w:vitrine",
							"m": true
						}, {
							"t": "w:body",
							"m": true
						}, {
							"p": {
								"when": "${not empty item.dialog_uri}"
							},
							"c": [{
								"p": {
									"_view": "strong"
								},
								"t": "w:comments",
								"m": true
							}],
							"t": "c:if",
							"m": true
						}, {
							"a": {
								"class": "body_bottom"
							},
							"t": "div",
							"m": true
						}],
						"t": "div",
						"m": true
					}],
					"t": "div",
					"m": true
				}, {
					"t": "w:footer",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": ".hide"
		},
		"r:81": [],
		"r:82": [],
		"r:83": [],
		"w:searchcontent": {
			"runzone": false,
			"title": "w:searchcontent",
			"params": {},
			"childs": {
				"a": {
					"class": "wcontent"
				},
				"c": [{
					"a": {
						"class": "wcontent_inner"
					},
					"c": [{
						"a": {
							"class": "body_top"
						},
						"c": [{
							"a": {
								"class": "top"
							},
							"t": "div",
							"m": true
						}, {
							"a": {
								"class": "bottom"
							},
							"t": "div",
							"m": true
						}],
						"t": "div",
						"m": true
					}, {
						"a": {
							"class": "wbody"
						},
						"c": [{
							"a": {
								"class": "wbody_inner"
							},
							"c": [{
								"t": "w:searchblock",
								"m": true
							}],
							"t": "div",
							"m": true
						}],
						"t": "div",
						"m": true
					}, {
						"a": {
							"class": "body_bottom"
						},
						"t": "div",
						"m": true
					}],
					"t": "div",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": ".hide"
		},
		"r:88": [],
		"r:89": [],
		"r:481": [],
		"r:480": [],
		"r:120": [],
		"r:483": [],
		"r:482": [],
		"r:485": [{
			"p": {
				"addr": "http://${_fe_item3}.${note.topd}${note.uri}"
			},
			"t": "c:redirect",
			"m": true
		}],
		"r:122": [],
		"r:121": [],
		"r:124": [],
		"r:366": [{
			"a": {
				"class": "wcarousel_top"
			},
			"c": [{
				"p": {
					"firstnavafter": "r:89",
					"rowbefore": "r:82",
					"pageout": "r:81",
					"rowin": "r:83",
					"styleprefix": "carousel_${_view}",
					"secondnavin": "r:93",
					"firstnavout": "r:88",
					"pagein": "r:80",
					"maxcol": "${_maxcol}",
					"array": "${_arritems}",
					"secondnavafter": "r:95",
					"item": "r:365",
					"secondnavbefore": "r:92",
					"var": "2",
					"firstnavpassive": "r:91",
					"rowout": "r:84",
					"pageafter": "r:325",
					"firstnavin": "r:87",
					"maxpage": "10",
					"secondnavout": "r:94",
					"secondnavpassive": "r:97",
					"rowafter": "r:85",
					"secondnavactive": "r:96",
					"pagebefore": "r:323",
					"maxrow": "1",
					"firstnavactive": "r:90",
					"firstnavbefore": "r:86"
				},
				"t": "c:foreach",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:123": [],
		"r:365": [{
			"a": {
				"class": "carousel_fe_item_inner"
			},
			"c": [{
				"a": {
					"href": "${_fe_item2.uri}"
				},
				"c": [{
					"s": {
						"defaultstyle": {
							"background-repeat": "no-repeat",
							"background-size": "100% 100%",
							"width": "100%",
							"background-image": "url(${_fe_item2.medium_icon})",
							"background-position": "0 bottom",
							"height": "100%"
						}
					},
					"t": "div",
					"m": true
				}],
				"t": "a",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:126": [],
		"r:125": [],
		"r:128": [],
		"r:127": [],
		"r:129": [],
		"r:1250": [{
			"a": {
				"class": "galeri_nav_item"
			},
			"c": [{
				"a": {
					"src": "${_fe_item2.medium_icon}"
				},
				"c": [{
					"p": {
						"val1": "${_fe_item2}",
						"then": "r:1249",
						"var1": "_selected_item"
					},
					"t": "c:onclick",
					"m": true
				}],
				"t": "img",
				"m": true
			}, {
				"a": {
					"class": "img_wrapper"
				},
				"s": {
					"defaultstyle": {
						"background-repeat": "no-repeat",
						"background-size": "100% 100%",
						"display": "none",
						"width": "100%",
						"background-image": "url(${_fe_item2.medium_icon})",
						"background-position": "center",
						"height": "100%"
					}
				},
				"c": [],
				"t": "div",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:1130": [],
		"r:1251": [{
			"a": {
				"class": "galeri_nav"
			},
			"c": [{
				"p": {
					"firstnavafter": "r:170",
					"rowbefore": "r:163",
					"pageout": "r:162",
					"rowin": "r:164",
					"styleprefix": "galeri_${_view}",
					"secondnavin": "r:174",
					"firstnavout": "r:169",
					"pagein": "r:161",
					"maxcol": "${_maxcol}",
					"array": "${_arritems}",
					"secondnavafter": "r:176",
					"item": "r:1250",
					"secondnavbefore": "r:173",
					"var": "2",
					"firstnavpassive": "r:172",
					"rowout": "r:165",
					"pageafter": "r:415",
					"firstnavin": "r:168",
					"maxpage": "${_maxpage}",
					"secondnavout": "r:175",
					"secondnavpassive": "r:178",
					"rowafter": "r:166",
					"secondnavactive": "r:177",
					"pagebefore": "r:413",
					"maxrow": "1",
					"firstnavactive": "r:171",
					"firstnavbefore": "r:167"
				},
				"t": "c:foreach",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:1252": [{
			"t": "c:comment",
			"m": true
		}],
		"r:1131": [],
		"r:1132": [{
			"c": [{
				"p": {
					"var": "_ref",
					"uri": "/_/_cart/activeget.rom"
				},
				"t": "c:load",
				"m": true
			}, {
				"p": {
					"firstnavafter": "r:752",
					"rowbefore": "r:745",
					"pageout": "r:743",
					"rowin": "r:746",
					"styleprefix": "_",
					"secondnavin": "r:756",
					"firstnavout": "r:751",
					"pagein": "r:742",
					"maxcol": "1",
					"array": "${_ref.calcdetails}",
					"secondnavafter": "r:758",
					"item": "r:1052",
					"secondnavbefore": "r:755",
					"var": "2",
					"firstnavpassive": "r:754",
					"rowout": "r:747",
					"pageafter": "r:744",
					"firstnavin": "r:750",
					"maxpage": "1",
					"secondnavout": "r:757",
					"secondnavpassive": "r:1131",
					"rowafter": "r:748",
					"secondnavactive": "r:1130",
					"pagebefore": "r:741",
					"maxrow": "2000",
					"firstnavactive": "r:753",
					"firstnavbefore": "r:749"
				},
				"t": "c:foreach",
				"m": true
			}, {
				"p": {
					"val": "rendered",
					"var": "_goal"
				},
				"t": "c:set",
				"m": true
			}, {
				"a": {
					"class": "header"
				},
				"c": [{
					"a": {
						"class": "sumspace"
					},
					"t": "span",
					"tx": "",
					"m": true
				}, {
					"a": {
						"class": "sumtitle"
					},
					"t": "span",
					"tx": "${trns 'total'}",
					"m": true
				}, {
					"a": {
						"class": "price"
					},
					"t": "span",
					"tx": "${money _ref.itemsprice}",
					"m": true
				}],
				"t": "div",
				"m": true
			}, {
				"a": {
					"class": "header"
				},
				"c": [{
					"a": {
						"class": "sumspace"
					},
					"t": "span",
					"tx": "",
					"m": true
				}, {
					"a": {
						"class": "sumtitle"
					},
					"t": "span",
					"tx": "${trns 'shipprice'}",
					"m": true
				}, {
					"a": {
						"class": "price"
					},
					"t": "span",
					"tx": "${money _ref.shipprice}",
					"m": true
				}],
				"t": "div",
				"m": true
			}, {
				"a": {
					"class": "header"
				},
				"c": [{
					"a": {
						"class": "sumspace"
					},
					"t": "span",
					"tx": "",
					"m": true
				}, {
					"a": {
						"class": "sumtitle bold"
					},
					"t": "span",
					"tx": "${trns 'finaltotal'}",
					"m": true
				}, {
					"a": {
						"class": "price bold"
					},
					"t": "span",
					"tx": "${money _ref.totalprice}",
					"m": true
				}],
				"t": "div",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:1254": [{
			"a": {
				"class": "comment"
			},
			"c": [{
				"p": {
					"var": "_contact",
					"uri": "/_/_initials/getcontact.rom?uri=${_fe_item2.contact}"
				},
				"t": "c:load",
				"m": true
			}, {
				"a": {
					"class": "contact"
				},
				"c": [{
					"a": {
						"class": "comment_text"
					},
					"c": [{
						"a": {
							"class": "comment_title"
						},
						"t": "span",
						"tx": "${_contact.first_name} ${_contact.last_name} ",
						"m": true
					}, {
						"a": {
							"class": "comment_date"
						},
						"t": "span",
						"tx": "${_fe_item2.creation_date%1}",
						"m": true
					}, {
						"a": {
							"class": "comment_body"
						},
						"t": "p",
						"tx": "${_fe_item2.comment}",
						"m": true
					}],
					"t": "div",
					"m": true
				}],
				"t": "div",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:1255": [{
			"p": {
				"stock": "${item.stock_uri}"
			},
			"t": "c:addtocart",
			"m": true
		}],
		"r:1257": [{
			"t": "c:cartcheckout",
			"m": true
		}],
		"w:langs2": {
			"runzone": false,
			"title": "w:langs2",
			"params": {
				"_langdomain": {
					"t": 1,
					"d": true,
					"v": ""
				}
			},
			"childs": {
				"c": [{
					"p": {
						"firstnavafter": "r:475",
						"rowbefore": "r:468",
						"pageout": "r:466",
						"rowin": "r:469",
						"styleprefix": "_",
						"secondnavin": "r:479",
						"firstnavout": "r:474",
						"pagein": "r:465",
						"maxcol": "100",
						"array": "${note.domains}",
						"secondnavafter": "r:481",
						"item": "r:572",
						"secondnavbefore": "r:478",
						"var": "3",
						"firstnavpassive": "r:477",
						"rowout": "r:470",
						"pageafter": "r:467",
						"firstnavin": "r:473",
						"maxpage": "1",
						"secondnavout": "r:480",
						"secondnavpassive": "r:483",
						"rowafter": "r:471",
						"secondnavactive": "r:482",
						"pagebefore": "r:464",
						"maxrow": "1",
						"firstnavactive": "r:476",
						"firstnavbefore": "r:472"
					},
					"t": "c:foreach",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": ".hide"
		},
		"w:image": {
			"runzone": false,
			"title": "w:image",
			"params": {
				"_link": {
					"c": 3,
					"t": 1,
					"v": ""
				},
				"_imageuri": {
					"r": true,
					"c": 5,
					"t": 1,
					"v": ""
				}
			},
			"childs": {
				"a": {
					"class": "wimage_holder"
				},
				"c": [{
					"a": {
						"href": "${_link}"
					},
					"c": [{
						"a": {
							"src": "${_imageuri}"
						},
						"t": "img",
						"m": true
					}],
					"t": "a",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": "middle"
		},
		"r:470": [],
		"r:472": [],
		"r:471": [],
		"r:474": [],
		"r:111": [],
		"r:110": [],
		"r:473": [],
		"r:597": [{
			"a": {
				"class": "slider_after"
			},
			"c": [{
				"p": {
					"val1": "${(_fe_pageid2+1)%_fe_pagecount2}",
					"then": "r:596",
					"var1": "_fe_pageid2"
				},
				"t": "c:onclick",
				"m": true
			}],
			"t": "div",
			"tx": "after",
			"m": true
		}],
		"r:476": [],
		"r:113": [],
		"r:475": [],
		"r:112": [],
		"r:596": [],
		"r:115": [],
		"r:478": [],
		"r:477": [],
		"r:114": [],
		"r:117": [],
		"r:116": [],
		"r:479": [],
		"r:118": [],
		"w:slider": {
			"runzone": true,
			"title": "w:slider",
			"params": {
				"_selected": {
					"t": 3,
					"d": true,
					"v": "false"
				},
				"_maxcol": {
					"t": 0,
					"d": true,
					"v": "20"
				},
				"_listitems": {
					"c": 16,
					"t": 5
				},
				"_view": {
					"r": true,
					"t": 1,
					"e": ["weak", "strong"],
					"v": "strong"
				},
				"_listuri": {
					"c": 2,
					"t": 1
				},
				"_maxpage": {
					"t": 0,
					"d": true,
					"v": "20"
				},
				"_maxrow": {
					"t": 0,
					"d": true,
					"v": "1"
				},
				"_looking": {
					"t": 3,
					"d": true
				},
				"_fe_pageid2": {
					"r": true,
					"t": 0,
					"d": true,
					"v": "0"
				},
				"_tick": {
					"r": true,
					"c": 8,
					"t": 0,
					"v": "10"
				},
				"_arritems": {
					"t": 5,
					"d": true
				}
			},
			"childs": {
				"a": {
					"class": "wslider inner"
				},
				"s": {
					"defaultstyle": {
						"width": "100%",
						"height": "100%"
					}
				},
				"c": [{
					"p": {
						"tickperiod": "30",
						"immediate": "false",
						"then": "r:1274",
						"when": "${_selected}",
						"append": "false"
					},
					"c": [],
					"t": "c:changeable",
					"m": true
				}, {
					"p": {
						"immediate": "false",
						"tickperiod": "${_tick}",
						"name": "arttir",
						"then": "r:1275",
						"when": "${not _looking and not _selected}",
						"append": "false"
					},
					"c": [],
					"t": "c:changeable",
					"m": true
				}, {
					"p": {
						"immediate": "true",
						"var": "_fe_pageid2",
						"effect": "fade",
						"then": "r:1267",
						"when": "${not empty _fe_pageid2}",
						"append": "false",
						"likes": "_fe_pageid2"
					},
					"c": [],
					"t": "c:changeable",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": "listing"
		},
		"w:head": {
			"runzone": false,
			"title": "w:head",
			"params": {},
			"childs": {
				"a": {
					"class": "inner"
				},
				"c": [{
					"p": {
						"when": "${info.login}"
					},
					"c": [{
						"a": {
							"id": "romtop",
							"class": "wheader_top"
						},
						"c": [],
						"t": "div",
						"m": true
					}],
					"t": "c:if",
					"m": true
				}, {
					"a": {
						"class": "wheader_holder"
					},
					"c": [{
						"p": {
							"clmn": "headertext",
							"wiki": "true",
							"symbl": "info",
							"tbl": "site.info"
						},
						"t": "c:text",
						"m": true
					}],
					"t": "div",
					"m": true
				}, {
					"a": {
						"class": "wheader_bottom"
					},
					"c": [{
						"a": {
							"class": "top"
						},
						"t": "div",
						"m": true
					}, {
						"a": {
							"class": "bottom"
						},
						"t": "div",
						"m": true
					}],
					"t": "div",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": ".hide"
		},
		"w:cartlist": {
			"runzone": true,
			"title": "w:cartlist",
			"params": {
				"cartchanged": {
					"t": 1,
					"d": true,
					"v": ""
				}
			},
			"childs": {
				"a": {
					"class": "inner cartholder"
				},
				"c": [{
					"a": {
						"class": "btnholder"
					},
					"s": {
						"defaultstyle": {
							"padding-bottom": "23px"
						}
					},
					"c": [{
						"a": {
							"class": "site-btn"
						},
						"c": [{
							"p": {
								"then": "r:1061"
							},
							"t": "c:onclick",
							"m": true
						}],
						"t": "a",
						"tx": "${trns 'completeorder'}",
						"m": true
					}],
					"t": "div",
					"tx": "",
					"m": true
				}, {
					"a": {
						"class": "header"
					},
					"c": [{
						"a": {
							"class": "title"
						},
						"t": "span",
						"m": true
					}, {
						"a": {
							"class": "title"
						},
						"t": "span",
						"tx": "${trns 'productdesc'}",
						"m": true
					}, {
						"a": {
							"class": "price"
						},
						"t": "span",
						"tx": "${trns 'price'}",
						"m": true
					}, {
						"a": {
							"class": "amount"
						},
						"t": "span",
						"tx": "${trns 'count'}",
						"m": true
					}, {
						"a": {
							"class": "price"
						},
						"t": "span",
						"tx": "${trns 'total'}",
						"m": true
					}],
					"t": "div",
					"m": true
				}, {
					"p": {
						"guardgoal": "start",
						"goal": "start",
						"then": "r:1132"
					},
					"c": [],
					"t": "c:changeable",
					"m": true
				}, {
					"a": {
						"class": "btnholder"
					},
					"s": {
						"defaultstyle": {
							"padding-top": "23px"
						}
					},
					"c": [{
						"a": {
							"class": "site-btn"
						},
						"c": [{
							"p": {
								"then": "r:1062"
							},
							"t": "c:onclick",
							"m": true
						}],
						"t": "a",
						"tx": "${trns 'completeorder'}",
						"m": true
					}],
					"t": "div",
					"tx": "",
					"m": true
				}],
				"t": "div",
				"tx": "",
				"m": true
			},
			"group": "ecommerce"
		},
		"r:1142": [{
			"p": {
				"val": "${int _itemcount  + int _fe_item2.amount}",
				"var": "_itemcount"
			},
			"t": "c:set",
			"m": true
		}],
		"r:1263": [{
			"a": {
				"class": "content"
			},
			"c": [{
				"a": {
					"href": "${_fe_item2.uri}"
				},
				"c": [{
					"a": {
						"src": "${_fe_item2.medium_icon}",
						"class": "item_img"
					},
					"t": "img",
					"m": true
				}],
				"t": "a",
				"m": true
			}, {
				"a": {
					"class": "wlistitem_text"
				},
				"c": [{
					"a": {
						"href": "${_fe_item2.uri}"
					},
					"c": [{
						"a": {
							"class": "wlistitem_title"
						},
						"c": [{
							"p": {
								"strlen": "50",
								"threedots": "...",
								"clmn": "title",
								"wiki": "false",
								"symbl": "_fe_item2",
								"tbl": "site.contents"
							},
							"t": "c:text",
							"m": true
						}],
						"t": "span",
						"m": true
					}],
					"t": "a",
					"m": true
				}, {
					"a": {
						"class": "wlistitem_spot"
					},
					"c": [{
						"p": {
							"strlen": "150",
							"threedots": "...",
							"clmn": "summary",
							"wiki": "false",
							"symbl": "_fe_item2",
							"tbl": "site.contents"
						},
						"t": "c:text",
						"m": true
					}],
					"t": "span",
					"m": true
				}],
				"t": "div",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"w:aboutus": {
			"runzone": false,
			"title": "w:aboutus",
			"params": {
				"_companyname": {
					"t": 1,
					"v": ""
				},
				"_companyinfo": {
					"c": 11,
					"t": 1,
					"v": ""
				}
			},
			"childs": {
				"a": {
					"class": "inner"
				},
				"c": [{
					"a": {
						"class": "widget_header"
					},
					"c": [{
						"t": "h3",
						"tx": "${_companyname}",
						"m": true
					}],
					"t": "div",
					"m": true
				}, {
					"t": "div",
					"tx": "${_companyinfo}",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": "middle"
		},
		"r:1143": [{
			"p": {
				"val": "${2}",
				"var": "_showcheckout"
			},
			"t": "c:set",
			"m": true
		}, {
			"p": {
				"val": "${true}",
				"var": "_switchshow"
			},
			"t": "c:set",
			"m": true
		}, {
			"p": {
				"var": "_ref",
				"uri": "/_/_cart/activeget.rom"
			},
			"t": "c:load",
			"m": true
		}, {
			"p": {
				"val": "no",
				"var": "cartchanged"
			},
			"t": "c:set",
			"m": true
		}, {
			"p": {
				"val": "0",
				"var": "_itemcount"
			},
			"t": "c:set",
			"m": true
		}, {
			"p": {
				"firstnavafter": "r:892",
				"rowbefore": "r:885",
				"pageout": "r:883",
				"rowin": "r:886",
				"styleprefix": "_basket_",
				"secondnavin": "r:896",
				"firstnavout": "r:891",
				"pagein": "r:882",
				"maxcol": "1",
				"array": "${_ref.items}",
				"secondnavafter": "r:898",
				"item": "r:1142",
				"secondnavbefore": "r:895",
				"var": "2",
				"firstnavpassive": "r:894",
				"rowout": "r:887",
				"pageafter": "r:884",
				"firstnavin": "r:890",
				"maxpage": "1",
				"secondnavout": "r:897",
				"secondnavpassive": "r:900",
				"rowafter": "r:888",
				"secondnavactive": "r:899",
				"pagebefore": "r:881",
				"maxrow": "1000",
				"firstnavactive": "r:893",
				"firstnavbefore": "r:889"
			},
			"c": [],
			"t": "c:foreach",
			"m": true
		}, {
			"a": {
				"href": "/cart"
			},
			"c": [{
				"a": {
					"class": "basket"
				},
				"t": "div",
				"tx": "( ${_itemcount} ${trns 'items'} ${money _ref.totalprice})",
				"m": true
			}],
			"t": "a",
			"m": true
		}],
		"r:1264": [{
			"p": {
				"when": "${_listuri}"
			},
			"c": [{
				"p": {
					"var": "_arritems",
					"list": "${_listuri}"
				},
				"t": "c:list",
				"m": true
			}],
			"t": "c:if",
			"m": true
		}, {
			"c": [{
				"p": {
					"arr": "${_listitems}",
					"var": "_arritems"
				},
				"t": "c:load",
				"m": true
			}],
			"t": "c:else",
			"m": true
		}, {
			"a": {
				"class": "wlist_top"
			},
			"c": [{
				"p": {
					"firstnavafter": "r:131",
					"rowbefore": "r:124",
					"pageout": "r:122",
					"rowin": "r:125",
					"styleprefix": "list_${_view}",
					"secondnavin": "r:135",
					"firstnavout": "r:130",
					"pagein": "r:121",
					"maxcol": "${_maxcol}",
					"array": "${_arritems}",
					"secondnavafter": "r:137",
					"item": "r:1263",
					"secondnavbefore": "r:134",
					"var": "2",
					"firstnavpassive": "r:133",
					"rowout": "r:126",
					"pageafter": "r:123",
					"firstnavin": "r:129",
					"maxpage": "${_maxpage}",
					"secondnavout": "r:136",
					"secondnavpassive": "r:420",
					"rowafter": "r:127",
					"secondnavactive": "r:418",
					"pagebefore": "r:120",
					"maxrow": "${_maxrow}",
					"firstnavactive": "r:132",
					"firstnavbefore": "r:128"
				},
				"t": "c:foreach",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:1266": [{
			"c": [{
				"a": {
					"href": "${_fe_item2.uri}",
					"class": "imgwrapper"
				},
				"c": [{
					"a": {
						"class": "wslider_text"
					},
					"c": [{
						"p": {
							"when": "${empty _fe_item2.summary[0]}"
						},
						"c": [{
							"a": {
								"class": "wslider_spot"
							},
							"t": "span",
							"tx": "${_fe_item2.title[0]}",
							"m": true
						}],
						"t": "c:if",
						"m": true
					}, {
						"c": [{
							"a": {
								"style": "",
								"class": "wslider_spot"
							},
							"t": "span",
							"tx": "${_fe_item2.summary[0]}",
							"m": true
						}],
						"t": "c:else",
						"m": true
					}],
					"t": "div",
					"m": true
				}, {
					"p": {
						"val1": "${false}",
						"then": "r:340",
						"var1": "_looking"
					},
					"t": "c:onmouseout",
					"m": true
				}, {
					"p": {
						"val1": "${true}",
						"then": "r:341",
						"var1": "_looking"
					},
					"t": "c:onmouseover",
					"m": true
				}, {
					"a": {
						"src": "${_fe_item2.large_icon}",
						"width": "100%",
						"class": "wslider_img",
						"height": "100%"
					},
					"t": "img",
					"m": true
				}],
				"t": "a",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:1267": [{
			"p": {
				"when": "${_listuri}"
			},
			"c": [{
				"p": {
					"var": "_arritems",
					"list": "${_listuri}"
				},
				"t": "c:list",
				"m": true
			}],
			"t": "c:if",
			"m": true
		}, {
			"c": [{
				"p": {
					"arr": "${_listitems}",
					"var": "_arritems"
				},
				"t": "c:load",
				"m": true
			}],
			"t": "c:else",
			"m": true
		}, {
			"p": {
				"when": "${not _loadok}"
			},
			"c": [{
				"t": "p",
				"tx": "Error:${_loaderr}",
				"m": true
			}],
			"t": "c:if",
			"m": true
		}, {
			"c": [{
				"p": {
					"firstnavafter": "r:57",
					"rowbefore": "r:50",
					"pageout": "r:48",
					"rowin": "r:51",
					"styleprefix": "slider_${_view}",
					"secondnavin": "r:61",
					"firstnavout": "r:56",
					"pagein": "r:47",
					"maxcol": "1",
					"array": "${_arritems}",
					"secondnavafter": "r:63",
					"item": "r:1266",
					"secondnavbefore": "r:60",
					"var": "2",
					"firstnavpassive": "r:59",
					"rowout": "r:52",
					"pageafter": "r:597",
					"firstnavin": "r:55",
					"maxpage": "50",
					"secondnavout": "r:62",
					"secondnavpassive": "r:344",
					"rowafter": "r:53",
					"secondnavactive": "r:31",
					"pagebefore": "r:586",
					"maxrow": "1",
					"firstnavactive": "r:58",
					"firstnavbefore": "r:54"
				},
				"t": "c:foreach",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:1025": [{
			"a": {
				"class": "selectbox"
			},
			"c": [{
				"c": [{
					"p": {
						"goal": "login",
						"then": "r:986"
					},
					"c": [],
					"t": "c:onclick",
					"m": true
				}],
				"t": "a",
				"tx": "${trns 'login'}",
				"m": true
			}],
			"t": "div",
			"tx": "",
			"m": true
		}],
		"w:top": {
			"runzone": false,
			"title": "w:top",
			"params": {},
			"childs": {
				"a": {
					"class": "wtop_inner"
				},
				"c": [{
					"p": {
						"clmn": "summary",
						"wiki": "true",
						"symbl": "info",
						"tbl": "site.info"
					},
					"t": "c:text",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": ".hide"
		},
		"w:vitrine": {
			"runzone": false,
			"title": "w:vitrine",
			"params": {},
			"childs": {
				"a": {
					"class": "wbody_inner"
				},
				"c": [{
					"p": {
						"clmn": "spot",
						"wiki": "true",
						"symbl": "item",
						"tbl": "site.writings"
					},
					"t": "c:text",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": ".hide"
		},
		"r:1028": [{
			"a": {
				"class": "selectbox"
			},
			"c": [{
				"c": [{
					"p": {
						"then": "r:986"
					},
					"t": "c:onclick",
					"m": true
				}],
				"t": "a",
				"tx": "${user}",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:188": [],
		"r:187": [],
		"r:189": [],
		"r:984": [],
		"r:742": [],
		"r:741": [],
		"r:744": [],
		"r:986": [],
		"r:743": [],
		"r:746": [],
		"r:745": [],
		"r:987": [],
		"w:standart": {
			"runzone": false,
			"title": "w:standart",
			"params": {},
			"childs": {
				"a": {
					"class": "body-inner"
				},
				"c": [{
					"t": "w:head",
					"m": true
				}, {
					"t": "w:content",
					"m": true
				}, {
					"t": "w:footer",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": ".hide"
		},
		"r:748": [],
		"r:747": [],
		"r:626": [{
			"a": {
				"class": "submenu_${_view}_item"
			},
			"c": [{
				"a": {
					"href": "${_fe_item2.uri}"
				},
				"c": [{
					"a": {
						"class": "img"
					},
					"s": {
						"defaultstyle": {
							"background-size": "100% 100%",
							"background-image": "url(${_fe_item2.medium_icon})"
						}
					},
					"c": [],
					"t": "div",
					"m": true
				}],
				"t": "a",
				"m": true
			}, {
				"a": {
					"href": "${_fe_item2.uri}"
				},
				"c": [{
					"a": {
						"class": "desc"
					},
					"c": [{
						"t": "h6",
						"tx": "${_fe_item2.title[0]}",
						"m": true
					}, {
						"a": {
							"class": "summary"
						},
						"c": [{
							"p": {
								"strlen": "32",
								"threedots": "...",
								"clmn": "summary",
								"wiki": "false",
								"symbl": "_fe_item2",
								"tbl": "site.contents"
							},
							"t": "c:text",
							"m": true
						}],
						"t": "div",
						"m": true
					}],
					"t": "div",
					"m": true
				}],
				"t": "a",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:989": [],
		"r:749": [],
		"w:langs": {
			"runzone": false,
			"title": "w:langs",
			"params": {
				"_langdomain": {
					"t": 1,
					"d": true,
					"v": "${note.lang}"
				}
			},
			"childs": {
				"c": [{
					"p": {
						"then": "r:11",
						"when": "${(not empty _langdomain) and (_langdomain!=note.lang) }",
						"likes": "_langdomain"
					},
					"t": "c:changeable",
					"m": true
				}, {
					"c": [{
						"p": {
							"val1": "\\${event.value}",
							"then": "r:463",
							"var1": "_langdomain"
						},
						"t": "c:onchange",
						"m": true
					}, {
						"p": {
							"firstnavafter": "r:475",
							"rowbefore": "r:468",
							"pageout": "r:466",
							"rowin": "r:469",
							"styleprefix": "_",
							"secondnavin": "r:479",
							"firstnavout": "r:474",
							"pagein": "r:465",
							"maxcol": "100",
							"array": "${note.domains}",
							"secondnavafter": "r:481",
							"item": "r:12",
							"secondnavbefore": "r:478",
							"var": "3",
							"firstnavpassive": "r:477",
							"rowout": "r:470",
							"pageafter": "r:467",
							"firstnavin": "r:473",
							"maxpage": "1",
							"secondnavout": "r:480",
							"secondnavpassive": "r:483",
							"rowafter": "r:471",
							"secondnavactive": "r:482",
							"pagebefore": "r:464",
							"maxrow": "1",
							"firstnavactive": "r:476",
							"firstnavbefore": "r:472"
						},
						"t": "c:foreach",
						"m": true
					}],
					"t": "select",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": ".hide"
		},
		"w:share": {
			"runzone": false,
			"title": "w:share",
			"params": {},
			"childs": {
				"a": {
					"id": "share-top",
					"class": "inner"
				},
				"c": [{
					"a": {
						"href": "https://www.addtoany.com/share_save",
						"class": "a2a_dd"
					},
					"c": [{
						"a": {
							"src": "https://static.addtoany.com/buttons/share_save_171_16.png"
						},
						"t": "img",
						"m": true
					}],
					"t": "a",
					"m": true
				}, {
					"t": "script",
					"tx": "var a2a_config = a2a_config || {};\na2a_config.onclick = 1;\n",
					"m": true
				}, {
					"a": {
						"src": "https://static.addtoany.com/menu/page.js",
						"type": "text/javascript"
					},
					"t": "script",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": "header"
		},
		"r:171": [],
		"r:170": [],
		"r:173": [],
		"r:172": [],
		"r:175": [],
		"r:174": [],
		"r:177": [],
		"r:176": [],
		"r:178": [],
		"r:299": [],
		"w:list": {
			"runzone": false,
			"title": "w:list",
			"params": {
				"_maxcol": {
					"r": true,
					"t": 0,
					"e": ["1", "2", "3", "4", "5"],
					"v": "1"
				},
				"_listitems": {
					"c": 16,
					"t": 5,
					"v": ""
				},
				"_view": {
					"r": true,
					"t": 1,
					"e": ["weak", "strong"],
					"v": "weak"
				},
				"_listuri": {
					"c": 2,
					"t": 1
				},
				"_header": {
					"t": 1
				},
				"_maxpage": {
					"r": true,
					"t": 0,
					"d": true,
					"v": "100"
				},
				"_maxrow": {
					"r": true,
					"t": 0,
					"e": ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"],
					"v": "10"
				},
				"_fe_pageid2": {
					"t": 0,
					"d": true,
					"v": "0"
				},
				"_arritems": {
					"t": 5,
					"d": true
				}
			},
			"childs": {
				"a": {
					"class": "wlist inner"
				},
				"c": [{
					"a": {
						"class": "widget_header"
					},
					"c": [{
						"t": "h3",
						"tx": "${_header}",
						"m": true
					}],
					"t": "div",
					"m": true
				}, {
					"p": {
						"immediate": "true",
						"var": "_fe_pageid2",
						"effect": "fade",
						"name": "show",
						"then": "r:1264",
						"when": "${true}",
						"append": "false",
						"likes": "_fe_pageid2"
					},
					"t": "c:changeable",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": "listing"
		},
		"r:1249": [],
		"w:pageicon": {
			"runzone": false,
			"title": "w:pageicon",
			"params": {},
			"childs": {
				"s": {
					"defaultstyle": {
						"overflow": "hidden",
						"width": "100%",
						"height": "100%"
					}
				},
				"c": [{
					"a": {
						"src": "${item.icon}"
					},
					"s": {
						"defaultstyle": {
							"width": "100%",
							"height": "100%"
						}
					},
					"t": "img",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": "middle"
		},
		"r:162": [],
		"w:search": {
			"runzone": false,
			"title": "w:search",
			"params": {
				"_view": {
					"t": 1,
					"e": ["weak", "strong"],
					"v": "strong"
				},
				"_searchuri": {
					"c": 13,
					"t": 1,
					"v": "/search"
				},
				"_text": {
					"t": 1,
					"v": ""
				}
			},
			"childs": {
				"a": {
					"class": "inner wsearchinner"
				},
				"c": [{
					"a": {
						"class": "widget_header"
					},
					"c": [{
						"t": "h3",
						"tx": "${trns 'searching'}",
						"m": true
					}],
					"t": "div",
					"m": true
				}, {
					"c": [{
						"a": {
							"method": "get",
							"action": "${_searchuri}"
						},
						"c": [{
							"a": {
								"name": "o_searchphrase",
								"class": "winput",
								"value": "${ (_view == 'weak') ? _text : '' }"
							},
							"c": [{
								"p": {
									"then": "r:1063"
								},
								"t": "c:onclick",
								"m": true
							}],
							"t": "input",
							"tx": "",
							"m": true
						}, {
							"a": {
								"name": "${trns 'search'}",
								"title": "${trns 'search'}",
								"type": "submit",
								"value": "${trns 'search'}",
								"class": "wbutton"
							},
							"t": "input",
							"m": true
						}],
						"t": "form",
						"m": true
					}],
					"t": "div",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": "middle"
		},
		"r:161": [],
		"r:164": [],
		"r:163": [],
		"r:166": [],
		"r:165": [],
		"r:168": [],
		"r:167": [],
		"r:169": [],
		"w:desk": {
			"runzone": false,
			"title": "w:desk",
			"params": {
				"_connectstr": {
					"t": 1
				}
			},
			"childs": {
				"a": {
					"class": "wdeskholder"
				},
				"c": [{
					"c": [{
						"c": [{
							"a": {
								"valign": "middle"
							},
							"c": [{
								"a": {
									"id": "chatdialog"
								},
								"c": [{
									"p": {
										"guardgoal": "online",
										"guardwhen": "${event.rttype==\"m\"}",
										"rtdlgvar": "_dlgvar",
										"scrolldown": "true",
										"name": "dialog",
										"then": "r:0",
										"when": "${not empty _dlgvar}",
										"append": "true"
									},
									"t": "c:changeable",
									"m": true
								}],
								"t": "div",
								"m": true
							}, {
								"p": {
									"guardgoal": "online",
									"name": "sendcomment",
									"then": "r:1",
									"when": "${not empty _dlgvar}",
									"append": "${false}",
									"likes": "_dlgvar"
								},
								"t": "c:changeable",
								"m": true
							}, {
								"p": {
									"goal": "beonline",
									"immediate": "${false}",
									"name": "rtconnect",
									"then": "r:2"
								},
								"t": "c:changeable",
								"m": true
							}, {
								"p": {
									"goal": "start",
									"name": "connectbutton",
									"then": "r:3"
								},
								"t": "c:changeable",
								"m": true
							}, {
								"p": {
									"goal": "deskoffline",
									"name": "deskoffline",
									"then": "r:4"
								},
								"t": "c:changeable",
								"m": true
							}],
							"t": "td",
							"m": true
						}, {
							"a": {
								"valign": "top"
							},
							"c": [{
								"a": {
									"id": "paintdiv"
								},
								"c": [{
									"p": {
										"guardgoal": "online",
										"guardwhen": "${event.rttype==\"b\" || event.rttype==\"e\"}",
										"rtdlgvar": "_dlgvar",
										"scrolldown": "false",
										"name": "paint",
										"then": "r:5",
										"when": "${event.rttype==\"b\"}",
										"append": "false"
									},
									"t": "c:changeable",
									"m": true
								}, {
									"p": {
										"guardgoal": "online",
										"guardwhen": "${event.rttype==\"w\" || event.rttype==\"u\"}",
										"rtdlgvar": "_dlgvar",
										"scrolldown": "false",
										"name": "marking",
										"then": "r:6",
										"when": "${event.rttype==\"w\"}",
										"append": "false"
									},
									"t": "c:changeable",
									"m": true
								}],
								"t": "div",
								"m": true
							}],
							"t": "td",
							"m": true
						}],
						"t": "tr",
						"m": true
					}],
					"t": "table",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": ".hide"
		},
		"w:footer": {
			"runzone": false,
			"title": "w:footer",
			"params": {},
			"childs": {
				"a": {
					"class": "inner wfooter_inner"
				},
				"s": {
					"defaultstyle": {
						"position": "relative"
					}
				},
				"c": [{
					"a": {
						"class": "wfooter_holder"
					},
					"c": [{
						"p": {
							"clmn": "site_footer",
							"wiki": "true",
							"symbl": "info",
							"tbl": "site.info"
						},
						"t": "c:text",
						"m": true
					}],
					"t": "div",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": ".hide"
		},
		"w:test": {
			"runzone": false,
			"w": 100,
			"title": "w:test",
			"params": {},
			"childs": {
				"a": {
					"class": "inner"
				},
				"c": [{
					"a": {
						"class": "widget_header"
					},
					"c": [{
						"t": "h3",
						"tx": "${_companyname}",
						"m": true
					}],
					"t": "div",
					"m": true
				}, {
					"p": {
						"var": "test",
						"list": "mest"
					},
					"t": "c:list",
					"m": true
				}, {
					"t": "div",
					"tx": "${_companyinfo}",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": "middle"
		},
		"w:iframe": {
			"runzone": false,
			"title": "w:iframe",
			"params": {
				"_src": {
					"r": true,
					"t": 1,
					"v": "http://www.tlos.net"
				}
			},
			"childs": {
				"a": {
					"src": "${_src}",
					"width": "100%",
					"frameborder": "0",
					"class": "inner",
					"height": "99%"
				},
				"t": "iframe",
				"m": true
			},
			"group": "middle"
		},
		"w:paypal": {
			"runzone": false,
			"w": 160,
			"h": 60,
			"title": "w:paypal",
			"params": {},
			"childs": {
				"a": {
					"method": "post",
					"action": "https://www.sandbox.paypal.com/cgi-bin/webscr"
				},
				"c": [{
					"a": {
						"name": "cmd",
						"type": "hidden",
						"value": "_s-xclick"
					},
					"t": "input",
					"m": true
				}, {
					"a": {
						"name": "hosted_button_id",
						"type": "hidden",
						"value": "W3SA75MLKJP52"
					},
					"t": "input",
					"m": true
				}, {
					"a": {
						"src": "https://www.sandbox.paypal.com/tr_TR/TR/i/btn/btn_buynowCC_LG.gif",
						"alt": "${trns 'paypaldesc'}",
						"name": "submit",
						"type": "image"
					},
					"t": "input",
					"m": true
				}, {
					"p": {
						"when": "${note.lang == 'tr'}"
					},
					"c": [{
						"a": {
							"src": "https://www.sandbox.paypal.com/tr_TR/TR/i/btn/btn_buynowCC_LG.gif",
							"alt": "${trns 'paypaldesc'}",
							"name": "submit",
							"type": "image"
						},
						"t": "input",
						"m": true
					}],
					"t": "c:if",
					"m": true
				}],
				"t": "form",
				"m": true
			},
			"group": ".hide"
		},
		"r:1216": [{
			"a": {
				"class": "selectbox"
			},
			"c": [{
				"a": {
					"class": "title"
				},
				"c": [{
					"c": [{
						"p": {
							"then": "r:986"
						},
						"t": "c:onclick",
						"m": true
					}],
					"t": "a",
					"tx": "${user}",
					"m": true
				}],
				"t": "span",
				"m": true
			}, {
				"a": {
					"method": "post",
					"action": "/_auth/logout.rom",
					"id": "logout_form",
					"class": "wauth_logout"
				},
				"s": {
					"defaultstyle": {
						"display": "inline"
					}
				},
				"c": [{
					"t": "c:forminline",
					"m": true
				}, {
					"a": {
						"name": "n",
						"type": "submit",
						"value": "${trns 'logout'}",
						"class": "wauth_logout_submit"
					},
					"t": "input",
					"m": true
				}],
				"t": "form",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:270": [],
		"r:272": [],
		"r:271": [],
		"r:274": [],
		"r:273": [],
		"r:275": [],
		"w:contactinfo": {
			"runzone": false,
			"title": "w:contactinfo",
			"params": {
				"_fax": {
					"t": 1,
					"v": ""
				},
				"_companyname": {
					"t": 1,
					"v": ""
				},
				"_email": {
					"t": 1,
					"v": ""
				},
				"_addr": {
					"c": 11,
					"t": 1,
					"v": ""
				},
				"_phone": {
					"t": 1,
					"v": ""
				}
			},
			"childs": {
				"a": {
					"class": "inner"
				},
				"c": [{
					"a": {
						"class": "widget_header"
					},
					"c": [{
						"t": "h3",
						"tx": "${trns 'contactinfo'}",
						"m": true
					}],
					"t": "div",
					"m": true
				}, {
					"c": [{
						"a": {
							"class": "addr_title"
						},
						"t": "div",
						"tx": "${_companyname}",
						"m": true
					}, {
						"a": {
							"class": "addr_lines"
						},
						"t": "div",
						"tx": "${_addr}",
						"m": true
					}, {
						"p": {
							"when": "${not empty _phone}"
						},
						"c": [{
							"a": {
								"class": "addr_phone"
							},
							"c": [{
								"a": {
									"class": "hili"
								},
								"t": "span",
								"tx": "${trns 'phone'}:",
								"m": true
							}],
							"t": "div",
							"tx": "${_phone}",
							"m": true
						}],
						"t": "c:if",
						"m": true
					}, {
						"p": {
							"when": "${not empty _email}"
						},
						"c": [{
							"a": {
								"class": "addr_email"
							},
							"c": [{
								"a": {
									"class": "hili"
								},
								"t": "span",
								"tx": "${trns 'email'}:",
								"m": true
							}],
							"t": "div",
							"tx": "${_email}",
							"m": true
						}],
						"t": "c:if",
						"m": true
					}, {
						"p": {
							"when": "${not empty _fax}"
						},
						"c": [{
							"a": {
								"class": "addr_fax"
							},
							"c": [{
								"a": {
									"class": "hili"
								},
								"t": "span",
								"tx": "${trns 'fax'}:",
								"m": true
							}],
							"t": "div",
							"tx": "${_fax}",
							"m": true
						}],
						"t": "c:if",
						"m": true
					}],
					"t": "div",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": "middle"
		},
		"r:710": [{
			"t": "div",
			"tx": "HELLLOOO",
			"m": true
		}],
		"r:1221": [{
			"a": {
				"class": "abutton"
			},
			"c": [{
				"c": [{
					"p": {
						"goal": "login",
						"then": "r:986"
					},
					"c": [],
					"t": "c:onclick",
					"m": true
				}],
				"t": "a",
				"tx": "${trns 'login'}",
				"m": true
			}],
			"t": "div",
			"tx": "",
			"m": true
		}, {
			"a": {
				"class": "abutton"
			},
			"c": [{
				"c": [{
					"p": {
						"goal": "register",
						"then": "r:986"
					},
					"c": [],
					"t": "c:onclick",
					"m": true
				}],
				"t": "a",
				"tx": "${trns 'register'}",
				"m": true
			}],
			"t": "div",
			"m": true
		}],
		"r:1222": [{
			"a": {
				"method": "post",
				"action": "/_auth/login.rom",
				"id": "loginform",
				"class": "wauth_loginform"
			},
			"c": [{
				"p": {
					"sucgoal": "start"
				},
				"t": "c:forminline",
				"m": true
			}, {
				"c": [{
					"c": [{
						"a": {
							"colspan": "2",
							"align": "right"
						},
						"c": [{
							"a": {
								"class": "wauth_form_close"
							},
							"c": [{
								"p": {
									"goal": "start",
									"then": "r:984"
								},
								"t": "c:onclick",
								"m": true
							}],
							"t": "a",
							"m": true
						}],
						"t": "td",
						"m": true
					}],
					"t": "tr",
					"m": true
				}, {
					"c": [{
						"c": [{
							"t": "label",
							"tx": "${trns 'username'}:",
							"m": true
						}],
						"t": "td",
						"m": true
					}, {
						"c": [{
							"a": {
								"name": "user",
								"type": "text"
							},
							"t": "input",
							"m": true
						}],
						"t": "td",
						"m": true
					}],
					"t": "tr",
					"m": true
				}, {
					"c": [{
						"c": [{
							"t": "label",
							"tx": "${trns 'password'}:",
							"m": true
						}],
						"t": "td",
						"m": true
					}, {
						"c": [{
							"a": {
								"name": "credential",
								"type": "password"
							},
							"t": "input",
							"m": true
						}],
						"t": "td",
						"m": true
					}],
					"t": "tr",
					"m": true
				}, {
					"c": [{
						"a": {
							"align": "right"
						},
						"c": [{
							"a": {
								"name": "s",
								"type": "submit",
								"value": "login",
								"class": ""
							},
							"c": [],
							"t": "input",
							"m": true
						}],
						"t": "td",
						"m": true
					}],
					"t": "tr",
					"m": true
				}],
				"t": "table",
				"m": true
			}],
			"t": "form",
			"m": true
		}],
		"r:1223": [],
		"r:1224": [],
		"w:content": {
			"runzone": false,
			"title": "w:content",
			"params": {},
			"childs": {
				"a": {
					"class": "wcontent_inner"
				},
				"c": [{
					"a": {
						"class": "body_top"
					},
					"c": [{
						"a": {
							"class": "top"
						},
						"t": "div",
						"m": true
					}, {
						"a": {
							"class": "bottom"
						},
						"t": "div",
						"m": true
					}],
					"t": "div",
					"m": true
				}, {
					"t": "w:body",
					"m": true
				}, {
					"p": {
						"when": "${not empty item.dialog_uri}"
					},
					"c": [{
						"t": "w:comments",
						"m": true
					}],
					"t": "c:if",
					"m": true
				}, {
					"a": {
						"class": "body_bottom"
					},
					"t": "div",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": ".hide"
		},
		"w:cartcheckout": {
			"runzone": false,
			"title": "w:cartcheckout",
			"params": {},
			"childs": {
				"a": {
					"class": "inner"
				},
				"c": [{
					"a": {
						"class": "site-btn"
					},
					"c": [{
						"p": {
							"then": "r:1257"
						},
						"t": "c:onclick",
						"m": true
					}],
					"t": "button",
					"tx": "${trns \"completeorder\"}",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": "ecommerce"
		},
		"w:contactform": {
			"runzone": true,
			"title": "w:contactform",
			"params": {},
			"childs": {
				"a": {
					"class": "inner"
				},
				"c": [{
					"t": "h2",
					"tx": "${trns 'contactform'}",
					"m": true
				}, {
					"p": {
						"goal": "contactsuccess",
						"name": "consuc",
						"then": "r:43"
					},
					"t": "c:changeable",
					"m": true
				}, {
					"p": {
						"goal": "start",
						"then": "r:44"
					},
					"t": "c:changeable",
					"m": true
				}],
				"t": "div",
				"m": true
			},
			"group": "middle"
		}
	},
	"weight": 100,
	"creation_date": "2015-06-20 06:35:34.032485",
	"modified_date": "2016-06-10 10:43:23.164328",
	"title": "/_/widgets",
	"uri": "/_/widgets",
	"host_id": 1,
	"mask": 16515108
}