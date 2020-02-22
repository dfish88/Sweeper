import random
import const

DIRECTIONS = 8
DELTA_X = [-1,-1,0,1,1,1,0,-1]
DELTA_Y = [0,1,1,1,0,-1,-1,-1]

first_move = True

def build_board(x, y, board, size):

	# Place all the mines
	num_mines = int((size*size) / 6)
	for mines in range(num_mines):

		# Pick mine spot randomly
		mine_x = random.randint(0, size-1)
		mine_y = random.randint(0, size-1)

		# Pick new mine spot if occupied or where person clicked
		while mine_x == x or mine_y ==y or board[mine_x][mine_y] != None:
			mine_x = random.randint(0, size-1)
			mine_y = random.randint(0, size-1)

		# Place a mine
		board[mine_x][mine_y] = const.Tile(x=mine_x, y=mine_y, adjacent=0, covered=True, flag=False, mine=True, symbol='m')

	# Fill out rest of board
	for r in range(size):
		for c in range(size):

			# Skip mines
			if board[r][c] != None:
				continue
		
			# For each tile, check each adjacent direction for mines
			adj = 0
			for i in range(DIRECTIONS):
				try:
					if board[x + DELTA_X[i]][y + DELTA_Y[i]].mine:
						adj+=1
				except:
					pass
			board[r][c] = const.Tile(x=r, y=c, adjacent=adj, covered=True, flag=False, mine=True, symbol='c')
	

def make_move(x, y, board, size):

	global first_move
	changes = []

	# Build board if first move
	if first_move:
		build_board(x, y, board, size)	
		first_move = False

		
