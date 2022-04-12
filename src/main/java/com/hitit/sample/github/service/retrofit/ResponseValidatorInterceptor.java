package com.hitit.sample.github.service.retrofit;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

public class ResponseValidatorInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (!response.isSuccessful()) {
            ResponseBody body = response.body();
            String error = body == null ? "-" : body.string();
            throw new RuntimeException("API Error: " + error);
        }

        return response;
    }
}
