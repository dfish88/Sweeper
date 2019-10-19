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
	game* g = create_game(6);
	graphics* r = create_graphics(6);
	point* changes;
	int x, y;

	SDL_Event e;	
	while (get_state(g) != STATE_QUIT)
	{
		while(SDL_PollEvent(&e) != 0)
		{
			if(e.type == SDL_QUIT)
			{
				set_state(g, STATE_QUIT);
				break;
			}


			// Mouse button clicked down but not released
			if(e.type == SDL_MOUSEBUTTONDOWN && e.button.button == SDL_BUTTON_LEFT && get_state(g) == STATE_RUNNING)
			{
				render_face_on_click(r);
			}

			// Left click when game is running
			if(e.type == SDL_MOUSEBUTTONUP && e.button.button == SDL_BUTTON_LEFT && get_state(g) == STATE_RUNNING)
			{
				//Get mouse position
				SDL_GetMouseState( &x, &y );
				printf("Clicked on (%d, %d)\n", y/IMAGE_SIZE, x/IMAGE_SIZE);

				// First row clicked, ignore
				if (y/IMAGE_SIZE == 0)
				{
					printf("First row, skipping\n");
					break;
				}
	
				if ( (y/IMAGE_SIZE) >= 1)
					changes = make_move(g, (y/IMAGE_SIZE) - 1, x/IMAGE_SIZE, false);

				if (get_state(g) == STATE_RUNNING)
					render_game_running(r, changes); 
				else if (get_state(g) == STATE_LOST)
					render_game_lost(r, changes);
				else if (get_state(g) == STATE_WON)
				{
					render_game_won(r, changes);
					printf("You Won!\n");
				}

				changes = NULL;
			}
			
			// Righ click when game is running
			if(e.type == SDL_MOUSEBUTTONUP && e.button.button == SDL_BUTTON_RIGHT && get_state(g) == STATE_RUNNING)
			{
				//Get mouse position
				SDL_GetMouseState( &x, &y );
				printf("Clicked on (%d, %d)\n", y/IMAGE_SIZE, x/IMAGE_SIZE);

				if ( (y/IMAGE_SIZE) >= 1)
					changes = make_move(g, (y/IMAGE_SIZE) - 1, x/IMAGE_SIZE, true);
				render_game_running(r, changes);

				changes = NULL;
			}
		}
	}

	destroy_graphics(r);
	destroy_game(g, 6);
	return 0;
}

