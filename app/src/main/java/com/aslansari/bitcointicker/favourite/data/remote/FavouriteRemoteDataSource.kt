package com.aslansari.bitcointicker.favourite.data.remote

import com.aslansari.bitcointicker.favourite.data.FavouriteCoin
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FavouriteRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun getFavourites(userId: String): List<FavouriteCoin> = suspendCoroutine { cont ->
        val favCollection = firestore.collection("/users/${userId}/favourites")
        favCollection.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val snapshot = task.result
                val coinList = snapshot.documents.map { doc ->
                    val id: String = doc.get("id", String::class.java) ?: ""
                    val name: String = doc.get("name", String::class.java) ?: ""
                    val symbol: String = doc.get("symbol", String::class.java) ?: ""
                    FavouriteCoin(id, name, symbol)
                }
                cont.resume(coinList)
            } else {
                cont.resume(emptyList())
            }
        }
    }

    suspend fun addToFavourites(userId: String, favouriteCoin: FavouriteCoin): Boolean = suspendCoroutine { cont ->
        val favCollection = firestore.collection("/users/${userId}/favourites")
        favCollection.document().set(favouriteCoin).addOnCompleteListener {
            cont.resume(it.isSuccessful)
        }
    }

    suspend fun removeFromFavourites(userId: String, favouriteCoin: FavouriteCoin): Boolean = suspendCoroutine { cont ->
        val favCollection = firestore.collection("/users/${userId}/favourites")
        val batch = firestore.batch()
        favCollection.whereEqualTo("id", favouriteCoin.id).get().continueWith {
            it.result.forEach { doc ->
                batch.delete(doc.reference)
            }
            batch.commit()
        }.addOnCompleteListener {
            cont.resume(it.isSuccessful)
        }
    }
}