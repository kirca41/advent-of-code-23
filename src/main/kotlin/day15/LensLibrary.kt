package day15

import println
import readFileAsString

fun main() {
    val content =
        readFileAsString("C:\\Data\\Kiril\\AdventOfCode23\\advent-of-code-23\\src\\main\\kotlin\\day15\\input.txt")

    part1(content)
    part2(content)
}

fun part1(content: String) {
    content.split(",").sumOf { hash(it) }.println()
}

fun part2(content: String) {
    val boxes = mutableMapOf<Int, List<Pair<String, Int>>>()
    content.split(",").forEach {
        if (it.contains("=")) {
            val label = it.split("=").first()
            val focalLength = it.split("=").last().toInt()
            val hash = hash(label)
            if (!boxes.containsKey(hash)) {
                boxes[hash] = listOf(label to focalLength)
            } else {
                if (boxes[hash]!!.find { l -> l.first == label } != null) {
                    boxes[hash] = boxes[hash]!!.map { l ->
                        if (l.first == label) label to focalLength else l
                    }
                } else {
                    boxes[hash] = boxes[hash]!!.plus(label to focalLength)
                }
            }

        } else {
            val label = it.replace("-", "")
            val hash = hash(label)
            if (boxes.containsKey(hash)) {
                boxes[hash] = boxes[hash]!!.filter { l -> l.first != label }
            }
        }
    }

    boxes.entries.sumOf { (k, v) ->
        (1 + k) * v.mapIndexed { idx, (_, focalLength) -> (idx + 1) * focalLength }.sum()
    }.println()
}

fun hash(str: String): Int =
    str.fold(0) { currentValue, char ->
        (currentValue + char.code) * 17 % 256
    }
