package com.ifba.meuifba.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    // Se precisar fornecer dependências específicas para ViewModels,
    // adicionar aqui

    // Na maioria dos casos, @HiltViewModel + @Inject no construtor
    // é suficiente e não precisa declarar nada aqui
}