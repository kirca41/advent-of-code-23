package day8

import getLinesFromFile
import kotlin.math.abs

fun main() {
    val lines = getLinesFromFile("C:\\Data\\Kiril\\AdventOfCode23\\advent-of-code-23\\src\\main\\kotlin\\day8\\input.txt")
    part1(lines)
    println()
    part2(lines)
}

fun part1(lines: List<String>) {
    val instructions = lines[0]
    val nodes = getNodes(lines)

    print(getNumberOfStepsForNode("AAA", "ZZZ", instructions, nodes))
}

fun part2(lines: List<String>) {
    val instructions = lines[0]
    val nodes = getNodes(lines)

    val startingNodes = nodes.keys.filter { it.endsWith("A") }
    startingNodes
        .map { getNumberOfStepsForNode(it, "Z", instructions, nodes) }
        .let { print(findLCM(it)) }
}

fun getNodes(lines: List<String>) =
    lines.drop(2).associate {
        val k = it.split("=")[0].trim()
        val v = it.split("=")[1].trim().replace("(", "").replace(")", "")

        k to Pair(v.split(", ")[0], v.split(", ")[1])
    }

fun getNumberOfStepsForNode(key: String, endCondition: String, instructions: String, nodes: Map<String, Pair<String, String>>): Int {
    var node = key
    var idx = 0
    var steps = 0
    while (!node.endsWith(endCondition)) {
        node = if (instructions[idx] == 'L') {
            nodes[node]!!.first
        } else {
            nodes[node]!!.second
        }
        idx = (idx + 1) % instructions.length
        steps++
    }

    return steps
}

fun findLCM(numbers: List<Int>): Long {

    fun gcd(a: Long, b: Long): Long {
        return if (b == 0L) a else gcd(b, a % b)
    }

    fun lcm(a: Long, b: Long): Long {
        return if (a == 0L || b == 0L) 0 else abs(a * b) / gcd(a, b)
    }

    var result = lcm(numbers[0].toLong(), numbers[1].toLong())

    for (i in 2..<numbers.size) {
        result = lcm(result, numbers[i].toLong())
    }

    return result
}
