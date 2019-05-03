#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "board.h"

/********************
*	TYPES
********************/ 
typedef struct
{
	char type;
	bool mine;
	bool flag;
	unsigned int adjacent;
} tile;

struct game_board
{
	int dimension;
	tile **board;
};

/********************
*	GETTERS
********************/ 

char get_type(game_board* gb, int x, int y)
{
	return gb->board[x][y].type;
}


bool get_mine(game_board* gb, int x, int y)
{
	return gb->board[x][y].mine;
}

bool get_flag(game_board* gb, int x, int y)
{
	return gb->board[x][y].flag;
}

unsigned int get_adjacent(game_board* gb, int x, int y)
{
	return gb->board[x][y].adjacent;
}

/********************
*	SETTERS
********************/ 
void add_tile(game_board* gb, char t, bool m, bool f, unsigned int a, int x, int y)
{
	gb->board[x][y].type = t;
	gb->board[x][y].mine = m;
	gb->board[x][y].flag = f;
	gb->board[x][y].adjacent = a;
}

void set_type(game_board* gb, int x, int y, char t)
{
	gb->board[x][y].type = t;
}

void set_mine(game_board* gb, int x, int y)
{
	gb->board[x][y].mine = true;
}

void set_flag(game_board* gb, int x, int y)
{
	gb->board[x][y].flag = !(gb->board[x][y].flag);
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

	int x;
	gb->board = malloc(gb->dimension * sizeof(tile *));
	for (x = 0; x < gb->dimension; x++)
	{
		gb->board[x] = malloc(gb->dimension * sizeof(tile));
	}
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
	free(gb);
}
