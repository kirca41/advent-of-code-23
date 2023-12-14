package day13

import getLinesFromFile
import println
import kotlin.math.min

fun main() {
    val lines =
        getLinesFromFile("C:\\Data\\Kiril\\AdventOfCode23\\advent-of-code-23\\src\\main\\kotlin\\day13\\input.txt")
    val patterns = mutableListOf<List<String>>()

    var pattern = mutableListOf<String>()
    for (line in lines) {
        if (line.isBlank()) {
            patterns.add(pattern)
            pattern = mutableListOf()
        } else {
            pattern.add(line)
        }
    }
    patterns.add(pattern)

    part1(patterns)
    part2(patterns)
}

fun part1(patterns: List<List<String>>) =
    patterns.sumOf { 100 * findHorizontalLineOfReflection(it) + findVerticalLineOfReflection(it) }.println()

fun part2(patterns: List<List<String>>) =
    patterns.sumOf { 100 * findHorizontalLineOfReflectionSmudge(it) + findVerticalLineOfReflectionSmudge(it) }.println()

fun findHalves(pattern: List<String>, idx: Int): Pair<List<String>, List<String>> {
    val span = min(idx, pattern.size - idx)
    val firstHalf = pattern.filterIndexed { index, _ -> index in idx - span..<idx }
    val secondHalf = pattern.filterIndexed { index, _ -> index in idx..<idx + span }.reversed()

    return firstHalf to secondHalf
}

fun findHorizontalLineOfReflectionSmudge(pattern: List<String>): Int {
    for (i in 1..<pattern.size) {
        val (firstHalf, secondHalf) = findHalves(pattern, i)
        val differences = firstHalf.zip(secondHalf).sumOf { (f, s) -> f.zip(s).count { (c1, c2) -> c1 != c2 } }
        if (differences == 1) {
            return i
        }
    }

    return 0
}

fun findVerticalLineOfReflectionSmudge(pattern: List<String>): Int =
    findHorizontalLineOfReflectionSmudge(transpose(pattern))

fun findHorizontalLineOfReflection(pattern: List<String>): Int {
    for (i in 1..<pattern.size) {
        val (firstHalf, secondHalf) = findHalves(pattern, i)
        if (firstHalf == secondHalf) {
            return i
        }
    }

    return 0
}

fun findVerticalLineOfReflection(pattern: List<String>): Int =
    findHorizontalLineOfReflection(transpose(pattern))

fun transpose(pattern: List<String>): List<String> {
    val result = mutableListOf<String>()
    for (j in 0..<pattern.first().length) {
        result.add(pattern.map { it[j] }.joinToString(""))
    }

    return result
}