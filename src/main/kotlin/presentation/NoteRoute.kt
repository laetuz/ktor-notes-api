package id.neotica.presentation

import id.neotica.data.repository.NotesRepositoryImpl
import id.neotica.domain.Note
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class NoteRoute(
    private val noteRepo: NotesRepositoryImpl
) {
    fun register(route: Route) {
        route.apply {
            getNotes()
            postNote()
            updateNote()
            deleteNote()
        }
    }

    fun public(route: Route) {
        route.apply {  }
    }

    private fun Route.getNotes() {
        get {
            call.respond(noteRepo.getAllNotes())
        }
    }

    private fun Route.postNote() {
        post {
            val note = call.receive<Note>()
            call.respond(noteRepo.postNote(note))
        }
    }

    private fun Route.updateNote() {
        put("/{id}") {
            val id = call.parameters["id"]?: throw NotFoundException()
            val note = call.receive<Note>()

            call.respond(noteRepo.updateNote(id, note))
        }
    }

    private fun Route.deleteNote() {
        delete("/{id}") {
            val id = call.parameters["id"]?: throw NotFoundException()

            noteRepo.deleteNote(id)
            call.respond("Note deleted.")
        }
    }
}