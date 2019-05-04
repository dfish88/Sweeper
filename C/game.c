#include "board.h"
#include <stdio.h>

int main()
{
	game_board* gb = create_board(4);	
	add_tile(gb,0, 0);
	set_type(gb, 0, 0, 'c');
	printf("type: %c\n", get_type(gb, 0, 0));
	destroy_board(gb, 4);
}
