/*
--- Day 17: Spinlock ---
 --- Part Two ---

The spinlock does not short-circuit. Instead, it gets more angry. At least, you assume that's what happened; it's spinning significantly faster than it was a moment ago.

You have good news and bad news.

The good news is that you have improved calculations for how to stop the spinlock. They indicate that you actually need to identify the value after 0 in the current state of the circular buffer.

The bad news is that while you were determining this, the spinlock has just finished inserting its fifty millionth value (50000000).

What is the value after 0 the moment 50000000 is inserted?

Your puzzle input is still 354.

 */

  class Node(val id: Int, var next: Option[Node] = None){
    override def toString() = s"Node id: $id, next: ${next.map(_.id)}"

    def insert(nextId: Int) = {
      val temp = new Node(nextId, this.next)
      this.next = Some(temp)
    }
  }

  def printBuffer(start: Node): Unit = {
    var current: Node = start
    do{
      print(s"${current.id}, ")
      current = current.next.get
    } while(current != start)
  }

  val SpinStep = 354
  val TotalInsertions = 50000000
  val startTime = System.nanoTime()

  var insertions = 1 // start after first insertion
  val head = new Node(0, None)
  var current = new Node(1, Some(head))
  head.next = Some(current)

  for (cycle <- (2 to TotalInsertions)){
    if (cycle % 1000000 == 0) println(s"cycle: ${(System.nanoTime()-startTime)/1000000000} secs")
    for (i <- (1 to SpinStep)) current = current.next.get
    current.insert(cycle)
    current = current.next.get
  }

  println(s"current node is $current")
  println(s"head node is $head")

  val endTime = System.nanoTime()
  println(s"This took ${(endTime - startTime) / 1000000} msecs")


