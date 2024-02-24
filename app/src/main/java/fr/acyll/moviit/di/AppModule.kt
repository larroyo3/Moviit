package fr.acyll.moviit.di

import fr.acyll.moviit.features.onboarding.auth.AuthViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { AuthViewModel(context = androidContext()) }
}