package com.artemissoftware.firegallery.navigation.routes.destinations

import com.artemissoftware.common.composables.navigation.models.BaseDestinations
import com.artemissoftware.common.composables.navigation.models.CustomArguments
import com.artemissoftware.firegallery.navigation.NavigationArguments
import com.artemissoftware.firegallery.navigation.graphs.GalleryDestinations
import com.artemissoftware.firegallery.navigation.navtypes.GalleryUINavType

sealed class Destination(
    route: String,
    customArguments: List<CustomArguments> = emptyList(),
    baseDeepLink: String = NavigationArguments.ARTEMIS_SOFTWARE_URI
) : BaseDestinations(route = route, customArguments = customArguments, baseDeepLink = baseDeepLink){

    object Profile : Destination(route = "PROFILE")
    object RegisterUser : Destination(route = "REGISTER_USER")
    object LogInUser : Destination(route = "LOG_IN_USER")
    object Pictures : Destination(route = "PICTURES", listOf(CustomArguments(key = NavigationArguments.GALLERY_ID, type = GalleryUINavType())))

}