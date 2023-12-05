package day2

import getLinesFromFile

fun main() {

    part1()
    part2()
}

fun part1() {
    val lines = getLinesFromFile("C:\\Data\\Kiril\\AdventOfCode23\\advent-of-code-23\\src\\main\\kotlin\\day2\\input.txt")
    associateLines(lines).filter { it.value["red"]!! <= 12 && it.value["green"]!! <= 13 && it.value["blue"]!! <= 14 }
        .keys.sum()
        .let { print(it) }
}

fun part2() {
    val lines = getLinesFromFile("C:\\Data\\Kiril\\AdventOfCode23\\advent-of-code-23\\src\\main\\kotlin\\day2\\input.txt")
    associateLines(lines)
        .map { it.value["red"]!! * it.value["green"]!! * it.value["blue"]!! }
        .sum()
        .let { print(it) }
}

fun associateLines(lines: List<String>) =
    lines
        .associate { l ->
            val gameId = l.split(":")[0].split(" ")[1].toInt()
            val sets = l.split(":")[1].split(";")
            val cubesMap = mutableMapOf<String, Int>()
            sets.forEach { s ->
                val cubes = s.split(",")
                cubes.forEach { c ->
                    val quantity = c.split(" ")[1].toInt()
                    val color = c.split(" ")[2]
                    if (!cubesMap.containsKey(color)) {
                        cubesMap[color] = quantity
                    } else {
                        if (cubesMap[color]!! < quantity) {
                            cubesMap[color] = quantity
                        }
                    }
                }
            }

            gameId to cubesMap
        }