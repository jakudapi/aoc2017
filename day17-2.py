from time import time

class Node(object):
  def __init__(self, id, next_node=None):
    self.id = id
    self.next_node = next_node

  def insert(self, nextId):
    temp = Node(nextId, self.next_node)
    self.next_node = temp

  def __str__(self):
    temp = self.next_node
    return f"Node id: {self.id}, next node: {temp.id}"


if __name__ == "__main__":

  start_t = time()

  SPINSTEP = 354
  TOTAL_INSERTIONS = 50000000

  insertions = 1

  head = Node(0)
  current = Node(1, head)
  head.next_node = current

  for cycle in range(2, TOTAL_INSERTIONS + 1):
    if cycle % 1000000 == 0: print(cycle)
    for i in range(0, SPINSTEP):
      current = current.next_node
    current.insert(cycle)
    current = current.next_node

  end_t = time()
  print(head)
  print(f"this took {round(end_t - start_t,5)} secs")

