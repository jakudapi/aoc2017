'''
--- Day 6: Memory Reallocation ---
--- Part Two ---

Out of curiosity, the debugger would also like to know the size of the loop: starting from a state that has already been seen, how many block redistribution cycles must be performed before that same state is seen again?

In the example above, 2 4 1 2 is seen again after four cycles, and so the answer in that example would be 4.

How many cycles are in the infinite loop that arises from the configuration in your puzzle input?

'''

from collections import defaultdict


def sort_key(a):
    return (15 - a[1]) + (a[0] * 1000)  # input is 15 indices long, so prioritize lower index


def redistribute(blocks, count, index):
  '''
  input- blocks list(int,int) the memory, count: int memory left to redistribute, index: int is index of highest node
  '''
  current_index = index

  while count > 0:
    current_index += 1  # go to the next index 
    if current_index > len(blocks) - 1:
      current_index -= 16  # start from beginning
    if current_index != index:
      blocks[current_index] = (blocks[current_index][0]+1, blocks[current_index][1])  # add 1
      count -= 1  # 1 less memory to redistribute
  blocks[index] = (0, index)  # clear out the high_node


PUZZLE_INPUT = "5 1 10 0 1 7 13 14 3 12 8 10 7 12 0 6"

if __name__ == "__main__":
  # make a list of tuples where the values are the num_blocks and index in list so when we sort
  # later we remember the index it was originally in.
  puzzle = [(int(x), index) for index, x in enumerate(PUZZLE_INPUT.split(" "))] 

  tracker = defaultdict(int) # empty dict to keep track of configurations seen
  cycles = 0

  while True:
    cycles += 1
    high_node = sorted(puzzle, key=sort_key, reverse=True)[0]
    redistribute(puzzle, high_node[0], high_node[1])
    key = str(puzzle)
    if key in tracker:
      print("number of cycles since seen: {}".format(cycles - tracker[key]))
    else:
      tracker[str(puzzle)] = cycles
    if len(tracker) != cycles:  # duplication found
      break

  print(cycles)
  

