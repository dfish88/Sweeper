#include <stdio.h>
#include <stdlib.h>

#include "logic.h"
#include "render.h"

int main()
{
	game* g = create_board(8);
	add_mines(g, 0, 0);
	printf("\n");
	
	make_window();	

	while (get_input() == RUNNING)
	{
	}

	if (get_state(g) == LOST)
		printf("GAME OVER, YOU BLEW UP!\n");
	else if (get_state(g) == WON)
		printf("YOU WON!\n");
	else if (get_state(g) == QUIT)
		printf("BYE BYE!\n");

	destroy_render();
	destroy_board(g, 4);
	return 0;
}

