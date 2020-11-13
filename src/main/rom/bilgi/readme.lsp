		(insert bilgi.tureyen host_id $host tur $



(defrule lazim_anasi
	?i<-(istek (host ?host) (cint ?cint))
	?l<-(lazim (cint ?cint) (soru kupe_no))
	=>
		(soyle soru ?host ?cint soru anasi tipi symbol yazi "Annesinin kupe numarasini giriniz?")
	)
	
	(lazim (cint ?cint) (soru anasi))
			(lazim (cint ?cint) (soru babasi))

(deftemplate kisi
	(slot host_id)
	(slot cid)
	(slot isim)
	)

(deftemplate konusma
	(slot cid)
	(slot kid)
	)
	
(deftemplate konusmayeri
	(slot kid)
	(slot yer)
	)

(deftemplate istek
	(slot kid)
	(slot calis)
	)

(deftemplate konum
	(slot yer)
	(multislot gidilebilir)
	(multislot calistirilabilir)
	)
	
(deffacts ilk_konumlar
	(konum giris)
		(gidilebilir )
		(calistirilabilir
	)

(defrule nerdeyim
	?bist <- (istek (kid ?bkid)	(calis ?bcal))
	(konusmayeri (kid ?bkid) (yer ?byer))		
	(konum (yer ?byer) (gidilebilir ?bgid) (calistirilabilir ?bcal))
	=>
	(yanit_nerdeyim ?bkid ?byer ?bgid ?bcal)
	(retract ?bist)
)