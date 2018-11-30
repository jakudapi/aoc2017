/*
--- Day 12: Digital Plumber ---

Walking along the memory banks of the stream, you find a small village that is experiencing a little confusion: some programs can't communicate with each other.

Programs in this village communicate using a fixed system of pipes. Messages are passed between programs using these pipes, but most programs aren't connected to each other directly. Instead, programs pass messages between each other until the message reaches the intended recipient.

For some reason, though, some of these messages aren't ever reaching their intended recipient, and the programs suspect that some pipes are missing. They would like you to investigate.

You walk through the village and record the ID of each program and the IDs with which it can communicate directly (your puzzle input). Each program has one or more programs with which it can communicate, and these pipes are bidirectional; if 8 says it can communicate with 11, then 11 will say it can communicate with 8.

You need to figure out how many programs are in the group that contains program ID 0.

For example, suppose you go door-to-door like a travelling salesman and record the following list:

0 <-> 2
1 <-> 1
2 <-> 0, 3, 4
3 <-> 2, 4
4 <-> 2, 3, 6
5 <-> 6
6 <-> 4, 5

In this example, the following programs are in the group that contains program ID 0:

    Program 0 by definition.
    Program 2, directly connected to program 0.
    Program 3 via program 2.
    Program 4 via program 2.
    Program 5 via programs 6, then 4, then 2.
    Program 6 via programs 4, then 2.

Therefore, a total of 6 programs are in this group; all but program 1, which has a pipe that connects it to itself.
--- Part Two ---

There are more programs than just the ones in the group containing program ID 0. The rest of them have no way of reaching that group, and still might have no way of reaching each other.

A group is a collection of programs that can all communicate via pipes either directly or indirectly. The programs you identified just a moment ago are all part of the same group. Now, they would like you to determine the total number of groups.

In the example above, there were 2 groups: one consisting of programs 0,2,3,4,5,6, and the other consisting solely of program 1.

How many groups are there in total?

 */

import scala.io.Source
import scala.collection.mutable.{ListBuffer, Set}
import util.control.Breaks._

  case class Node(id: Int, destinations: List[Int] = Nil)

  val startTime = System.nanoTime()
  val inputFile = "day12input.txt"
  val rawInput = ListBuffer[String]()

  for (row <- Source.fromFile(inputFile).getLines) {
    rawInput.append(row)
  }

  val groupVisited: Set[Int] = Set()
  var allVisited: Set[Int] = Set()
  var counter = 0

  val allNodesList: List[(Int, Node)] = rawInput
    .map { row =>
      row.split(" <-> ")
    } // ("0", "480, 1750")
    .map { row =>
      val id = row(0).toInt
      val dest: List[Int] = row(1).split(", ").map(_.toInt).toList
      (id, Node(id, dest))
    }
    .toList

  val allNodesMap: Map[Int, Node] = allNodesList.toMap

  for ((id, node) <- allNodesList) {
    breakable {
      if (allVisited.contains(id)) break
      else {
        travel(allNodesMap(id))
        counter += 1
        allVisited = allVisited.union(groupVisited)
        groupVisited.clear
      }
    }
  }

  def travel(node: Node): Unit = {
    if (groupVisited.contains(node.id)) Unit
    else {
      groupVisited.add(node.id)
      for (dest <- node.destinations) {
        travel(allNodesMap(dest))
      }
    }
  }

  println(counter)

