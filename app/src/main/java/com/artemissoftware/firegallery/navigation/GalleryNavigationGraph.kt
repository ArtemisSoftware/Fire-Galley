package com.artemissoftware.firegallery.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.artemissoftware.firegallery.navigation.models.Graph
import com.artemissoftware.firegallery.screens.picturedetail.PictureDetailScreen
import com.artemissoftware.firegallery.screens.pictures.PicturesScreen

fun NavGraphBuilder.galleryNavigationGraph(navController: NavHostController) {
    navigation(
        route = Graph.GALLERY,
        startDestination = GalleryDestinationScreen.Pictures.route
    ) {

        composable(route = GalleryDestinationScreen.Pictures.route) {
            PicturesScreen(navController)
        }

        composable(route = GalleryDestinationScreen.PictureDetail.route) {
            PictureDetailScreen()
        }
    }
}

sealed class GalleryDestinationScreen(val route: String) {
    object Pictures : GalleryDestinationScreen(route = "PICTURES")
    object PictureDetail : GalleryDestinationScreen(route = "PICTURE_DETAIL")
}