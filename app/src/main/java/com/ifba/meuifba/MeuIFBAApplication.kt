package com.ifba.meuifba

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MeuIFBAApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inicialização do app acontece aqui
    }
}