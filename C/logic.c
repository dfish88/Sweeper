#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include "game.h"

/********************
*     CONSTANTS
********************/ 
#define DIRECTIONS 8
const int delta_x[DIRECTIONS] = {-1,-1,0,1,1,1,0,-1};
const int delta_y[DIRECTIONS] = {0,1,1,1,0,-1,-1,-1};

/********************
*      HELPERS
********************/ 

/*
* Used to add to linked lists which are used to track
* changes and reveal tiles.
*/ 
point* add(point* tail, int x, int y)
{
	point* new = malloc(sizeof(point));
	new->x = x;
	new->y = y;
	new->next = NULL;
	tail->next = new;
	return new;
}

/********************
*	TYPES
********************/ 
typedef struct
{
	bool mine;
	bool flag;
	bool revealed;
	unsigned int adjacent;
} tile;

struct game
{
	int dimension;
	tile **board;
	bool done;
};

/********************
*	GETTERS
********************/ 
char get_type(game* gb, int x, int y)
{
	if (!gb->board[x][y].revealed)
	{
		if(gb->board[x][y].flag)
			return 'f';
		else
			return 'x';
	}

	if (gb->board[x][y].mine)
		return 'm';
	else
		return gb->board[x][y].adjacent + '0';
}

char get_type_revealed(game* gb, int x, int y)
{
	if (gb->board[x][y].mine)
		return 'm';
	else
		return gb->board[x][y].adjacent + '0';
}


bool get_mine(game* gb, int x, int y)
{
	return gb->board[x][y].mine;
}

bool get_flag(game* gb, int x, int y)
{
	return gb->board[x][y].flag;
}

bool get_revealed(game* gb, int x, int y)
{
	return gb->board[x][y].revealed;
}

unsigned int get_adjacent(game* gb, int x, int y)
{
	return gb->board[x][y].adjacent;
}

bool game_over(game* gb)
{
	return gb->done;
}

/********************
*	SETTERS
********************/ 
void add_tile(game* gb, int x, int y)
{
	gb->board[x][y].mine = false;
	gb->board[x][y].flag = false;
	gb->board[x][y].adjacent = 0;
	gb->board[x][y].revealed = false;
}

void set_mine(game* gb, int x, int y)
{
	gb->board[x][y].mine = true;
}

void set_flag(game* gb, int x, int y)
{
	gb->board[x][y].flag = !(gb->board[x][y].flag);
}

void set_revealed(game* gb, int x, int y)
{
	gb->board[x][y].revealed = true;
}

void set_adjacent(game* gb, int x, int y, int a)
{
	gb->board[x][y].adjacent = a;
}

/******************************
*    CONSTRUCTORS/DESTRUCTORS
******************************/ 
game* create_board(int size)
{
	game* gb = malloc(sizeof(game));
	gb->dimension = size;

	/*
	int x, y;
	gb->board = malloc(gb->dimension * sizeof(tile *));
	for (x = 0; x < gb->dimension; x++)
	{
		gb->board[x] = malloc(gb->dimension * sizeof(tile));
		for (y = 0; y < gb->dimension; y++)
		{
			gb->board[x][y].mine = false;
			gb->board[x][y].flag = false;
			gb->board[x][y].revealed = false;
			gb->board[x][y].adjacent = 0;
		}
	}

	gb->changes = malloc(gb->dimension * gb->dimension * sizeof(point));
	gb->change_head = 0;

	return gb;
	*/
	
	int x;
	gb->board = malloc(gb->dimension * sizeof(tile *));
	for (x = 0; x < gb->dimension; x++)
	{
		gb->board[x] = calloc(gb->dimension,  sizeof(tile));
	}

	gb->done = false;

	srand(time(0));

	return gb;
}

void destroy_board(game* gb, int size) 
{
	int x;
	for (x = 0; x < size; x++)
	{
		free(gb->board[x]);
	}
	free(gb->board);
	free(gb);
}

/******************************
*       BUILD BOARD
******************************/ 
bool check_for_mine(game* gb, int x, int y)
{
	if (x >= gb->dimension || y >= gb->dimension)
		return false;
	else if (x < 0 || y < 0)
		return false;
	else if (gb->board[x][y].mine)
		return true;
	else
		return false;
}

