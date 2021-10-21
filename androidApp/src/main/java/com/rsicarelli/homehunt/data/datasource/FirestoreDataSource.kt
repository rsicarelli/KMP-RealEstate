package com.rsicarelli.homehunt.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.rsicarelli.homehunt.data.datasource.FirestoreDataSourceImpl.FirestoreMap.PROPERTY_COLLECTION
import com.rsicarelli.homehunt_kmm.domain.model.Mapper
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt_kmm.domain.model.toProperty
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import timber.log.Timber

interface FirestoreDataSource {
    fun toggleFavourite(referenceId: String, isFavourited: Boolean)
    fun markAsViewed(referenceId: String, userId: String)
    val activeProperties: StateFlow<List<Property>?>
}

@OptIn(ExperimentalCoroutinesApi::class)
class FirestoreDataSourceImpl(
    private val db: FirebaseFirestore,
    coroutinesScope: CoroutineScope
) : FirestoreDataSource {

    private val _activeProperties = MutableStateFlow<List<Property>?>(null)
    override val activeProperties = _activeProperties

    init {
        coroutinesScope.launch {
            observeActiveProperties().collect {
                _activeProperties.emit(it)
            }
        }
    }

    private fun observeActiveProperties(): Flow<List<Property>> {
        return callbackFlow {
            val propertiesDocument = db.collection(PROPERTY_COLLECTION)
                .whereEqualTo(Mapper.IS_ACTIVE, true)

            val subscription = propertiesDocument.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    cancel(
                        message = "error fetching collection data at path",
                        cause = error
                    )
                    return@addSnapshotListener
                }

                snapshot?.documents?.let { documentSnapshot ->
                    documentSnapshot
                        .mapNotNull { it.data?.toProperty() }
                        .run { trySend(this) }
                } ?: cancel("Document is null", RuntimeException())
            }

            awaitClose { subscription.remove() }
        }.flowOn(Dispatchers.IO)
    }

    override fun toggleFavourite(
        referenceId: String,
        isFavourited: Boolean
    ) {
        val document = db.collection(PROPERTY_COLLECTION).document(referenceId)
        document.update(Mapper.IS_FAVOURITED, isFavourited).addOnSuccessListener {
            Timber.d("Toggled favourite: $referenceId, $isFavourited")
        }
    }

    override fun markAsViewed(referenceId: String, userId: String) {
        val document = db.collection(PROPERTY_COLLECTION).document(referenceId)
        document.get().addOnSuccessListener {
            it.data?.toProperty()?.let { property ->
                val viewedBy = property.viewedBy.toMutableList().apply {
                    add(userId)
                }
                document.update(Mapper.VIEWED_BY, viewedBy).addOnSuccessListener {
                    Timber.d("Marked as viewed")
                }
            }
        }
    }

    private object FirestoreMap {
        const val PROPERTY_COLLECTION = "properties"
    }
}