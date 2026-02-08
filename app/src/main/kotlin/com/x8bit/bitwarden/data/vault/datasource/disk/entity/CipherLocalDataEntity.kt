package com.x8bit.bitwarden.data.vault.datasource.disk.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.time.ZonedDateTime

/**
 * Entity representing local-only data for a cipher in the database.
 * This data is not synced to the server and is used for tracking
 * local usage metadata such as the last time a cipher was used.
 */
@Entity(
    tableName = "cipher_local_data",
    primaryKeys = ["cipher_id", "user_id"],
)
data class CipherLocalDataEntity(
    @ColumnInfo(name = "cipher_id")
    val cipherId: String,

    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "last_used_date")
    val lastUsedDate: ZonedDateTime,
)
