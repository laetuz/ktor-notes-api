package id.neotica.extension

import java.util.UUID

fun String.toUUID(): UUID = UUID.fromString(this)