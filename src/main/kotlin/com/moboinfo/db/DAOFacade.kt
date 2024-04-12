package com.moboinfo.db

import com.moboinfo.models.Wallpaper
import com.moboinfo.models.Wallpapers

interface DAOFacade {
    suspend fun allWallpaper(): List<Wallpaper>
    suspend fun article(id: Int): Wallpaper?
    suspend fun addNewArticle(wallpaper: Wallpaper): Wallpaper?
    suspend fun editArticle(mId: Int,imgNameS:String, typeN: Int, mLikes: Int): Boolean
    suspend fun deleteArticle(id: Int): Boolean
}