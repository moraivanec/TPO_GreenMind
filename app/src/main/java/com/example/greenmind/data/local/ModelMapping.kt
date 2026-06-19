package com.example.greenmind.data.local

import com.example.greenmind.data.Plant
import com.example.greenmind.data.PlantDetail

fun Plant.toLocal() = PlantLocal(
    id = id,
    commonName = commonName,
    scientificName = scientificName,
    careLevel = careLevel,
    imageUrl = imageUrl
)

fun List<Plant>.toLocal() = map(Plant::toLocal)

fun PlantLocal.toExternal() = Plant(
    id = id,
    commonName = commonName,
    scientificName = scientificName,
    careLevel = careLevel,
    imageUrl = imageUrl
)

fun List<PlantLocal>.toExternal() = map(PlantLocal::toExternal)

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

fun List<PlantDetailLocal>.toPlantDetailExternal() = map(PlantDetailLocal::toExternal)