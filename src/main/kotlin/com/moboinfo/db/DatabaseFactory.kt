package com.pmledge.db

import com.moboinfo.models.Wallpapers
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        val database = Database.connect(
            "jdbc:mysql://127.0.0.1:3306/ktor?useSSL=false&allowPublicKeyRetrieval=true",
            driver = "com.mysql.jdbc.Driver",
            user = "root",
            password = ""
        )
//        val driverClassName = "org.h2.Driver"
//        val jdbcURL = "jdbc:h2:file:./build/ktor"
//        val database = Database.connect(jdbcURL, driverClassName)

        transaction(database) {
            SchemaUtils.create(Wallpapers)
        }


    }

    /**
     * maximumPoolSize defines the maximum size the connection pool can reach.
     */

    private fun provideDataSource(url:String,driverClass:String): HikariDataSource {
        val hikariConfig= HikariConfig().apply {
            driverClassName=driverClass
            jdbcUrl=url
            maximumPoolSize=3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        return HikariDataSource(hikariConfig)
    }
    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}