import sys
sys.path.append('../src')

import unittest
from Model.tile import tile

class test_model(unittest.TestCase):
	"""
	A class used to test te tile class in the Model module

	Methods
	-------
	test_constructor_default()
		Creates a tile using all default values and checks if 
		fields are set properly
	test_constructor_mine()
		Creates a tile setting it to a mine and checks if fields
		are set properly
	test_constructor_adjacent()
		Creates a tile setting adjacent and checks if fields
		are set properly
	test_constructor_all()
		Creates a tile setting mine and adjacent and checks if fields
		are set properly
	test_setter_adjacent()
		Creates a tile, uses setter to set adjacent and checks if it
		was set properly
	test_flag_valid()
		Creates a tile, sets it to a flag when it is covered and checks
		it it was flagged
	test_flag_invalid()
		Creates a tile, uncovers it, and tries to flag it. Since tile is
		incovered it shouldn't be flagged
	test_uncover_no_flag()
		Creates a tile, uncovers it and checks if it was uncovered
	test_uncover_flag()
		Creates a tile, flags it, and uncovers it. The tile should be
		uncoverd and the flag should be removed
	"""

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
