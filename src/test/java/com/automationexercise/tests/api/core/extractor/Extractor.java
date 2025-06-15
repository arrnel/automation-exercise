package com.automationexercise.tests.api.core.extractor;

import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

/**
 * @author Arrnel
 * <h3>Extractions examples</h3>
 *
 * <h4>1) Extract cookie</h4>
 * Cookie cookieValue = response.extract().getCookie("etpsid");<br/>
 *
 * <h4>2) Extract body as dto class</h4>
 * RootDTO example1 = response.extract().asPojo(RootDTO.class);<br/>
 * or<br/>
 * Example example2 = response.extract().asPojo(".", Example.class);<br/>
 *
 * <h4>
 * 3) Extract key as dto class.
 * </h4>
 * Example example3 = response.extract().asPojo("path.to.the.key", Example.class);<br/>
 *
 * <h4>
 * 4) Extract body as list.
 * </h4>
 * Example example4 = response.extract().asList(Example.class);<br/>
 * or<br/>
 * Example example5 = response.extract().asList(".", Example.class);<br/>
 *
 * <h4>
 * 5) Extract key as list.
 * </h4>
 * Example example6 = response.extract().asList("path.to.the.key", Example.class);<br/>
 *
 * <h4>
 * 6) Extract key as value.
 * </h4>
 * String stringVal = response.extract().asValue("path.to.the.key");<br/>
 * int stringVal = response.extract().asValue("path.to.the.key");<br/>
 */
@ParametersAreNonnullByDefault
public record Extractor(Response response) {

    /**
     * Deserialize object JSON/XML as T &lt;T&gt;.
     *
     * @param path   path to the key in response body;
     * @param tClass DTO class;
     * @return T &lt;T&gt;
     */
    public <T> T asPojo(String path, Class<T> tClass) {

        var contentType = ContentType.fromContentType(response.getContentType());

        return switch (contentType) {
            case JSON -> response.jsonPath().getObject(path, tClass);
            case HTML -> response.jsonPath().getObject(path, tClass);
            default -> throw new IllegalStateException("Unexpected response content type: %s".formatted(contentType));
        };

    }

    /**
     * Deserialize object JSON/XML as T &lt;T&gt;.
     *
     * @param tClass DTO class;
     * @return T &lt;T&gt;;
     */
    public <T> T asPojo(Class<T> tClass) {
        return asPojo(".", tClass);
    }

    /**
     * Deserialize object JSON/XML as List&lt;T&gt;.
     *
     * @param path   path to the key in response body;
     * @param tClass DTO class
     * @return List &lt;T&gt;;
     */
    public <T> List<T> asList(String path, Class<T> tClass) {

        var contentType = ContentType.fromContentType(response.getContentType());

        return switch (contentType) {
            case JSON -> response.jsonPath().getList(path, tClass);
            case HTML -> response.jsonPath().getList(path, tClass);
            default -> throw new IllegalStateException("Unexpected response content type: %s".formatted(contentType));
        };

    }

    /**
     * Deserialize object JSON/XML as List&lt;T&gt;.
     *
     * @param tClass DTO class;
     * @return List&lt;T&gt;;
     */
    public <T> List<T> asList(Class<T> tClass) {
        return asList(".", tClass);
    }

    /**
     * Get key value &lt;T&gt; T.
     *
     * @param path path to the key in response body;
     * @return &lt;T&gt; T;
     */
    public <T> T asValue(String path) {
        return response.jsonPath().get(path);
    }

    /**
     * Get body as String.
     *
     * @return String;
     */
    public String getBody() {
        return response.getBody().prettyPrint();
    }

    /**
     * Get response cookie by name.<br/>
     * If request was redirected, RestAssured will return cookie from last response.
     *
     * @param name cookie name;
     * @return Cookie;
     */
    public Cookie getCookie(String name) {
        return response.getDetailedCookie(name);
    }

    /**
     * Get all cookies from response<br/>
     * If request was redirected, RestAssured will return all cookies from last response.
     *
     * @return Cookies;
     */
    public Cookies getCookies() {
        return response.getDetailedCookies();
    }

    /**
     * Get response header by name<br/>
     * If request was redirected, RestAssured will return header from last response.
     *
     * @param name header name;
     * @return String;
     */
    public String getHeader(String name) {
        return response.getHeader(name);
    }

    /**
     * Get all response headers. <br/>
     * If request was redirected, RestAssured will return all headers from last response.
     *
     * @return Headers;
     */
    public Headers getHeaders() {
        return response.getHeaders();
    }

    /**
     * Return RestAssured Response
     *
     * @return Response
     */
    @Override
    public Response response() {
        return response;
    }

}
