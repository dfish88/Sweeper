#include <stdio.h>
#include <stdlib.h>

#include "logic.h"
#include "render.h"

const int STATE_RUNNING = 0;
const int STATE_WON = 1;
const int STATE_LOST = 2;
const int STATE_QUIT = 3; 

int main()
{
	game* g = create_game(8);
	add_mines(g, 0, 0);
	printf("\n");
	
	make_window();	

	while (get_input() == STATE_RUNNING)
	{
	}

	if (get_state(g) == STATE_LOST)
		printf("GAME OVER, YOU BLEW UP!\n");
	else if (get_state(g) == STATE_WON)
		printf("YOU WON!\n");
	else if (get_state(g) == STATE_QUIT)
		printf("BYE BYE!\n");

	destroy_render();
	destroy_game(g, 4);
	return 0;
}

