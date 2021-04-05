package com.pat.notepad

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    //single { DatabaseHelper(get())}
    viewModel { MainViewModel() }
}