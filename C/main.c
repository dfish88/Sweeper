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

void destroy_graphics()
{

	int i;
	for(i = 0; i < dimension; i++)
		SDL_DestroyTexture(adjacent[i]);
	free(adjacent);

	SDL_DestroyTexture(covered);
	SDL_DestroyTexture(flag);
	SDL_DestroyTexture(mine);
	SDL_DestroyTexture(boom);
	SDL_DestroyTexture(wrong);
	SDL_DestroyTexture(dead);
	SDL_DestroyTexture(glasses);
	SDL_DestroyTexture(happy);
	SDL_DestroyTexture(surprise);
	SDL_DestroyTexture(empty);
	SDL_DestroyTexture(hint);
	SDL_DestroyTexture(restart);

	//Destroy window
	SDL_DestroyWindow( window );

	for(i = 0; i < dimension; i++)
		free(board[i]);
	free(board);

	free(top_panel);

	SDL_DestroyRenderer(rend);


	// Stop timer thread
	timer_state = -1;
	pthread_join(*(timer_thread), NULL);
	free(timer_thread);

	//Quit SDL subsystems
	SDL_Quit();
}

void create_graphics(int d)
{
	printf("Creating graphics\n");
	dimension = d;

	adjacent = malloc(ADJACENT * sizeof(SDL_Texture*));

	int x;
	board = malloc(dimension * sizeof(SDL_Rect*));
	for (x = 0; x < dimension; x++)
		board[x] = malloc(dimension * sizeof(SDL_Rect));

	top_panel = malloc(TOP_PANEL_BUTTONS * sizeof(SDL_Rect));

	if (SDL_Init(SDL_INIT_VIDEO) != 0) 
	{
		return NULL;
	}

	if (!(IMG_Init(IMG_INIT_PNG) & IMG_INIT_PNG))
	{
		return NULL;
	}

	if (TTF_Init() == -1)
	{
		return NULL;
	}

	printf("Created board\n");
	
	create_window();
	printf("Created window\n");
	load_images();
	printf("Created images\n");
	
	// Hint button
	top_panel[0].x = 0;
	top_panel[0].y = 0;
	top_panel[0].w = 2 * IMAGE_SIZE;
	top_panel[0].h = IMAGE_SIZE;
	SDL_RenderSetViewport(rend, &top_panel[0]);
	SDL_RenderCopy(rend, hint, 0, 0);

	// Face
	top_panel[1].x = 2 * IMAGE_SIZE;
	top_panel[1].y = 0;
	top_panel[1].w = IMAGE_SIZE;
	top_panel[1].h = IMAGE_SIZE;
	SDL_RenderSetViewport(rend, &top_panel[1]);
	SDL_RenderCopy(rend, happy, 0, 0);

	// Restart
	top_panel[2].x = 3 * IMAGE_SIZE;
	top_panel[2].y = 0;
	top_panel[2].w = 2 * IMAGE_SIZE;
	top_panel[2].h = IMAGE_SIZE;
	SDL_RenderSetViewport(rend, &top_panel[2]);
	SDL_RenderCopy(rend, restart, 0, 0);

	// Timer
	top_panel[3].x = 5 * IMAGE_SIZE;
	top_panel[3].y = 0;
	top_panel[3].w = IMAGE_SIZE;
	top_panel[3].h = IMAGE_SIZE;
	SDL_RenderSetViewport(rend, &top_panel[3]);

	printf("Created top panel\n");

	int i, j;
	for (i = 0; i < dimension; i++)	
	{
		for (j = 0; j < dimension; j++)
		{
			board[i][j].x = j * IMAGE_SIZE;
			board[i][j].y = (i * IMAGE_SIZE) + IMAGE_SIZE;
			board[i][j].w = IMAGE_SIZE;
			board[i][j].h = IMAGE_SIZE;

			SDL_RenderSetViewport(rend, &board[i][j]);
			SDL_RenderCopy(rend, covered, 0, 0);
		}
	}

	printf("Created each tile\n");

	// Create timer thread
	timer_state = 1;
	timer_thread = malloc(sizeof(pthread_t));
	pthread_create(timer_thread, NULL, &render_timer, (void *)&(timer_state));

	printf("Created timer thread\n");

	SDL_RenderPresent(rend);
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
					destroy_game(&g);
					g = malloc(sizeof(game));
					create_game(g, 6);
					render_game_restart();
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
					destroy_game(&g);
					g = malloc(sizeof(game));
					create_game(g, 6);
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
	destroy_game(&g);
	return 0;
}

