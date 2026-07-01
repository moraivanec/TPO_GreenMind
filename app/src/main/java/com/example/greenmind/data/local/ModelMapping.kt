package com.example.greenmind.data.local

import com.example.greenmind.data.Plant
import com.example.greenmind.data.PlantDetail

// Convierte una planta del modelo externo al modelo local de Room
fun Plant.toLocal() = PlantLocal(
    id = id,
    commonName = commonName,
    scientificName = scientificName,
    careLevel = careLevel,
    imageUrl = imageUrl
)

// Convierte una lista de plantas externas a una lista de plantas locales
fun List<Plant>.toLocal() = map(Plant::toLocal)

// Convierte una planta local de Room al modelo externo usado por la app
fun PlantLocal.toExternal() = Plant(
    id = id,
    commonName = commonName,
    scientificName = scientificName,
    careLevel = careLevel,
    imageUrl = imageUrl
)

// Convierte una lista de plantas locales a una lista de plantas externas
fun List<PlantLocal>.toExternal() = map(PlantLocal::toExternal)

// Convierte el detalle de una planta al modelo local para guardarlo en Room
fun PlantDetail.toLocal() = PlantDetailLocal(
    id = id,
    commonName = commonName,
    scientificName = scientificName,
    description = description,
    watering = watering,
    sunlight = sunlight,
    careLevel = careLevel,
    pruningMonth = pruningMonth,
    propagation = propagation,
    cycle = cycle,
    imageUrl = imageUrl,
    dateAdded = System.currentTimeMillis()
)

// Convierte un detalle local de Room al modelo externo usado por la app
fun PlantDetailLocal.toExternal() = PlantDetail(
    id = id,
    commonName = commonName,
    scientificName = scientificName,
    description = description,
    watering = watering,
    sunlight = sunlight,
    careLevel = careLevel,
    pruningMonth = pruningMonth,
    propagation = propagation,
    cycle = cycle,
    imageUrl = imageUrl
)

// Convierte una lista de detalles locales a una lista de detalles externos
fun List<PlantDetailLocal>.toPlantDetailExternal() = map(PlantDetailLocal::toExternal)