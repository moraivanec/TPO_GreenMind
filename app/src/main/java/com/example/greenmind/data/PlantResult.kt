package com.example.greenmind.data

import com.google.gson.annotations.SerializedName

// Modelo principal de una planta usado por la app para mostrar listados
data class Plant(
    val id: Int,
    val commonName: String,
    val scientificName: String,
    val careLevel: String,
    val imageUrl: String
)

// Modelo principal usado por la app para mostrar el detalle completo de una planta
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

// Respuesta que devuelve la API cuando se consulta el listado de plantas
data class PlantListResponse(
    @SerializedName("data")
    val data: List<PlantDto> = emptyList()
)

// Modelo DTO que representa una planta tal como viene desde la API
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

// Modelo DTO que representa las imágenes recibidas desde la API
data class PlantImageDto(
    @SerializedName("thumbnail")
    val thumbnail: String? = null,

    @SerializedName("regular_url")
    val regularUrl: String? = null
)

// Modelo DTO que representa el detalle de una planta tal como viene desde la API
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


// Convierte una planta recibida desde la API al modelo usado por la app.
fun PlantDto.toPlant(): Plant {
    return Plant(
        id = id ?: 0,
        commonName = commonName ?: "Sin nombre",
        scientificName = scientificName?.joinToString(", ") ?: "Sin nombre científico",
        careLevel = careLevel?.takeIf { it.isNotBlank() } ?: "Ver detalle",
        imageUrl = defaultImage?.thumbnail ?: ""
    )
}

// Convierte el detalle recibido desde la API al modelo usado por la app
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