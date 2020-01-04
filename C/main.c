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

void create_game(game* g, int size)
{
	g->dimension = size;
	
	int x;
	g->game_board = malloc(g->dimension * sizeof(tile *));
	for (x = 0; x < g->dimension; x++)
	{
		g->game_board[x] = calloc(g->dimension,  sizeof(tile));
	}

	g->state = STATE_RUNNING;

	g->first_move = true;
}

void destroy_game(game** g) 
{
	int x;
	for (x = 0; x < (*g)->dimension; x++)
	{
		free((*g)->game_board[x]);
	}
	free((*g)->game_board);
	free((*g));
}

int main()
{
	game* g = malloc(sizeof(game));
	create_game(g, 6);
	create_graphics(6);
	point* changes;
	int x, y;

	SDL_Event e;	
	while (g->state != STATE_QUIT)
	{
		while(SDL_PollEvent(&e) != 0)
		{
			if(e.type == SDL_QUIT)
			{
				g->state = STATE_QUIT;
				break;
			}


			// Mouse button clicked down but not released
			if(e.type == SDL_MOUSEBUTTONDOWN && e.button.button == SDL_BUTTON_LEFT && g->state == STATE_RUNNING)
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
			if(e.type == SDL_MOUSEBUTTONUP && e.button.button == SDL_BUTTON_LEFT && (g->state == STATE_WON || g->state == STATE_LOST))
			{
				//Get mouse position
				SDL_GetMouseState( &x, &y );

				// Restart button clicked
				if (y/IMAGE_SIZE == 0 && (x/IMAGE_SIZE == 3 || x/IMAGE_SIZE == 4))
				{
					printf("Restart Button Clicked!!\n");
					//restart_game();
					//render_game_restart();
					break;
				}
			}

			// Left click when game is running
			if(e.type == SDL_MOUSEBUTTONUP && e.button.button == SDL_BUTTON_LEFT && g->state == STATE_RUNNING)
			{
				//Get mouse position
				SDL_GetMouseState( &x, &y );
				printf("Clicked on (%d, %d)\n", y/IMAGE_SIZE, x/IMAGE_SIZE);

				// Restart button clicked
				if (y/IMAGE_SIZE == 0 && (x/IMAGE_SIZE == 3 || x/IMAGE_SIZE == 4))
				{
					printf("Restart Button Clicked!!\n");
					//restart_game();
					//render_game_restart();
					break;
				}
				// First row clicked, ignore
				else if (y/IMAGE_SIZE == 0)
				{
					printf("First row, skipping\n");
					break;
				}
	
				if ( (y/IMAGE_SIZE) >= 1)
					changes = make_move(g, (y/IMAGE_SIZE) - 1, x/IMAGE_SIZE, false);

				if (g->state == STATE_RUNNING)
					render_game_running(changes); 
				else if (g->state == STATE_LOST) 
					render_game_lost(changes);
				else if (g->state == STATE_WON)
				{
					render_game_won(changes);
					printf("You Won!\n");
				}

				changes = NULL;
			}
			
			// Righ click when game is running
			if(e.type == SDL_MOUSEBUTTONUP && e.button.button == SDL_BUTTON_RIGHT && g->state == STATE_RUNNING)
			{
				//Get mouse position
				SDL_GetMouseState( &x, &y );
				printf("Clicked on (%d, %d)\n", y/IMAGE_SIZE, x/IMAGE_SIZE);

				if ( (y/IMAGE_SIZE) >= 1)
					changes = make_move(g, (y/IMAGE_SIZE) - 1, x/IMAGE_SIZE, true);
				render_game_running(changes);

				changes = NULL;
			}
		}
	}

	destroy_graphics();
	//destroy_game(&g);
	return 0;
}

