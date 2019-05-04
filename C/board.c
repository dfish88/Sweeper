#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "board.h"

/********************
*     CONSTANTS
********************/ 

/********************
*	TYPES
********************/ 
typedef struct
{
	char type;
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
	int change_head;
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
	gb->change_head--;
	return (gb->changes + gb->change_head);
}

bool no_changes(game_board* gb)
{
	if(gb->change_head <= 0)
		return true;
	else
		return false;
}

/********************
*	SETTERS
********************/ 
void add_tile(game_board* gb, int x, int y)
{
	gb->board[x][y].type = ' ';
	gb->board[x][y].mine = false;
	gb->board[x][y].flag = false;
	gb->board[x][y].adjacent = 0;
	gb->board[x][y].revealed = false;
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

	int x;
	gb->board = malloc(gb->dimension * sizeof(tile *));
	for (x = 0; x < gb->dimension; x++)
	{
		gb->board[x] = malloc(gb->dimension * sizeof(tile));
	}

	gb->changes = malloc(gb->dimension * gb->dimension * sizeof(point));
	gb->change_head = 0;

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
*       REVEALING TILES
******************************/ 

void reveal_tile(game_board* gb, int x, int y)
{
}
