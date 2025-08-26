package com.mokaneko.recycle2.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mokaneko.recycle2.data.model.SampahItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class TrashRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {
    fun addTrashItem(item: SampahItem, isActive: Boolean): Task<Void> {
        val collection = if (isActive) "sampah_aktif" else "sampah_terbuang"
        val docRef = firestore.collection(collection).document(item.id)
        return docRef.set(item)
    }

    fun getTrashItemsByUser(userId: String, isActive: Boolean): Flow<List<SampahItem>> = callbackFlow {
        val collection = if (isActive) "sampah_aktif" else "sampah_terbuang"
        val listener = firestore.collection(collection)
            .whereEqualTo("userId", userId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, _ ->
                val items = snapshot?.documents?.mapNotNull {
                    it.toObject(SampahItem::class.java)
                } ?: emptyList()
                trySend(items).isSuccess
            }
        awaitClose { listener.remove() }
    }

    fun deleteItem(itemId: String, isActive: Boolean): Task<Void> {
        val collection = if (isActive) "sampah_aktif" else "sampah_terbuang"
        return firestore.collection(collection).document(itemId).delete()
    }

    fun editItem(item: SampahItem, isActive: Boolean): Task<Void> {
        val collection = if (isActive) "sampah_aktif" else "sampah_terbuang"
        return firestore.collection(collection).document(item.id).set(item)
    }

    fun moveItem(item: SampahItem, toActive: Boolean): Task<Void> {
        val fromCollection = if (toActive) "sampah_terbuang" else "sampah_aktif"
        val toCollection = if (toActive) "sampah_aktif" else "sampah_terbuang"

        return firestore.runBatch { batch ->
            batch.delete(firestore.collection(fromCollection).document(item.id))
            batch.set(firestore.collection(toCollection).document(item.id), item)
        }
    }
}