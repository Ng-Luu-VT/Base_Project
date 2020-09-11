package com.teachenglish.ielts.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import com.teachenglish.ielts.base.SharedReferenceHelper
import com.teachenglish.ielts.data.error.DateTypeDeserializer
import com.teachenglish.ielts.data.error.NullOnEmptyConverterFactory
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit

val retrofitModule = module {

    fun provideBaseURL(sharedReferenceHelper: SharedReferenceHelper): String {
        var link = "sharedReferenceHelper.getString(BASE_REMOTE_URL)"
        if (link.isEmpty()) {
            link = "base url"
        }
        return link
    }

    fun providerGson(): Gson {
//        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)

        return GsonBuilder()
            .serializeNulls()
            .setDateFormat(DateFormat.LONG)
            .registerTypeAdapter(
                Date::class.java,
                DateTypeDeserializer()
            )
            .registerTypeAdapter(
                Date::class.java,
                DateTypeDeserializer()
            )
            .create()
    }

    fun provideHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
//            .addInterceptor { chain ->
//                val newRequest = chain.request().newBuilder()
//                    .addHeader("Authorization", "5H801z7dtj1qjqnU0StuL43bCJlcl337M7pGa7fM1C4=")
//                    .build()
//                chain.proceed(newRequest)
//            }
            .addInterceptor(interceptor).build()
    }

    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(providerGson()))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    factory(named("remoteUrl")) { provideBaseURL(get()) } bind String::class

    single(named("remoteOKHttp")) { provideHttpClient() } bind OkHttpClient::class
    single(named("remoteGson")) { providerGson() } bind Gson::class
    single(named("remoteRetrofit")) { provideRetrofit(get(named("remoteOKHttp"))) } bind Retrofit::class


}

val apiModule = module {
//    single { createWebService<UserApi>(get(named("remoteRetrofit"))) }


}

inline fun <reified T> createWebService(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}

val remoteModule = listOf(retrofitModule, apiModule)