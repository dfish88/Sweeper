#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>
#include <SDL2/SDL_ttf.h>

#include "types.h"
#include "graphics.h"

//Screen dimension constants
const int SCREEN_WIDTH = 300;
const int SCREEN_HEIGHT = 350;
const int ADJACENT = 9;
const int TOP_PANEL_BUTTONS = 4;

struct graphics
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
	SDL_Texture* empty;
	SDL_Texture* hint;
	SDL_Texture* restart;

	SDL_Window* window;
	SDL_Renderer* rend;
	TTF_Font* button_font;
	int dimension;

	SDL_Rect** board;
	SDL_Rect* top_panel;
};

bool create_window(graphics* g)
{
	//Create window
	g->window = SDL_CreateWindow( "SDL Tutorial", SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED, SCREEN_WIDTH, SCREEN_HEIGHT, SDL_WINDOW_SHOWN );
	if( g->window == NULL )
	{
		return true;
	}
	else
	{
		//Create graphics for window
		g->rend = SDL_CreateRenderer( g->window, -1, SDL_RENDERER_ACCELERATED );
		if( g->rend == NULL )
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

SDL_Texture* load_texture(graphics* g, char* path)
{
	SDL_Surface* tmp = IMG_Load(path);
	SDL_Texture* opt = SDL_CreateTextureFromSurface(g->rend, tmp);
	SDL_FreeSurface(tmp);
	return opt;
}

void load_images(graphics* g)
{
	g->adjacent[0] = load_texture(g, "../icons/0.png");
	g->adjacent[1] = load_texture(g, "../icons/1.png");
	g->adjacent[2] = load_texture(g, "../icons/2.png");
	g->adjacent[3] = load_texture(g, "../icons/3.png");
	g->adjacent[4] = load_texture(g, "../icons/4.png");
	g->adjacent[5] = load_texture(g, "../icons/5.png");
	g->adjacent[6] = load_texture(g, "../icons/6.png");
	g->adjacent[7] = load_texture(g, "../icons/7.png");
	g->adjacent[8] = load_texture(g, "../icons/8.png");

	g->covered = load_texture(g, "../icons/blank.png");
	g->flag = load_texture(g, "../icons/flag.png");
	g->mine = load_texture(g, "../icons/mine.png");
	g->boom = load_texture(g, "../icons/boom.png");
	g->wrong = load_texture(g, "../icons/wrong.png");
	g->dead = load_texture(g, "../icons/dead.png");
	g->glasses = load_texture(g, "../icons/glasses.png");
	g->happy = load_texture(g, "../icons/smile.png");
	g->surprise = load_texture(g, "../icons/click.png");
	g->empty = load_texture(g, "../icons/empty.png");
	g->hint = load_texture(g, "../icons/hint.png");
	g->restart = load_texture(g, "../icons/restart.png");
}

/******************************
*     RENDERING THE GAME
******************************/ 
void render_game_running(graphics* g, point* changes)
{
	point* tmp;
	while (changes != NULL)
	{
		SDL_RenderSetViewport(g->rend, &g->board[changes->x][changes->y]);
		printf("Changed at (%d, %d) to %c\n", changes->x, changes->y, changes->c);
		if (changes->c == 'f')
			SDL_RenderCopy(g->rend, g->flag, 0, 0);
		else if (changes->c == ' ')
			SDL_RenderCopy(g->rend, g->covered, 0, 0);
		else
			SDL_RenderCopy(g->rend, g->adjacent[changes->c - '0'], 0, 0);
		tmp = changes->next;
		free(changes);
		changes = tmp;
	}
	SDL_RenderPresent(g->rend);

	SDL_RenderSetViewport(g->rend, &g->top_panel[1]);
	SDL_RenderCopy(g->rend, g->happy, 0, 0);
	SDL_RenderPresent(g->rend);
} 

void render_game_lost(graphics* g, point* changes)
{
	point* tmp;
	while (changes != NULL)
	{
		SDL_RenderSetViewport(g->rend, &g->board[changes->x][changes->y]);
		printf("Changed at (%d, %d) to %c\n", changes->x, changes->y, changes->c);

		if (changes->c == 'b')
			SDL_RenderCopy(g->rend, g->boom, 0, 0);
		else if (changes->c == 'm')
			SDL_RenderCopy(g->rend, g->mine, 0, 0);
		else if (changes->c == 'w')
			SDL_RenderCopy(g->rend, g->wrong, 0, 0);

		tmp = changes->next;
		free(changes);
		changes = tmp;
	}
	SDL_RenderPresent(g->rend);

	SDL_RenderSetViewport(g->rend, &g->top_panel[1]);
	SDL_RenderCopy(g->rend, g->dead, 0, 0);
	SDL_RenderPresent(g->rend);
}

void render_game_won(graphics* g, point* changes)
{
	point* tmp;
	while (changes != NULL)
	{
		SDL_RenderSetViewport(g->rend, &g->board[changes->x][changes->y]);
		printf("Changed at (%d, %d) to %c\n", changes->x, changes->y, changes->c);
		SDL_RenderCopy(g->rend, g->adjacent[changes->c - '0'], 0, 0);
		tmp = changes->next;
		free(changes);
		changes = tmp;
	}
	SDL_RenderPresent(g->rend);

	SDL_RenderSetViewport(g->rend, &g->top_panel[1]);
	SDL_RenderCopy(g->rend, g->glasses, 0, 0);
	SDL_RenderPresent(g->rend);
}

void render_face_on_click(graphics* g)
{
	SDL_RenderSetViewport(g->rend, &g->top_panel[1]);
	SDL_RenderCopy(g->rend, g->surprise, 0, 0);
	SDL_RenderPresent(g->rend);
}

/******************************
*    CONSTRUCTORS/DESTRUCTORS
******************************/ 
graphics* create_graphics(int d)
{
	graphics* g = malloc(sizeof(graphics));
	g->dimension = d;

	g->adjacent = malloc(ADJACENT * sizeof(SDL_Texture*));

	int x;
	g->board = malloc(g->dimension * sizeof(SDL_Rect*));
	for (x = 0; x < g->dimension; x++)
		g->board[x] = malloc(g->dimension * sizeof(SDL_Rect));


	g->top_panel = malloc(TOP_PANEL_BUTTONS * sizeof(SDL_Rect));

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
	
	create_window(g);
	load_images(g);
	
	// Hint button
	g->top_panel[0].x = 0;
	g->top_panel[0].y = 0;
	g->top_panel[0].w = 2 * IMAGE_SIZE;
	g->top_panel[0].h = IMAGE_SIZE;
	SDL_RenderSetViewport(g->rend, &g->top_panel[0]);
	SDL_RenderCopy(g->rend, g->hint, 0, 0);

	// Face
	g->top_panel[1].x = 2 * IMAGE_SIZE;
	g->top_panel[1].y = 0;
	g->top_panel[1].w = IMAGE_SIZE;
	g->top_panel[1].h = IMAGE_SIZE;
	SDL_RenderSetViewport(g->rend, &g->top_panel[1]);
	SDL_RenderCopy(g->rend, g->happy, 0, 0);

	// Restart
	g->top_panel[2].x = 3 * IMAGE_SIZE;
	g->top_panel[2].y = 0;
	g->top_panel[2].w = 2 * IMAGE_SIZE;
	g->top_panel[2].h = IMAGE_SIZE;
	SDL_RenderSetViewport(g->rend, &g->top_panel[2]);
	SDL_RenderCopy(g->rend, g->restart, 0, 0);

	// Timer
	g->top_panel[3].x = 5 * IMAGE_SIZE;
	g->top_panel[3].y = 0;
	g->top_panel[3].w = IMAGE_SIZE;
	g->top_panel[3].h = IMAGE_SIZE;
	SDL_RenderSetViewport(g->rend, &g->top_panel[3]);


	int i, j;
	for (i = 0; i < g->dimension; i++)	
	{
		for (j = 0; j < g->dimension; j++)
		{
			g->board[i][j].x = j * IMAGE_SIZE;
			g->board[i][j].y = (i * IMAGE_SIZE) + IMAGE_SIZE;
			g->board[i][j].w = IMAGE_SIZE;
			g->board[i][j].h = IMAGE_SIZE;

			SDL_RenderSetViewport(g->rend, &g->board[i][j]);
			SDL_RenderCopy(g->rend, g->covered, 0, 0);
		}
	}
	SDL_RenderPresent(g->rend);
	return g;
}

void destroy_graphics(graphics* g)
{
	int i;
	for(i = 0; i < g->dimension; i++)
		SDL_DestroyTexture(g->adjacent[i]);
	free(g->adjacent);

	SDL_DestroyTexture(g->covered);
	SDL_DestroyTexture(g->flag);
	SDL_DestroyTexture(g->mine);
	SDL_DestroyTexture(g->boom);
	SDL_DestroyTexture(g->wrong);
	SDL_DestroyTexture(g->dead);
	SDL_DestroyTexture(g->glasses);
	SDL_DestroyTexture(g->happy);
	SDL_DestroyTexture(g->surprise);
	SDL_DestroyTexture(g->empty);
	SDL_DestroyTexture(g->hint);
	SDL_DestroyTexture(g->restart);

	//Destroy window
	SDL_DestroyWindow( g->window );

	for(i = 0; i < g->dimension; i++)
		free(g->board[i]);
	free(g->board);

	free(g->top_panel);

	SDL_DestroyRenderer(g->rend);

	free(g);

	//Quit SDL subsystems
	SDL_Quit();
}
