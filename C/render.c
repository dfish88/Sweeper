#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>

#include "types.h"
#include "render.h"

//Screen dimension constants
const int SCREEN_WIDTH = 400;
const int SCREEN_HEIGHT = 400;
const int IMAGE_SIZE = 50;
const int ADJACENT = 9;

struct renderer
{
	// Images used for game
	SDL_Texture** adjacent;
	SDL_Texture* flag;
	SDL_Texture* mine;
	SDL_Texture* covered;
	SDL_Texture* boom;
	SDL_Texture* wrong;
	SDL_Texture* happy;
	SDL_Texture* surprise;
	SDL_Texture* dead;
	SDL_Texture* glasses;

	SDL_Window* window;
	SDL_Renderer* rend;
	int dimension;

	SDL_Rect** board;
};

renderer* create_renderer(int d)
{
	renderer* r = malloc(sizeof(renderer));
	r->dimension = d;

	r->adjacent = malloc(ADJACENT * sizeof(SDL_Texture*));

	int x;
	r->board = malloc(r->dimension * sizeof(SDL_Rect*));
	for (x = 0; x < r->dimension; x++)
		r->board[x] = malloc(r->dimension * sizeof(SDL_Rect));

	return r;
}

void load_images();
int create_window();

int init_render(int d)
{
	if (SDL_Init(SDL_INIT_VIDEO) != 0) 
	{
		return EXIT_FAILURE;
	}

	if (!(IMG_Init(IMG_INIT_PNG) & IMG_INIT_PNG))
	{
		return EXIT_FAILURE;
	}

	adjacent = malloc(ADJACENT * sizeof(SDL_Texture*));
	create_window();
	load_images();

	dimension = d;

	int i, j;
	board = malloc(dimension * sizeof(SDL_Rect*));
	for (i = 0; i < dimension; i++)
		board[i] = malloc(dimension * sizeof(SDL_Rect));

	for (i = 0; i < dimension; i++)
	{
		for (j = 0; j < dimension; j++)
		{
			board[i][j].x = j * IMAGE_SIZE;
			board[i][j].y = i * IMAGE_SIZE;
			board[i][j].w = IMAGE_SIZE;
			board[i][j].h = IMAGE_SIZE;

			SDL_RenderSetViewport(renderer, &board[i][j]);

			SDL_RenderCopy(renderer, covered, NULL, NULL);
		}
	}

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
		//Create renderer for window
		renderer = SDL_CreateRenderer( window, -1, SDL_RENDERER_ACCELERATED );
		if( renderer == NULL )
		{
			printf( "Renderer could not be created! SDL Error: %s\n", SDL_GetError() );
		}
		else
		{
			//Initialize renderer color
			//SDL_SetRenderDrawColor( renderer, 0xFF, 0xFF, 0xFF, 0xFF );

			//Initialize PNG loading
			int imgFlags = IMG_INIT_PNG;
			if( !( IMG_Init( imgFlags ) & imgFlags ) )
			{
			    printf( "SDL_image could not initialize! SDL_image Error: %s\n", IMG_GetError() );
			}
		}
	}
	return 0;
}

SDL_Texture* load_optimized(char* path)
{
	SDL_Surface* tmp = IMG_Load(path);
	//Convert surface to screen format
	SDL_Texture* opt = SDL_CreateTextureFromSurface(renderer, tmp);
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

	covered = load_optimized("../icons/blank.png");
}

void destroy_render()
{
	free(adjacent);

	//Destroy window
	SDL_DestroyWindow( window );
	window = NULL;

	int i;
	for (i = 0; i < dimension; i++)
		free(board[i]);
	free(board);

	//Quit SDL subsystems
	SDL_Quit();
}

void make_window()
{
	init_render(8);
	SDL_RenderPresent(renderer);
}

void convert_to_tile(int x, int y)
{
	int tile_x = x / IMAGE_SIZE; 
	int tile_y = y / IMAGE_SIZE;

	printf("Clicked on tile (%d, %d)\n", tile_x, tile_y);
}

int get_input()
{
	SDL_Event e;	
	while(SDL_PollEvent(&e) != 0)
	{
		if(e.type == SDL_MOUSEBUTTONUP)
		{
			//Get mouse position
			int x, y;
			SDL_GetMouseState( &x, &y );

			convert_to_tile(x, y);
		}

		if(e.type == SDL_QUIT)
		{
			printf("QUIT!\n");
			return STATE_QUIT;
		}
	}
	return STATE_RUNNING;
}

