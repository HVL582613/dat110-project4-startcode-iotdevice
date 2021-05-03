package no.hvl.dat110.aciotdevice.client;

import java.io.IOException;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestClient {

	public RestClient() {
		// TODO Auto-generated constructor stub
	}

	private static String logpath = "/accessdevice/log";
	private String host = Configuration.host;
	private int port = Configuration.port;
	private String url = "http://" + host + ":" + port;
	private String logUrl = url + logpath;
	
	private static final MediaType JSON
			= MediaType.parse("application/json; charset=utf-8");

	public void doPostAccessEntry(String message) {

		// TODO: implement a HTTP POST on the service to post the message
		
		String json = new Gson().toJson(new AccessMessage(message));
		RequestBody req = RequestBody.create(JSON, json);
		OkHttpClient client = new OkHttpClient();
		
		Request request = new Request.Builder()
				.url(logUrl)
				.post(req)
				.build();
		
		try(Response response = client.newCall(request).execute()) {
			System.out.println(response.body().string());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String codepath = "/accessdevice/code";
	
	public AccessCode doGetAccessCode() {

		AccessCode code = null;
		
		// TODO: implement a HTTP GET on the service to get current access code
		
		OkHttpClient client = new OkHttpClient();
		
		Request request = new Request.Builder()
				.url(url + codepath)
				.get()
				.build();
		System.out.println(request.toString());
		
		try(Response response = client.newCall(request).execute()) {
			String stringResponse = response.body().string();
			System.out.println(stringResponse);
			code = new Gson().fromJson(stringResponse, AccessCode.class);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return code;
	}
}
