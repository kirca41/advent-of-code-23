package day11

import getCharMatrixFromFile
import print
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    val matrix =
        getCharMatrixFromFile("C:\\Data\\Kiril\\AdventOfCode23\\advent-of-code-23\\src\\main\\kotlin\\day11\\input.txt")

    val emptyRows = findEmptyRows(matrix)
    val emptyColumns = findEmptyColumns(matrix)
    val galaxies = findGalaxiesCoordinates(matrix)
    part1(emptyRows, emptyColumns, galaxies)
    println()
    part2(emptyRows, emptyColumns, galaxies)
}

fun part1(emptyRows: List<Int>, emptyColumns: List<Int>, galaxies: List<Pair<Int, Int>>) {
    solve(emptyRows, emptyColumns, galaxies)
}

fun part2(emptyRows: List<Int>, emptyColumns: List<Int>, galaxies: List<Pair<Int, Int>>) {
    solve(emptyRows, emptyColumns, galaxies, 999999)
}

fun solve(emptyRows: List<Int>, emptyColumns: List<Int>, galaxies: List<Pair<Int, Int>>, expansionRate: Long = 1) {
    galaxies.flatMapIndexed { i, g1 ->
        galaxies.filterIndexed { j, g2 -> j > i }
            .map { g2 ->
                findShortestDistance(g1, g2)
                    .plus(expansionRate * emptyRows.count {
                        it in (min(g1.first + 1, g2.first + 1)..<max(
                            g1.first,
                            g2.first
                        ))
                    })
                    .plus(expansionRate * emptyColumns.count {
                        it in (min(g1.second + 1, g2.second + 1)..<max(
                            g1.second,
                            g2.second
                        ))
                    })
            }
    }.sum().print()
}

fun findEmptyRows(matrix: List<List<Char>>): List<Int> =
    matrix.mapIndexed { index, row -> Pair(index, row) }.filter { !it.second.contains('#') }
        .map { it.first }

fun findEmptyColumns(matrix: List<List<Char>>): List<Int> {
    val nonEmptyColumns = mutableListOf<Int>()
    for (j in 0..<matrix.first().size) {
        for (i in matrix.indices) {
            if (matrix[i][j] == '#') {
                nonEmptyColumns.add(j)
                break
            }
        }
    }

    return (0..<matrix.first().size).filter { !nonEmptyColumns.contains(it) }
}

fun findGalaxiesCoordinates(matrix: List<List<Char>>): List<Pair<Int, Int>> {
    return matrix.flatMapIndexed { i, row ->
        row.mapIndexed { j, col -> Pair(j, col) }.filter { it.second == '#' }.map { Pair(i, it.first) }
    }
}

fun findShortestDistance(galaxy1: Pair<Int, Int>, galaxy2: Pair<Int, Int>): Int =
    abs(galaxy1.first - galaxy2.first) + abs(galaxy1.second - galaxy2.second)