int count_mines(game* gb, int x, int y)
{
	int num_mines = 0;
	// NORTH
	if (check_for_mine(gb, x - 1, y))
		num_mines++;

	// NORTH EAST
	if (check_for_mine(gb, x - 1, y + 1))
		num_mines++;

	// EAST
	if (check_for_mine(gb, x, y + 1))
		num_mines++;
	
	// SOUTH EAST
	if (check_for_mine(gb, x + 1, y + 1))
		num_mines++;

	// SOUTH
	if (check_for_mine(gb, x + 1, y))
		num_mines++;

	// SOUTH WEST
	if (check_for_mine(gb, x + 1, y - 1))
		num_mines++;

	// WEST
	if (check_for_mine(gb, x, y - 1))
		num_mines++;

	// NORTH WEST
	if (check_for_mine(gb, x - 1, y - 1))
		num_mines++;


	return num_mines;
}

void add_mines(game* gb, int x, int y)
{
	// Maximum number of mines
	int limit = (int)((gb->dimension * gb->dimension)/6);
	int num_mines;

	// Randomly place mines
	for (num_mines = 0; num_mines < limit; num_mines++)
	{
		int rand_x = rand() % gb->dimension;
		int rand_y = rand() % gb->dimension;

		// Don't place mine on first tile clicked
		if (x == rand_x && y == rand_y)
			continue;

		gb->board[rand_x][rand_y].mine = true;
		gb->board[rand_x][rand_y].flag = false;
		gb->board[rand_x][rand_y].revealed = false;
		gb->board[rand_x][rand_y].adjacent = 0;
	}

	// Place all adjacent tiles
	int r, c;
	for (r = 0; r < gb->dimension; r++)
	{
		for (c = 0; c < gb->dimension; c++)
		{
			// Skip mines that have been added
			if (!(gb->board[r][c].mine))
			{
				int a = 0;
				gb->board[r][c].flag = false;
				gb->board[r][c].revealed = false;

				// Find how many mines tile is adjacent to
				gb->board[r][c].adjacent = count_mines(gb, r, c);
			}
		}
	}
}

/******************************
*       REVEALING TILES
******************************/ 


/*
* Returns true if (x,y) is on board and
* tile at (x,y) isn't revealed
*/
bool in_bounds(game* gb, int x, int y)
{
	if (x >= gb->dimension || y >= gb->dimension)
		return false;
	else if (x < 0 || y < 0)
		return false;
	else if (gb->board[x][y].revealed)
		return false;
	else
		return true;
}

point* reveal_all_adjacent(game* gb, int x, int y)
{
	point* tail = malloc(sizeof(point));
	tail->x = x;
	tail->y = y;
	tail->next = NULL;

	point* current = tail;
	point* head = tail;

	int dir, new_x, new_y;

	while (current != NULL)
	{
		// Reveal all adjacent tiles and add 0 tiles to list
		if (gb->board[current->x][current->y].adjacent == 0)
		{
			for(dir = 0; dir < DIRECTIONS; dir++)
			{
				new_x = current->x + delta_x[dir];
				new_y = current->y + delta_y[dir];

				if (in_bounds(gb, new_x, new_y)) 
				{

					gb->board[new_x][new_y].revealed = true;

					if(gb->board[new_x][new_y].adjacent == 0)
						tail = add(tail, new_x, new_y);
				}
			}
		}
		gb->board[current->x][current->y].revealed = true;
		current = current->next;
	}
	return head;
}

point* reveal_tile(game* gb, int x, int y)
{
	// GAME OVER!
	if (gb->board[x][y].mine)
	{
		gb->done = true;
		gb->board[x][y].revealed = true;
		return NULL;
	}

	return reveal_all_adjacent(gb, x, y);
}

/******************************
*       PRINTING BOARD
******************************/ 
void print_board(game* gb)
{
	int x, y;

	for (x = 0; x < gb->dimension; x++)
	{
		for (y = 0; y < gb->dimension; y++)
		{
			printf(" %c", get_type(gb, x, y));
		}
		printf("\n");
	}
}

void print_board_revealed(game* gb)
{
	int x, y;

	for (x = 0; x < gb->dimension; x++)
	{
		for (y = 0; y < gb->dimension; y++)
		{
			printf(" %c", get_type_revealed(gb, x, y));
		}
		printf("\n");
	}
}
