package com.bilgidoku.rom.web.cookie;

import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.ilk.util.Assert;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.run.KosuGorevlisi;
import com.bilgidoku.rom.run.timer.EveryHour;
import com.bilgidoku.rom.shared.Cookie;
import com.bilgidoku.rom.shared.err.KnownError;

/**
 * 
 * ================================================ GOOGLE
 * ===========================================================================
 * 
 * login ekrani
 * set-cookie:GAPS=1:Juqtt02ZAacNvm6S2VJXdxXxGlv4Yg:9_7L3jnPNU4npl5h
 * ;Path=/;Expires=Fri, 26-Sep-2014 05:29:56 GMT;Secure;HttpOnly
 * set-cookie:GoogleAccountsLocale_session=tr; Secure
 * 
 * logged in
 * 
 * set-cookie:SAPISID=xS_TlNZDU3xmdDwi/AvHZyaKQA3Bbd9UGT;Domain=.google.com;Path
 * =/;Secure
 * set-cookie:APISID=rl_jSZtkd-IKJDDY/A0LqOSXptP5oFAnPG;Domain=.google.
 * com;Path=/
 * set-cookie:GAPS=1:bUKYnWPKWurOrdDMT13xmVHlPaPGXg:ZksVVN5xLDwaK6IY;Path
 * =/;Expires=Fri, 26-Sep-2014 06:12:25 GMT;Secure;HttpOnly
 * set-cookie:GoogleAccountsLocale_session=tr; Secure
 * set-cookie:SSID=AkxYqFWiLuEOW4uLO;Domain=.google.com;Path=/;Secure;HttpOnly
 * set-cookie:NID=63=K5vb-GabxDixOOpHNdnP8v06-6e7VWFNYokAtmO1ff5CL4__-
 * GCGRMYh94HDB0v7mshPjY91cqbcsN1wc7dYUNrD8jP1bhZF5WcD2EvvykTM58sWqwsOuSLK_jNQIDsArpBJb6WaG7Op7yCPa8DXTVQN
 * -drcAn1688V26wDmD-VJ0uhdPNeEGlDea2vXauFjVfI2gsTeCWudsPeIqQ;Domain=.google.com
 * ;Path=/;Expires=Thu, 28-Mar-2013 06:12:25 GMT;HttpOnly
 * set-cookie:SID=DQAAAMMAAABuzs1l5Ps7Dmh9O1DAiMYsNR5ETg_n8XSmXIOW3xfAoaj
 * -ui0kbrP1zjx2leiuFUVmw0B4mwlx42dWmsNIwDg0cXZcV60kJiuKRzwDBq69_thW_-6
 * UJHt9gZrdhiqMf8cTR0b8CYEYGNKDxk5W0VcP68dy2OOXuOs7vpo5_1vA47YdAOIqpSAtfg7xf3IEmWki0Ka8kY9VR
 * -INQZ9SHCn5IRayJ62lJnXsaub2PBAfh2199heg0sDBfwbw2VnX0C3R2fD512qPEXsSCJGlup-Y;
 * Domain=.google.com;Path=/ set-cookie:LSID=mail|s.TR|s.youtube|ss:
 * DQAAAMYAAACEzj_PnvGw6sYxG9MHhPGk39AJ5EDFuUnoJhlWRMEs_TuEQXnJciLqnuUOssN6nsSmL56_bg4BhAoEsb0iYlUyq2RJMsmgP0amV9m_OA6U5E8v3RhgMLgP1NNKErxonLWrSF8cN7QybEntTKPNtwsYwv3iKw4vf8XqBEfkZ817audBIp4a24i
 * -41bD1MUX39o0jlIDsX8DrPZaY_FPh1hePxcxm8M5y82mc4e2K4dn6x52TBV4AtAtsQQguG9rJx-
 * OY-Rh91Iz--pXnuUNnhtL;Path=/;Secure;HttpOnly
 * set-cookie:HSID=AxXAdJJfWbvv2uM5K;Domain=.google.com;Path=/;HttpOnly
 * 
 * set-cookie:NID=64private Object session; // // private HashMap<String,
 * String> cur; // // private static Set<String> cookieNames = new
 * HashSet<String>(); // static { // cookieNames.add("user"); //
 * cookieNames.add("cid"); // cookieNames.add("cname"); //
 * cookieNames.add("roles"); // cookieNames.add("lang"); // } // // // public
 * CookieStore(Object session){ // this.session=session; // } // // public
 * boolean isACookie(String name) { // return cookieNames.contains(name); // }
 * // // public MyLiteral getFrom(String name) throws RunException { // String
 * val = Portable.one.getCookie(session, name); // if(val==null) // return new
 * MyLiteral(); // // if(name.equals("cid")){ // return new MyLiteral(val); // }
 * // // if (name.equals("roles")) { // // String[] vals = val.split(","); //
 * JSONArray arr = new JSONArray(); // for (int i = 0; i < vals.length; i++) {
 * // arr.set(i, new JSONString(vals[i])); // } // return new MyLiteral(arr); //
 * } // return new MyLiteral(val); // } // // public void saveState() throws
 * RunException{ // cur=new HashMap<String,String>(); // for (String name :
 * cookieNames) { // String val = Portable.one.getCookie(session, name); //
 * cur.put(name, val); // } // } // // public List<String> getModifieds() throws
 * RunException{ // List<String> modifieds=new ArrayList<String>(); // for
 * (String name : cookieNames) { // String val1 =
 * Portable.one.getCookie(session, name); // String val2 = cur.get(name); //
 * if((val1!=null && val2==null) || (val1==null && val2!=null) // || (val1!=null
 * && val2!=null && !val1.equals(val2))){ // modifieds.add(name); // } // } //
 * return modifieds; // }=
 * ia8Gk27MIxlewB6c5aPe1CUTbz5CH5dmbmuQ_LIFgirAdj2QZJMAbVmI5DPDRUaZkRKfJptwrmOWVo6KFpSScXQRMtCffTav5npD1SuSYLkPR9vP6NQ6YpOQD5y65vcK9v2q7wZhbGMcx8yfJ1122RXYEkOzGoDyKINDSOVI1tgKIi0kejNWA3k2
 * -t87cg;Domain=.google.com.tr;Path=/;Expires=Thu, 28-Mar-2013 06:12:25
 * GMT;HttpOnly
 * set-cookie:SAPISID=t1wQP5jZa8cjgRpf/AvHZyaKQA3Bbd9UGT;Domain=.google
 * .com.tr;Path=/;Secure
 * set-cookie:SID=DQAAAMQAAABuzs1l5Ps7Dmh9O1DAiMYsNR5ETg_n8XSmXIOW3xfAoaj
 * -ui0kbrP1zjx2leiuFUVmw0B4mwlx42dWmsNIwDg0cXZcV60kJiuKRzwDBq69_thW_-6
 * UJHt9gZrdhiqMf8cTR0b8CYEYGNKDxk5W0VcP68dy2OOXuOs7vpo5_1vA47YdAOIqpSAtfg7xf3IEmWlO0OoWbEyne8RF92r2vucYaTt3TmR4u8F1k5nfNRvDgxtEbJfJ1HTYylZgU8sl8HKm1ItMaFX9OG2LBnTpCqpz
 * ;Domain=.google.com.tr;Path=/
 * set-cookie:HSID=A2atzLGv4zEjynSft;Domain=.google.com.tr;Path=/;HttpOnly
 * set-cookie
 * :SSID=AB1d5Rciea1SPGzf1;Domain=.google.com.tr;Path=/;Secure;HttpOnly
 * set-cookie
 * :APISID=JgxZCEciURuquR2j/A0LqOSXptP5oFAnPG;Domain=.google.com.tr;Path=/
 * 
 * set-cookie:SID=DQAAAMMAAABuzs1l5Ps7Dmh9O1DAiMYsNR5ETg_n8XSmXIOW3xfAoaj-
 * ui0kbrP1zjx2leiuFUVmw0B4mwlx42dWmsNIwDg0cXZcV60kJiuKRzwDBq69_thW_-6
 * UJHt9gZrdhiqMf8cTR0b8CYEYGNKDxk5W0VcP68dy2OOXuOs7vpo5_1vA47YdAOIqpSAtfg7xf3IEmWl2v1lz4kshsEw181MlvwRpBMwT46eejZmzd3QDuUrYCkJihoe
 * -H0SQykVNEshlj9rJ7R66tc8GEzO4Uzv3S2wj;Domain=.youtube.com;Path=/
 * set-cookie:SAPISID
 * =t1wQP5jZa8cjgRpf/AvHZyaKQA3Bbd9UGT;Domain=.youtube.com;Path=/;Secure
 * set-cookie
 * :APISID=JgxZCEciURuquR2j/A0LqOSXptP5oFAnPG;Domain=.youtube.com;Path=/
 * set-cookie:HSID=A2atzLGv4zEjynSft;Domain=.youtube.com;Path=/;HttpOnly
 * set-cookie:SSID=AB1d5Rciea1SPGzf1;Domain=.youtube.com;Path=/;Secure;HttpOnly
 * 
 * set-cookie:GMAIL_RTT=EXPIRED; Domain=.google.com; Expires=Tue, 25-Sep-2012
 * 05:31:30 GMT; Path=/; Secure
 * set-cookie:GMAIL_AT=AF6bupOVUbQAsdfTM4SCKiax-NK0P70NNw; Path=/mail; Secure
 * set-cookie:GX=
 * DQAAAMUAAAD3Hnq54AaVUWbDK7QzdHh8DWi5FO5Lyk_ib3_qpajgJJAhmGojlcgvG5UwqW1LvbYR0aIGFAP5TgVlIyzRRManGKoHh11jiF5vWTQyCCFjfIVmnZ0ATH1M9MTsqC1fCWHnMpoFM
 * -Nbwv4CZvP0XbUfoX_fNKaY3qazpt5qQb8pw4HHEqTQq0ONqqlznFoL6FI2CaYVo6-
 * v4CaeVqQ_V6gIYXTdjtBAgI1M
 * -Aze8f4_VARVNlj1FYEElsYi2b2RsBqEszKy9h5R5JMdI_yPnGpL; Domain=mail.google.com;
 * Path=/mail; Secure; HttpOnly
 * 
 * taken alot
 * 
 * set-cookie:GMAIL_IMP=EXPIRED; Expires=Tue, 25-Sep-2012 05:31:32 GMT;
 * Path=/mail; Secure set-cookie:GMAIL_STAT_b08b=EXPIRED; Expires=Tue,
 * 25-Sep-2012 05:31:32 GMT; Path=/mail; Secure
 * 
 * set-cookie:SID=
 * DQAAAMQAAAB_GqweyI1gXVvkjjzoL0oPTFIy1QRi2ptiE4oB2W5mV8D1QruRmoWmOgaLOohmMyiSfZP2
 * -
 * EJ0y_UAgvfFN1M6aTf8JPlafp_da6mKCHcrcaDI6tbEToGqshUQX8SCSZ6m8lzOHUYl0pxybpfnfD2cyCZYg_ax2t6KwzGVer6PcI9h7hjA5FDux0siFIFlCxJLcGenAToV0bbNvuxYGQwk7lNhfpcOpTrBe6ptqMsmmLNjOl09L5NuvzRH2eZY3SvYfOIf2V
 * -qko1H7ns1Bg-R;Domain=.google.com;Path=/
 * 
 * set-cookie:SID=
 * DQAAAMQAAAB_GqweyI1gXVvkjjzoL0oPTFIy1QRi2ptiE4oB2W5mV8D1QruRmoWmOgaLOohmMyiSfZP2
 * -
 * EJ0y_UAgvfFN1M6aTf8JPlafp_da6mKCHcrcaDI6tbEToGqshUQX8SCSZ6m8lzOHUYl0pxybpfnfD2cyCZYg_ax2t6KwzGVer6PcI9h7hjA5FDux0siFIFlCxJLcGenAToV0bbNvuxYGQwk7lNhfpcOpTrBe6ptqMsmmLNjOl09L5NuvzRH2eZY3SvYfOIf2V
 * -qko1H7ns1Bg-R;Domain=.google.com;Path=/
 * 
 * 
 * set-cookie:GMAIL_IMP=EXPIRED; Expires=Tue, 25-Sep-2012 05:31:32 GMT;
 * Path=/mail; Secure
 * 
 * set-cookie:GMAIL_IMP=EXPIRED; Expires=Tue, 25-Sep-2012 05:31:35 GMT;
 * Path=/mail; Secure
 * 
 * set-cookie:GMAIL_IMP=EXPIRED; Expires=Tue, 25-Sep-2012 05:31:37 GMT;
 * Path=/mail; Secure
 * 
 * set-cookie:SID=DQAAAMMAAAB_GqweyI1gXVvkjjzoL0oPGnDQRlsq43dhZR7Vdb-486f
 * lnVRl_7RGSa2ISL3SlBU3RLFMn6PgBeJamNaSOFDaxXbapXmDgapf5PjHKUzlOf2Ut
 * -3amSFmYQMTO6eHjzNGEq1cR14XuT4h_W-RHTnHfi3vWCpsGXYHU8TNChRuSvFQI94N5i-
 * yyQDqYrWUGDZXfA9Fi9klyZGerdfLPRM_cHI3J0FcaMFHThbGhtXv0Sf
 * -QDitB0sDLkMvjYsSrHflVuDCH0DVqPO40jZm8LbB;Domain=.google.com;Path=/
 * 
 * 
 * Request cookie Cookie:GoogleAccountsLocale_session=tr;
 * GAPS=1:4bJmvQkS7OIjprRRFpFcDig4Ze0Urg:mEgXvqQFFOF0u3Nd;
 * PREF=ID=8f1456ad3db10a11
 * :U=943f879a094ca9ec:FF=0:LD=tr:TM=1347553969:LM=1348509215
 * :S=GTxqVill1Rx8psRB; SID=
 * DQAAAMUAAABYxjIv3Zagt4MdjuurmdWvllBNRPrPFQkv1j3xg1zbvf1XdwUV4ZcXPRrwoRyWnnDOoCsEw9kFTnGYBRqU4dQ2WlRQ0VfEW9RCptqdWg3FNNBQFPLTMggTVqvDgsBA
 * -Nr12RU1eHwKPYndlpTQSjs7SLnzbRATJc1ifLifk2ntueiDSa2DHXIs8HVHvTU8Vz2i1-
 * zZ85yfFtGzoLt2gW3aNXTpk1Jg7sT0pStb
 * -pXEozBK1rkaW5No9tYmU65-6hhLdjKSFnNsOhpN_A0ApVzI; NID=64=vn2fXz_1zdY-
 * eTrRU6_EvSuDTbKMqwYvCv7D1UE4M8uMgIyQKZZy1ttvKLmvYPjc7MbIfA4YZLp_iOJvueZvA_K6AAxU
 * -
 * u5kgp2mejaro6KRB4Ki9rrJolhdsoSutWRJP2tPmVYh0MfxlZLYiP6FND1tgPzkp_LaPDH_EQJRekshWSrzUBlZgHKvwna9qw
 * ; HSID=AfwwioSJYeJA5HCS-; APISID=9XOiIQFkgHjNBTNT/A01ebTnYm5HMcrX8x Response
 * Set-Cookie:GoogleAccountsLocale_session=tr
 * Set-Cookie:GAPS=1:ilM6_id6vsqe7ksGeqp1LqrIJGdYyw
 * :lISHUVJsIIRJ7VYE;Path=/accounts;Expires=Fri, 26-Sep-2014 05:45:49
 * GMT;HttpOnly
 * 
 * set-cookie:NID=64=I7092hs4-JF2HWU9gupX8-
 * C2z4yZ79L3uHBukGv1LkN6BFPRObjpNtcMx7bWfFR
 * -vQepF9ayS_TI9tWYC-FewZCzbDbTkaO-bjpN3g5rCxSRfVcTIbOerirX
 * -1-MgUee;Domain=.google.com.tr;Path=/;Expires=Thu, 28-Mar-2013 05:45:49
 * GMT;HttpOnly
 * set-cookie:SAPISID=EXPIRED;Domain=.google.com.tr;Path=/;Expires=Mon,
 * 01-Jan-1990 00:00:00 GMT
 * set-cookie:SID=EXPIRED;Domain=.google.com.tr;Path=/;Expires=Mon, 01-Jan-1990
 * 00:00:00 GMT
 * set-cookie:HSID=EXPIRED;Domain=.google.com.tr;Path=/;Expires=Mon, 01-Jan-1990
 * 00:00:00 GMT
 * set-cookie:SSID=EXPIRED;Domain=.google.com.tr;Path=/;Expires=Mon, 01-Jan-1990
 * 00:00:00 GMT
 * set-cookie:APISID=EXPIRED;Domain=.google.com.tr;Path=/;Expires=Mon,
 * 01-Jan-1990 00:00:00 GMT
 * 
 * 
 * ============================================= FACEBOOK
 * ================================================
 * 
 * login page Cookie:datr=jw1SUDNzPFtV6gCnu5RqPFdN; locale=tr_TR;
 * fr=0aMHQp5nwyu99pncg.AWVIrSyJYtWIzWc5UX3lQ-L7QB8.BQUg2U.Sl.AWUONJpw;
 * lu=TAmBy91pwN8pqGg1jfQr0l7g;
 * reg_fb_gate=http%3A%2F%2Fwww.facebook.com%2Findex
 * .php%3Fstype%3Dlo%26lh%3DAc8oKatAyyv7840q;
 * reg_fb_ref=http%3A%2F%2Fwww.facebook
 * .com%2Findex.php%3Fstype%3Dlo%26lh%3DAc8oKatAyyv7840q; wd=1341x414
 * 
 * Cookie:datr=jw1SUDNzPFtV6gCnu5RqPFdN; s=Aa4QJzLfXuXnpNdv.BQW1Vt;
 * locale=tr_TR;
 * fr=0aMHQp5nwyu99pncg.AWVIrSyJYtWIzWc5UX3lQ-L7QB8.BQUg2U.Sl.AWUONJpw;
 * lu=TAmBy91pwN8pqGg1jfQr0l7g;
 * reg_fb_gate=http%3A%2F%2Fwww.facebook.com%2Findex
 * .php%3Fstype%3Dlo%26lh%3DAc8oKatAyyv7840q;
 * reg_fb_ref=http%3A%2F%2Fwww.facebook.com%2F; wd=1341x414
 * 
 * Set-Cookie:reg_fb_gate=-romdeleted-; expires=Thu, 01-Jan-1970 00:00:01
 * GMT;path=/; domain=.facebook.com
 * Set-Cookie:fr=0aMHQp5nwyu99pncg.AWWqYTALhV3YlvBO6cv0B8ILx7c
 * .BQUg2U.Sl.AWWjvJ-Y; expires=Fri, 26-Oct-2012 06:00:37 GMT; path=/;
 * domain=.facebook.com; httponly Set-Cookie:c_user=756449541; expires=Fri,
 * 26-Oct-2012 06:00:37 GMT; path=/; domain=.facebook.com
 * Set-Cookie:datr=jw1SUDNzPFtV6gCnu5RqPFdN; expires=Fri, 26-Sep-2014 06:00:36
 * GMT; path=/; domain=.facebook.com; httponly
 * Set-Cookie:xs=4%3AdEWk8iA4Z6rjRA%3A0%3A1348639237; expires=Fri, 26-Oct-2012
 * 06:00:37 GMT; path=/; domain=.facebook.com; httponly
 * Set-Cookie:lu=TgY6ZuGGO7mCUDbpx-XMjb5g; expires=Fri, 26-Sep-2014 06:00:37
 * GMT; path=/; domain=.facebook.com; httponly Set-Cookie:wd=-romdeleted-;
 * expires=Thu, 01-Jan-1970 00:00:01 GMT; path=/; domain=.facebook.com; httponly
 * Set-Cookie:reg_fb_ref=-romdeleted-; expires=Thu, 01-Jan-1970 00:00:01 GMT;
 * path=/; domain=.facebook.com Set-Cookie:s=Aa5qCDihuiZFDpbi.BQYpoF;
 * expires=Fri, 26-Oct-2012 06:00:37 GMT; path=/; domain=.facebook.com; secure;
 * httponly
 * 
 * 
 * Cookie:locale=tr_TR; c_user=756449541; datr=jw1SUDNzPFtV6gCnu5RqPFdN;
 * fr=0aMHQp5nwyu99pncg.AWWqYTALhV3YlvBO6cv0B8ILx7c.BQUg2U.Sl.AWWjvJ-Y;
 * lu=TgY6ZuGGO7mCUDbpx-XMjb5g; xs=4%3AdEWk8iA4Z6rjRA%3A0%3A1348639237
 * 
 * Cookie:locale=tr_TR; c_user=756449541; datr=jw1SUDNzPFtV6gCnu5RqPFdN;
 * fr=0aMHQp5nwyu99pncg.AWWqYTALhV3YlvBO6cv0B8ILx7c.BQUg2U.Sl.AWWjvJ-Y;
 * lu=TgY6ZuGGO7mCUDbpx-XMjb5g; xs=4%3AdEWk8iA4Z6rjRA%3A0%3A1348639237
 * 
 * Cookie:locale=tr_TR; c_user=756449541; datr=jw1SUDNzPFtV6gCnu5RqPFdN;
 * fr=0aMHQp5nwyu99pncg.AWWqYTALhV3YlvBO6cv0B8ILx7c.BQUg2U.Sl.AWWjvJ-Y;
 * lu=TgY6ZuGGO7mCUDbpx-XMjb5g; xs=4%3AdEWk8iA4Z6rjRA%3A0%3A1348639237; sub=1
 * Set-Cookie:p=5; path=/; domain=.facebook.com
 * 
 * Cookie:locale=tr_TR; c_user=756449541; datr=jw1SUDNzPFtV6gCnu5RqPFdN;
 * fr=0aMHQp5nwyu99pncg.AWWqYTALhV3YlvBO6cv0B8ILx7c.BQUg2U.Sl.AWWjvJ-Y;
 * lu=TgY6ZuGGO7mCUDbpx-XMjb5g; xs=4%3AdEWk8iA4Z6rjRA%3A0%3A1348639237; sub=1;
 * act=1348639226449%2F0%3A1;
 * _e_0oiy_0=%5B%220oiy%22%2C1348639226450%2C%22act%22
 * %2C1348639226449%2C0%2C%22-
 * %22%2C%22vpv%22%2C%22-%22%2C%22home_stream%22%2C%22
 * r%22%2C%22%2F%22%2C%7B%22ft
 * %22%3A%7B%22qid%22%3A%225792361422385200995%22%2C%22
 * mf_story_key%22%3A%22431531895870648166%22%2C%
 * 22evt%22%3A51%7D%2C%22gt%22%3A%7B%22ref%22%3A%22nf%22%7D%7D%2C0%2C0%2C0%2C0%2C1
 * 6 % 5 D Session-> Set-Cookie:_e_0oiy_0=-romdeleted-; expires=Thu, 01-Jan-1970
 * 00:00:01 GMT; path=/; domain=.facebook.com; httponly
 * 
 * Cookie:locale=tr_TR; c_user=756449541; datr=jw1SUDNzPFtV6gCnu5RqPFdN;
 * fr=0aMHQp5nwyu99pncg.AWWqYTALhV3YlvBO6cv0B8ILx7c.BQUg2U.Sl.AWWjvJ-Y;
 * lu=TgY6ZuGGO7mCUDbpx-XMjb5g; xs=4%3AdEWk8iA4Z6rjRA%3A0%3A1348639237; sub=1;
 * act=1348639226449%2F0%3A1;
 * _e_0oiy_1=%5B%220oiy%22%2C1348639226505%2C%22act%22
 * %2C1348639226505%2C4%2C%22-
 * %22%2C%22first_story_load%22%2C%22-%22%2C%22-%22%2C
 * %22r%22%2C%22%2F%22%2C%7B%22f
 * t%22%3A%7B%22mf_story_key%22%3A%22431531895870648166
 * %22%2C%22qid%22%3A%225792361422385200995
 * %22%7D%2C%22gt%22%3A%7B%7D%7D%2C0%2C0%2C0%2C0%2C16%5D; p=5; presence=
 * EM348639226EuserFA2756449541A2EstateFDutF0Et2F_5b_5dEuct2F1348638638BElm2FnullEtrFnullEtwF4009092939EatF1348639226654Esb2F0CEchFDp_5f756449541F3CC
 * ; wd=1341x414 Set-Cookie:_e_0oiy_1=-romdeleted-; expires=Thu, 01-Jan-1970
 * 00:00:01 GMT; path=/; domain=.facebook.com; httponly
 * 
 * Cookie:locale=tr_TR; c_user=756449541; datr=jw1SUDNzPFtV6gCnu5RqPFdN;
 * fr=0aMHQp5nwyu99pncg.AWWqYTALhV3YlvBO6cv0B8ILx7c.BQUg2U.Sl.AWWjvJ-Y;
 * lu=TgY6ZuGGO7mCUDbpx-XMjb5g; xs=4%3AdEWk8iA4Z6rjRA%3A0%3A1348639237; sub=1;
 * act=1348639226449%2F0%3A1; p=5; presence=
 * EM348639226EuserFA2756449541A2EstateFDutF0Et2F_5b_5dEuct2F1348638638BElm2FnullEtrFnullEtwF4009092939EatF1348639226654Esb2F0CEchFDp_5f756449541F3CC
 * 
 * LOGOUT
 * 
 * Cookie:locale=tr_TR; c_user=756449541; datr=jw1SUDNzPFtV6gCnu5RqPFdN;
 * fr=0aMHQp5nwyu99pncg.AWWqYTALhV3YlvBO6cv0B8ILx7c.BQUg2U.Sl.AWWjvJ-Y;
 * lu=TgY6ZuGGO7mCUDbpx-XMjb5g; xs=4%3AdEWk8iA4Z6rjRA%3A0%3A1348639237; sub=1;
 * p=5; presence=
 * EM348639694EuserFA2756449541A2EstateFDsb2F0Et2F_5b_5dElm2FnullEuct2F1348638638BEtrFnullEtwF4009092939EatF1348639694735G348639694837CEchFDp_5f756449541F9CC
 * ; wd=1341x414;
 * _e_0oiy_4=%5B%220oiy%22%2C1348639717853%2C%22act%22%2C1348639717852
 * %2C8%2C%22http
 * %3A%2F%2Fwww.facebook.com%2Feditaccount.php%3Fref%3Dmb%26drop%22
 * %2C%22click%22
 * %2C%22click%22%2C%22bluebar%22%2C%22r%22%2C%22%2F%22%2C%7B%22ft%
 * 22%3A%7B%7D%2C%22gt%22%3A%7B%7D%7D%2C1035%2C29%2C0%2C981%2C16%5D;
 * act=1348639719112%2F11%3A2;
 * _e_0oiy_5=%5B%220oiy%22%2C1348639719113%2C%22act%22
 * %2C1348639719112%2C11%2C%22
 * http%3A%2F%2Fwww.facebook.com%2Flogout.php%22%2C%22f
 * orm%22%2C%22submit%22%2C%22
 * bluebar%22%2C%22r%22%2C%22%2F%22%2C%7B%22ft%22%3A%7
 * B%7D%2C%22gt%22%3A%7B%7D%7D%2C0%2C0%2C0%2C0%2C16%5D
 * Set-Cookie:c_user=-romdeleted-; expires=Thu, 01-Jan-1970 00:00:01 GMT;
 * path=/; domain=.facebook.com Set-Cookie:_e_0oiy_4=-romdeleted-; expires=Thu,
 * 01-Jan-1970 00:00:01 GMT; path=/; domain=.facebook.com; httponly
 * Set-Cookie:xs=-romdeleted-; expires=Thu, 01-Jan-1970 00:00:01 GMT; path=/;
 * domain=.facebook.com; httponly Set-Cookie:_e_0oiy_5=-romdeleted-;
 * expires=Thu, 01-Jan-1970 00:00:01 GMT; path=/; domain=.facebook.com; httponly
 * Set-Cookie:act=-romdeleted-; expires=Thu, 01-Jan-1970 00:00:01 GMT; path=/;
 * domain=.facebook.com; httponly Set-Cookie:wd=-romdeleted-; expires=Thu,
 * 01-Jan-1970 00:00:01 GMT; path=/; domain=.facebook.com; httponly
 * Set-Cookie:checkpoint=-romdeleted-; expires=Thu, 01-Jan-1970 00:00:01 GMT;
 * path=/; domain=.facebook.com; httponly
 * Set-Cookie:lu=TAN3XiWi6_KghH8GYkBxNIYw; expires=Fri, 26-Sep-2014 06:08:52
 * GMT; path=/; domain=.facebook.com; httponly Set-Cookie:p=-romdeleted-;
 * expires=Thu, 01-Jan-1970 00:00:01 GMT; path=/; domain=.facebook.com
 * Set-Cookie:presence=-romdeleted-; expires=Thu, 01-Jan-1970 00:00:01 GMT;
 * path=/; domain=.facebook.com; httponly Set-Cookie:sub=-romdeleted-;
 * expires=Thu, 01-Jan-1970 00:00:01 GMT; path=/; domain=.facebook.com
 * 
 * 
 * 
 * @author bilo
 * 
 */
