package id.neotica.presentation

import id.neotica.data.repository.NotesRepositoryImpl
import id.neotica.domain.Note
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import org.jetbrains.exposed.sql.not
import java.util.UUID

class NoteRoute(
    private val noteRepo: NotesRepositoryImpl
) {
    fun register(route: Route) {
        route.apply {
            getNotes()
            postNote()
            updateNote()
        }
    }

    private fun Route.getNotes() {
        get {
            call.respond(noteRepo.getAllNotes())
        }
    }

    private fun Route.postNote() {
        val noteDummy = Note(
            title = "Legend",
            content = "hahahahah",
        )
        post { call.respond(noteRepo.postNote(noteDummy)) }
    }

    private fun Route.updateNote() {
        put {  }
    }
}