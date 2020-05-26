from enum import Enum, unique

@unique
class tile_rep(Enum):

	ZERO = 0
	ONE = 1
	TWO = 2
	THREE = 3
	FOUR = 4
	FIVE = 5 
	SIX = 6
	SEVEN = 7
	EIGHT = 8
	COVERED = 9
	FLAG = 10
	FLAG_WRONG = 11
	MINE = 12
	BOOM = 13
	EMPTY = 14
	