public class CookieGorevlisi extends GorevliDir implements EveryHour {

	public static final int NO = 13;

	public static CookieGorevlisi tek() {
		if (tek == null) {
			synchronized (CookieGorevlisi.class) {
				if (tek == null) {
					tek = new CookieGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static CookieGorevlisi tek;

	private CookieGorevlisi() {
		super("Cookie", NO);
	}

	final static private MC mc = new MC(CookieGorevlisi.class);

	final static private Astate unknownCookieName = mc.c("unknown-cookie-name");

	private static final String USER = "user";
	private static final String ROLES = "roles";
	private static final String LANG = "lang";
	private static final String SID = "sid";
	private static final String CID = "cid";
	private static final String CNAME = "cname";
	private static final String HOST = "host";
	private static final String ISAUTH = "isauth";
	private static final String PRESENCE = "presence";

	private static final long LONG_TERM_EXPIRE_PERIOD_IN_MSEC = 3 * 30 * 24 * 60 * 60 * 1000;

	public static Set<String> cookieNames = new HashSet<String>();
	static {
		cookieNames.add(USER);
		cookieNames.add(CID);
		cookieNames.add(CNAME);
		cookieNames.add(ROLES);
		cookieNames.add(LANG);
		cookieNames.add(HOST);
		cookieNames.add(ISAUTH);
		cookieNames.add(PRESENCE);
	}

	// final static private MonitorService monitor =
	// ServiceDiscovery.getService(MonitorService.class);
	private String longTermExpireDate;
	private String deleteCookieExpireDate;
	private Map<String, String> langCookies = new ConcurrentHashMap<String, String>();



	public void selfDescribe(JSONObject jo) {
		jo.safePut("langcookiecount", langCookies.size());
	}

	@Override
	public void kur() throws KnownError {
		run();
		KosuGorevlisi.tek().waitHour(this);
	}

	private void run() {
		Date d = new Date(System.currentTimeMillis() + LONG_TERM_EXPIRE_PERIOD_IN_MSEC);
		SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
		longTermExpireDate = sdf.format(d);
		deleteCookieExpireDate = sdf.format(new Date(System.currentTimeMillis() - 999000));
		langCookies.clear();
	}

	private static final Astate pc = mc.c("parse");

	public ParseCookie parse(String domainName, String str) throws KnownError {
		destur();

		pc.more();
		ParseCookie c = new ParseCookie(domainName);

		int nameEnd = -1, valueEnd = -1;
		String name = null;
		int i = 0;
		for (i = 0; i < str.length(); i++) {
			switch (str.charAt(i)) {
			case '=':
				nameEnd = i;
				name = extractName(str, nameEnd, valueEnd);
				break;
			case ';':
				valueEnd = i;
				newCookie(c, name, str, nameEnd, valueEnd);
				break;
			default:
				break;
			}
		}
		if (i != valueEnd) {
			newCookie(c, name, str, nameEnd, valueEnd);
		}
		return c;
	}

	private void newCookie(ParseCookie c, String name, String str, int nameEnd, int valueEnd) {
		if (name == null)
			return;
		String value = extractValue(str, nameEnd, valueEnd);
		if (name.equals(SID)) {
			c.setSid(value);
		} else if (name.equals(USER)) {
			c.setUserName(value);
		}
	}

	private String extractName(String str, int nameEnd, int valueEnd) {
		return str.substring(valueEnd + 1, nameEnd).trim();
	}

	private String extractValue(String str, int nameEnd, int valueEnd) {
		if (valueEnd < nameEnd)
			return str.substring(nameEnd + 1).trim();
		return str.substring(nameEnd + 1, valueEnd).trim();
	}


	public String[] write(Cookie cookie) throws KnownError {
		destur();

		Assert.notNull(cookie.getSid());
//		Assert.notNull(cookie.getCookieUser());

		String rls = cookie.getRoles();
		// int len=rls.length;
		// String
		// rlstr=len==0?"":(len==1?rls[0]:(len==2?rls[0]+","+rls[1]:strWithComma(rls)));
		return new String[] { getSidHeader(cookie.getCookieDomainName(), cookie.getSid()),
				getLangHeader(cookie.getCookieDomainName(), cookie.getCookieLang()),
				getCidHeader(cookie.getCookieDomainName(), cookie.getCid()),
				getCnameHeader(cookie.getCookieDomainName(), cookie.getCname()),
				getUserHeader(cookie.getCookieDomainName(), cookie.getCookieUser()),
				getRolesHeader(cookie.getCookieDomainName(), rls),
				getHostHeader(cookie.getCookieDomainName(), cookie.getCookieHostName()),
				getPresenceHeader(cookie.getCookieDomainName(), cookie.getCookiePresence()),
				getIsAuthHeader(cookie.getCookieDomainName(), cookie.getCookieAuth())

		};

	}

	private String strWithComma(String[] rls) {
		StringBuilder sb = new StringBuilder();
		sb.append(rls[0]);
		for (int i = 1; i < rls.length; i++) {
			sb.append(",");
			sb.append(rls[i]);
		}
		return sb.toString();
	}

	private String getExpireDate(String str) {
		return str == null ? deleteCookieExpireDate : longTermExpireDate;
	}

	private String getUserHeader(String domainName, String user) {
		if (user == null || user.equals("_guest")) {
			return USER + "=-romdeleted-; expires=Thu, 01-Jan-1970 00:00:01 GMT;Domain=" + domainName + ";Path=/;";
		}
		// return USER + "=" + user + "; Path=/; Expires=" + longTermExpireDate;
		return USER + "=" + user + ";Domain=" + domainName + ";Path=/;";
		// Expires="+getExpireDate(user)+";";
	}

	private String getRolesHeader(String domainName, String roles) {

		if (roles == null) {
			return ROLES + "=-romdeleted-; expires=Thu, 01-Jan-1970 00:00:01 GMT;Domain=" + domainName + ";Path=/;";
		}
		// return USER + "=" + user + "; Path=/; Expires=" + longTermExpireDate;
		return ROLES + "=" + roles + ";Domain=" + domainName + ";Path=/;";
		// Expires="+getExpireDate(roles)+";";
	}

	private String getSidHeader(String domainName, String sid) {
		return SID + "=" + sid + ";Domain=" + domainName + ";Path=/;";
//		return SID + "=" + sid + ";Domain=" + domainName + ";Path=/; HttpOnly;";
		// Expires="+getExpireDate(sid)+";";
	}

	private String getCidHeader(String domainName, String cid) {

		if (cid == null) {
			return CID + "=-romdeleted-; expires=Thu, 01-Jan-1970 00:00:01 GMT;Domain=" + domainName + ";Path=/;";
		}
		return CID + "=" + cid + ";Domain=" + domainName + ";Path=/;";
		// Expires="+getExpireDate(cid)+";";
	}

	private String getLangHeader(String domainName, String lang) {

		if (lang == null) {
			return LANG + "=-romdeleted-; expires=Thu, 01-Jan-1970 00:00:01 GMT;Domain=" + domainName + ";Path=/;";
		}
		return LANG + "=" + lang + ";Domain=" + domainName + ";Path=/;";
		// Expires="+getExpireDate(cid)+";";
	}

	private String getCnameHeader(String domainName, String cname) {

		if (cname == null) {
			// reg_fb_gate=-romdeleted-; expires=Thu, 01-Jan-1970 00:00:01
			// GMT;path=/; domain=.facebook.com
//			return CNAME + "=-romdeleted-; expires=Thu, 01-Jan-1970 00:00:01 GMT;Domain=" + domainName + ";Path=/;";
			cname = "_guest";
		}

		return CNAME + "=" + URLEncoder.encode(cname) + ";Domain=" + domainName + ";Path=/;";
		// Expires="+getExpireDate(cname)+";";
	}

	private String getIsAuthHeader(String domainName, boolean isAuth) {

		if (!isAuth) {
			return ISAUTH + "=-romdeleted-; expires=Thu, 01-Jan-1970 00:00:01 GMT;Domain=" + domainName + ";Path=/;";
		}

		return ISAUTH + "=1;Domain=" + domainName + ";Path=/;";
		// Expires="+getExpireDate(cid)+";";
	}

	private String getHostHeader(String domainName, String hostName) {

		return HOST + "=" + URLEncoder.encode(hostName) + ";Domain=" + domainName + ";Path=/;";
		// Expires="+getExpireDate(cid)+";";
	}

	private String getPresenceHeader(String domainName, String presence) {

		if (presence == null) {
			// reg_fb_gate=-romdeleted-; expires=Thu, 01-Jan-1970 00:00:01
			// GMT;path=/; domain=.facebook.com
			return PRESENCE + "=-romdeleted-; expires=Thu, 01-Jan-1970 00:00:01 GMT;Domain=" + domainName + ";Path=/;";
		}

		return PRESENCE + "=" + URLEncoder.encode(presence) + ";Domain=" + domainName + ";Path=/;";
		// Expires="+getExpireDate(cname)+";";
	}

	private static final Astate _getCookieByName = mc.c("getCookieByName");


	public String getCookieByName(Cookie cookie, String name) throws KnownError {
		destur();
		_getCookieByName.more();
		if (name.equals(SID))
			return cookie.getSid();
		if (name.equals(USER))
			return cookie.getCookieUser();
		if (name.equals(CID))
			return cookie.getCid();
		if (name.equals(CNAME))
			return cookie.getCname();
		if (name.equals(LANG))
			return cookie.getCookieLang();

		_getCookieByName.fail(cookie, name);
		throw new KnownError().internalError();
	}


	public void everyHour(int year, int month, int day, int hour) {
		run();
	}


	public boolean isACookie(String name) {
		return cookieNames.contains(name);
	}

}
