package com.x8bit.bitwarden.data.vault.datasource.disk.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.x8bit.bitwarden.data.vault.datasource.disk.entity.CipherLocalDataEntity
import kotlinx.coroutines.flow.Flow

/**
 * Provides methods for inserting, retrieving, and deleting cipher local data from the database
 * using the [CipherLocalDataEntity].
 */
@Dao
interface CipherLocalDataDao {

    /**
     * Inserts or updates cipher local data.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCipherLocalData(cipherLocalData: CipherLocalDataEntity)

    /**
     * Retrieves all cipher local data from the database for a given [userId] as a [Flow].
     */
    @Query("SELECT * FROM cipher_local_data WHERE user_id = :userId")
    fun getAllCipherLocalDataFlow(userId: String): Flow<List<CipherLocalDataEntity>>

    /**
     * Retrieves all cipher local data from the database for a given [userId].
     */
    @Query("SELECT * FROM cipher_local_data WHERE user_id = :userId")
    suspend fun getAllCipherLocalData(userId: String): List<CipherLocalDataEntity>

    /**
     * Deletes all the stored cipher local data associated with the given [userId].
     */
    @Query("DELETE FROM cipher_local_data WHERE user_id = :userId")
    suspend fun deleteAllCipherLocalData(userId: String)
}
