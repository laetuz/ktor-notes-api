package id.neotica.di

import id.neotica.data.Database
import id.neotica.data.DatabaseImpl
import id.neotica.data.repository.AuthRepositoryImpl
import id.neotica.data.repository.NotesRepositoryImpl
import id.neotica.data.repository.UserRepositoryImpl
import id.neotica.domain.repository.NotesRepository
import id.neotica.presentation.AuthRoute
import id.neotica.presentation.NoteRoute
import id.neotica.presentation.PublicNoteRoute
import id.neotica.presentation.UserRoute
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single<Database> { DatabaseImpl() }

    //repositories
    singleOf(::NotesRepositoryImpl).bind(NotesRepository::class)
    singleOf(::AuthRepositoryImpl)
    singleOf(::UserRepositoryImpl)

    //routes
    singleOf(::NoteRoute)
    singleOf(::PublicNoteRoute)
    singleOf(::AuthRoute)
    singleOf(::UserRoute)
}