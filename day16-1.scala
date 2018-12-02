/*
--- Day 16: Permutation Promenade ---

You come upon a very unusual sight; a group of programs here appear to be dancing.

There are sixteen programs in total, named a through p. They start by standing in a line: a stands in position 0, b stands in position 1, and so on until p, which stands in position 15.

The programs' dance consists of a sequence of dance moves:

    Spin, written sX, makes X programs move from the end to the front, but maintain their order otherwise. (For example, s3 on abcde produces cdeab).
    Exchange, written xA/B, makes the programs at positions A and B swap places.
    Partner, written pA/B, makes the programs named A and B swap places.

For example, with only five programs standing in a line (abcde), they could do the following dance:

    s1, a spin of size 1: eabcd.
    x3/4, swapping the last two programs: eabdc.
    pe/b, swapping programs e and b: baedc.

After finishing their dance, the programs end up in order baedc.

You watch the dance for a while and record their dance moves (your puzzle input). In what order are the programs standing after their dance?


 */

import scala.io.Source

  val startTime = System.nanoTime()
  val inputFile = "day16input.txt"

  val instructions = Source //Array("s1", "x3/4", "pe/b")
    .fromFile(inputFile)
    .getLines()
    .mkString
    .split(",")

  var positions: Array[String] = Array("a",
                                       "b",
                                       "c",
                                       "d",
                                       "e",
                                       "f",
                                       "g",
                                       "h",
                                       "i",
                                       "j",
                                       "k",
                                       "l",
                                       "m",
                                       "n",
                                       "o",
                                       "p")

  def execute(input: String): Unit = {
    def swap(a: Int, b: Int): Unit = {
      val temp = positions(a)
      positions(a) = positions(b)
      positions(b) = temp
    }

    val command = input(0)
    val args: String = input.slice(1, input.length)

    command match {
      case 's' => {
        val move = args.toInt
        val (start, end) = positions.splitAt(positions.length - move)
        positions = end ++ start
      }
      case 'x' => {
        val programs = args.split("/").map(_.toInt)
        swap(programs(0), programs(1))
      }
      case 'p' => {
        val programs = args.split("/")
        val indexA = positions.indexOf(programs(0))
        val indexB = positions.indexOf(programs(1))
        swap(indexA, indexB)
      }
    }
  }

  for (instruction <- instructions) {
    execute(instruction)
  }

  val endTime = System.nanoTime()
  println(positions.mkString)
  println(s"This took ${(endTime - startTime) / 1000000} msecs")
