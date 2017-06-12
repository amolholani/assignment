package hello;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONUtil {
private static String GEOURL ="http://maps.googleapis.com/maps/api/geocode/json?address=&sensor=true";

public static String readLatLongFromAddress(String address) throws Exception {
String url =GEOURL.replaceAll("", address) ;
String jsonText = getJSON(url);
JSONObject json = new JSONObject(jsonText);
JSONArray mainAry=json.getJSONArray("results");
JSONObject jsonAry =(JSONObject) mainAry.get(0);
JSONObject geoObj =(JSONObject) jsonAry.get("geometry");
JSONObject locaObj =(JSONObject) geoObj.get("location");
return locaObj.getString("lng")+":"+locaObj.getString("lat");
}
public static String getJSON(String url) {
HttpURLConnection c = null;
try {
URL u = new URL(url);
c = (HttpURLConnection) u.openConnection();
c.setRequestMethod("GET");
c.setRequestProperty("Content-length", "0");
c.setUseCaches(false);
c.setAllowUserInteraction(false);

c.connect();
int status = c.getResponseCode();

switch (status) {
case 200:
case 201:
BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
StringBuilder sb = new StringBuilder();
String line;
while ((line = br.readLine()) != null) {
sb.append(line+"\n");
}
br.close();
return sb.toString();
}

} catch (Exception ex) {
}finally {
if (c != null) {
try {
c.disconnect();
} catch (Exception ex) {
}
}
}
return null;
}
public static void main(String[] args) throws Exception {

System.out.println(readLatLongFromAddress("delhi"));
}
}