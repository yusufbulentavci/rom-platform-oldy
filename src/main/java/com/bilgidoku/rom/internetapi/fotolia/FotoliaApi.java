package com.bilgidoku.rom.internetapi.fotolia;

/**
 *    FotoliaApi client = new FotoliaApi("your_api_key");

        // fetching a media data
        syso(client.getMediaData(18136053));

        // searching files
        FotoliaSearchQuery query = new FotoliaSearchQuery();
        query.setWords("car").setLanguageId(FotoliaApi.LANGUAGE_ID_EN_US).setLimit(1);
        syso(client.getSearchResults(query));

        // buying and downloading a file
        try {
            client.loginUser("your_login", "your_password");
            JSONObject res = client.getMedia(35957426, "XS");
            client.downloadMedia((String) res.get("url"), "/tmp/" + (String) res.get("name"));
        } catch (Exception e) {
            Sistem.printStackTrace(e);
        }
 */


import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.min.Sistem;


public class FotoliaApi {
    public static String REST_URI = "api.fotolia.com/Rest";

    public static String REST_VERSION = "1";

    public static int TOKEN_TIMEOUT = 1200;

    public static int API_CONNECT_TIMEOUT = 30;
    public static int API_PROCESS_TIMEOUT = 120;

    public static int LANGUAGE_ID_FR_FR= 1;
    public static int LANGUAGE_ID_EN_US= 2;
    public static int LANGUAGE_ID_EN_GB= 3;
    public static int LANGUAGE_ID_DE_DE= 4;
    public static int LANGUAGE_ID_ES_ES= 5;
    public static int LANGUAGE_ID_IT_IT= 6;
    public static int LANGUAGE_ID_PT_PT= 7;
    public static int LANGUAGE_ID_PT_BR= 8;
    public static int LANGUAGE_ID_JA_JP= 9;
    public static int LANGUAGE_ID_PL_PL= 11;
    public static int LANGUAGE_ID_RU_RU= 12;
    public static int LANGUAGE_ID_ZH_CN= 13;
    public static int LANGUAGE_ID_TR_TR= 14;
    public static int LANGUAGE_ID_KO_KR= 15;

    public static int THUMB_SIZE_TINY = 30;
    public static int THUMB_SIZE_SMALL = 110;
    public static int THUMB_SIZE_MEDIUM = 160;
    public static int THUMB_SIZE_LARGE = 400;

    public static String TAG_TYPE_USED = "Used";
    public static String TAG_TYPE_SEARCHED = "Searched";

    /**
     * API key
     */
    private final String _api_key;

    /**
     * HTTPs mode flag
     */
    private boolean _use_https;

    /**
     * Current session ID
     */
    private String _session_id;

    /**
     * Current session ID fetched timestamp
     */
    private long _session_id_timestamp;

    /**
     * Constructor
     *
     * @param  api_key
     */
    public FotoliaApi(final String api_key)
    {
        this._api_key = api_key;
        this.setHttpsMode(false);
    }

    /**
     * Constructor
     *
     * @param  api_key
     * @param  use_https
     */
    public FotoliaApi(final String api_key, final boolean use_https)
    {
        this._api_key = api_key;
        this.setHttpsMode(use_https);
    }

    /**
     * Returns the current api key
     *
     * @return String
     */
    public String getApiKey()
    {
        return this._api_key;
    }

    /**
     * Sets the HTTPs mode flag
     *
     * @param  flag
     * @return FotoliaApi
     */
    public FotoliaApi setHttpsMode(final boolean flag)
    {
        this._use_https = flag;
        return this;
    }

    /**
     * Fotolia API test mode
     *
     * @return boolean
     * @throws JSONException 
     */
    public boolean test() throws JSONException
    {
        return this._api("test").get("test") != null;
    }

    /**
     * Fotolia general data
     *
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getData() throws JSONException
    {
        return this._api("getData");
    }

    /**
     * This method makes possible to search media in fotolia image bank.
     * Full search capabilities are available through the API
     *
     * @param  result_columns if specified, a list a columns you want in the resultset
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getSearchResults(FotoliaSearchQuery query) throws JSONException
    {
        return this._api("getSearchResults", query.getFotoliaApiArgs());
    }

    /**
     * This method makes possible to search media in fotolia image bank.
     * Full search capabilities are available through the API
     *
     * @param  result_columns if specified, a list a columns you want in the resultset
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getSearchResults(FotoliaSearchQuery query, ArrayList<String> result_columns) throws JSONException
    {
        FotoliaApiArgs args;

        args = query.getFotoliaApiArgs();

        for (String column : result_columns) {
            args.add(new BasicNameValuePair("result_columns[]", column));
        }

        return this._api("getSearchResults", args);
    }

    /**
     * Download a media and write it to stdout
     *
     * @param  download_url URL as returned by getMedia()
     * @throws JSONException 
     */
    public void downloadMedia(final String download_url) throws FileNotFoundException, IOException, FotoliaApiException, JSONException
    {
        this.downloadMedia(download_url, null);
    }

