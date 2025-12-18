package com.ifba.meuifba.di

import android.content.Context
import androidx.room.Room
import com.ifba.meuifba.data.local.database.AppDatabase
import com.ifba.meuifba.data.local.database.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "meu_ifba_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUsuarioDao(database: AppDatabase): UsuarioDao {
        return database.usuarioDao()
    }

    @Provides
    fun provideEventoDao(database: AppDatabase): EventoDao {
        return database.eventoDao()
    }

    @Provides
    fun provideCategoriaEventoDao(database: AppDatabase): CategoriaEventoDao {
        return database.categoriaEventoDao()
    }

    @Provides
    fun provideCursoDao(database: AppDatabase): CursoDao {
        return database.cursoDao()
    }

    @Provides
    fun provideAreaConhecimentoDao(database: AppDatabase): AreaConhecimentoDao {
        return database.areaConhecimentoDao()
    }

    @Provides
    fun providePreferenciaUsuarioDao(database: AppDatabase): PreferenciaUsuarioDao {
        return database.preferenciaUsuarioDao()
    }

    @Provides
    fun provideMarcacaoParticipacaoDao(database: AppDatabase): MarcacaoParticipacaoDao {
        return database.marcacaoParticipacaoDao()
    }

    @Provides
    fun provideMidiaEventoDao(database: AppDatabase): MidiaEventoDao {
        return database.midiaEventoDao()
    }

    @Provides
    fun provideNotificacaoDao(database: AppDatabase): NotificacaoDao {
        return database.notificacaoDao()
    }

    @Provides
    fun provideEstatisticaEventoDao(database: AppDatabase): EstatisticaEventoDao {
        return database.estatisticaEventoDao()
    }

    @Provides
    fun provideLogAtividadeDao(database: AppDatabase): LogAtividadeDao {
        return database.logAtividadeDao()
    }
}