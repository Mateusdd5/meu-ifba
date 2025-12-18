package com.ifba.meuifba.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    // Repositories serão fornecidos aqui depois
    // Exemplo:
    // @Provides
    // @Singleton
    // fun provideEventoRepository(
    //     eventoDao: EventoDao,
    //     eventoApi: EventoApi
    // ): EventoRepository {
    //     return EventoRepository(eventoDao, eventoApi)
    // }
}