package it.vashykator.scraper

import java.nio.file.Files
import java.nio.file.Path

private const val usersPath = "src/main/resources/USERS"

@JvmInline
value class User(val id: Long)

fun getUsersFromFileSystem(): List<User> = Files
    .readAllLines(Path.of(usersPath))
    .map { User(it.toLong()) }
