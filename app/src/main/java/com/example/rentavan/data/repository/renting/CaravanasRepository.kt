package com.example.rentavan.data.repository.renting

import com.example.rentavan.data.model.renting.Caravana
import kotlinx.coroutines.delay

suspend fun obtenerCaravanas(): Result<List<Caravana>> {
    delay(800)
    val lista = (1..6).map { i -> Caravana(id = "car_$i", nombre = "Caravana $i") }
    return Result.success(lista)
}
