package day3

import getCharMatrixFromFile

fun main() {
    val matrix =
        getCharMatrixFromFile("C:\\Data\\Kiril\\AdventOfCode23\\advent-of-code-23\\src\\main\\kotlin\\day3\\input.txt")
    part1(matrix)
    println()
    part2(matrix)
}

fun part1(matrix: List<List<Char>>) {
    val numbers = mutableListOf<Int>()
    matrix.forEachIndexed { i, row ->
        var number = ""
        var start = 0
        row.forEachIndexed { j, col ->
            if (!col.isDigit()) {
                if (number != "" && isAdjacentToSymbols(
                        matrix,
                        i,
                        start,
                        j - 1,
                        listOf('*', '#', '+', '$', '/', '-', '&', '@', '=', '%')
                    )
                ) {
                    numbers.add(number.toInt())
                }

                number = ""
            } else {
                if (number == "") {
                    start = j
                }

                number += col
            }
        }

        if (number != "" && isAdjacentToSymbols(
                matrix,
                i,
                start,
                row.size - 1,
                listOf('*', '#', '+', '$', '/', '-', '&', '@', '=', '%')
            )
        ) {
            numbers.add(number.toInt())
        }
    }

    print(numbers.sum())
}

fun part2(matrix: List<List<Char>>) {
    val partNumbersToCoords = mutableMapOf<Int, List<Pair<Int, Int>>>()

    matrix.forEachIndexed { i, row ->
        var number = ""
        var start = 0
        row.forEachIndexed { j, col ->
            if (!col.isDigit()) {
                if (number != "" && isAdjacentToSymbols(matrix, i, start, j - 1, listOf('*'))) {
                    if (!partNumbersToCoords.containsKey(number.toInt()))
                        partNumbersToCoords[number.toInt()] = (start..<j).map { Pair(i, it) }
                    else
                        partNumbersToCoords[number.toInt()] = partNumbersToCoords[number.toInt()]?.plus((start..<j).map { Pair(i, it) })!!
                }

                number = ""
            } else {
                if (number == "") {
                    start = j
                }

                number += col
            }
        }

        if (number != "" && isAdjacentToSymbols(matrix, i, start, row.size - 1, listOf('*'))) {
            if (!partNumbersToCoords.containsKey(number.toInt()))
                partNumbersToCoords[number.toInt()] = (start..<row.size - 1).map { Pair(i, it) }
            else
                partNumbersToCoords[number.toInt()] = partNumbersToCoords[number.toInt()]?.plus((start..<row.size - 1).map { Pair(i, it) })!!
        }
    }

    var sum = 0
    matrix.forEachIndexed { i, row ->
        row.forEachIndexed { j, col ->
            if (col == '*') {
                val neighbours = mutableListOf<Pair<Int, Int>>()
                neighbours.add(i to j + 1)
                neighbours.add(i + 1 to j)
                neighbours.add(i + 1 to j + 1)
                neighbours.add(i - 1 to j - 1)
                neighbours.add(i - 1 to j)
                neighbours.add(i to j - 1)
                neighbours.add(i - 1 to j + 1)
                neighbours.add(i + 1 to j - 1)

                val eligibleNumbers = partNumbersToCoords.filter {
                    if (neighbours.find { n -> it.value.contains(n) } != null) true else false
                }.map { it.key }

                if (eligibleNumbers.size == 2) {
                    sum += eligibleNumbers[0] * eligibleNumbers[1]
                }
            }
        }
    }

    print(sum)
}

fun isAdjacentToSymbols(
    matrix: List<List<Char>>,
    row: Int,
    start: Int,
    end: Int,
    validSymbols: List<Char>
): Boolean {
    if (start >= 1 && validSymbols.contains(matrix[row][start - 1]) ||
        row >= 1 && start >= 1 && validSymbols.contains(matrix[row - 1][start - 1]) ||
        row < matrix.size - 1 && start >= 1 && validSymbols.contains(matrix[row + 1][start - 1]) ||
        end < matrix[0].size - 1 && validSymbols.contains(matrix[row][end + 1]) ||
        row >= 1 && end < matrix[0].size - 1 && validSymbols.contains(matrix[row - 1][end + 1]) ||
        row < matrix.size - 1 && end < matrix[0].size - 1 && validSymbols.contains(matrix[row + 1][end + 1])
    )
        return true

    for (i in start..end) {
        if (row >= 1 && validSymbols.contains(matrix[row - 1][i]) || row < matrix.size - 1 && validSymbols.contains(
                matrix[row + 1][i]
            )
        )
            return true
    }

    return false
}