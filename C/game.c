#include "board.h"
#include <stdio.h>
#include <stdlib.h>

int main()
{
	game_board* gb = create_board(8);
	add_mines(gb, 0, 0);
	print_board_revealed(gb);
	printf("\n");
	print_board(gb);
	point* changes = reveal_tile(gb, 0, 0);
	printf("\n");
	print_board(gb);

	point* tmp;
	while (changes != NULL)
	{
		printf("change at (%d, %d)\n", changes->x, changes->y);
		tmp = changes->next;
		free(changes);
		changes = tmp;
	}


	//printf("\e[1;1H\e[2J");

	destroy_board(gb, 4);
}

