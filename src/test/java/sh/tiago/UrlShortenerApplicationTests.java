package sh.tiago;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UrlShortenerApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testShortenerURLWithAnInvalidURL() {
		final String EXPECTED_ERROR_MESSAGE = "{message: \"Invalid URL\"}";

		MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
		request.add("url", "someInvalidURL1234");
		ResponseEntity result = this.restTemplate.postForEntity("/shorten", request, String.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
		JSONAssert.assertEquals(EXPECTED_ERROR_MESSAGE, result.getBody().toString(), false);
	}

	@Test
	public void testShortenerURLWithAValidURL() {
		final String EXPECTED_SHORT_ID = "4170157c";

		MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
		request.add("url", "www.google.com");
		ResponseEntity result = this.restTemplate.postForEntity("/shorten", request, String.class);
		Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assert.assertEquals(EXPECTED_SHORT_ID, result.getBody().toString());

		result = this.restTemplate.getForEntity("/" + EXPECTED_SHORT_ID, String.class);
		Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	public void testNavigatingToNonExistingMap() {
		final String EXPECTED_ERROR_MESSAGE = "Impossible to find the URL for someInvalidID!";
		ResponseEntity result = this.restTemplate.getForEntity("/someInvalidID", String.class);
		Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assert.assertTrue(result.getBody().toString().contains(EXPECTED_ERROR_MESSAGE));
	}
}
