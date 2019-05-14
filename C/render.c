#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>

#include "types.h"
#include "render.h"

//Screen dimension constants
const int SCREEN_WIDTH = 400;
const int SCREEN_HEIGHT = 400;
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

SDL_Renderer* get_renderer(renderer* r)
{
	return r->rend;
}

bool create_window(renderer* r)
{
	//Create window
	r->window = SDL_CreateWindow( "SDL Tutorial", SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED, SCREEN_WIDTH, SCREEN_HEIGHT, SDL_WINDOW_SHOWN );
	if( r->window == NULL )
	{
		return true;
	}
	else
	{
		//Create renderer for window
		r->rend = SDL_CreateRenderer( r->window, -1, SDL_RENDERER_ACCELERATED );
		if( r->rend == NULL )
		{
			return true;
		}
		else
		{
			//Initialize PNG loading
			int imgFlags = IMG_INIT_PNG;
			if( !( IMG_Init( imgFlags ) & imgFlags ) )
			{
				return true;
			}
		}
	}
	return false;
}

SDL_Texture* load_texture(renderer* r, char* path)
{
	SDL_Surface* tmp = IMG_Load(path);
	SDL_Texture* opt = SDL_CreateTextureFromSurface(r->rend, tmp);
	SDL_FreeSurface(tmp);
	return opt;
}

void load_images(renderer* r)
{
	r->adjacent[0] = load_texture(r, "../icons/0.png");
	r->adjacent[1] = load_texture(r, "../icons/1.png");
	r->adjacent[2] = load_texture(r, "../icons/2.png");
	r->adjacent[3] = load_texture(r, "../icons/3.png");
	r->adjacent[4] = load_texture(r, "../icons/4.png");
	r->adjacent[5] = load_texture(r, "../icons/5.png");
	r->adjacent[6] = load_texture(r, "../icons/6.png");
	r->adjacent[7] = load_texture(r, "../icons/7.png");
	r->adjacent[8] = load_texture(r, "../icons/8.png");

	r->covered = load_texture(r, "../icons/blank.png");
	r->flag = load_texture(r, "../icons/flag.png");
	r->mine = load_texture(r, "../icons/mine.png");
	r->boom = load_texture(r, "../icons/boom.png");
	r->wrong = load_texture(r, "../icons/wrong.png");
	r->dead = load_texture(r, "../icons/dead.png");
	r->glasses = load_texture(r, "../icons/glasses.png");
	r->happy = load_texture(r, "../icons/smile.png");
	r->surprise = load_texture(r, "../icons/click.png");
}

/******************************
*    CONSTRUCTORS/DESTRUCTORS
******************************/ 
renderer* create_renderer(int d)
{
	renderer* r = malloc(sizeof(renderer));
	r->dimension = d;

	r->adjacent = malloc(ADJACENT * sizeof(SDL_Texture*));

	int x;
	r->board = malloc(r->dimension * sizeof(SDL_Rect*));
	for (x = 0; x < r->dimension; x++)
		r->board[x] = malloc(r->dimension * sizeof(SDL_Rect));


	if (SDL_Init(SDL_INIT_VIDEO) != 0) 
	{
		return NULL;
	}

	if (!(IMG_Init(IMG_INIT_PNG) & IMG_INIT_PNG))
	{
		return NULL;
	}
	
	create_window(r);
	load_images(r);

	int i, j;
	for (i = 0; i < r->dimension; i++)	
	{
		for (j = 0; j < r->dimension; j++)
		{
			r->board[i][j].x = j * IMAGE_SIZE;
			r->board[i][j].y = i * IMAGE_SIZE;
			r->board[i][j].w = IMAGE_SIZE;
			r->board[i][j].h = IMAGE_SIZE;

			SDL_RenderSetViewport(r->rend, &r->board[i][j]);
			SDL_RenderCopy(r->rend, r->covered, 0, 0);
		}
	}
	return r;
}

void destroy_renderer(renderer* r)
{
	int i;
	for(i = 0; i < r->dimension; i++)
		SDL_DestroyTexture(r->adjacent[i]);
	free(r->adjacent);

	SDL_DestroyTexture(r->covered);
	SDL_DestroyTexture(r->flag);
	SDL_DestroyTexture(r->mine);
	SDL_DestroyTexture(r->boom);
	SDL_DestroyTexture(r->wrong);
	SDL_DestroyTexture(r->dead);
	SDL_DestroyTexture(r->glasses);
	SDL_DestroyTexture(r->happy);
	SDL_DestroyTexture(r->surprise);

	//Destroy window
	SDL_DestroyWindow( r->window );

	for(i = 0; i < r->dimension; i++)
		free(r->board[i]);
	free(r->board);

	SDL_DestroyRenderer(r->rend);

	free(r);

	//Quit SDL subsystems
	SDL_Quit();
}
