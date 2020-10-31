from .tile_rep import *

class tile:
	"""
	A class used to represent a tile on the game board.

	Parameters
	----------
	mine : bool
		Used to set the tile to a mine
	adjacent : int
		Used to set how many mines this tile is adjacent to

	Attributes
	----------
	mine : bool
		is this tile a mine (default false)
	flag : bool
		is this tile flagged (default False)
	adjacent : int
		how many mines is this tile adjacent to (default 0)
	covered : bool
		is this tile covered (default True)

	Methods
	-------
	get_rep()
		Returns the tile_rep enum associated with this tile
	mine()
		Returns true if tile is mine, false otherwise
	flag()
		Returns true if tile is flagged, false otherwise
	place_flag()
		Reverses the flag variable if tile is covered
	adjacent()
		Returns the number of mines this tile is adjacent to
	covered()
		Returns true if tile if covered, false otherwise
	uncover()
		Uncovers this tile and sets flag to false 
	"""

	def __init__(self, *, mine=False, adjacent=0):
		"""Initializes this tile object. This init method requires
		any arguments to be named to make creating tile objects much
		clearer.

		Parameters
		----------
		mine : bool
			is this tile a mine (default false)
		adjacent : int
			how many mines is this tile adjacent to (default 0)
		"""

		self._covered = True
		self._flag = False
		self._mine = mine
		self._adjacent = adjacent

	@property
	def rep(self):
		"""Returns the tile_rep enum based on the fields of this tile
		
		Returns
		-------
		tile_rep
			The enum representaiton of this tile
		"""

		if self.covered:
			if self.flag:
				return tile_rep.FLAG
			else:
				return tile_rep.COVERED
		else:
			if self.mine:
				return tile_rep.BOOM
			else:
				return tile_rep(self.adjacent)

	@property
	def mine(self):
		"""Returns true if this tile is a mine, false otherwise
		
		Returns
		-------
		bool
			True if mine, false otherwise
		"""

		return self._mine

	@property
	def flag(self):
		"""Returns true if this tile is a flag, false otherwise
		
		Returns
		-------
		bool
			True if flag, false otherwise
		"""

		return self._flag

	def place_flag(self):
		"""Places a flag on this tile only if it is covered
		"""

		if self._covered: self._flag = not self._flag 

	@property
	def adjacent(self):
		"""Returns the number of mines this tile is adjacent to
		
		Returns
		-------
		int
			number of mines adjacent to this tile
		"""

		return self._adjacent

	@property
	def covered(self):
		"""Returns true if this tile is covered, false otherwise
		
		Returns
		-------
		bool	
			true if tile is covered, false otherwise
		"""

		return self._covered

	def uncover(self):
		"""Uncovers this tile by setting cover to false and
		flag to flase
		"""

		self._covered = False
		self._flag = False
