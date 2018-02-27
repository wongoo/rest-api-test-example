package cn.soundbus.test.rest;



import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author wangoo
 * @crated 2018-02-27 11:29
 */
public class GithubUserTest {

    @Test
    public void testQueryGithubUser() throws Exception {
        // Given
        String name = RandomStringUtils.randomAlphabetic(8);

        HttpUriRequest request = new HttpGet("https://api.github.com/users/" + name);

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // Then
        assertThat(httpResponse.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_NOT_FOUND));
    }

    @Test
    public void testMimeType() throws Exception {
        // Given
        String jsonMimeType = "application/json";
        HttpUriRequest request = new HttpGet("https://api.github.com/users/eugenp");

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Then
        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        assertEquals(jsonMimeType, mimeType);
    }
}
