#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>

#include "types.h"

//Screen dimension constants
const int SCREEN_WIDTH = 400;
const int SCREEN_HEIGHT = 400;
const int NUM_IMAGES = 19;

SDL_Surface** images;;

SDL_Window* window = NULL;
SDL_Surface* screen = NULL;
SDL_Surface* image = NULL;

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

	images = malloc(NUM_IMAGES * sizeof(SDL_Surface*));
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

	images[0] = load_optimized("../icons/0.png");
	images[1] = load_optimized("../icons/1.png");
	images[2] = load_optimized("../icons/2.png");
	images[3] = load_optimized("../icons/3.png");
	images[4] = load_optimized("../icons/4.png");
	images[5] = load_optimized("../icons/5.png");
	images[6] = load_optimized("../icons/6.png");
	images[7] = load_optimized("../icons/7.png");
	images[8] = load_optimized("../icons/8.png");
}

int load_image()
{
	image = load_optimized("../icons/mine.png");
	return 0;
}

void destroy_render()
{
	free(images);

	SDL_FreeSurface(screen);
	screen = NULL;

	SDL_FreeSurface(image);
	image = NULL;

	//Destroy window
	SDL_DestroyWindow( window );
	window = NULL;

	//Quit SDL subsystems
	SDL_Quit();
}

void make_window()
{
	init_render();
	load_image();
	SDL_BlitSurface( images[0], NULL, screen, NULL );
	SDL_UpdateWindowSurface(window);
}

int get_input()
{
	SDL_Event e;	
	static int cur_img = 1;
	while(SDL_PollEvent(&e) != 0)
	{
		SDL_BlitSurface( images[cur_img], NULL, screen, NULL );
		SDL_UpdateWindowSurface(window);
		cur_img++;
		cur_img = cur_img % 9;
		
		if(e.type == SDL_QUIT)
		{
			printf("QUIT!\n");
			return STATE_QUIT;
		}
	}
	return STATE_RUNNING;
}

