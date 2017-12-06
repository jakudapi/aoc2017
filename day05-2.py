'''
--- Day 5: A Maze of Twisty Trampolines, All Alike ---
--- Part Two ---

Now, the jumps are even stranger: after each jump, if the offset was three or more, instead decrease it by 1. Otherwise, increase it by 1 as before.

Using this rule with the above example, the process now takes 10 steps, and the offset values after finding the exit are left as 2 3 2 3 -1.

How many steps does it now take to reach the exit?

'''

if __name__ == "__main__":
  with open("day05input.txt", 'r') as fp:
    instrucs = fp.read()
  instrucs = [int(instruc) for instruc in instrucs.split('\n')]

  counter = 0
  location = 0

  while location <= len(instrucs) - 1:
    counter += 1
    new_location = location + instrucs[location]
    if instrucs[location] >= 3:
      instrucs[location] -= 1
    else:
      instrucs[location] += 1

    location = new_location

  print(counter)
