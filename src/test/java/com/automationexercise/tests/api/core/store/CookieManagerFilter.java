package com.automationexercise.tests.api.core.store;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class CookieManagerFilter implements Filter {

    private final ThreadSafeCookieStore cookieStore = ThreadSafeCookieStore.INSTANCE;

    @Nonnull
    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx
    ) {
        requestSpec.cookies(cookieStore.getCookies());
        var response = ctx.next(requestSpec, responseSpec);
        cookieStore.add(response.getDetailedCookies());
        return response;
    }

}
