package com.artemissoftware.data.repositories

import com.artemissoftware.data.errors.FireGalleryException
import com.artemissoftware.data.firebase.FireStoreCollection
import com.artemissoftware.data.firebase.FireStoreDocumentField
import com.artemissoftware.data.firebase.HandleFirebase
import com.artemissoftware.data.firebase.cloudstore.source.CloudStoreSource
import com.artemissoftware.data.firebase.entities.GalleryFso
import com.artemissoftware.data.firebase.entities.PictureFso
import com.artemissoftware.data.mappers.toFirebaseError
import com.artemissoftware.data.mappers.toGallery
import com.artemissoftware.data.mappers.toPicture
import com.artemissoftware.domain.FirebaseResponse
import com.artemissoftware.domain.models.Gallery
import com.artemissoftware.domain.models.Picture
import com.artemissoftware.domain.repositories.GalleryRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.toObject
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val cloudStoreSource: CloudStoreSource
) : GalleryRepository {

    override suspend fun getGalleries(): FirebaseResponse<List<Gallery>> {

        return try {

            val response = HandleFirebase.safeApiCall<List<DocumentSnapshot>, PictureFso>{ cloudStoreSource.getDocuments(collectionName = FireStoreCollection.GALLERY) }

            return FirebaseResponse(data = response.map { document ->
                document.toObject<GalleryFso>()!!.toGallery()
            })

        } catch (ex: FireGalleryException) {
            FirebaseResponse(error = ex.toFirebaseError())
        }

    }

    override suspend fun getPictures(galleryId: Int): List<Picture> {

        return cloudStoreSource.getPictures(galleryId).map { document ->
            document.toObject<PictureFso>()!!.toPicture()
        }
    }

    override suspend fun getFavoritePictures(pictureIds: List<String>): FirebaseResponse<List<Picture>> {

        return try {

            val response = HandleFirebase.safeApiCall<List<DocumentSnapshot>, PictureFso>{
                cloudStoreSource.getDocumentItems(
                    collectionName = FireStoreCollection.PICTURES,
                    documentField = FireStoreDocumentField.ID,
                    id = pictureIds
                )
            }

            return FirebaseResponse(data = response.map { document ->
                document.toObject<PictureFso>()!!.toPicture()
            })

        }
        catch (ee: IllegalArgumentException){
            FirebaseResponse(error = ee.toFirebaseError())
        }
        catch (ex: FireGalleryException) {
            FirebaseResponse(error = ex.toFirebaseError())
        }

    }

    override suspend fun getPictureDetail(pictureId: String): Picture? {




            return cloudStoreSource.getDocumentItems(
            collectionName = FireStoreCollection.PICTURES,
            documentField = FireStoreDocumentField.ID,
            id = pictureId as Object
        ).map { document ->
            document.toObject<PictureFso>()!!.toPicture()
        }.firstOrNull()
    }


}