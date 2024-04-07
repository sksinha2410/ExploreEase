package com.sksinha2410.exploreease.DataClass

data class MapPlace(
    var placeId: String="",
    var placeName: String="",
    var placeAddress: String="",
    var placeLatitude: Double=0.0,
    var placeLongitude: Double=0.0,
    var placeRating: Double=0.0,
    var placeType: String="",
    var placePhoto: String="",
    var placePhone: String=""

)
