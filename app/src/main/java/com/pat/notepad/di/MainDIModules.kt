package com.pat.notepad

import com.pat.notepad.view.navController
import com.pat.notepad.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.math.sin

val mainModule = module {
    single <DatabaseInterface>{ DatabaseInterfaceImpl(androidApplication())}
    single <NavigationCommander>(Definitions.navigationCommander){NavigationCommanderImpl(androidApplication(), ::navController)}
    viewModel { MainViewModel(get(), get(Definitions.navigationCommander)) }
}

internal object Definitions
{
    val navigationCommander = named("navigationCommander")
}