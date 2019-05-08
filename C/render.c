#include <SDL2/SDL.h>

#include "types.h"

//Screen dimension constants
const int SCREEN_WIDTH = 400;
const int SCREEN_HEIGHT = 400;

SDL_Window* window = NULL;
SDL_Surface* screen = NULL;

int init_render()
{

	if (SDL_Init(SDL_INIT_VIDEO) != 0) 
	{
		return EXIT_FAILURE;
	}
}

int create_window()
{
	//Create window
	window = SDL_CreateWindow( "SDL Tutorial", SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED, SCREEN_WIDTH, SCREEN_HEIGHT, SDL_WINDOW_SHOWN );
	if( window == NULL )
	{
		printf( "Window could not be created! SDL_Error: %s\n", SDL_GetError() );
		return EXIT_FAILURE;
	}
	else
	{
		//Get window surface
		screen = SDL_GetWindowSurface( window );

		//Fill the surface white
		SDL_FillRect( screen, NULL, SDL_MapRGB( screen->format, 0xFF, 0xFF, 0xFF ) );

		//Update the surface
		SDL_UpdateWindowSurface( window );
	}
}

void destroy_render()
{
	//Destroy window
	SDL_DestroyWindow( window );

	//Quit SDL subsystems
	SDL_Quit();

	return 0;
}
