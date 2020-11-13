uriworkitem(table)::=<<
//uriworkitem
nets.put("%table.prefix%",Net.%table.net%);
hiers.put("%table.prefix%",UriHierarychy.%if(table.hsc)%HSC%elseif(table.one)%ONE%else%SINGLE%endif%);

>>