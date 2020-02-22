import random
import const

first_move = True
mines_left = 0

def place_mines(x, y, board, size):

	num_mines = int((size*size) / 6)
	for mines in range(num_mines):

		# Pick mine spot randomly
		mine_x = random.randit(0, size-1)
		mine_y = random.randit(0, size-1)

		# Pick new mine spot if occupied or where person clicked
		while mine_x == x or mine_y ==y or board[mine_x][mine_y] != None:
			mine_x = random.randit(0, size-1)
			mine_y = random.randit(0, size-1)

		# Place a mine
		board[mine_x][mine_y] = Tile(x=mine_x, y=mine_y, adjacent=0, covered=True, flag=False, mine=True, symbol='m')

def build_board(x, y, board, size):

	place_mines(x, y, board, size)

	# Fill out rest of board 

def make_move(x, y, board, size):

	# Build board if first move
	if first_move:

		return
		build_board(x, y, board, size)	

