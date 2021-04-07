package com.example.superhero.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory




object NetworkClient {


    //ლეიზი ინიციალიზაცია - ერთხელ გამოძახების მერე იქმნება და ინახება ცვლადში
    val userService by lazy { createUserService() }

    val superheroService by lazy { createSuperheroService() }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /**createUserService მიმართავს აპს და მოაქვს აპიდან მონაცემები
    Retrofit.Builder() <- პატერნი ობიექტების ასაწყობად. დასეტილია აპის ძირითადი მისამართი
    retrofitBuilder გადაეცემა HttpClient, Interceptor დალოგავს რექვესთებს
    retrofitBuilder გადაეცემა mochiConvertor() ფუნქცია  და გვიბრუნებს UserService კლასის ინსტანს
    და შეგვიძლია გამოვიძახოთ ნეთვორქქოლი**/
    private fun createUserService(): UserService {
        val retrofitBuilder = Retrofit.Builder()
        retrofitBuilder.baseUrl("https://commschool-android-api.herokuapp.com")
        retrofitBuilder.client(
            OkHttpClient().newBuilder()
                .addInterceptor(AuthInterceptor())
                .addInterceptor(loggingInterceptor)
                .build()
        )
        retrofitBuilder.addConverterFactory(mochiConvertor())
        return retrofitBuilder.build().create(UserService::class.java)
    }


    private fun createSuperheroService(): SuperheroService {
        val retrofitBuilder = Retrofit.Builder()
        retrofitBuilder.baseUrl("https://commschool-android-api.herokuapp.com")
        retrofitBuilder.client(
            OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor).build()
        )
        retrofitBuilder.addConverterFactory(mochiConvertor())
        return retrofitBuilder.build().create(SuperheroService::class.java)
    }

    //MoshiConverterFactory კლასი რომელსაც გადაეცემა ბილდერი
    private fun mochiConvertor() =
        MoshiConverterFactory.create(
            Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
        )

}