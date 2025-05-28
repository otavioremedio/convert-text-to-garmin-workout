package org.example

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File
import java.util.*
import org.example.service.GarminService

//fun main() {
//
//    val scanner = Scanner(System.`in`)
//    val instructions = mutableListOf<String>()
//    var line: String
//    val lthr: String
//
//    println("Copy and paste the instructions:")
//
//    while (true) {
//        line = scanner.nextLine()
//
//        if (line.isNotEmpty()) {
//            instructions.add(line)
//        }
//        else {
//            println("Enter your LTHR:")
//            lthr = scanner.nextLine()
//            break
//        }
//    }
//
//    GarminService().start(instructions, lthr.toInt())
//}


fun main() {

    collectAndWriteFirstLines()

    val scanner = Scanner(System.`in`)
    var line: String
    val params = StringBuilder()

    while (true) {
        line = scanner.nextLine()

        if (line.isNotEmpty()) {
            params.append("$line\n" )
        } else {
            break
        }
    }

    createDTO(params.trim().toString())
}

fun createDTO(params: String) {
    val messageDto = MessageDto(params.trim().split("\n").map { line ->
        val (psMerchant) = line.split("\t")
        ParamsDto(ps_merchant = psMerchant)
    })
    messageDto.params.chunked(50)
        .map { MessageDto(it) }
        .let {
            GarminService().start(it, 0)
//            File("payloads.json")
//                .printWriter()
//                .use { out ->
//                    it.forEach { p ->
//                        out.println(jacksonObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(p))
//                    }
//                }
        }
}



data class MessageDto(
    val params: List<ParamsDto> = listOf()
)

data class ParamsDto(
    val merchant_code: String? = null,
    val ps_merchant: String,
    val quantity: Int = 2000
)

fun collectAndWriteFirstLines() {
    val directory = File("/home/oremedio/Downloads")
    val outputFile = File("/home/oremedio/Downloads/output.csv")

    if (directory.exists() && directory.isDirectory) {
        val firstLines = directory.listFiles { file -> file.name.startsWith("subscription") && file.extension == "csv" }
            ?.mapNotNull { file ->
                file.useLines { lines -> lines.drop(1).firstOrNull() }
            } ?: emptyList()

        outputFile.printWriter().use { writer ->
            writer.println("cod_merchant;quantity;success;errors")
            firstLines.forEach { line ->
                writer.println(line)
            }
        }
    }
}


