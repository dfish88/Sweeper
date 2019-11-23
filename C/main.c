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
	create_game(6);
	create_graphics(6);
	point* changes;
	int x, y;

	SDL_Event e;	
	while (get_state() != STATE_QUIT)
	{
		while(SDL_PollEvent(&e) != 0)
		{
			if(e.type == SDL_QUIT)
			{
				set_state(STATE_QUIT);
				break;
			}


			// Mouse button clicked down but not released
			if(e.type == SDL_MOUSEBUTTONDOWN && e.button.button == SDL_BUTTON_LEFT && get_state() == STATE_RUNNING)
			{
				//Get mouse position
				SDL_GetMouseState( &x, &y );

				// First row clicked, ignore
				if (y/IMAGE_SIZE == 0)
				{
					printf("First row, skipping\n");
					break;
				}

				render_face_on_click();
			}
			
			// Left click when game is over (restart button)
			if(e.type == SDL_MOUSEBUTTONUP && e.button.button == SDL_BUTTON_LEFT && (get_state() == STATE_WON || get_state() == STATE_LOST))
			{
				//Get mouse position
				SDL_GetMouseState( &x, &y );

				// Restart button clicked
				if (y/IMAGE_SIZE == 0 && (x/IMAGE_SIZE == 3 || x/IMAGE_SIZE == 4))
				{
					printf("Restart Button Clicked!!\n");
					restart_game();
					render_game_restart();
					break;
				}
			}

			// Left click when game is running
			if(e.type == SDL_MOUSEBUTTONUP && e.button.button == SDL_BUTTON_LEFT && get_state() == STATE_RUNNING)
			{
				//Get mouse position
				SDL_GetMouseState( &x, &y );
				printf("Clicked on (%d, %d)\n", y/IMAGE_SIZE, x/IMAGE_SIZE);

				// Restart button clicked
				if (y/IMAGE_SIZE == 0 && (x/IMAGE_SIZE == 3 || x/IMAGE_SIZE == 4))
				{
					printf("Restart Button Clicked!!\n");
					restart_game();
					render_game_restart();
					break;
				}
				// First row clicked, ignore
				else if (y/IMAGE_SIZE == 0)
				{
					printf("First row, skipping\n");
					break;
				}
	
				if ( (y/IMAGE_SIZE) >= 1)
					changes = make_move((y/IMAGE_SIZE) - 1, x/IMAGE_SIZE, false);

				if (get_state() == STATE_RUNNING)
					render_game_running(changes); 
				else if (get_state() == STATE_LOST)
					render_game_lost(changes);
				else if (get_state() == STATE_WON)
				{
					render_game_won(changes);
					printf("You Won!\n");
				}

				changes = NULL;
			}
			
			// Righ click when game is running
			if(e.type == SDL_MOUSEBUTTONUP && e.button.button == SDL_BUTTON_RIGHT && get_state() == STATE_RUNNING)
			{
				//Get mouse position
				SDL_GetMouseState( &x, &y );
				printf("Clicked on (%d, %d)\n", y/IMAGE_SIZE, x/IMAGE_SIZE);

				if ( (y/IMAGE_SIZE) >= 1)
					changes = make_move((y/IMAGE_SIZE) - 1, x/IMAGE_SIZE, true);
				render_game_running(changes);

				changes = NULL;
			}
		}
	}

	destroy_graphics();
	destroy_game(6);
	return 0;
}

