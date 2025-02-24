package org.example

import org.example.service.GarminService

fun main() {

    val instructions = listOf(
        "7 X (1 min @ 75% (75w) 35-45 rpm with 2 min @ 40% (40w) rest )",
        "1 min @ 75% (75w) 35-45 rpm",
        "2 min @ 50% (50w) 50-100 rpm",
        "5 min @ 65% (65w) 92-102 rpm",
        "2 min @ 71% (71w)",
        "5 min @ 60% (60w) 92-102 rpm",
        "2 min @ 70% (70w)",
        "5 min @ 69% (69w) 92-102 rpm",
        "3 min @ 68% (68w)",
        "5 min @ 60% (60w) 92-102 rpm",
        "1 min @ 55% (55w)",
        "3 min @ 50% (50w) 92-102 rpm"
    )

    GarminService().start(instructions)
}



