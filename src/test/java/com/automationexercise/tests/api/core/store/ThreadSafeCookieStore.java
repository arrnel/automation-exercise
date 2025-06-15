package com.automationexercise.tests.api.core.store;

import com.automationexercise.tests.ex.CookieNotFoundException;
import com.automationexercise.tests.models.api.Token;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.automationexercise.tests.config.test.Config.CSRF_COOKIE_TITLE;
import static com.automationexercise.tests.config.test.Config.SESSION_ID_COOKIE_TITLE;

public enum ThreadSafeCookieStore {

    INSTANCE;

    private final ThreadLocal<Map<String, Map<String, Cookie>>> threadSafeCookieStore = ThreadLocal.withInitial(
            ThreadSafeCookieStore::inMemoryCookieStore
    );

    private static Map<String, Map<String, Cookie>> inMemoryCookieStore() {
        return new HashMap<>();
    }

    public void add(Cookies cookies) {
        cookies.forEach(cookie ->
                get().put(cookie.getName(), cookie)
        );
    }

    public Map<String, Cookie> get(String uri) {
        return getStore().computeIfAbsent(
                uri,
                v -> new HashMap<>()
        );
    }

    public Map<String, Cookie> get() {
        return getStore().computeIfAbsent(
                null,
                v -> new HashMap<>()
        );
    }

    public Cookies getCookies() {
        return new Cookies(
                getStore().values().stream()
                        .flatMap(c -> c.values().stream())
                        .toList());
    }

    public Cookies getCookies(String domain) {
        return new Cookies(get(domain).values().stream().toList());
    }

    public List<String> getURIs() {
        return getStore().keySet().stream()
                .toList();
    }

    public void removeCookie(String url, Cookie cookie) {
        get(url).remove(cookie.getName());
    }

    public void removeDomainCookies(String url) {
        getStore().remove(url);
    }

    public void removeAllCookies() {
        getStore().clear();
    }

    private Map<String, Map<String, Cookie>> getStore() {
        return threadSafeCookieStore.get();
    }

    public Cookie cookie(String url, String cookieName) {
        return get(url).values().stream()
                .filter(c -> c.getName().equals(cookieName))
                .findFirst()
                .orElseThrow();
    }

    public String cookieValue(String url, String cookieName) {
        return get(url).values().stream()
                .filter(c -> c.getName().equals(cookieName))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new CookieNotFoundException("Cookie with name = [%s] not found in domain = [%s]".formatted(cookieName, url)));
    }

    public String cookieValue(String cookieName) {
        return get().values().stream()
                .filter(c -> c.getName().equals(cookieName))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new CookieNotFoundException("Cookie with name = [%s] not found in empty domain".formatted(cookieName)));
    }

    public Token getCsrfToken() {
        return new Token(
                CSRF_COOKIE_TITLE,
                cookieValue(CSRF_COOKIE_TITLE)
        );
    }

    public Token getSessionToken() {
        return new Token(
                SESSION_ID_COOKIE_TITLE,
                cookieValue(SESSION_ID_COOKIE_TITLE)
        );
    }

}
