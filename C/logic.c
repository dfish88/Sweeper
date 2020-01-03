#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>

#include "types.h"
#include "logic.h"

/********************
*     CONSTANTS
********************/ 
const int DIRECTIONS = 8;
const int delta_x[] = {-1,-1,0,1,1,1,0,-1};
const int delta_y[] = {0,1,1,1,0,-1,-1,-1};

/********************
*      HELPERS
********************/ 
int dimension;
int tiles_left;
tile **game_board;
int state;
bool first_move;


/*
* Used to add to linked lists which are used to track
* changes and reveal tiles.
*/ 
point* add(point* tail, int x, int y, char c)
{
	point* new = malloc(sizeof(point));
	new->x = x;
	new->y = y;
	new->c = c;
	new->next = NULL;
	tail->next = new;
	return new;
}

bool check_win()
{
	if (tiles_left <= 0)
		return true;
	else
		return false;
}

void reveal(int x, int y)
{
	if (!game_board[x][y].revealed)
	{
		game_board[x][y].revealed = true;
		tiles_left--;
		printf("Revealed tile (%d,%d), %d left\n", x, y, tiles_left);
	}
}


/********************
*	GETTERS
********************/ 
char get_type(int x, int y)
{
	if (!game_board[x][y].revealed)
	{
		if(game_board[x][y].flag)
			return 'f';
		else
			return 'x';
	}

	if (game_board[x][y].mine)
		return 'm';
	else
		return game_board[x][y].adjacent + '0';
}

char get_type_revealed(int x, int y)
{
	if (game_board[x][y].mine)
		return 'm';
	else
		return game_board[x][y].adjacent + '0';
}


bool get_mine(int x, int y)
{
	return game_board[x][y].mine;
}

bool get_flag(int x, int y)
{
	return game_board[x][y].flag;
}

bool get_revealed(int x, int y)
{
	return game_board[x][y].revealed;
}

unsigned int get_adjacent(int x, int y)
{
	return game_board[x][y].adjacent;
}

int get_state()
{
	return state;
}

/********************
*	SETTERS
********************/ 
void add_tile(int x, int y)
{
	game_board[x][y].mine = false;
	game_board[x][y].flag = false;
	game_board[x][y].adjacent = 0;
	game_board[x][y].revealed = false;
}

void set_mine(int x, int y)
{
	game_board[x][y].mine = true;
}

void set_flag(int x, int y)
{
	game_board[x][y].flag = !(game_board[x][y].flag);
}

void set_revealed(int x, int y)
{
	game_board[x][y].revealed = true;
}

void set_adjacent(int x, int y, int a)
{
	game_board[x][y].adjacent = a;
}

void set_state(int s)
{
	if (s > STATE_QUIT)
		return;
	state = s;
}

/******************************
*       BUILD BOARD
******************************/ 
bool check_for_mine(int x, int y)
{
	if (x >= dimension || y >= dimension)
		return false;
	else if (x < 0 || y < 0)
		return false;
	else if (game_board[x][y].mine)
		return true;
	else
		return false;
}

int count_mines(int x, int y)
{
	int num_mines = 0;
	int d, new_x, new_y;
	
	for (d = 0; d < DIRECTIONS; d++)
	{
		new_x = x + delta_x[d];
		new_y = y + delta_y[d];

		if (check_for_mine(new_x, new_y))
			num_mines++;
	}

	return num_mines;
}

void add_mines(int x, int y)
{
	// Maximum number of mines
	int limit = (int)((dimension * dimension)/6);
	int num_mines;
	int placed = 0;
	srand(time(0));

	// Randomly place mines
	for (num_mines = 0; num_mines < limit; num_mines++)
	{
		int rand_x = rand() % dimension;
		int rand_y = rand() % dimension;

		// Don't place mine on first tile clicked
		if (x == rand_x && y == rand_y)
			continue;

		// Don't add mine where there is already a mine
		if (game_board[rand_x][rand_y].mine)
			continue;

		game_board[rand_x][rand_y].mine = true;
		game_board[rand_x][rand_y].flag = false;
		game_board[rand_x][rand_y].revealed = false;
		game_board[rand_x][rand_y].adjacent = 0;
		placed++;
		printf("Placed mine at (%d,%d)\n", rand_x, rand_y);
	}

	tiles_left = (dimension * dimension) - placed;
	printf("Added mines, need to reveal %d tiles to win\n", tiles_left);

	// Place all adjacent tiles
	int r, c;
	for (r = 0; r < dimension; r++)
	{
		for (c = 0; c < dimension; c++)
		{
			// Skip mines that have been added
			if (!(game_board[r][c].mine))
			{
				game_board[r][c].flag = false;
				game_board[r][c].revealed = false;

				// Find how many mines tile is adjacent to
				game_board[r][c].adjacent = count_mines(r, c);
			}
		}
	}
}