    /**
     * Download a media and write it to a file if necessary
     *
     * @param  download_url URL as returned by getMedia()
     * @param  output_file if null the downloaded file will be echoed on standard output
     * @throws JSONException 
     */
    public void downloadMedia(final String download_url, final String output_file) throws FileNotFoundException, IOException, FotoliaApiException, JSONException
    {
        BufferedOutputStream stream;
        DefaultHttpClient client;
        HttpResponse response;
        StatusLine statusLine;
        HttpEntity entity;
        JSONObject obj;
        String error_msg;
        int error_code;

        if (output_file == null) {
            stream = new BufferedOutputStream(new BufferedOutputStream(System.out));
        } else {
            stream = new BufferedOutputStream(new FileOutputStream(output_file));
        }

        client = this._getHttpClient(true);
        response = client.execute(new HttpGet(download_url));

        statusLine = response.getStatusLine();
        entity = response.getEntity();
        if (statusLine.getStatusCode() != 200) {
            if (entity == null) {
                throw new FotoliaApiException(statusLine.getStatusCode(),
                                              statusLine.getReasonPhrase());
            } else {
                obj = new JSONObject(EntityUtils.toString(entity));
                error_msg = (String) obj.get("error");
                if (obj.get("code") != null) {
                    error_code = Integer.parseInt((String) obj.get("code"));
                } else {
                    error_code = statusLine.getStatusCode();
                }

                throw new FotoliaApiException(error_code, error_msg);
            }

        }

        stream.write(EntityUtils.toByteArray(entity));

        stream.flush();
        if (output_file != null) {
            stream.close();
        }
    }

