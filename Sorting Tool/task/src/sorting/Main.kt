package sorting
import java.io.File
import java.util.*
val sc = Scanner(System.`in`)

class SortingTool {

    fun inputData(inputFileName: String): List<String> {
        val result = mutableListOf<String>()
        return when {
            inputFileName.isEmpty() -> {
                while (sc.hasNext()) {
                    val input = sc.nextLine()
                    result += input.split("\n").map { it }
                }
                result
            }

            else -> {
                File(inputFileName).readLines().map { it }

            }

        }

    }



    object SortedBy {

        fun strategy(sortingType: String = "natural", dataType: String = "word", inputFileName: String = "",outputFileName: String = "") {
            val inputData = SortingTool().inputData(inputFileName)

            return when(sortingType.toLowerCase()) {
                "natural" -> naturalSort(dataType, inputData, outputFileName)
                "bycount" -> sortByCount(dataType, inputData, outputFileName)
                else -> throw Exception("Unknown command")
            }
        }


        fun naturalSort(dataType: String, inputData: List<String>, outputFile: String) {
            val outputData = File(outputFile)
            val totalNum = "Total numbers:"
            val sortedData = "Sorted data:"

            when (dataType) {
                "word" -> {
                    val result = mutableListOf<String>()
                    inputData.forEach { line -> result +=  line.split(" ").filter { it.isNotEmpty() }.map { it } }
                    if (outputFile.isEmpty()) {
                        println("$totalNum ${result.size}.")
                        println("$sortedData ${result.sorted().joinToString(" ")}")
                    } else {
                        outputData.writeText("$totalNum ${result.size}.\n$sortedData ${result.sorted().joinToString(" ")}")
                    }

                }

                "line" -> {
                    if (outputFile.isEmpty()) {
                        println("$totalNum ${ inputData.size}.")
                        println("$sortedData\n${inputData.sorted().joinToString("\n")}")
                    } else {
                        outputData.writeText("$totalNum ${inputData.size}.\n$sortedData\n${inputData.sorted().joinToString("\n")}")
                    }

                }

                "long" -> {
                    val result = mutableListOf<Int>()
                    inputData.forEach { line -> result +=  line.split(" ").mapNotNull { it.toIntOrNull() } }
                    if (outputFile.isEmpty()) {
                        println("$totalNum ${ result.size}.")
                        println("$sortedData ${result.sorted().joinToString(" ")}")
                    } else {
                        outputData.writeText("$totalNum ${result.size}.\n$sortedData ${result.sorted().joinToString(" ")}")
                    }

                }
                else -> throw Exception("Unknown command")
            }
        }

        fun sortByCount(dataType: String, inputData: List<String>, outputFile: String) {
            val outputData = File(outputFile)
            val totalNum = "Total numbers:"

            when (dataType) {
                "word" -> {
                    val result = mutableListOf<String>()
                    inputData.forEach { line -> result +=  line.split(" ").filter { it.isNotEmpty() }.map { it } }
                    if (outputFile.isEmpty()) {
                        println("$totalNum ${result.size}.")
                        result.groupingBy { it }
                                .eachCount()
                                .toList()
                                .sortedBy { it.second }
                                .forEach {
                                    println("${it.first}: ${it.second} time(s), ${(it.second.toDouble() / result.size.toDouble() * 100 ).toInt()}%")
                                }
                    } else {
                        outputData.writeText("$totalNum ${result.size}.\n")
                                result.groupingBy { it }
                                .eachCount()
                                .toList()
                                .sortedBy { it.second }
                                .forEach {
                                    outputData.appendText("${it.first}: ${it.second} time(s), ${(it.second.toDouble() / result.size.toDouble() * 100 ).toInt()}%\n")
                                }
                    }

                }

                "line" -> {
                    if (outputFile.isEmpty()) {
                        println("Total numbers: ${inputData.size}.")
                        inputData.sorted().groupingBy { it }
                                .eachCount()
                                .toList()
                                .sortedBy { it.second }
                                .forEach {
                                    println("${it.first}: ${it.second} time(s), ${(it.second.toDouble() / inputData.size.toDouble() * 100 ).toInt()}%")
                                }
                    } else {
                        outputData.writeText("$totalNum ${inputData.size}.\n")
                        inputData.groupingBy { it }
                                .eachCount()
                                .toList()
                                .sortedBy { it.second }
                                .forEach {
                                    outputData.appendText("${it.first}: ${it.second} time(s), ${(it.second.toDouble() / inputData.size.toDouble() * 100 ).toInt()}%\n")
                                }
                    }

                }

                "long" -> {
                    val result = mutableListOf<Int>()
                    inputData.forEach { line -> result +=  line.split(" ").mapNotNull { it.toIntOrNull() } }
                    if (outputFile.isEmpty()) {
                        println("Total numbers: ${result.size}.")
                        result.sorted()
                                .groupingBy { it }
                                .eachCount()
                                .toList()
                                .sortedBy { it.second }
                                .forEach {
                                    println("${it.first}: ${it.second} time(s), ${(it.second.toDouble() / result.size.toDouble() * 100 ).toInt()}%")
                                }
                    } else {
                        outputData.writeText("$totalNum ${result.size}.\n")
                        result.sorted()
                                .groupingBy { it }
                                .eachCount()
                                .toList()
                                .sortedBy { it.second }
                                .forEach {
                                    outputData.appendText("${it.first}: ${it.second} time(s), ${(it.second.toDouble() / result.size.toDouble() * 100 ).toInt()}%\n")
                                }
                    }

                }
                else -> throw Exception("Unknown command")
            }
        }

}

}

fun main(args: Array<String>) {
    var sortingType = "natural"
    var dataType = "word"
    var ( inputFileName, outputFileName) = Array(2) { "" }
    args.map { it }.forEachIndexed { i, word ->
        try {
            when (word) {
                "-sortingType" -> sortingType = args[i + 1]
                "-dataType" -> dataType = args[i + 1]
                "-inputFile" -> inputFileName = args[i + 1]
                "-outputFile" -> outputFileName = args[i + 1]
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    SortingTool.SortedBy.strategy(sortingType, dataType, inputFileName, outputFileName)

}




