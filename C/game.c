#include "board.h"
#include <stdio.h>

int main()
{
	game_board* gb = create_board(8);
	add_mines(gb, 0, 0);
	print_board_revealed(gb);
	printf("\n");
	print_board(gb);
	reveal_tile(gb, 0, 0);
	printf("\n");
	print_board(gb);
	destroy_board(gb, 4);
}

