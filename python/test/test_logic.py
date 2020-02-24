import unittest
import src.const
from src.logic import make_move

class LogicTest(unittest.TestCase):

	# Build and empty board and see if clicking reveals all tiles
	def test_all_empty(self):

		first_move = False

		size = 8
		test_board = []

		for r in range(size):
			test_board.append([])
			for c in range(size):
				test_board[r].append({'x':r, 'y':c, 'adjacent':0, 'covered':True, 'flag':False, 'mine':False})

		make_move(0, 0, test_board, size)

		for r in range(size):
			for c in range(size):
				self.assertEqual(test_board[r][c]['covered'], False)

