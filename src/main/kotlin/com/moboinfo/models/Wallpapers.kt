package com.moboinfo.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Wallpaper(val id: Int=0, val imgName: String, val type: Int, val likes: Int)

object Wallpapers : Table() {
    val id = integer("id")
    val imgName = varchar("imgName", 512)
    val type = integer("type")
    val likes = integer("likes")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}