package id.neotica.di

import id.neotica.data.Database
import id.neotica.data.DatabaseImpl
import id.neotica.data.repository.NotesRepositoryImpl
import id.neotica.presentation.NoteRoute
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    single<Database> { DatabaseImpl() }
    singleOf(::NotesRepositoryImpl)
    singleOf(::NoteRoute)
}