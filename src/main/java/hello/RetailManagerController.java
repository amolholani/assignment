package hello;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
/*
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
*/
import hello.ShopAddress;
import hello.ShopBean;
import hello.RetailStore;
import hello.JSONUtil;

@Controller
public class RetailManagerController {

@RequestMapping(value = "/getShopDetails")
public ResponseEntity get() {
ShopBean shopBean = new ShopBean();
ShopAddress shopAddress = new ShopAddress();
shopAddress.setShopNumber(12345);
shopAddress.setShopPostCode(458990);
shopBean.setShopAddress(shopAddress);
shopBean.setShopName("Vishal Shop");
shopBean.setShopLongitude(1.2);
shopBean.setShopLatitude(1.2);
return new ResponseEntity(shopBean, HttpStatus.OK);
}

@RequestMapping(value = "/postShopDetails", method = RequestMethod.POST)
public ResponseEntity postShopDetails(
@RequestBody ShopBean shopBean) {

if (shopBean != null) {
try {
String latLong = JSONUtil.readLatLongFromAddress(shopBean
.getShopAddress().getShopPlace());
String[] latLongAry = latLong.split(":");
shopBean.setShopLongitude(Double.parseDouble(latLongAry[0]));
shopBean.setShopLatitude(Double.parseDouble(latLongAry[1]));
List shops = RetailStore.datamap.get(shopBean
.getShopAddress().getShopPlace());
if (shops == null) {
shops = new ArrayList();
}

shops.add(shopBean);
RetailStore.datamap.put(shopBean.getShopAddress()
.getShopPlace(), shops);

} catch (Exception e) {

}
}

return new ResponseEntity(shopBean, HttpStatus.OK);
}

@RequestMapping(value = "/getShopList", method = RequestMethod.GET)
public @ResponseBody ResponseEntity<List> getShopList(
@RequestParam("Lng") double lng, @RequestParam("Lat") double lat) {
List shopLst = new ArrayList();
for (Entry<String, List> shopMap : RetailStore.datamap
.entrySet()) {
/*
for (ShopBean shopBean1 : shopMap.getValue()) {
	if (lng == shopBean1.getShopLongitude()
		&& lat == shopBean1.getShopLatitude()) {
			shopLst.add(shopBean1);
	}

}*/

}

return new ResponseEntity<List>(shopLst, HttpStatus.OK);
}
}