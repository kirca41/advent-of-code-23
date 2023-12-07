package day4

import getLinesFromFile
import kotlin.math.pow

fun main() {
    part1()
    println()
    part2()
}

fun part1() {
    getLinesFromFile("C:\\Data\\Kiril\\AdventOfCode23\\advent-of-code-23\\src\\main\\kotlin\\day4\\input.txt")
        .map { it.split(":")[1] }
        .map { getNumberOfWinningNumbersPerLine(it) }
        .map { (winningNumbers, elfNumbers) ->
            elfNumbers.filter { winningNumbers.contains(it) }.size
        }.filter { it > 0 }
        .sumOf { 2.0.pow(it - 1.0) }
        .let { print(it) }
}

fun part2() {
    val lines =
        getLinesFromFile("C:\\Data\\Kiril\\AdventOfCode23\\advent-of-code-23\\src\\main\\kotlin\\day4\\input.txt")
    val cardCopies = lines.associate {
        val card = it.split(":")[0].split("\\s+".toRegex())[1].toInt()
        card to 1
    }.toMutableMap()

    lines
        .map { it.split(":")[1] }
        .map { getNumberOfWinningNumbersPerLine(it) }
        .map { (winningNumbers, elfNumbers) ->
            elfNumbers.filter { winningNumbers.contains(it) }.size
        }.forEachIndexed { index, n ->
            (1..n).forEach { i -> cardCopies[index + 1 + i] = cardCopies[index + 1 + i]!! + cardCopies[index + 1]!! }
        }

    cardCopies.values.sum().let { print(it) }

}

fun getNumberOfWinningNumbersPerLine(line: String): Pair<Set<Int>, Set<Int>> {
    val winningNumbers = line.split("|")[0].trim().split("\\s+".toRegex())
        .filter { n -> n != "" }.map { n -> n.toInt() }.toSet()
    val elfNumbers = line.split("|")[1].trim().split("\\s+".toRegex())
        .filter { n -> n != "" }.map { n -> n.toInt() }.toSet()

    return Pair(winningNumbers, elfNumbers)
}