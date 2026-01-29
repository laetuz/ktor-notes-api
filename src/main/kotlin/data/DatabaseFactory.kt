package id.neotica.data

const val USE_ROOM = true // << 在這裡切換 true/false

object DatabaseFactory {
    fun create(): Database {
        return if (USE_ROOM) {
            DatabaseRoomImpl()
        } else {
            DatabaseExposedImpl()
        }
    }
}