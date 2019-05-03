#include "board.h"
#include <stdio.h>

int main()
{
	game_board* gb = create_board(4);	
	add_tile(gb, 'x', false, false, 4, 0, 0);
	printf("type: %c\n", get_type(gb, 0, 0));
	destroy_board(gb, 4);
}
