package hello;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import hello.ShopAddress;
import hello.ShopBean;
import hello.Application;


@RunWith(SpringRunner.class)
@SpringBootTest

/*(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
*/
@TestPropertySource(properties = { "management.port=0" })
public class RetailManagerControllerTests {

@LocalServerPort
private int port;

@Value("${local.management.port}")
private int mgt;

@Autowired
private TestRestTemplate testRestTemplate;

@Test
public void getShopDetailsTest() throws Exception {
@SuppressWarnings("rawtypes")
ResponseEntity entity = this.testRestTemplate.getForEntity(
"http://localhost:" + this.port + "/getShopDetails",
ShopBean.class);

then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
}

@Test
public void postShopDetailsTest1() throws Exception {
HttpHeaders headers = new HttpHeaders();

headers.set("Content-Type", "application/json");
ShopBean shopBean = new ShopBean();
ShopAddress shopAddress = new ShopAddress();
shopAddress.setShopNumber(12345);
shopAddress.setShopPostCode(458990);
shopAddress.setShopPlace("Warje");
shopBean.setShopAddress(shopAddress);
shopBean.setShopName("Amol Shop1");
HttpEntity httpEntity = new HttpEntity(shopBean,
headers);
@SuppressWarnings("rawtypes")
ResponseEntity entity = this.testRestTemplate.postForEntity(
"http://localhost:" + this.port + "/postShopDetails",
httpEntity, ShopBean.class);

then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
}
@Test
public void postShopDetailsTest2() throws Exception {
HttpHeaders headers = new HttpHeaders();

headers.set("Content-Type", "application/json");
ShopBean shopBean = new ShopBean();
ShopAddress shopAddress = new ShopAddress();
shopAddress.setShopNumber(12345);
shopAddress.setShopPostCode(458990);
shopAddress.setShopPlace("Warje");
shopBean.setShopAddress(shopAddress);
shopBean.setShopName("Vishal Shop2");
HttpEntity httpEntity = new HttpEntity(shopBean,
headers);
@SuppressWarnings("rawtypes")
ResponseEntity entity = this.testRestTemplate.postForEntity(
"http://localhost:" + this.port + "/postShopDetails",
httpEntity, ShopBean.class);

then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
}

@Test
public void getShopList() throws Exception {
@SuppressWarnings("rawtypes")
ResponseEntity entity = this.testRestTemplate.getForEntity(
"http://localhost:" + this.port + "/getShopList?Lng=77.10249019999999&Lat=28.7040592",
List.class);

then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
}
}