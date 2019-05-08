#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>

#include "types.h"

//Screen dimension constants
const int SCREEN_WIDTH = 400;
const int SCREEN_HEIGHT = 400;

SDL_Window* window = NULL;
SDL_Surface* screen = NULL;
SDL_Surface* image = NULL;

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
	}
}

int load_image()
{

	

	if (!(IMG_Init(IMG_INIT_PNG) & IMG_INIT_PNG))
	{
		printf("Couldn't load image!\n");
		return -1;	
	}
	else
	{
		image = IMG_Load("../icons/mine.png");
	}
	return 0;
}

void destroy_render()
{
	SDL_FreeSurface(screen);
	screen = NULL;

	SDL_FreeSurface(image);
	image = NULL;

	//Destroy window
	SDL_DestroyWindow( window );
	window = NULL;

	//Quit SDL subsystems
	SDL_Quit();

	return 0;
}

void make_window()
{
	init_render();
	create_window();
	load_image();
	SDL_BlitSurface( image, NULL, screen, NULL );
	SDL_UpdateWindowSurface(window);
}

int get_input()
{
	SDL_Event e;	
	while(SDL_PollEvent(&e) != 0)
	{
		if(e.type == SDL_QUIT)
		{
			printf("QUIT!\n");
			return QUIT;
		}
	}
	return RUNNING;
}

