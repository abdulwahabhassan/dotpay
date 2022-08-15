package com.devhassan.dotpay.di

import com.devhassan.dotpay.api.Api
import com.devhassan.dotpay.api.MakeUpApiService
import com.devhassan.dotpay.network.NetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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