/*--- Day 14: Disk Defragmentation ---

Suddenly, a scheduled job activates the system's disk defragmenter. Were the situation different, you might sit and watch it for a while, but today, you just don't have that kind of time. It's soaking up valuable system resources that are needed elsewhere, and so the only option is to help it finish its task as soon as possible.

The disk in question consists of a 128x128 grid; each square of the grid is either free or used. On this disk, the state of the grid is tracked by the bits in a sequence of knot hashes.

A total of 128 knot hashes are calculated, each corresponding to a single row in the grid; each hash contains 128 bits which correspond to individual grid squares. Each bit of a hash indicates whether that square is free (0) or used (1).

The hash inputs are a key string (your puzzle input), a dash, and a number from 0 to 127 corresponding to the row. For example, if your key string were flqrgnkx, then the first row would be given by the bits of the knot hash of flqrgnkx-0, the second row from the bits of the knot hash of flqrgnkx-1, and so on until the last row, flqrgnkx-127.

The output of a knot hash is traditionally represented by 32 hexadecimal digits; each of these digits correspond to 4 bits, for a total of 4 * 32 = 128 bits. To convert to bits, turn each hexadecimal digit to its equivalent binary value, high-bit first: 0 becomes 0000, 1 becomes 0001, e becomes 1110, f becomes 1111, and so on; a hash that begins with a0c2017... in hexadecimal would begin with 10100000110000100000000101110000... in binary.

Continuing this process, the first 8 rows and columns for key flqrgnkx appear as follows, using # to denote used squares, and . to denote free ones:

##.#.#..-->
.#.#.#.#
....#.#.
#.#.##.#
.##.#...
##..#..#
.#...#..
##.#.##.-->
|      |
V      V

In this example, 8108 squares are used across the entire 128x128 grid.

Given your actual key string, how many squares are used?

Your puzzle input is wenycdww.
 */

import scala.math.BigInt

object Day10Hasher {

  def apply(rawInput: String): String = {
    val FullLength = 256
    //val RawInput: String = "97,167,54,178,2,11,209,174,119,248,254,0,255,1,64,190"

    val input = rawInput.map(_.toInt).toArray ++ Array(17, 31, 73, 47, 23)

    var nums = (0 to 255).toArray
    var current = 0
    var skip = 0
    for (i <- (0 to 63)) {
      for (length <- input) {
        if (current + length >= FullLength) {
          val seg1 = nums.slice(current, FullLength) //segment at end
          val seg2 = nums.slice(0, length - seg1.length) //segment that wrapped
          val reversedSegment = (seg1 ++ seg2).reverse
          val newSeg1 = reversedSegment.slice(0, seg1.length)
          val newSeg2 = reversedSegment.slice(seg1.length, FullLength)

          nums = newSeg2 ++ nums.slice(length - seg1.length, current) ++ newSeg1
        } else {
          val reversedSegment = nums.slice(current, current + length).reverse
          nums = nums.slice(0, current) ++ reversedSegment ++ nums.slice(
            current + length,
            FullLength)
        }

        current = (current + length + skip) % FullLength
        skip = skip + 1
      }
    }

    val result: String =
      (for (numbers <- nums.grouped(16)) yield numbers.reduce((a, b) => a ^ b))
        .map(_.toHexString)
        .map { letter =>
          if (letter.length == 1) "0" ++ letter
          else letter
        }
        .reduce { (a, b) =>
          a ++ b
        }

    result
  }

}

val Input = "wenycdww-"
val TestInput = "flqrgnkx-"

val result = (0 to 127).map(Input ++ _.toString).toArray
  .map(Day10Hasher.apply)
  .map(BigInt(_, 16).toString(2))
  .flatMap{ row =>
    row.map{ char =>
      char.getNumericValue}}
  .sum

println(result)
