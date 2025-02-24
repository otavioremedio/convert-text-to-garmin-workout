package org.example.dto

data class Step(
    val intervals: List<Interval>,
    val repeat: Int = 1
)