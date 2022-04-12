package com.hitit.sample.github.service.retrofit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class HeaderRequestInterceptor implements Interceptor {

    private final String headerName;

    private final String headerValue;

    public HeaderRequestInterceptor(String headerName, String headerValue) {
        this.headerName = headerName;
        this.headerValue = headerValue;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader(headerName, headerValue)
                .build();
        return chain.proceed(request);
    }
}
