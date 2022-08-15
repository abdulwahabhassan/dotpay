package com.devhassan.dotpay.di

import com.devhassan.dotpay.Api
import com.devhassan.dotpay.MakeUpApiService
import com.devhassan.dotpay.NetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun providesApiService(
        networkClient: NetworkClient
    ): MakeUpApiService {
        return networkClient.getApiService(Api.MAKEUP)
    }

}