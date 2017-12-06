'''
--- Day 4: High-Entropy Passphrases ---

A new system policy has been put in place that requires all accounts to use a passphrase instead of simply a password. A passphrase consists of a series of words (lowercase letters) separated by spaces.

To ensure security, a valid passphrase must contain no duplicate words.

For example:

    aa bb cc dd ee is valid.
    aa bb cc dd aa is not valid - the word aa appears more than once.
    aa bb cc dd aaa is valid - aa and aaa count as different words.

The system's full passphrase list is available as your puzzle input. How many passphrases are valid?
'''

from __future__ import print_function
import re

if __name__ == "__main__":
  with open('day04input.txt', 'r') as fp:
    passphrases = fp.readlines()

count = 0
for passphrase in passphrases:
  words = passphrase.strip().split(' ')  # split each line (a string) to a list of its words. 
  if len(words) == len(set(words)):
    count += 1

print(count)