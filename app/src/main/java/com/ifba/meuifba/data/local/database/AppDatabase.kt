package com.ifba.meuifba.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ifba.meuifba.data.local.database.dao.*
import com.ifba.meuifba.data.local.database.entities.*

@Database(
    entities = [
        Usuario::class,
        Evento::class,
        CategoriaEvento::class,
        Curso::class,
        AreaConhecimento::class,
        PreferenciaUsuario::class,
        MarcacaoParticipacao::class,
        MidiaEvento::class,
        Notificacao::class,
        EstatisticaEvento::class,
        LogAtividade::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun eventoDao(): EventoDao
    abstract fun categoriaEventoDao(): CategoriaEventoDao
    abstract fun cursoDao(): CursoDao
    abstract fun areaConhecimentoDao(): AreaConhecimentoDao
    abstract fun preferenciaUsuarioDao(): PreferenciaUsuarioDao
    abstract fun marcacaoParticipacaoDao(): MarcacaoParticipacaoDao
    abstract fun midiaEventoDao(): MidiaEventoDao
    abstract fun notificacaoDao(): NotificacaoDao
    abstract fun estatisticaEventoDao(): EstatisticaEventoDao
    abstract fun logAtividadeDao(): LogAtividadeDao
}