    /**
     * This method returns childs of a parent category in fotolia representative category system.
     * This method could be used to display a part of the category system or the all tree.
     * Fotolia categories system counts three levels.
     *
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getCategories1() throws JSONException
    {
        return this.getCategories1(FotoliaApi.LANGUAGE_ID_EN_US);
    }

    /**
     * This method returns childs of a parent category in fotolia representative category system.
     * This method could be used to display a part of the category system or the all tree.
     * Fotolia categories system counts three levels.
     *
     * @param  language_id
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getCategories1(final int language_id) throws JSONException
    {
        return this.getCategories1(language_id, 0);
    }

    /**
     * This method returns childs of a parent category in fotolia representative category system.
     * This method could be used to display a part of the category system or the all tree.
     * Fotolia categories system counts three levels.
     *
     * @param  language_id
     * @param  id
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getCategories1(final int language_id, final long id) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("language_id", Long.toString(language_id)));
        args.add(new BasicNameValuePair("id", Long.toString(id)));

        return this._api("getCategories1", args);
    }

    /**
     * This method returns childs of a parent category in fotolia conceptual category system.
     * This method could be used to display a part of the category system or the all tree.
     * Fotolia categories system counts three levels.
     *
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getCategories2() throws JSONException
    {
        return this.getCategories2(FotoliaApi.LANGUAGE_ID_EN_US);
    }

    /**
     * This method returns childs of a parent category in fotolia conceptual category system.
     * This method could be used to display a part of the category system or the all tree.
     * Fotolia categories system counts three levels.
     *
     * @param  language_id
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getCategories2(final int language_id) throws JSONException
    {
        return this.getCategories2(language_id, 0);
    }

    /**
     * This method returns childs of a parent category in fotolia conceptual category system.
     * This method could be used to display a part of the category system or the all tree.
     * Fotolia categories system counts three levels.
     *
     * @param  language_id
     * @param  id
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getCategories2(final int language_id, final long id) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("language_id", Long.toString(language_id)));
        args.add(new BasicNameValuePair("id", Long.toString(id)));

        return this._api("getCategories2", args);
    }

    /**
     * This method returns most searched tag and most used tag on fotolia website.
     * This method may help you to create a tags cloud.
     *
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getTags() throws JSONException
    {
        return this.getTags(FotoliaApi.LANGUAGE_ID_EN_US);
    }

    /**
     * This method returns most searched tag and most used tag on fotolia website.
     * This method may help you to create a tags cloud.
     *
     * @param  language_id
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getTags(final int language_id) throws JSONException
    {
        return this.getTags(language_id, FotoliaApi.TAG_TYPE_USED);
    }

    /**
     * This method returns most searched tag and most used tag on fotolia website.
     * This method may help you to create a tags cloud.
     *
     * @param  language_id
     * @param  type
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getTags(final int language_id, final String type) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("language_id", Long.toString(language_id)));
        args.add(new BasicNameValuePair("type", type));

        return this._api("getTags", args);
    }

    /**
     * Returns Fotolia public galleries
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getGalleries() throws JSONException
    {
        return this.getGalleries(FotoliaApi.LANGUAGE_ID_EN_US);
    }

    /**
     * Returns Fotolia public galleries
     *
     * @param  language_id
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getGalleries(final int language_id) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("language_id", Long.toString(language_id)));

        return this._api("getGalleries", args);
    }

    /**
     * Returns Fotolia list of countries
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getCountries() throws JSONException
    {
        return this.getCountries(FotoliaApi.LANGUAGE_ID_EN_US);
    }

    /**
     * Returns Fotolia list of countries
     *
     * @param  language_id
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getCountries(final int language_id) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("language_id", Long.toString(language_id)));

        return this._api("getCountries", args);
    }

    /**
     * Returns a media data
     *
     * @param  id
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getMediaData(final long id) throws JSONException
    {
        return this.getMediaData(id, FotoliaApi.THUMB_SIZE_SMALL);
    }

    /**
     * Returns a media data
     *
     * @param  id
     * @param  thumbnail_size
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getMediaData(final long id, final int thumbnail_size) throws JSONException
    {
        return this.getMediaData(id, thumbnail_size, FotoliaApi.LANGUAGE_ID_EN_US);
    }

    /**
     * Returns a media data
     *
     * @param  id
     * @param  thumbnail_size
     * @param  language_id
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getMediaData(final long id, final int thumbnail_size, final int language_id) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("id", Long.toString(id)));
        args.add(new BasicNameValuePair("thumbnail_size", Long.toString(thumbnail_size)));
        args.add(new BasicNameValuePair("language_id", Long.toString(language_id)));

        return this._api("getMediaData", args);
    }

    /**
     * Returns media data for a series of media
     *
     * @param  ids
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getMediaData(final ArrayList<Long> ids) throws JSONException
    {
        return this.getMediaData(ids, FotoliaApi.THUMB_SIZE_SMALL);
    }

    /**
     * Returns a media data
     *
     * @param  ids
     * @param  thumbnail_size
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getMediaData(final ArrayList<Long> ids, final int thumbnail_size) throws JSONException
    {
        return this.getMediaData(ids, thumbnail_size, FotoliaApi.LANGUAGE_ID_EN_US);
    }

    /**
     * Returns a media data
     *
     * @param  ids
     * @param  thumbnail_size
     * @param  language_id
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getMediaData(final ArrayList<Long> ids, final int thumbnail_size, final int language_id) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();

        for (Long id : ids) {
            args.add(new BasicNameValuePair("ids[]", Long.toString(id)));
        }
        args.add(new BasicNameValuePair("thumbnail_size", Long.toString(thumbnail_size)));
        args.add(new BasicNameValuePair("language_id", Long.toString(language_id)));

        return this._api("getBulkMediaData", args);
    }

    /**
     * This method return private galleries for logged user
     *
     * @param  id
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getMediaGalleries(final long id) throws JSONException
    {
        return this.getMediaGalleries(id, FotoliaApi.THUMB_SIZE_SMALL);
    }

    /**
     * This method return private galleries for logged user
     *
     * @param  id
     * @param  thumbnail_size
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getMediaGalleries(final long id, final int thumbnail_size) throws JSONException
    {
        return this.getMediaGalleries(id, thumbnail_size, FotoliaApi.LANGUAGE_ID_EN_US);
    }

    /**
     * This method return private galleries for logged user
     *
     * @param  id
     * @param  thumbnail_size
     * @param  language_id
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getMediaGalleries(final long id, final int thumbnail_size, final int language_id) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("id", Long.toString(id)));
        args.add(new BasicNameValuePair("thumbnail_size", Long.toString(thumbnail_size)));
        args.add(new BasicNameValuePair("language_id", Long.toString(language_id)));

        return this._api("getMediaGalleries", args);
    }

    /**
     * This method allows to purchase a media and returns url to the purchased file
     *
     * @param  id
     * @param  license_name
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getMedia(final long id, final String license_name) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("id", Long.toString(id)));
        args.add(new BasicNameValuePair("license_name", license_name));

        return this._api("getMedia", args);
    }

    /**
     * This method allows to purchase a media and returns url to the purchased file
     *
     * @param  id
     * @param  license_name
     * @param  subaccount_id
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getMedia(final long id, final String license_name, final long subaccount_id) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("id", Long.toString(id)));
        args.add(new BasicNameValuePair("license_name", license_name));
        args.add(new BasicNameValuePair("subaccount_id", Long.toString(subaccount_id)));

        return this._api("getMedia", args);
    }

    /**
     * This method returns comp images. Comp images can ONLY be used to evaluate the image
     * as to suitability for a project, obtain client or internal company approvals,
     * or experiment with layout alternatives.
     *
     * @param  id
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getMediaComp(final long id) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("id", Long.toString(id)));

        return this._api("getMediaComp", args);
    }

    /**
     * Authenticate a user
     *
     * @param  login
     * @param  password
     * @throws JSONException 
     */
    public void loginUser(final String login, final String password) throws JSONException
    {
        FotoliaApiArgs args;
        JSONObject res;
        Date cdate;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("login", login));
        args.add(new BasicNameValuePair("pass", password));
        res = this._api("loginUser", args);

