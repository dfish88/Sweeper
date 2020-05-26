import sys
sys.path.append('../src')

import unittest
from Model.tile import tile

class test_model(unittest.TestCase):

	def test_constructor_default(self):
		test = tile()
		self.assertEqual(False, test.mine)
		self.assertEqual(False, test.flag)
		self.assertEqual(0, test.adjacent)
		self.assertEqual(True, test.covered)

	def test_constructor_mine(self):
		test = tile(mine=True)
		self.assertEqual(True, test.mine)
		self.assertEqual(False, test.flag)
		self.assertEqual(0, test.adjacent)
		self.assertEqual(True, test.covered)
	
	def test_constructor_adjacent(self):
		test = tile(adjacent=8)
		self.assertEqual(False, test.mine)
		self.assertEqual(False, test.flag)
		self.assertEqual(8, test.adjacent)
		self.assertEqual(True, test.covered)

	def test_constructor_all(self):
		test = tile(mine=True,  adjacent=8)
		self.assertEqual(True, test.mine)
		self.assertEqual(False, test.flag)
		self.assertEqual(8, test.adjacent)
		self.assertEqual(True, test.covered)

	def test_setter_adjacent(self):
		test = tile()
		test.adjacent = 4
		self.assertEquals(4, test.adjacent)

	def test_flag_valid(self):
		test = tile()
		test.place_flag()
		self.assertEqual(True, test.flag)

	def test_flag_invalid(self):
		test = tile()
		test.uncover()
		test.place_flag()
		self.assertEqual(False, test.flag)

	def test_uncover_no_flag(self):
		test = tile()
		test.uncover()
		self.assertEqual(False, test.covered)

	def test_uncover_flag(self):
		test = tile()
		test.place_flag()
		test.uncover()
		self.assertEqual(False, test.covered)
		self.assertEqual(False, test.flag)
