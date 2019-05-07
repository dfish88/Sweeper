#include <SDL2/SDL.h>

int init_render()
{
	if (SDL_Init(SDL_INIT_VIDEO) != 0) 
	{
		return EXIT_FAILURE;
	}
}
