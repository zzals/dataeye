package kr.co.penta.dataeye.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;

public class UrlConnctionUtils {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());
    
    private static final UrlConnctionUtils URL_CONNCTION_UTILS = new UrlConnctionUtils();
    
    private UrlConnctionUtils() {
        super();
    }

    public JSONObject sendPostPayloadRequest(String requestUrl, JSONObject reqParam) {
        JSONObject response = new JSONObject();
        HttpURLConnection connection = null;
        OutputStream          os   = null;
        InputStream           is   = null;
        ByteArrayOutputStream baos = null;
        try {
            URL url = new URL(requestUrl);
            connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            
            os = connection.getOutputStream();
            os.write(reqParam.toString().getBytes());
            os.flush();
            
            String responseStr = null;
            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK) {
             
                is = connection.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData = null;
                int nLength = 0;
                while((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();
                 
                responseStr = new String(byteData);
                 
                response = new JSONObject(responseStr);     
            }
        } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
        } finally {
            try { is.close(); } catch (IOException e) {}
            try { os.close(); } catch (IOException e) {}
            connection.disconnect();
        }
        
        return response;
    }
    
    public JSONObject sendPostRequest(String requestUrl, Map<String, Object> reqParam) {
        JSONObject response = new JSONObject();
        HttpURLConnection connection = null;
        OutputStream          os   = null;
        InputStream           is   = null;
        ByteArrayOutputStream baos = null;
        try {
            URL url = new URL(requestUrl);
            connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            
            os = connection.getOutputStream();
            os.write(paramBuild(reqParam).getBytes());
            os.flush();
            
            String responseStr = null;
            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK) {
             
                is = connection.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData = null;
                int nLength = 0;
                while((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();
                 
                responseStr = new String(byteData);
                 
                response = new JSONObject(responseStr);     
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            try { is.close(); } catch (IOException e) {}
            try { os.close(); } catch (IOException e) {}
            connection.disconnect();
        }
        
        return response;
    }
    
    private String paramBuild(Map<String, Object> reqParam) {
        String params = Joiner.on("&").withKeyValueSeparator("=").join(reqParam);
        LOG.debug(params);
        
        return params;
    }
    
    public static UrlConnctionUtils getInstance() {
        return URL_CONNCTION_UTILS;
    }
}