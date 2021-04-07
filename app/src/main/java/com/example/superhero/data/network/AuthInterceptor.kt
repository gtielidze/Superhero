package com.example.superhero.data.network

import com.example.superhero.data.storage.DataStore
import okhttp3.Interceptor
import okhttp3.Response
/** რექვეთებისთებისთვის ტოკენების მიწოდებას უზრუნველყოფს ინტერსეპტორი*/
class AuthInterceptor : Interceptor {

    /**chain <--ინტერსეპტორების, რესპონსის მიმდევრობა იუზერიდან სერვერამდე და უკან
    chain.request <-- აქედან ვიღებთ ჩვენ რექვესთს, რომელსაც newBuilder() საშუალებით გადავაკეთებთ,
    ვამატებთ UserService-დან "Authorization" და ჰედერში გავატანთ სტრინგს "Bearer ${DataStore.authToken}"
    და ამ ყველაფერს უკან ვდებთ რექვესთ chain.proceed(builder.build()) */
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        DataStore.authToken?.let {
            builder.addHeader("Authorization", "Bearer ${DataStore.authToken}")
        }
        return chain.proceed(builder.build())
    }

}