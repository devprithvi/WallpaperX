package com.moboinfo.db


import com.moboinfo.models.Wallpaper
import com.moboinfo.models.Wallpapers
import com.moboinfo.models.Wallpapers.id
import com.moboinfo.models.Wallpapers.imgName
import com.moboinfo.models.Wallpapers.likes
import com.moboinfo.models.Wallpapers.type
import com.pmledge.db.DatabaseFactory.dbQuery
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOFacadeImpl : DAOFacade {

    private fun resultRowToArticle(row: ResultRow) = Wallpaper(
        id = row[id],
        imgName = row[imgName],
        type = row[type],
        likes = row[likes],
    )

    /**
     * Table.selectAll returns an instance of Query, so to get the list of Article
     */
    override suspend fun allWallpaper(): List<Wallpaper> = dbQuery {
        Wallpapers.selectAll().map(::resultRowToArticle)
    }

    override suspend fun article(id: Int): Wallpaper? = dbQuery {
        Wallpapers
            .select { Wallpapers.id eq id } //comparisons (eq, less, greater),plus, times,...inList, notInList
            .map(::resultRowToArticle)
            .singleOrNull()
    }

    override suspend fun addNewArticle(wallpaper: Wallpaper): Wallpaper? = dbQuery {
        val insertStatement = Wallpapers.insert {
            it[id] = wallpaper.id
            it[imgName] = wallpaper.imgName
            it[type] = wallpaper.type
            it[likes] = wallpaper.likes
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToArticle)
    }

    override suspend fun editArticle(mId: Int, imgNameS: String, typeN: Int, mLikes: Int): Boolean = dbQuery {
        Wallpapers.update({ id eq mId }) {
            it[id] = mId
            it[imgName] = imgNameS
            it[type] = typeN
            it[likes] = mLikes
        } > 0
    }

    override suspend fun deleteArticle(id: Int): Boolean = dbQuery {
        Wallpapers.deleteWhere { Wallpapers.id eq id } > 0
    }

    /**
     * et's create an instance of DAOFacade and add a sample article into be
     * inserted to the database before the application is started
     */
    companion object {
        val dao: DAOFacade = DAOFacadeImpl().apply {
            runBlocking {
                if (allWallpaper().isEmpty()) {
                    print("Empty DB_WALL")
//                    addNewArticle(1, "The drive to develop!", 0, 12)
                }
            }
        }
    }
}

