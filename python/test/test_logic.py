import sys
sys.path.append('../src')

import unittest
from logic import make_move, determine_adjacent, get_symbol
from const import *

class LogicTest(unittest.TestCase):

	def test_covered_tile(self):

		test_tile = {'x':0, 'y':0, 'adjacent':0, 'covered':True, 'flag':False, 'mine':False}
		self.assertEqual(get_symbol(test_tile), 'b')		

	def test_adjacent_tile(self):

		test_tile = {'x':0, 'y':0, 'adjacent':1, 'covered':False, 'flag':False, 'mine':False}
		self.assertEqual(get_symbol(test_tile), '1')

	def test_blank_tile(self):

		test_tile = {'x':0, 'y':0, 'adjacent':0, 'covered':False, 'flag':False, 'mine':False}
		self.assertEqual(get_symbol(test_tile), '0')	

	def test_flag_tile(self):

		test_tile = {'x':0, 'y':0, 'adjacent':0, 'covered':True, 'flag':True, 'mine':False}
		self.assertEqual(get_symbol(test_tile), 'f')	

	# Build and empty board and see if clicking reveals all tiles
	def test_empty(self):

		size = 8
		test_board = []

		for r in range(size):
			test_board.append([])
			for c in range(size):
				test_board[r].append({'x':r, 'y':c, 'adjacent':0, 'covered':True, 'flag':False, 'mine':False})

		test_game = {'tiles_left':0, 'size': size, 'board':test_board}
		make_move(0, 0, test_game)

		for r in range(size):
			for c in range(size): self.assertEqual(test_board[r][c]['covered'], False) 

	# Build a board with one mine and all tiles adjacent to it
	# Clicking should only reveal tile clicked on
	def test_one_mine(self):

		size = 2
		test_board = []

		for r in range(size):
			test_board.append([])
			for c in range(size):
				test_board[r].append({'x':r, 'y':c, 'adjacent':1, 'covered':True, 'flag':False, 'mine':False})

		test_board[size-1][size-1] = {'x':size-1, 'y':size-1, 'adjacent':0, 'covered':True, 'flag':False, 'mine':True}
		test_game = {'tiles_left':0, 'size': size, 'board':test_board}

		make_move(0, 0, test_game)

		self.assertEqual(test_board[0][0]['covered'], False)
		self.assertEqual(test_board[1][0]['covered'], True)
		self.assertEqual(test_board[1][0]['covered'], True)
		self.assertEqual(test_board[1][1]['covered'], True)

	# Build board with 1 mine and some empty tiles and all tiles
	# but mine should be revealed when empty clicked
	def test_mixed_1(self):

		size = 3
		test_board = []

		for r in range(size):
			test_board.append([])
			for c in range(size):
				test_board[r].append({'x':r, 'y':c, 'adjacent':0, 'covered':True, 'flag':False, 'mine':False})

		test_board[size-1][size-1] = {'x':size-1, 'y':size-1, 'adjacent':0, 'covered':True, 'flag':False, 'mine':True}
		test_board[1][1] = {'x':1, 'y':1, 'adjacent':1, 'covered':True, 'flag':False, 'mine':False}
		test_board[1][2] = {'x':1, 'y':2, 'adjacent':1, 'covered':True, 'flag':False, 'mine':False}
		test_board[2][1] = {'x':2, 'y':1, 'adjacent':1, 'covered':True, 'flag':False, 'mine':False}
		test_game = {'tiles_left':0, 'size': size, 'board':test_board}

		make_move(0, 0, test_game)

		self.assertEqual(test_board[0][0]['covered'], False)
		self.assertEqual(test_board[1][0]['covered'], False)
		self.assertEqual(test_board[2][0]['covered'], False)
		self.assertEqual(test_board[2][1]['covered'], False)
		self.assertEqual(test_board[1][1]['covered'], False)
		self.assertEqual(test_board[1][2]['covered'], False)
		self.assertEqual(test_board[0][1]['covered'], False)
		self.assertEqual(test_board[0][2]['covered'], False)
		self.assertEqual(test_board[2][2]['covered'], True)

	# Build board with 1 mine and some empty tiles and only
	# tile clicked on should be revealed if it is a 1
	def test_mixed_2(self):

		size = 3
		test_board = []

		for r in range(size):
			test_board.append([])
			for c in range(size):
				test_board[r].append({'x':r, 'y':c, 'adjacent':0, 'covered':True, 'flag':False, 'mine':False})

		test_board[size-1][size-1] = {'x':size-1, 'y':size-1, 'adjacent':0, 'covered':True, 'flag':False, 'mine':True}
		test_board[1][1] = {'x':1, 'y':1, 'adjacent':1, 'covered':True, 'flag':False, 'mine':False}
		test_board[1][2] = {'x':1, 'y':2, 'adjacent':1, 'covered':True, 'flag':False, 'mine':False}
		test_board[2][1] = {'x':2, 'y':1, 'adjacent':1, 'covered':True, 'flag':False, 'mine':False}
		test_game = {'tiles_left':0, 'size': size, 'board':test_board}

		make_move(1, 1, test_game)

		self.assertEqual(test_board[0][0]['covered'], True)
		self.assertEqual(test_board[1][0]['covered'], True)
		self.assertEqual(test_board[2][0]['covered'], True)
		self.assertEqual(test_board[2][1]['covered'], True)
		self.assertEqual(test_board[1][1]['covered'], False)
		self.assertEqual(test_board[1][2]['covered'], True)
		self.assertEqual(test_board[0][2]['covered'], True)
		self.assertEqual(test_board[2][2]['covered'], True)
		self.assertEqual(test_board[0][1]['covered'], True)

	# Build board with 1 mine and test if board is built propery
	def test_mixed_3(self):

		size = 3
		test_board = []

		for r in range(size):
			test_board.append([])
			for c in range(size):
				test_board[r].append(None)

		test_board[size-1][size-1] = {'x':size-1, 'y':size-1, 'adjacent':0, 'covered':True, 'flag':False, 'mine':True}
		test_game = {'tiles_left':0, 'size': size, 'board':test_board}

		determine_adjacent(test_game)

		self.assertEqual(test_board[0][0]['adjacent'], 0)
		self.assertEqual(test_board[0][1]['adjacent'], 0)
		self.assertEqual(test_board[0][2]['adjacent'], 0)
		self.assertEqual(test_board[1][0]['adjacent'], 0)
		self.assertEqual(test_board[2][0]['adjacent'], 0)
		self.assertEqual(test_board[1][1]['adjacent'], 1)
		self.assertEqual(test_board[1][2]['adjacent'], 1)
		self.assertEqual(test_board[2][1]['adjacent'], 1)

	# Build board with 2 mine and test if board is built propery
	def test_mixed_4(self):

		size = 3
		test_board = []

		for r in range(size):
			test_board.append([])
			for c in range(size):
				test_board[r].append(None)

		test_board[size-1][size-1] = {'x':size-1, 'y':size-1, 'adjacent':0, 'covered':True, 'flag':False, 'mine':True}
		test_board[size-2][size-2] = {'x':size-2, 'y':size-2, 'adjacent':0, 'covered':True, 'flag':False, 'mine':True}
		test_game = {'tiles_left':0, 'size': size, 'board':test_board}

		determine_adjacent(test_game)

		self.assertEqual(test_board[0][0]['adjacent'], 1)
		self.assertEqual(test_board[0][1]['adjacent'], 1)
		self.assertEqual(test_board[0][2]['adjacent'], 1)
		self.assertEqual(test_board[1][0]['adjacent'], 1)
		self.assertEqual(test_board[2][0]['adjacent'], 1)
		self.assertEqual(test_board[1][2]['adjacent'], 2)
		self.assertEqual(test_board[2][1]['adjacent'], 2)

	def test_valid_flag(self):
		
		size = 2
		test_board = []

		for r in range(size):
			test_board.append([])
			for c in range(size):
				test_board[r].append({'x':r, 'y':c, 'adjacent':1, 'covered':True, 'flag':False, 'mine':False})

		test_board[size-1][size-1] = {'x':size-1, 'y':size-1, 'adjacent':0, 'covered':True, 'flag':False, 'mine':True}
		test_game = {'tiles_left':0, 'size': size, 'board':test_board}

		make_move(1, 1, test_game, flag=True)
		self.assertEqual(test_board[1][1]['flag'], True)

	def test_invalid_flag(self):

		size = 2
		test_board = []

		for r in range(size):
			test_board.append([])
			for c in range(size):
				test_board[r].append({'x':r, 'y':c, 'adjacent':1, 'covered':True, 'flag':False, 'mine':False})

		test_board[size-1][size-1] = {'x':size-1, 'y':size-1, 'adjacent':0, 'covered':True, 'flag':False, 'mine':True}
		test_game = {'tiles_left':0, 'size': size, 'board':test_board}

		make_move(0, 0, test_game)
		make_move(0, 0, test_game, flag=True)
		self.assertEqual(test_board[1][1]['flag'], False)

	# Click on a mine and see if we lose game
	def test_lose(self):

		size = 2
		test_board = []

		for r in range(size):
			test_board.append([])
			for c in range(size):
				test_board[r].append({'x':r, 'y':c, 'adjacent':1, 'covered':True, 'flag':False, 'mine':False})

		test_board[size-1][size-1] = {'x':size-1, 'y':size-1, 'adjacent':0, 'covered':True, 'flag':False, 'mine':True}
		test_game = {'tiles_left':0, 'size': size, 'board':test_board}

		state = make_move(1, 1, test_game)[1]
		self.assertEqual(state, LOST)

	# Clear all mines and see if we win game
	def test_win(self):

		size = 2
		test_board = []

		for r in range(size):
			test_board.append([])
			for c in range(size):
				test_board[r].append({'x':r, 'y':c, 'adjacent':1, 'covered':True, 'flag':False, 'mine':False})

		test_board[size-1][size-1] = {'x':size-1, 'y':size-1, 'adjacent':0, 'covered':True, 'flag':False, 'mine':True}
		test_game = {'tiles_left':3, 'size': size, 'board':test_board}

		make_move(0, 0, test_game)
		make_move(0, 1, test_game)
		state = make_move(1, 0, test_game)[1]
		self.assertEqual(state, WON)

if __name__ == '__main__':
	unittest.main()
