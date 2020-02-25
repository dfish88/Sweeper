import random
from const import *

DIRECTIONS = 8
DELTA_X = [-1,-1,0,1,1,1,0,-1]
DELTA_Y = [0,1,1,1,0,-1,-1,-1]

tiles_left = 0

def get_state(x, y, board, size):

	# Check for loss by seeing if player clicked on mine
	if board[x][y]['mine']:
		return LOST

	# Check for win by seeing if all non-mine tiles are revealed
	if tiles_left == 0:
		return WON

	return RUNNING

def get_symbol(tile):

	symbol = ' '
	if tile['covered'] and tile['flag']:
		symbol = 'f'
	elif tile['covered']:
		symbol = 'b'
	elif not tile['covered'] and tile['mine']:
		symbol = 'm'
	elif not tile['covered']:
		symbol = str(tile['adjacent'])
	return symbol

def get_coordinate(x, y, direction):

	new_x = x + DELTA_X[direction]
	new_y = y + DELTA_Y[direction]

	if new_x < 0 or new_y < 0:
		raise IndexError("list index out of range")

	return (new_x, new_y)
	

def determine_adjacent(board, size):

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
					coords = get_coordinate(r, c, i)
					if board[coords[0]][coords[1]]['mine']:
						adj+=1
				except:
					continue
		
			board[r][c] = {'x':r, 'y':c, 'adjacent':adj, 'covered':True, 'flag':False, 'mine':False}

def build_board(x, y, board, size):

	global tiles_left
	# Build the board
	for r in range(size):
		board.append([])
		for c in range(size):
			board[r].append(None)

	# Place all the mines
	num_mines = int((size*size) / 6)
	for mines in range(num_mines):

		# Pick mine spot randomly
		mine_x = random.randint(0, size-1)
		mine_y = random.randint(0, size-1)

		# Pick new mine spot if occupied or where person clicked
		while mine_x == x or mine_y == y or board[mine_x][mine_y] != None:
			mine_x = random.randint(0, size-1)
			mine_y = random.randint(0, size-1)

		# Place a mine
		board[mine_x][mine_y] = {'x':mine_x, 'y':mine_y, 'adjacent':0, 'covered':True, 'flag':False, 'mine':True}

	tiles_left = size*size - num_mines	
	determine_adjacent(board, size)	

def make_move(x, y, board, size, flag=False):

	global tiles_left
	# tracks changes made as a result of move made
	changes = []

	# Deal with right click (flag)
	if flag and len(board) == 0:
		return changes

	elif flag and board[x][y]['covered']:
		board[x][y]['flag'] = True
		changes.append((x, y, get_symbol(board[x][y])))
		return changes

	elif flag and not board[x][y]['covered']:
		return changes

	# Build board if first move
	if len(board) == 0:
		build_board(x, y, board, size)	

	# Reveal tile clicked on
	board[x][y]['covered'] = False
	tiles_left-=1

	# If tile clicked on is a 0 we need to reveal adjacent tiles
	# that are not mines and repeat process if adjacent tiles are also 0
	empty_tiles = []
	if board[x][y]['adjacent'] == 0:
		empty_tiles.append(board[x][y])

	while len(empty_tiles) != 0:

		current_x = empty_tiles[0]['x']
		current_y = empty_tiles[0]['y']

		# Check adjacent tiles in each direction
		for i in range(DIRECTIONS):
		

			new_x = current_x + DELTA_X[i]
			new_y = current_y + DELTA_Y[i]


			try:
				# Reveal non-mine adjacent tiles
				if not board[new_x][new_y]['mine']:

					# Add 0 tiles to empty tile list that haven't been revealed
					if board[new_x][new_y]['adjacent'] == 0 and board[new_x][new_y]['covered']:
						empty_tiles.append(board[new_x][new_y])

					board[new_x][new_y]['covered'] = False
					tiles_left-=1
					changes.append((new_x, new_y, get_symbol(board[new_x][new_y])))

			except:
				continue

		del empty_tiles[0]

	return (changes, get_state(x, y, board, size))
