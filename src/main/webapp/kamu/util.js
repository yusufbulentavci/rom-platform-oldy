   function tje(rzid,changeid,type,event,params,value) {
            var wnd=(parent==null)?window:parent;
            var obj={
                    "rzid":rzid,
                    "changeid":changeid,
                    "type":type,
                    "value":value,
                    "mk":false,
                    "params":params
            };
            if(event.target!=null){
                    obj.targettag=event.target.tagName.toLowerCase();
                    if(event.target.id!=null)
                            obj.targetid=event.target.id;
            }
            wnd.re[wnd.re.length]=obj;
   }
   function je(rzid,changeid,event,params,value) {
   			tje(rzid,changeid,event.type,event,params,value);
    }
    function mke(rzid,changeid,event,params) {
            var wnd=(parent==null)?window:parent;
            var obj={
                    "rzid":rzid,
                    "changeid":changeid,
                    "type":event.type,
                    "value":null,
                    "mk":true,
                    "params":params,
                    "altkey":event.altKey,
                    "button":event.button,
                    "clientx":event.clientX,
                    "clienty":event.clientY,
                    "ctrlkey":event.ctrlKey,
                    "metakey":event.metaKey,
                    "screenx":event.screenX,
                    "screeny":event.screenY,
                    "shiftkey":event.shiftKey
            };
            if(event.target!=null){
                    obj.targettag=event.target.tagName.toLowerCase();
                    if(event.target.id!=null)
                            obj.targetid=event.target.id;
            }
            wnd.re[wnd.re.length]=obj;
    }
