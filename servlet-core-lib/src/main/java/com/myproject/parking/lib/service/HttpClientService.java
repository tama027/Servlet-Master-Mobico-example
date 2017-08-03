package com.myproject.parking.lib.service;

import java.io.IOException;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientService {
	private static final Logger LOG = LoggerFactory.getLogger(HttpClientService.class);
	
	private CloseableHttpClient closeableHttpClient;
	private String urlMidtrans = "";
	private String baseurlMidtransAPI = "";
	private String serverKey = "";
	private int timeout = 30;  // wait for 30s

	public void start() {
		HttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(timeout * 1000)
				.setConnectTimeout(timeout * 1000)
				.build();
		ConnectionReuseStrategy connStrategy = new DefaultConnectionReuseStrategy();
		closeableHttpClient = HttpClientBuilder
				.create()
				.setDefaultRequestConfig(requestConfig)
				.setConnectionManager(cm)
				.setConnectionReuseStrategy(connStrategy)
				.build();
		
		
		LOG.info("HttpClientService is started with destUrl: {}, timeout: {}", 
				new String[] { urlMidtrans, "" + timeout });
	}
	
	public void stop() throws IOException {
		closeableHttpClient.close();
		LOG.info("HttpClientService has been stopped");
	}
	
	public String getTransactionStatus(String orderId) throws ParkingEngineException, IOException {
		String baseurl = baseurlMidtransAPI+orderId+"/status";
        HttpGet httpGet = new HttpGet(baseurl);
		try {
			String str = serverKey+":";
//		    String authString = "Basic" + Base64.getEncoder().encodeToString(str.getBytes());
			String authString = "Basic";
		    httpGet.setHeader("Content-type", "application/json");
		    httpGet.setHeader("Accept", "application/json");		    
		    httpGet.setHeader("Authorization", authString);
			HttpResponse response = closeableHttpClient.execute(httpGet);			
			if (response.getStatusLine().getStatusCode() == 200) {
				String respString = EntityUtils.toString(response.getEntity());
                LOG.debug("Response: {}", new String[] { respString} );                
                return respString;
			} else {				
				LOG.warn("Invalid statusCode: {}", new String[] { "" + response.getStatusLine().getStatusCode()} );
				String respString = EntityUtils.toString(response.getEntity());
				LOG.warn("Response: {}", new String[] { respString} );
				throw new ParkingEngineException(ParkingEngineException.NE_ERROR_RESPONSE_HOST);
			}  // end if statusCode != 200
		} catch (ParkingEngineException me) {
			throw me;
		}catch (Exception e) {
			if (httpGet != null)
				httpGet.abort();
            LOG.warn("Unknown Error", e);
            throw new ParkingEngineException(ParkingEngineException.ENGINE_UNKNOWN_ERROR);
        }   finally {
	    	if (httpGet != null)
	    		httpGet.releaseConnection();
	    } // end try catch
	}
	
	public String chargeMidtransPost(String body) throws ParkingEngineException, IOException {
        HttpPost httpPost = new HttpPost(urlMidtrans);
		try {
			String str = serverKey+":";
//		    String authString = "Basic "+Base64.getEncoder().encodeToString(str.getBytes());
			String authString = "Basic";
		    httpPost.setHeader("Content-type", "application/json");
			httpPost.setHeader("Accept", "application/json");		    
		    httpPost.setHeader("Authorization", authString);
		    StringEntity entity = new StringEntity(body);
		    httpPost.setEntity(entity);
			LOG.debug("sendingPost: " + body);			
			HttpResponse response = closeableHttpClient.execute(httpPost);			
			if (response.getStatusLine().getStatusCode() == 201) {
				String respString = EntityUtils.toString(response.getEntity());                                
                return respString;
			} else {
				LOG.warn("Invalid statusCode: {}", new String[] { "" + response.getStatusLine().getStatusCode()} );
				String respString = EntityUtils.toString(response.getEntity());
				LOG.warn("Response: {}", new String[] { respString} );
				throw new ParkingEngineException(ParkingEngineException.NE_ERROR_RESPONSE_HOST);
			}  // end if statusCode != 200
		} catch (ParkingEngineException me) {
			throw me;
		} catch (Exception e) {
			if (httpPost != null)
				httpPost.abort();
            LOG.warn("Unknown Error", e);
            throw new ParkingEngineException(ParkingEngineException.ENGINE_UNKNOWN_ERROR);
        }   finally {
	    	if (httpPost != null)
	    		httpPost.releaseConnection();
	    } // end try catch
	}
	
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setServerKey(String serverKey) {
		this.serverKey = serverKey;
	}

	public void setUrlMidtrans(String urlMidtrans) {
		this.urlMidtrans = urlMidtrans;
	}

	public void setBaseurlMidtransAPI(String baseurlMidtransAPI) {
		this.baseurlMidtransAPI = baseurlMidtransAPI;
	}

}