/******************************
*       REVEALING TILES
******************************/ 

/*
* Returns true if (x,y) is on game_board and
* tile at (x,y) isn't revealed
*/
bool in_bounds(int x, int y)
{
	if (x >= dimension || y >= dimension)
		return false;
	else if (x < 0 || y < 0)
		return false;
	else if (game_board[x][y].revealed)
		return false;
	else
		return true;
}

/*
* Reveals tile at x y and adds to linked list that tracks changes. 
* If tile is a 0 then we add all adjacent tiles to list and reveal them.
* If any of those are 0 tiles we do the same.
* Returns the linked list of changes so graphics can be updated.
*/
point* reveal_tile(int x, int y)
{
	reveal(x, y);
	point* tail = malloc(sizeof(point));
	tail->x = x;
	tail->y = y;
	tail->c = get_type(x, y);
	tail->next = NULL;

	point* current = tail;
	point* head = tail;

	int dir, new_x, new_y;

	while (current != NULL)
	{
		// Add all adjacent tiles to list if tile is a 0
		if (game_board[current->x][current->y].adjacent == 0)
		{
			for(dir = 0; dir < DIRECTIONS; dir++)
			{
				new_x = current->x + delta_x[dir];
				new_y = current->y + delta_y[dir];

				if (in_bounds( new_x, new_y)) 
				{
					reveal(new_x, new_y);
					tail = add(tail, new_x, new_y, get_type(new_x, new_y));
				}
			}
		}
		current = current->next;
	}
	return head;
}

/*
* Called when player clicks on mine, reveal mines and check flags.
*/
point* reveal_mines(int x, int y)
{
	point* tail = malloc(sizeof(point));
	tail->x = x;
	tail->y = y;
	tail->c = 'b';
	tail->next = NULL;
	point* head = tail;

	int i, j;
	for (i = 0; i < dimension; i++)
	{
		for (j = 0; j < dimension; j++)
		{
			// Mines that are not revealed and are not flags
			if (game_board[i][j].mine && !game_board[i][j].revealed && !game_board[i][j].flag)
			{
				game_board[i][j].revealed = true;
				tail = add(tail, i, j, 'm');
			}
			// Flags that are not on mines
			else if (!game_board[i][j].mine && game_board[i][j].flag)
			{
				game_board[i][j].revealed = true;
				tail = add(tail, i, j, 'w');
			}
		}	
	}
	return head;
}

/******************************
*       PLAYING GAME
******************************/ 
point* make_move(game* g, int x, int y, bool flag)
{
	// Build game_board on first click
	if (first_move)
	{
		add_mines(x, y);
		first_move = false;
	}

	// Flag tile or remove flag from tile
	if (flag)
	{
		if (!game_board[x][y].revealed)
		{
			if (!game_board[x][y].flag)
			{
				game_board[x][y].flag = true;
				point* tmp = malloc(sizeof(point));
				tmp->x = x;
				tmp->y = y;
				tmp->c = 'f';
				tmp->next = NULL;
				return tmp;
			}
			else
			{
				game_board[x][y].flag = false;
				point* tmp = malloc(sizeof(point));
				tmp->x = x;
				tmp->y = y;
				tmp->c = ' ';
				tmp->next = NULL;
				return tmp;
			}
			
		}
	}

	if (game_board[x][y].revealed)
		return NULL;

	// GAME OVER!
	if (game_board[x][y].mine)
	{
		game_board[x][y].revealed = true;
		state = STATE_LOST;
		return reveal_mines(x, y);
	}

	point* head = reveal_tile(x, y);

	if (check_win())
	{
		state = STATE_WON;
	}

	return head;
}

void restart_game()
{
	// Re-build the game_board
	destroy_game(dimension);
	create_game(dimension);
}
