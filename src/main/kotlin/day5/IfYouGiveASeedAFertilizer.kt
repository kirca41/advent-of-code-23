package day5

import getLinesFromFile

fun main() {
    val lines =
        getLinesFromFile("C:\\Data\\Kiril\\AdventOfCode23\\advent-of-code-23\\src\\main\\kotlin\\day5\\input.txt")

    part1(lines)
    println()
    part2(lines)
}

fun getMaps(lines: List<String>): MutableList<List<Triple<Long, Long, Long>>> {
    val maps = mutableListOf<List<Triple<Long, Long, Long>>>()
    var list = mutableListOf<Triple<Long, Long, Long>>()
    lines.drop(2)
        .filter { it != "\n" && it != "" }
        .forEach {
            if (it.contains(":")) {
                if (list.isNotEmpty()) {
                    maps.add(list)
                    list = mutableListOf()
                }
            } else {
                val (first, second, third) = it.split("\\s+".toRegex()).map { it.toLong() }
                list.add(Triple(first, second, third))
            }
        }

    maps.add(list)

    return maps
}

fun part1(lines: List<String>) {
    val seeds = lines[0].split(": ")[1].split("\\s+".toRegex()).map { it.toLong() }
    val maps = getMaps(lines)

    seeds.minOfOrNull { s ->
        var seed = s
        maps.forEach { map ->
            for ((destination, source, range) in map) {
                if (seed in source..<source + range) {
                    seed = destination + seed - source
                    break
                }
            }
        }
        seed
    }.let { print(it) }
}

fun part2(lines: List<String>) {
    val seedRanges = lines[0]
        .split(": ")[1]
        .split("\\s+".toRegex())
        .map { it.toLong() }
        .windowed(2, 2)
        .map { (first, second) -> first to first + second }

    val maps = getMaps(lines)

    var location = 0L
    while (true) {
        val seed = getSeedByLocation(location, maps)
        if (seedRanges.any { (start, end) -> seed in start..<end }) {
            println(location)
            break
        }
        location++
    }

}

fun getSeedByLocation(location: Long, maps: List<List<Triple<Long, Long, Long>>>): Long {
    var seed = location
    maps.reversed().forEach { map ->
        for ((source, destination, range) in map) {
            if (seed in source..<source + range) {
                seed = destination + seed - source
                break
            }
        }
    }

    return seed
}
