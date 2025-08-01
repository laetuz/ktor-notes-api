package id.neotica.presentation

import id.neotica.data.repository.NotesRepositoryImpl
import id.neotica.domain.Note
import io.ktor.server.auth.authenticate
import io.ktor.server.plugins.NotFoundException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put

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

    private fun Route.getNotes() {
        authenticate("auth-jwt") {
            get {
                call.respond(noteRepo.getAllNotes())
            }
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