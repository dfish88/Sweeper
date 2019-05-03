#include <stdio.h>
#include <stdbool.h> 
#include <string.h>
#include <stdlib.h>

#include "board.h"

// tile on the board
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


game_board* create_game_board(int size)
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

int main()
{
	/*dimension = 4;
	board = malloc(dimension * sizeof(tile *));

	int x,y;
	for (x = 0; x < dimension; x++)
	{
		board[x] = malloc(dimension * sizeof(tile));;
	}
	

	board[0][0] = (tile) {'x', false, false, 2};
	board[0][1] = (tile) {'f', false, false, 4}; 

	printf("Tile type: %c\n", board[0][0].type);
	printf("Adjacent to: %d\n", board[0][0].adjacent);

	printf("Tile type: %c\n", board[0][1].type);
	printf("Adjacent to: %d\n", board[0][1].adjacent);
	free_board();
	*/
}