        cdate = new Date();
        this._session_id = (String) res.get("session_token");
        this._session_id_timestamp = cdate.getTime();
    }

    /**
     * Logout a user
     */
    public void logoutUser()
    {
        this._session_id = null;
    }

    /**
     * Create a user
     *
     * @param  properties
     * @return Long
     * @throws JSONException 
     * @throws NumberFormatException 
     */
    public Long createUser(final FotoliaCreateMemberQuery properties) throws NumberFormatException, JSONException
    {
        FotoliaApiArgs args;
        JSONObject res;
        Date cdate;

        return Long.parseLong(this._api("createUser", properties.getFotoliaApiArgs()).toString());
    }


    /**
     * Returns user's data
     *
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getUserData() throws JSONException
    {
        return this._api("getUserData");
    }

    /**
     * This method returns sales data for logged user.
     *
     * @param  sales_type
     * @param  offset
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getSalesData(final String sales_type, final int offset) throws JSONException
    {
        return this.getSalesData(sales_type, offset, 50);
    }

    /**
     * This method returns sales data for logged user.
     *
     * @param  sales_type
     * @param  offset
     * @param  sales_day
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getSalesData(final String sales_type, final int offset, final String sales_day) throws JSONException
    {
        return this.getSalesData(sales_type, offset, 50, sales_day);
    }

    /**
     * This method returns sales data for logged user.
     *
     * @param  sales_type
     * @param  offset
     * @param  limit
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getSalesData(final String sales_type, final int offset, final int limit) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("sales_type", sales_type));
        args.add(new BasicNameValuePair("offset", Long.toString(offset)));
        args.add(new BasicNameValuePair("limit", Long.toString(limit)));

        return this._api("getSalesData", args);
    }

    /**
     * This method returns sales data for logged user.
     *
     * @param  sales_type
     * @param  offset
     * @param  limit
     * @param  sales_day
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getSalesData(final String sales_type, final int offset, final int limit, final String sales_day) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("sales_type", sales_type));
        args.add(new BasicNameValuePair("offset", Long.toString(offset)));
        args.add(new BasicNameValuePair("limit", Long.toString(limit)));
        args.add(new BasicNameValuePair("sales_day", sales_day));

        return this._api("getSalesData", args);
    }

    /**
     * This method returns sales data for logged user.
     *
     * @param  sales_type
     * @param  offset
     * @param  limit
     * @param  id
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getSalesData(final String sales_type, final int offset, final int limit, final long id) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("sales_type", sales_type));
        args.add(new BasicNameValuePair("offset", Long.toString(offset)));
        args.add(new BasicNameValuePair("limit", Long.toString(limit)));
        args.add(new BasicNameValuePair("id", Long.toString(id)));

        return this._api("getSalesData", args);
    }

    /**
     * This method returns sales data for logged user.
     *
     * @param  sales_type
     * @param  offset
     * @param  limit
     * @param  id
     * @param  sales_day
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getSalesData(final String sales_type, final int offset, final int limit, final long id, final String sales_day) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("sales_type", sales_type));
        args.add(new BasicNameValuePair("offset", Long.toString(offset)));
        args.add(new BasicNameValuePair("limit", Long.toString(limit)));
        args.add(new BasicNameValuePair("id", Long.toString(id)));
        args.add(new BasicNameValuePair("sales_day", sales_day));

        return this._api("getSalesData", args);
    }

    /**
     * This method allows you to get sales/views/income statistics from your account.
     *
     * @param  type
     * @param  time_range
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getUserAdvancedStats(final String type, final String time_range) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("type", type));
        args.add(new BasicNameValuePair("time_range", time_range));

        return this._api("getUserAdvancedStats", args);
    }

    /**
     * This method allows you to get sales/views/income statistics from your account.
     *
     * @param  type
     * @param  time_range
     * @param  easy_date_period
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getUserAdvancedStats(final String type, final String time_range, final String easy_date_period) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("type", type));
        args.add(new BasicNameValuePair("time_range", time_range));
        args.add(new BasicNameValuePair("easy_date_period", easy_date_period));

        return this._api("getUserAdvancedStats", args);
    }

    /**
     * This method allows you to get sales/views/income statistics from your account.
     *
     * @param  type
     * @param  time_range
     * @param  start_date
     * @param  end_date
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getUserAdvancedStats(final String type, final String time_range, final String start_date, final String end_date) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("type", type));
        args.add(new BasicNameValuePair("time_range", time_range));
        args.add(new BasicNameValuePair("start_date", start_date));
        args.add(new BasicNameValuePair("end_date", end_date));

        return this._api("getUserAdvancedStats", args);
    }

    /**
     * Returns statistics for logged in user
     *
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getUserStats() throws JSONException
    {
        return this._api("getUserStats");
    }

    /**
     * Delete a user's gallery
     *
     * @param  id
     * @return JSONObject
     * @throws JSONException 
     */
    public boolean deleteUserGallery(final String id) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("id", id));
        return this._api("deleteUserGallery", args).get("deleteusergallery") != null;
    }

    /**
     * Create a user's gallery
     *
     * @param  name
     * @return String
     * @throws JSONException 
     */
    public String createUserGallery(final String name) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("name", name));
        return (String) this._api("createUserGallery", args).get("id");
    }

    /**
     * Add a media to a user default gallery
     *
     * @param  content_id
     * @param  id
     * @return JSONObject
     * @throws JSONException 
     */
    public boolean addToUserGallery(final long content_id) throws JSONException
    {
        return this.addToUserGallery(content_id, "");
    }

    /**
     * Add a media to a user's gallery
     *
     * @param  content_id
     * @param  id
     * @return JSONObject
     * @throws JSONException 
     */
    public boolean addToUserGallery(final long content_id, final String id) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("id", id));
        args.add(new BasicNameValuePair("content_id", Long.toString(content_id)));
        return this._api("addToUserGallery", args).get("addToUserGallery") != null;
    }

    /**
     * Remove a media from a user default gallery
     *
     * @param  content_id
     * @param  id
     * @return JSONObject
     * @throws JSONException 
     */
    public boolean removeFromUserGallery(final long content_id) throws JSONException
    {
        return this.removeFromUserGallery(content_id, "");
    }

    /**
     * Remove a media from a user's gallery
     *
     * @param  content_id
     * @param  id
     * @return JSONObject
     * @throws JSONException 
     */
    public boolean removeFromUserGallery(final long content_id, final String id) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("id", id));
        args.add(new BasicNameValuePair("content_id", Long.toString(content_id)));
        return this._api("removeFromUserGallery", args).get("removeFromUserGallery") != null;
    }

    /**
     * This method allows to search media in logged user lightbox
     *
     * @param  page
     * @param  per_page
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getUserGalleryMedia(final int page, final int per_page) throws JSONException
    {
        return this.getUserGalleryMedia(page, per_page, FotoliaApi.THUMB_SIZE_SMALL);
    }

    /**
     * This method allows to search media in logged user lightbox
     *
     * @param  page
     * @param  per_page
     * @param  thumbnail_size
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getUserGalleryMedia(final int page, final int per_page, final int thumbnail_size) throws JSONException
    {
        return this.getUserGalleryMedia(page, per_page, thumbnail_size, "");
    }

    /**
     * This method allows to search media in logged user lightbox
     *
     * @param  page
     * @param  per_page
     * @param  id
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getUserGalleryMedia(final int page, final int per_page, final String id) throws JSONException
    {
        return this.getUserGalleryMedia(page, per_page, FotoliaApi.THUMB_SIZE_SMALL, id);
    }

    /**
     * This method allows to search media in logged user galleries or lightbox.
     *
     * @param  page
     * @param  per_page
     * @param  thumbnail_size
     * @param  id
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getUserGalleryMedia(final int page, final int per_page, final int thumbnail_size, final String id) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("page", Long.toString(page)));
        args.add(new BasicNameValuePair("per_page", Long.toString(per_page)));
        args.add(new BasicNameValuePair("thumbnail_size", Long.toString(thumbnail_size)));
        args.add(new BasicNameValuePair("id", id));
        return this._api("getUserGalleryMedias", args);
    }

    /**
     * This method returns private galleries for logged user.
     *
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject getUserGalleries() throws JSONException
    {
        return this._api("getUserGalleries");
    }

    /**
     * This method allows move up media in logged user lightbox
     *
     * @param  page
     * @param  per_page
     * @param  thumbnail_size
     * @param  id
     * @throws JSONException 
     */
    public void moveUpMediaInUserGallery(final long content_id) throws JSONException
    {
        this.moveUpMediaInUserGallery(content_id, "");
    }

    /**
     * This method allows move up media in logged user galleries
     *
     * @param  page
     * @param  per_page
     * @param  thumbnail_size
     * @param  id
     * @throws JSONException 
     */
    public void moveUpMediaInUserGallery(final long content_id, final String id) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("content_id", Long.toString(content_id)));
        args.add(new BasicNameValuePair("id", id));
        this._api("moveUpMediaInUserGallery", args);
    }

    /**
     * This method allows move down media in logged user lightbox
     *
     * @param  page
     * @param  per_page
     * @param  thumbnail_size
     * @param  id
     * @throws JSONException 
     */
    public void moveDownMediaInUserGallery(final long content_id) throws JSONException
    {
        this.moveDownMediaInUserGallery(content_id, "");
    }

    /**
     * This method allows move down media in logged user galleries
     *
     * @param  page
     * @param  per_page
     * @param  thumbnail_size
     * @param  id
     * @throws JSONException 
     */
    public void moveDownMediaInUserGallery(final long content_id, final String id) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("content_id", Long.toString(content_id)));
        args.add(new BasicNameValuePair("id", id));
        this._api("moveDownMediaInUserGallery", args);
    }

    /**
     * This method allows move a media to top position in logged user lightbox
     *
     * @param  page
     * @param  per_page
     * @param  thumbnail_size
     * @param  id
     * @throws JSONException 
     */
    public void moveMediaToTopInUserGallery(final long content_id) throws JSONException
    {
        this.moveMediaToTopInUserGallery(content_id, "");
    }

    /**
     * This method allows move a media to top position in logged user galleries
     *
     * @param  page
     * @param  per_page
     * @param  thumbnail_size
     * @param  id
     * @throws JSONException 
     */
    public void moveMediaToTopInUserGallery(final long content_id, final String id) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("content_id", Long.toString(content_id)));
        args.add(new BasicNameValuePair("id", id));
        this._api("moveMediaToTopInUserGallery", args);
    }

    /**
     * Create a new subaccount for the given api key
     *
     * @param  subaccount_data
     * @return long
     * @throws JSONException 
     * @throws NumberFormatException 
     */
    public long subaccountCreate(final FotoliaSubAccountData subaccount_data) throws NumberFormatException, JSONException
    {
        return Long.parseLong(this._api("user/subaccount/create", subaccount_data.getFotoliaApiArgs()).toString());
    }

    /**
     * Edit a subaccount for the given api key
     *
     * @param  subaccount_id
     * @param  subaccount_data
     * @throws JSONException 
     */
    public void subaccountEdit(final long subaccount_id, final FotoliaSubAccountData subaccount_data) throws JSONException
    {
        FotoliaApiArgs args;

        args = subaccount_data.getFotoliaApiArgs();
        args.add(new BasicNameValuePair("subaccount_id", Long.toString(subaccount_id)));
        this._api("user/subaccount/edit", args);
    }

    /**
     * Delete a subaccount of the given api key
     *
     * @param  subaccount_id
     * @throws JSONException 
     */
    public void subaccountDelete(final long subaccount_id) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("subaccount_id", Long.toString(subaccount_id)));
        this._api("user/subaccount/delete", args);
    }

    /**
     * Returns the list of subaccount IDs of the given api key
     *
     * @return ArrayList<Long>
     * @throws JSONException 
     */
    public ArrayList<Long> subaccountGetIds() throws JSONException
    {
        JSONObject response;
        Iterator iter;
        ArrayList<Long> ids;
        Map.Entry entry;

        response = this._api("user/subaccount/getIds");
        iter = response.keys();

        
        
        ids = new ArrayList<Long>();
        while (iter.hasNext()) {
        	String value = (String)response.get((String)iter.next());
            ids.add(Long.parseLong(value));
        }

        return ids;
    }

    /**
     * Returns details of a give subaccount ID
     *
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject subaccountGet(final long subaccount_id) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("subaccount_id", Long.toString(subaccount_id)));
        return this._api("user/subaccount/get", args);
    }

    /**
     * Returns the purchased contents of a given subaccount
     *
     * @param  subaccount_id
     * @param  page current page number
     * @param  nb_per_page number of downloads per page
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject subaccountGetPurchasedContents(final long subaccount_id, final int page, final int nb_per_page) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("subaccount_id", Long.toString(subaccount_id)));
        args.add(new BasicNameValuePair("page", Long.toString(page)));
        args.add(new BasicNameValuePair("nb_per_page", Long.toString(nb_per_page)));
        return this._api("user/subaccount/getPurchasedContents", args);
    }

    /**
     * Returns the content of the shopping cart
     *
     * @return JSONObject
     * @throws JSONException 
     */
    public JSONObject shoppingcartGetList() throws JSONException
    {
        return this._api("shoppingcart/getList");
    }

    /**
     * Clear the content of the shopping cart
     * @throws JSONException 
     */
    public void shoppingcartClear() throws JSONException
    {
        this._api("shoppingcart/clear");
    }

    /**
     * Transfer one file from the shopping cart to a lightbox
     * @throws JSONException 
     */
    public void shoppingcartTransferToLightbox(final long id) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("id", Long.toString(id)));
        this._api("shoppingcart/transferToLightbox", args);
    }

    /**
     * Transfer several files from the shopping cart to a lightbox
     * @throws JSONException 
     */
    public void shoppingcartTransferToLightbox(final ArrayList<Long> ids) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        for (Long id : ids) {
            args.add(new BasicNameValuePair("ids[]", Long.toString(id)));
        }
        this._api("shoppingcart/transferToLightbox", args);
    }

    /**
     * Add a content to the shopping cart
     *
     * @param  id
     * @param  license_name
     * @return array
     * @throws JSONException 
     */
    public void shoppingcartAdd(final long id, final String license_name) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("id", Long.toString(id)));
        args.add(new BasicNameValuePair("license_name", license_name));
        this._api("shoppingcart/add", args);
    }

    /**
     * Update a content in the shopping cart
     *
     * @param  id
     * @param  license_name
     * @return array
     * @throws JSONException 
     */
    public void shoppingcartUpdate(final long id, final String license_name) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("id", Long.toString(id)));
        args.add(new BasicNameValuePair("license_name", license_name));
        this._api("shoppingcart/update", args);
    }

    /**
     * Delete a file from the shopping cart
     * @throws JSONException 
     */
    public void shoppingcartRemove(final long id) throws JSONException
    {
        FotoliaApiArgs args;

        args = new FotoliaApiArgs();
        args.add(new BasicNameValuePair("id", Long.toString(id)));
        this._api("shoppingcart/remove", args);
    }

    /**
     * Call a methof of the Fotolia API
     *
     * @param  method_name
     * @return JSONObject
     * @throws JSONException 
     */
    private JSONObject _api(final String method_name) throws JSONException
    {
        return this._api(method_name, null, true);
    }

    /**
     * Call a methof of the Fotolia API
     *
     * @param  method_name
     * @param  args
     * @return JSONObject
     * @throws JSONException 
     */
    private JSONObject _api(final String method_name, final FotoliaApiArgs args) throws JSONException
    {
        return this._api(method_name, args, true);
    }

    /**
     * Call a methof of the Fotolia API
     *
     * @param  method_name
     * @param  args
     * @param  auto_refresh_token
     * @return JSONObject
     * @throws JSONException 
     */
    private JSONObject _api(final String method_name, final FotoliaApiArgs args, final boolean auto_refresh_token) throws JSONException
    {
        DefaultHttpClient client;
        FotoliaApiResponse responseHandler;
        JSONObject response = null;

        client = this._getHttpClient(auto_refresh_token);

        try {
            responseHandler = new FotoliaApiResponse();
            response = new JSONObject(client.execute(this._getMethod(method_name, args), responseHandler).toString());
        } catch (Exception e) {
        	Sistem.printStackTrace(e);
//            syso("Exception while retrieving data : " + e);
        } finally {
            client.getConnectionManager().shutdown();
        }

        return response;
    }

    /**
     * Construct and returns a usuable http client
     *
     * @param  auto_refresh_token
     * @return DefaultHttpClient
     * @throws JSONException 
     */
    private DefaultHttpClient _getHttpClient(final boolean auto_refresh_token) throws JSONException
    {
        DefaultHttpClient client;

        client = new DefaultHttpClient();
        client.getCredentialsProvider()
            .setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM),
                            new UsernamePasswordCredentials(this._api_key, this._getSessionId(auto_refresh_token)));

        HttpConnectionParams.setConnectionTimeout(client.getParams(), FotoliaApi.API_CONNECT_TIMEOUT * 1000);
        HttpConnectionParams.setSoTimeout(client.getParams(), FotoliaApi.API_PROCESS_TIMEOUT * 1000);

        return client;
    }

    /**
     * Returns session ID
     *
     * @param  auto_refresh_token
     * @return String
     * @throws JSONException 
     */
    private String _getSessionId(final boolean auto_refresh_token) throws JSONException
    {
        Date ctime = new Date();
        JSONObject response;


        if (
            this._session_id != null &&
            ctime.getTime() > this._session_id_timestamp + FotoliaApi.TOKEN_TIMEOUT
            && auto_refresh_token
            ) {
            response = this._api("refreshToken", null, false);
            this._session_id = (String) response.get("session_token");
            this._session_id_timestamp = ctime.getTime();
        }

        return this._session_id == null ? "" : this._session_id;
    }

    /**
     * Genereate the correct HTTP method to execute
     *
     * @param  method_name
     * @param  args
     * @return HttpRequestBase
     */
    private HttpRequestBase _getMethod(final String method_name, final FotoliaApiArgs args) throws UnsupportedEncodingException
    {
        if (this._isPostMethod(method_name)) {
            HttpPost method = new HttpPost(this._getUri(method_name));
            method.setEntity(args.getUrlEncodedFormEntity());

            return method;
        } else {
            return new HttpGet(this._getUri(method_name, args));
        }
    }

    /**
     * Generate the Fotolia API call URI
     *
     * @return String
     */
    private String _getUri(final String method_name)
    {
        return this._getUri(method_name, null);
    }

    /**
     * Generate the Fotolia API call URI
     *
     * @return String
     */
    private String _getUri(final String method_name, final FotoliaApiArgs args)
    {
        String uri;
        String namespace;

        namespace = this._getNamespace(method_name);
        if (namespace.length() > 0) {
            namespace += "/";
        }

        uri = "http";

        if (this._use_https) {
            uri += 's';
        }

        uri += "://" + FotoliaApi.REST_URI + "/" + FotoliaApi.REST_VERSION + "/" + namespace + method_name;

        if (args != null) {
            uri += "?" + args;
        }

        return uri;
    }

    /**
     * Returns the namespace to use for a method
     *
     * @param  method_name
     * @return String
     */
    private String _getNamespace(final String method_name)
    {
        if (
            method_name == "getSearchResults" ||
            method_name == "getCategories1" ||
            method_name == "getCategories2" ||
            method_name == "getTags" ||
            method_name == "getGalleries" ||
            method_name == "getCountries"
            ) {
                return "search";
        }

        if (
            method_name == "getMediaData" ||
            method_name == "getBulkMediaData" ||
            method_name == "getMediaGalleries" ||
            method_name == "getMedia" ||
            method_name == "getMediaComp"
            ) {
                return "media";
        }

        if (
            method_name == "loginUser" ||
            method_name == "createUser" ||
            method_name == "refreshToken" ||
            method_name == "getUserData" ||
            method_name == "getSalesData" ||
            method_name == "getUserGalleries" ||
            method_name == "getUserGalleryMedias" ||
            method_name == "deleteUserGallery" ||
            method_name == "createUserGallery" ||
            method_name == "addToUserGallery" ||
            method_name == "removeFromUserGallery" ||
            method_name == "moveUpMediaInUserGallery" ||
            method_name == "moveDownMediaInUserGallery" ||
            method_name == "moveMediaToTopInUserGallery" ||
            method_name == "getUserAdvancedStats" ||
            method_name == "getUserStats"
            ) {
            return "user";
        }

        if (
            method_name == "getData" ||
            method_name == "test"
            ) {
            return "main";
        }

        return "";
    }

    /**
     * Returns TRUE if the method requires a POST method
     *
     * @param  method
     * @return boolean
     */
    private boolean _isPostMethod(final String method)
    {
        if (
            method == "loginUser" ||
            method == "createUser" ||
            method == "shoppingcart/add" ||
            method == "shoppingcart/update" ||
            method == "shoppingcart/remove" ||
            method == "shoppingcart/transferToLightbox" ||
            method == "shoppingcart/clear" ||
            method == "refreshToken" ||
            method == "deleteUserGallery" ||
            method == "createUserGallery" ||
            method == "renameUserGallery" ||
            method == "addToUserGallery" ||
            method == "removeFromUserGallery" ||
            method == "moveUpMediaInUserGallery" ||
            method == "moveDownMediaInUserGallery" ||
            method == "moveMediaToTopInUserGallery" ||
            method == "updateProfile" ||
            method == "user/subaccount/create" ||
            method == "user/subaccount/edit" ||
            method == "user/subaccount/delete" ||
            method == "user/subaccount/createSubscription" ||
            method == "user/subaccount/deleteSubscription"
            ) {
                return true;
        } else {
            return false;
        }
    }
}
