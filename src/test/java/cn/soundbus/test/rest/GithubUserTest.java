package cn.soundbus.test.rest;



import com.google.gson.Gson;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

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

    /**
     * 该测试测试如何解析返回的json
     *
     * 浏览器访问 https://api.github.com/users/wongoo 可以看到返回的jason内容
     *
     * 使用gson库来解析返回的内容为json来进行处理
     *
     * gson简单教程参考： http://blog.csdn.net/top_code/article/details/51896555
     */
    @Test
    public void testParseJson() throws IOException {
        // Given
        String name = "wongoo";

        HttpUriRequest request = new HttpGet("https://api.github.com/users/" + name);

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // Then
        assertThat(httpResponse.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

        /* 获得返回的内容未字符串 */
        String jsonString = EntityUtils.toString(httpResponse.getEntity());

        /* 将内容通过gson库转换为一个hashmap对象 */
        Gson gson = new Gson();
        HashMap responseObject = gson.fromJson(jsonString, HashMap.class);

        /* 从hashmap中去除值判断值是否和期望一致 */
        assertEquals(responseObject.get("name"), "望哥");
        assertEquals(responseObject.get("company"), "soundbus");


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
