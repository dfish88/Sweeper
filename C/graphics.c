#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>
#include <SDL2/SDL_ttf.h>
#include <pthread.h>
#include <unistd.h>

#include "types.h"
#include "graphics.h"

//Screen dimension constants
const int SCREEN_WIDTH = 300;
const int SCREEN_HEIGHT = 350;
const int ADJACENT = 9;
const int TOP_PANEL_BUTTONS = 4;

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

int timer_state;
pthread_t *timer_thread;

bool create_window()
{
	//Create window
	window = SDL_CreateWindow( "SDL Tutorial", SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED, SCREEN_WIDTH, SCREEN_HEIGHT, SDL_WINDOW_SHOWN );
	if( window == NULL )
	{
		return true;
	}
	else
	{
		//Create graphics for window
		rend = SDL_CreateRenderer( window, -1, SDL_RENDERER_ACCELERATED );
		if( rend == NULL )
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

SDL_Texture* load_texture(char* path)
{
	SDL_Surface* tmp = IMG_Load(path);
	SDL_Texture* opt = SDL_CreateTextureFromSurface(rend, tmp);
	SDL_FreeSurface(tmp);
	return opt;
}

void load_images()
{
	restart = load_texture("../icons/restart.png");
	adjacent[0] = load_texture("../icons/0.png");
	adjacent[1] = load_texture("../icons/1.png");
	adjacent[2] = load_texture("../icons/2.png");
	adjacent[3] = load_texture("../icons/3.png");
	adjacent[4] = load_texture("../icons/4.png");
	adjacent[5] = load_texture("../icons/5.png");
	adjacent[6] = load_texture("../icons/6.png");
	adjacent[7] = load_texture("../icons/7.png");
	adjacent[8] = load_texture("../icons/8.png");


	covered = load_texture("../icons/blank.png");
	flag = load_texture("../icons/flag.png");
	mine = load_texture("../icons/mine.png");
	boom = load_texture("../icons/boom.png");
	wrong = load_texture("../icons/wrong.png");
	dead = load_texture("../icons/dead.png");
	glasses = load_texture("../icons/glasses.png");
	happy = load_texture("../icons/smile.png");
	surprise = load_texture("../icons/click.png");
	empty = load_texture("../icons/empty.png");
	hint = load_texture("../icons/hint.png");
}

/******************************
*     RENDERING THE GAME
******************************/ 
void render_game_running(point* changes)
{
	point* tmp;
	while (changes != NULL)
	{
		SDL_RenderSetViewport(rend, &board[changes->x][changes->y]);
		printf("Changed at (%d, %d) to %c\n", changes->x, changes->y, changes->c);
		if (changes->c == 'f')
			SDL_RenderCopy(rend, flag, 0, 0);
		else if (changes->c == ' ')
			SDL_RenderCopy(rend, covered, 0, 0);
		else
			SDL_RenderCopy(rend, adjacent[changes->c - '0'], 0, 0);
		tmp = changes->next;
		free(changes);
		changes = tmp;
	}
	SDL_RenderPresent(rend);

	SDL_RenderSetViewport(rend, &top_panel[1]);
	SDL_RenderCopy(rend, happy, 0, 0);
	SDL_RenderPresent(rend);
} 

void *render_timer(void *arg)
{
	int* pthread_state = arg;
	int sec = 0;
	while(1)
	{
		sleep(1);
		printf("Seconds passed: %d\n", sec);
		printf("Thread state: %d\n", (*pthread_state));
		sec++;

		if ((*pthread_state) == -1)
		{
			printf("Killing thread\n");
			pthread_exit(0);
			break;
		}
	}
	return 0;
}

void render_game_restart()
{
	int i, j;
	for (i = 0; i < dimension; i++)	
	{
		for (j = 0; j < dimension; j++)
		{
			SDL_RenderSetViewport(rend, &board[i][j]);
			SDL_RenderCopy(rend, covered, 0, 0);
		}
	}

	// Stop timer thread
	timer_state = -1;
	pthread_join(*(timer_thread), NULL);
	free(timer_thread);

	// Create timer thread
	timer_state = 1;
	timer_thread = malloc(sizeof(pthread_t));
	pthread_create(timer_thread, NULL, &render_timer, (void *)&(timer_state));

	SDL_RenderPresent(rend);
}

void render_game_lost(point* changes)
{
	point* tmp;
	while (changes != NULL)
	{
		SDL_RenderSetViewport(rend, &board[changes->x][changes->y]);
		printf("Changed at (%d, %d) to %c\n", changes->x, changes->y, changes->c);

		if (changes->c == 'b')
			SDL_RenderCopy(rend, boom, 0, 0);
		else if (changes->c == 'm')
			SDL_RenderCopy(rend, mine, 0, 0);
		else if (changes->c == 'w')
			SDL_RenderCopy(rend, wrong, 0, 0);

		tmp = changes->next;
		free(changes);
		changes = tmp;
	}
	SDL_RenderPresent(rend);

	SDL_RenderSetViewport(rend, &top_panel[1]);
	SDL_RenderCopy(rend, dead, 0, 0);
	SDL_RenderPresent(rend);
}

void render_game_won(point* changes)
{
	point* tmp;
	while (changes != NULL)
	{
		SDL_RenderSetViewport(rend, &board[changes->x][changes->y]);
		printf("Changed at (%d, %d) to %c\n", changes->x, changes->y, changes->c);
		SDL_RenderCopy(rend, adjacent[changes->c - '0'], 0, 0);
		tmp = changes->next;
		free(changes);
		changes = tmp;
	}
	SDL_RenderPresent(rend);

	SDL_RenderSetViewport(rend, &top_panel[1]);
	SDL_RenderCopy(rend, glasses, 0, 0);
	SDL_RenderPresent(rend);
}

void render_face_on_click()
{
	SDL_RenderSetViewport(rend, &top_panel[1]);
	SDL_RenderCopy(rend, surprise, 0, 0);
	SDL_RenderPresent(rend);
}


