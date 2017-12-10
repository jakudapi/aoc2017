'''
--- Day 8: I Heard You Like Registers ---
--- Part Two ---

To be safe, the CPU also needs to know the highest value held in any register during this process so that it can decide how much memory to allocate to these operations. For example, in the above instructions, the highest value ever held was 10 (in register c after the third instruction was evaluated).

'''

from collections import Counter
from operator import lt, gt, eq, ne, ge, le

if __name__ == "__main__":
  with open("day08input.txt", 'r') as fp:
    instrucs = fp.readlines()

registers = Counter()
conditions = {"==":eq, ">":gt, "<":lt, ">=":ge, "<=":le, "!=":ne}
highest = 0

for instruc in instrucs:
  instruc = instruc.strip().split(" ")
  val_condition = int(instruc[-1])
  condition = instruc[-2]
  reg_test = instruc[-3]

  if conditions[condition](registers[reg_test], val_condition):
    if instruc[1] == "inc":
      registers[instruc[0]] += int(instruc[2])
    else:
      registers[instruc[0]] -= int(instruc[2])
    if registers[instruc[0]] > highest:
      highest = registers[instruc[0]]

print(highest)
