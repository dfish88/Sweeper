#include <stdio.h>
#include <stdbool.h> 
#include <string.h>
#include <stdlib.h>

// tile on the board
typedef struct
{
	char type;
	bool mine;
	bool flag;
	unsigned int adjacent;
} tile;

int dimension;
tile **board;

void free_board()
{
	int x;
	for (x = 0; x < dimension; x++)
	{
		free(board[x]);
	}
	free(board);
}

int main()
{
	dimension = 4;
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
}
