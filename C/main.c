#include <stdio.h>
#include <stdlib.h>

#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>

#include "logic.h"
#include "graphics.h"

const int STATE_RUNNING = 0;
const int STATE_WON = 1;
const int STATE_LOST = 2;
const int STATE_QUIT = 3; 
const int IMAGE_SIZE = 50;

int main()
{
	game* g = create_game(8);
	add_mines(g, 0, 0);
	graphics* r = create_graphics(8);
	point* changes;

	SDL_Event e;	
	while (get_state(g) != STATE_QUIT)
	{
		while(SDL_PollEvent(&e) != 0)
		{
			if(e.type == SDL_MOUSEBUTTONUP)
			{
				//Get mouse position
				int x, y;
				SDL_GetMouseState( &x, &y );
				
				printf("Clicked on (%d, %d)\n", y/IMAGE_SIZE, x/IMAGE_SIZE);
				if (get_state(g) == STATE_RUNNING)
					changes = make_move(g, y/IMAGE_SIZE, x/IMAGE_SIZE, false);

				if (get_state(g) == STATE_RUNNING)
					render_game_running(r, changes); 
				else if (get_state(g) == STATE_LOST)
					render_game_lost(r, changes);

				changes = NULL;
			}

			if(e.type == SDL_QUIT)
			{
				set_state(g, STATE_QUIT);
				break;
			}
		}
	}

	destroy_graphics(r);
	destroy_game(g, 4);
	return 0;
}

