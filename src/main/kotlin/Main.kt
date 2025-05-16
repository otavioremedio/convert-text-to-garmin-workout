package org.example

import org.example.service.GarminService
import java.util.*

fun main() {

    val scanner = Scanner(System.`in`)
    val instructions = mutableListOf<String>()
    var line: String
    val name: String

    println("Copy and paste the instructions:")

    while (true) {
        line = scanner.nextLine()

        if (line.isNotEmpty()) {
            instructions.add(line)
        }
        else {
            println("Enter the training name:")
            name = scanner.nextLine()
            break
        }
    }

    GarminService().start(instructions, name)
}


