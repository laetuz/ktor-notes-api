package id.neotica.di

import id.neotica.data.Database
import id.neotica.data.DatabaseImpl
import id.neotica.data.repository.AuthRepositoryImpl
import id.neotica.data.repository.NotesRepositoryImpl
import id.neotica.presentation.AuthRoute
import id.neotica.presentation.NoteRoute
import id.neotica.presentation.UserRoute
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    single<Database> { DatabaseImpl() }

    //repositories
    singleOf(::NotesRepositoryImpl)
    singleOf(::AuthRepositoryImpl)

    //routes
    singleOf(::NoteRoute)
    singleOf(::AuthRoute)
    singleOf(::UserRoute)
}