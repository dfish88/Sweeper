#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include "board.h"
/********************
*     CONSTANTS
********************/ 

/********************
*      HELPERS
********************/ 
void add(point* head, int x, int y, point* next)
{
	point* new = malloc(sizeof(point));
	new->x = x;
	new->y = y;
	new->next = next;
	head->next = new;
	head = new;
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

struct game_board
{
	int dimension;
	tile **board;
	point* changes;
	point* change_head;
	point* change_start;
	bool done;
};

/********************
*	GETTERS
********************/ 
char get_type(game_board* gb, int x, int y)
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

char get_type_revealed(game_board* gb, int x, int y)
{
	if (gb->board[x][y].mine)
		return 'm';
	else
		return gb->board[x][y].adjacent + '0';
}


bool get_mine(game_board* gb, int x, int y)
{
	return gb->board[x][y].mine;
}

bool get_flag(game_board* gb, int x, int y)
{
	return gb->board[x][y].flag;
}

bool get_revealed(game_board* gb, int x, int y)
{
	return gb->board[x][y].revealed;
}

unsigned int get_adjacent(game_board* gb, int x, int y)
{
	return gb->board[x][y].adjacent;
}

point* get_change(game_board* gb)
{
	return gb->changes;
}

bool no_changes(game_board* gb)
{
	return true;
}

/********************
*	SETTERS
********************/ 
void add_tile(game_board* gb, int x, int y)
{
	gb->board[x][y].mine = false;
	gb->board[x][y].flag = false;
	gb->board[x][y].adjacent = 0;
	gb->board[x][y].revealed = false;
}

void set_mine(game_board* gb, int x, int y)
{
	gb->board[x][y].mine = true;
}

void set_flag(game_board* gb, int x, int y)
{
	gb->board[x][y].flag = !(gb->board[x][y].flag);
}

void set_revealed(game_board* gb, int x, int y)
{
	gb->board[x][y].revealed = true;
}

void set_adjacent(game_board* gb, int x, int y, int a)
{
	gb->board[x][y].adjacent = a;
}

/******************************
*    CONSTRUCTORS/DESTRUCTORS
******************************/ 
game_board* create_board(int size)
{
	game_board* gb = malloc(sizeof(game_board));
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

	gb->changes = malloc(gb->dimension * gb->dimension * sizeof(point));
	gb->change_head = NULL;
	gb->change_start = NULL;
	gb->done = false;

	srand(time(0));

	return gb;
}

void destroy_board(game_board* gb, int size) 
{
	int x;
	for (x = 0; x < size; x++)
	{
		free(gb->board[x]);
	}
	free(gb->board);
	free(gb->changes);
	free(gb);
}

/******************************
*       BUILD BOARD
******************************/ 
bool check_for_mine(game_board* gb, int x, int y)
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

int count_mines(game_board* gb, int x, int y)
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

void add_mines(game_board* gb, int x, int y)
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
bool in_bounds(game_board* gb, int x, int y)
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

void reveal_all_adjacent(game_board* gb, int x, int y)
{
	point* head = malloc(sizeof(point));
	head->x = x;
	head->y = y;
	head->next = NULL;

	point* current = head;

	while (current != NULL)
	{
		printf("Current tile: (%d, %d)\n", current->x, current->y);

		// Reveal all adjacent tiles and add 0 tiles to list
		if (gb->board[current->x][current->y].adjacent == 0)
		{
			// NORTH
			if (in_bounds(gb, current->x - 1, current->y))
			{
			}

			// NORTH EAST
			if (in_bounds(gb, current->x - 1, current->y + 1))
			{
			}

			// EAST
			if (in_bounds(gb, current->x, current->y + 1))
			{
				gb->board[current->x][current->y+1].revealed = true;
				if (gb->board[current->x][current->y+1].adjacent == 0)
					add(head, current->x, current->y+1, NULL);
			}
			
			// SOUTH EAST
			if (in_bounds(gb, current->x + 1, current->y + 1))
			{
			}

			// SOUTH
			if (in_bounds(gb, current->x + 1, current->y))
			{
			}

			// SOUTH WEST
			if (in_bounds(gb, current->x + 1, current->y - 1))
			{
			}

			// WEST
			if (in_bounds(gb, current->x, current->y - 1))
			{
			}

			// NORTH WEST
			if (in_bounds(gb, current->x - 1, current->y - 1))
			{
			}

		}
		gb->board[current->x][current->y].revealed = true;
		current = current->next;
	}
}

void reveal_tile(game_board* gb, int x, int y)
{
	// GAME OVER!
	if (gb->board[x][y].mine)
	{
		gb->done = true;
		return;
	}

	reveal_all_adjacent(gb, x, y);
}

/******************************
*       PRINTING BOARD
******************************/ 
void print_board(game_board* gb)
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

void print_board_revealed(game_board* gb)
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

