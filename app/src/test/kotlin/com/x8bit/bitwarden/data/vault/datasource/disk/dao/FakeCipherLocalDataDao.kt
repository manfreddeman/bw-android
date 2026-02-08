package com.x8bit.bitwarden.data.vault.datasource.disk.dao

import com.bitwarden.core.data.repository.util.bufferedMutableSharedFlow
import com.x8bit.bitwarden.data.vault.datasource.disk.entity.CipherLocalDataEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FakeCipherLocalDataDao : CipherLocalDataDao {

    val storedCipherLocalData = mutableListOf<CipherLocalDataEntity>()

    var insertCipherLocalDataCalled: Boolean = false
    var deleteAllCipherLocalDataCalled: Boolean = false

    private val cipherLocalDataFlow =
        bufferedMutableSharedFlow<List<CipherLocalDataEntity>>(replay = 1)

    init {
        cipherLocalDataFlow.tryEmit(emptyList())
    }

    override suspend fun insertCipherLocalData(cipherLocalData: CipherLocalDataEntity) {
        storedCipherLocalData.removeAll {
            it.cipherId == cipherLocalData.cipherId && it.userId == cipherLocalData.userId
        }
        storedCipherLocalData.add(cipherLocalData)
        cipherLocalDataFlow.tryEmit(storedCipherLocalData.toList())
        insertCipherLocalDataCalled = true
    }

    override fun getAllCipherLocalDataFlow(
        userId: String,
    ): Flow<List<CipherLocalDataEntity>> =
        cipherLocalDataFlow.map { data -> data.filter { it.userId == userId } }

    override suspend fun getAllCipherLocalData(
        userId: String,
    ): List<CipherLocalDataEntity> =
        storedCipherLocalData.filter { it.userId == userId }

    override suspend fun deleteAllCipherLocalData(userId: String) {
        storedCipherLocalData.removeAll { it.userId == userId }
        cipherLocalDataFlow.tryEmit(storedCipherLocalData.toList())
        deleteAllCipherLocalDataCalled = true
    }
}
