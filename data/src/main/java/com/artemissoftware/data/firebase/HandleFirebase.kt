package com.artemissoftware.data.firebase

import com.artemissoftware.data.errors.FireGalleryException
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestoreException

object HandleFirebase {

    suspend inline fun <T, reified MM> safeApiCall(callFunction: suspend () -> T): T {

        return try{

            val apiResponse = callFunction.invoke()

//            with(apiResponse as List<DocumentSnapshot>) {
//
//                this.map { document ->
//                    if(document.toObject<MM>() == null) throw FireGalleryException(message = "Invalid Type")
//                }
//
//            }

            apiResponse

        }
        catch (ex: FirebaseException){

            when(ex){
                is FirebaseFirestoreException -> {

                    throw FireGalleryException(
                        code = ex.code.value(),
                        message = ex.code.name,
                        description = ex.message
                    )
                }
                else -> throw ex//emitter.onError(ErrorType.UNKNOWN)
            }
        }
    }




}