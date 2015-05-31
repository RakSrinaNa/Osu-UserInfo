package mrcraftcod.osuuserinfo.utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import mrcraftcod.osuuserinfo.Main;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class URLHandler
{
	private static final int TIMEOUT = 30000;
	private static final String USER_AGENT_KEY = "User-Agent";
	private static final String USER_AGENT = "Osu!UserInfo/" + Main.VERSION;
	private static final String JSON_TYPE_KEY = "accept";
	private static final String JSON_TYPE = "application/json";
	private static final String CHARSET_TYPE_KEY = "charset";
	private static final String CHARSET_TYPE = "utf-8";
	private static final String CONTENT_TYPE_KEY = "Content-Type";
	private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
	private static final String LANGUAGE_TYPE_KEY = "Accept-Language";
	private static final String LANGUAGE_TYPE = "fr-FR";

	public static InputStream getAsBinary(URL url, Map<String, String> headers) throws UnirestException, URISyntaxException
	{
		return getAsBinary(url, headers, null);
	}

	private static InputStream getAsBinary(URL url, Map<String, String> headers, Map<String, String> params) throws UnirestException, URISyntaxException
	{
		GetRequest req = getRequest(url, headers, params);
		HttpResponse<InputStream> binaryResponse = req.asBinary();
		return binaryResponse.getBody();
	}

	public static JSONObject getAsJSON(URL url, Map<String, String> headers) throws UnirestException, URISyntaxException
	{
		return getAsJSON(url, headers, null);
	}

	private static JSONObject getAsJSON(URL url, Map<String, String> headers, Map<String, String> params) throws UnirestException, URISyntaxException
	{
		headers.put(JSON_TYPE_KEY, JSON_TYPE);
		GetRequest req = getRequest(url, headers, params);
		HttpResponse<JsonNode> jsonResponse = req.asJson();
		return jsonResponse.getBody().getObject();
	}

	private static String getAsString(URL url, Map<String, String> headers, Map<String, String> params) throws UnirestException, URISyntaxException
	{
		GetRequest req = getRequest(url, headers, params);
		HttpResponse<String> response = req.asString();
		return response.getBody();
	}

	private static GetRequest getRequest(URL url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException
	{
		Unirest.clearDefaultHeaders();
		Unirest.setTimeouts(TIMEOUT, TIMEOUT);
		Unirest.setDefaultHeader(USER_AGENT_KEY, USER_AGENT);
		URIBuilder uriBuilder = new URIBuilder(url.toURI());
		if(params != null)
			for(String key : params.keySet())
				uriBuilder.addParameter(key, params.get(key));
		System.out.println(uriBuilder.build().toString());
		return Unirest.get(uriBuilder.build().toString()).headers(headers).header(LANGUAGE_TYPE_KEY, LANGUAGE_TYPE).header(CONTENT_TYPE_KEY, CONTENT_TYPE).header(CHARSET_TYPE_KEY, CHARSET_TYPE).header(USER_AGENT_KEY, USER_AGENT);
	}
}
