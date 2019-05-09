#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>

#include "types.h"

//Screen dimension constants
const int SCREEN_WIDTH = 400;
const int SCREEN_HEIGHT = 400;
const int ADJACENT = 9;

// Images used for game
SDL_Surface** adjacent;
SDL_Surface* flag;
SDL_Surface* mine;
SDL_Surface* covered;
SDL_Surface* boom;
SDL_Surface* wrong;
SDL_Surface* happy;
SDL_Surface* surprise;
SDL_Surface* dead;
SDL_Surface* glasses;

SDL_Window* window = NULL;
SDL_Surface* screen = NULL;

void load_images();
int create_window();

int init_render()
{
	if (SDL_Init(SDL_INIT_VIDEO) != 0) 
	{
		return EXIT_FAILURE;
	}

	if (!(IMG_Init(IMG_INIT_PNG) & IMG_INIT_PNG))
	{
		return EXIT_FAILURE;
	}

	adjacent = malloc(ADJACENT * sizeof(SDL_Surface*));
	create_window();
	load_images();

	return 0;
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
	return 0;
}

SDL_Surface* load_optimized(char* path)
{
	SDL_Surface* tmp = IMG_Load(path);
	//Convert surface to screen format
	SDL_Surface* opt = SDL_ConvertSurface( tmp, screen->format, 0 );
	SDL_FreeSurface(tmp);
	return opt;
}

void load_images()
{
	adjacent[0] = load_optimized("../icons/0.png");
	adjacent[1] = load_optimized("../icons/1.png");
	adjacent[2] = load_optimized("../icons/2.png");
	adjacent[3] = load_optimized("../icons/3.png");
	adjacent[4] = load_optimized("../icons/4.png");
	adjacent[5] = load_optimized("../icons/5.png");
	adjacent[6] = load_optimized("../icons/6.png");
	adjacent[7] = load_optimized("../icons/7.png");
	adjacent[8] = load_optimized("../icons/8.png");
}

void destroy_render()
{
	free(adjacent);

	SDL_FreeSurface(screen);
	screen = NULL;

	//Destroy window
	SDL_DestroyWindow( window );
	window = NULL;

	//Quit SDL subsystems
	SDL_Quit();
}

void make_window()
{
	init_render();
	SDL_BlitSurface( adjacent[0], NULL, screen, NULL );
	SDL_UpdateWindowSurface(window);
}

int get_input()
{
	SDL_Event e;	
	static int cur_img = 1;
	while(SDL_PollEvent(&e) != 0)
	{
		SDL_BlitSurface( adjacent[cur_img], NULL, screen, NULL );
		SDL_UpdateWindowSurface(window);
		cur_img++;
		cur_img = cur_img % ADJACENT;
		
		if(e.type == SDL_QUIT)
		{
			printf("QUIT!\n");
			return STATE_QUIT;
		}
	}
	return STATE_RUNNING;
}

