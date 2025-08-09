package id.neotica.presentation

import id.neotica.data.repository.NotesRepositoryImpl
import id.neotica.domain.Note
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class NoteRoute(
    private val noteRepo: NotesRepositoryImpl,
) {
    fun invoke(route: Route) {
        route.apply {
            getNotes()
            getNoteById()
            postNote()
            updateNote()
            deleteNote()
        }
    }

    private fun Route.getNotes() {
        get {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("id", String::class) ?: return@get call.respond(HttpStatusCode.Unauthorized)

            call.respond(noteRepo.getAllNotes(userId))
        }
    }

    private fun Route.getNoteById() {
        get("/{id}") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("id", String::class) ?: return@get call.respond(HttpStatusCode.Unauthorized)

            val noteId = call.parameters["id"]?: throw NotFoundException()
            call.respond(noteRepo.getNoteById(noteId, userId))
        }
    }

    private fun Route.postNote() {
        post {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("id", String::class) ?: return@post call.respond(HttpStatusCode.Unauthorized)

            val note = call.receive<Note>()
            call.respond(noteRepo.postNote(userId, note))
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