package com.example.greenmind.data

import com.google.gson.annotations.SerializedName

data class Plant(
    val id: Int,
    val commonName: String,
    val scientificName: String,
    val careLevel: String,
    val imageUrl: String
)

data class PlantDetail(
    val id: Int,
    val commonName: String,
    val scientificName: String,
    val description: String,
    val watering: String,
    val sunlight: String,
    val careLevel: String,
    val pruningMonth: String,
    val propagation: String,
    val cycle: String,
    val imageUrl: String
)

data class PlantListResponse(
    @SerializedName("data")
    val data: List<PlantDto> = emptyList()
)

data class PlantDto(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("common_name")
    val commonName: String? = null,

    @SerializedName("scientific_name")
    val scientificName: List<String>? = null,

    @SerializedName("care_level")
    val careLevel: String? = null,

    @SerializedName("default_image")
    val defaultImage: PlantImageDto? = null
)

data class PlantImageDto(
    @SerializedName("thumbnail")
    val thumbnail: String? = null,

    @SerializedName("regular_url")
    val regularUrl: String? = null
)

data class PlantDetailDto(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("common_name")
    val commonName: String? = null,

    @SerializedName("scientific_name")
    val scientificName: List<String>? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("watering")
    val watering: String? = null,

    @SerializedName("sunlight")
    val sunlight: List<String>? = null,

    @SerializedName("care_level")
    val careLevel: String? = null,

    @SerializedName("pruning_month")
    val pruningMonth: List<String>? = null,

    @SerializedName("propagation")
    val propagation: List<String>? = null,

    @SerializedName("cycle")
    val cycle: String? = null,

    @SerializedName("default_image")
    val defaultImage: PlantImageDto? = null
)

fun PlantDto.toPlant(): Plant {
    return Plant(
        id = id ?: 0,
        commonName = commonName ?: "Sin nombre",
        scientificName = scientificName?.joinToString(", ") ?: "Sin nombre científico",
        careLevel = careLevel ?: "No informado",
        imageUrl = defaultImage?.thumbnail ?: ""
    )
}

fun PlantDetailDto.toPlantDetail(): PlantDetail {
    return PlantDetail(
        id = id ?: 0,
        commonName = commonName ?: "Sin nombre",
        scientificName = scientificName?.joinToString(", ") ?: "Sin nombre científico",
        description = description ?: "No hay descripción disponible.",
        watering = watering ?: "No informado",
        sunlight = sunlight?.joinToString(", ") ?: "No informado",
        careLevel = careLevel ?: "No informado",
        pruningMonth = pruningMonth?.joinToString(", ") ?: "No informado",
        propagation = propagation?.joinToString(", ") ?: "No informado",
        cycle = cycle ?: "No informado",
        imageUrl = defaultImage?.regularUrl ?: defaultImage?.thumbnail ?: ""
    )
}