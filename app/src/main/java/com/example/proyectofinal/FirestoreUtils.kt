package com.example.proyectofinal

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FirestoreUtils {
    private val db = Firebase.firestore

    fun agregarProductoAlCarritoFirestore(producto: Producto, userId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val productoMap = hashMapOf(
            "imagen" to producto.imagen,
            "nombre" to producto.nombre,
            "precio" to producto.precio,
            "descripcion" to producto.descripcion
        )

        db.collection("carritos")
            .document(userId)
            .collection("productos")
            .add(productoMap)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    fun limpiarCarritoFirestore(userId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("carritos")
            .document(userId)
            .collection("productos")
            .get()
            .addOnSuccessListener { snapshot ->
                val batch = db.batch()
                for (document in snapshot.documents) {
                    batch.delete(document.reference)
                }
                batch.commit()
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { e -> onFailure(e) }
            }
            .addOnFailureListener { e -> onFailure(e) }
    }

    fun obtenerProductosDelCarritoFirestore(userId: String, onSuccess: (List<Producto>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("carritos")
            .document(userId)
            .collection("productos")
            .get()
            .addOnSuccessListener { snapshot ->
                val productos = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Producto::class.java)?.apply { id = doc.id }
                }
                onSuccess(productos)
            }
            .addOnFailureListener { e -> onFailure(e) }
    }
}