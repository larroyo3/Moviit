package fr.acyll.moviit.di

import fr.acyll.moviit.features.main.account.AccountViewModel
import fr.acyll.moviit.features.main.settings.SettingsViewModel
import fr.acyll.moviit.features.onboarding.auth.AuthViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { AuthViewModel(context = androidContext()) }
    viewModel { AccountViewModel(context = androidContext()) }
    viewModel { SettingsViewModel(context = androidContext())}
}