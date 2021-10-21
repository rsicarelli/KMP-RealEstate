package com.rsicarelli.homehunt.data.repository

import com.rsicarelli.homehunt.data.datasource.FirestoreDataSource
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt.domain.repository.PropertyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PropertyRepositoryImpl @Inject constructor(
    private val firestoreDataSource: FirestoreDataSource
) : PropertyRepository {
    override fun getActiveProperties(): Flow<List<Property>?> =
        firestoreDataSource.activeProperties

    override fun toggleFavourite(referenceId: String, isFavourited: Boolean) =
        firestoreDataSource.toggleFavourite(referenceId, isFavourited)

    override fun markAsViewed(referenceId: String, userId: String) =
        firestoreDataSource.markAsViewed(referenceId, userId)

}