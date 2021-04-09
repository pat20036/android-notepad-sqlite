package com.pat.notepad

import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single <DatabaseInterface>{ DatabaseInterfaceImpl(androidApplication())}
    viewModel { MainViewModel(get()) }
}