package com.teachenglish.ielts.di.module

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import com.teachenglish.ielts.base.SharedReferenceHelper
import com.teachenglish.ielts.viewmodel.CategoryViewModel
import com.teachenglish.ielts.viewmodel.DocumentViewModel
import com.teachenglish.ielts.viewmodel.FirstViewModel
import org.koin.androidx.viewmodel.dsl.viewModel


val appData = module {
    single { SharedReferenceHelper(androidContext()) }
}

val viewModelModule = module {
  viewModel { FirstViewModel() }
  viewModel { CategoryViewModel() }
  viewModel { DocumentViewModel() }

}

val repositoryModule = module {
//    factory {
//        UserRepository(get())
//    }

}


val appModule = remoteModule + listOf(
    appData,
    viewModelModule,
    repositoryModule
)
