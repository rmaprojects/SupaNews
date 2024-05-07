package com.rmaprojects.newssupabaseapp.di

import com.rmaprojects.newssupabaseapp.data.repository.SupaNewsRepositoryImpl
import com.rmaprojects.newssupabaseapp.data.source.remote.RemoteDataSource
import com.rmaprojects.newssupabaseapp.domain.repository.SupaNewsRepository
import com.rmaprojects.newssupabaseapp.domain.usecases.SupaNewsUseCases
import com.rmaprojects.newssupabaseapp.domain.usecases.SupaNewsUseCasesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.realtime.RealtimeChannel
import io.github.jan.supabase.realtime.channel
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = "https://vvfjqqtinknnoyvbdisg.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZ2ZmpxcXRpbmtubm95dmJkaXNnIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTcxNDkxMDMzNSwiZXhwIjoyMDMwNDg2MzM1fQ.TGnSY6vsxgzaGgaQdGNnOdRppTA4WxgSQMyv3gf30RQ"
        ) {
            install(Auth)
            install(Postgrest)
            install(Realtime)
        }
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        client: SupabaseClient
    ): RemoteDataSource {
        return RemoteDataSource(client)
    }

    @Provides
    @Singleton
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
        client: SupabaseClient
    ): SupaNewsRepository {
        return SupaNewsRepositoryImpl(remoteDataSource, client)
    }

    @Provides
    @Singleton
    fun provideUseCases(
        repository: SupaNewsRepository
    ): SupaNewsUseCases {
        return SupaNewsUseCasesImpl(repository)
    }
}