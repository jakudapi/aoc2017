/*--- Day 20: Particle Swarm ---
--- Part Two ---

To simplify the problem further, the GPU would like to remove any particles that collide. Particles collide if their positions ever exactly match. Because particles are updated simultaneously, more than two particles can collide at the same time and place. Once particles collide, they are removed and cannot collide with anything else after that tick.

For example:

p=<-6,0,0>, v=< 3,0,0>, a=< 0,0,0>
p=<-4,0,0>, v=< 2,0,0>, a=< 0,0,0>    -6 -5 -4 -3 -2 -1  0  1  2  3
p=<-2,0,0>, v=< 1,0,0>, a=< 0,0,0>    (0)   (1)   (2)            (3)
p=< 3,0,0>, v=<-1,0,0>, a=< 0,0,0>

p=<-3,0,0>, v=< 3,0,0>, a=< 0,0,0>
p=<-2,0,0>, v=< 2,0,0>, a=< 0,0,0>    -6 -5 -4 -3 -2 -1  0  1  2  3
p=<-1,0,0>, v=< 1,0,0>, a=< 0,0,0>             (0)(1)(2)      (3)
p=< 2,0,0>, v=<-1,0,0>, a=< 0,0,0>

p=< 0,0,0>, v=< 3,0,0>, a=< 0,0,0>
p=< 0,0,0>, v=< 2,0,0>, a=< 0,0,0>    -6 -5 -4 -3 -2 -1  0  1  2  3
p=< 0,0,0>, v=< 1,0,0>, a=< 0,0,0>                       X (3)
p=< 1,0,0>, v=<-1,0,0>, a=< 0,0,0>

------destroyed by collision------
------destroyed by collision------    -6 -5 -4 -3 -2 -1  0  1  2  3
------destroyed by collision------                      (3)
p=< 0,0,0>, v=<-1,0,0>, a=< 0,0,0>

In this example, particles 0, 1, and 2 are simultaneously destroyed at the time and place marked X. On the next tick, particle 3 passes through unharmed.

How many particles are left after all collisions are resolved?

 */

import scala.io.Source
import scala.collection.mutable.HashMap

case class Position(var x: Long, var y: Long, var z: Long)
case class Velocity(var dx: Long, var dy: Long, var dz: Long)
case class Acceleration(d2x: Long, d2y: Long, d2z: Long)

case class Particle(p: Position,
                    v: Velocity,
                    a: Acceleration,
                    var isDestroyed: Boolean = false) {
  def update(tracker: HashMap[Position, Int]): Unit = {
    if (!isDestroyed) {
      v.dx = v.dx + a.d2x
      v.dy = v.dy + a.d2y
      v.dz = v.dz + a.d2z
      p.x = p.x + v.dx
      p.y = p.y + v.dy
      p.z = p.z + v.dz
      if (!tracker.contains(p)) tracker(p) = 1 else tracker(p) = tracker(p) + 1
    } else Unit
  }

  def checkCollision(tracker: HashMap[Position, Int]) =
    if (!isDestroyed && tracker(p) > 1) isDestroyed = true

  private def arithmeticSum(n: Long, a1: Long, d: Long): Long = {
    val aN = a1 + n * d
    (n * (a1 + aN) * .5).toLong
  }
}

  def convert(raw: String): Particle = {
    val items: Array[(Long, Long, Long)] = raw
      .split(", ")
      .map { item =>
        item.drop(3).dropRight(1).split(",").map(_.toLong)
      }
      .map { case Array(x, y, z) => (x, y, z) }
    Particle(Position.tupled(items(0)),
             Velocity.tupled(items(1)),
             Acceleration.tupled(items(2)))
  }

  val startTime = System.nanoTime()
  val inputFile = "day20input.txt"

  val particles: Array[Particle] = Source
    .fromFile(inputFile)
    .getLines
    .toArray
    .map(convert)

  for (i <- (1 to 1000000)) {
    val tracker = HashMap[Position, Int]()
    particles.map { particle =>
      particle.update(tracker)
    }
    particles.map(_.checkCollision(tracker))
  }

  val endTime = System.nanoTime()
  println(particles.filterNot(_.isDestroyed).length)
  println(s"This took ${(endTime - startTime) / 1000000} msecs")
