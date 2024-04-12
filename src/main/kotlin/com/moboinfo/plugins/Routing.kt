package com.moboinfo.plugins

import com.moboinfo.db.DAOFacadeImpl.Companion.dao
import com.moboinfo.models.Wallpaper
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.jetbrains.exposed.exceptions.ExposedSQLException

fun Application.configureRouting() {
    routing {
        get("/name") {
            call.respondText("Wallpapers")
        }
        get("getAllWallpapers") {
            call.respond(FreeMarkerContent("index.ftl", mapOf("wallpapers" to dao.allWallpaper())))
        }


//        post("/addWallV1") {
//            val wallpaper = call.receive<Wallpaper>()
//            try {
//                val result = dao.addNewArticle(
//                    Wallpaper(
//                        wallpaper.id,
//                        wallpaper.imgName,
//                        wallpaper.type,
//                        wallpaper.likes,
//                    )
//                )
//                result?.let {
//                    call.respond(HttpStatusCode.Created, it)
//                } ?: call.respond(HttpStatusCode.NotImplemented, "Error adding user")
//            } catch (e: ExposedSQLException) {
//                call.respond(HttpStatusCode.BadRequest, e.message ?: "SQL Exception!!")
//            }
//        }

        post("/addWall") {
            val formParameters = call.receiveParameters()
            val id = formParameters.getOrFail("id")
            val imageName = formParameters.getOrFail("imageName")
            val likes = formParameters.getOrFail("likes")
            val imageType = formParameters.getOrFail("imageType")
            //mId: Int, imgNameS: String, typeN: Int, mLikes: Int
            val article = dao.addNewArticle(
                Wallpaper(
                    id.toInt(),
                    imageName,
                    imageType.toInt(),
                    likes.toInt()
                )
            )
            println("$article")
            call.respondRedirect("/articles/${article?.id}")
//            call.respond(HttpStatusCode.Created,"Data inserted")
        }
        get("/getWallById/{id}") {
            val id = call.parameters.getOrFail<Int>("id")
            call.respond("${dao.article(id)}")
//            call.respond(FreeMarkerContent("show.ftl", mapOf("article" to dao.article(id))))
        }
        get("{id}/edit") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(FreeMarkerContent("edit.ftl", mapOf("article" to dao.article(id))))
        }
    }
}
