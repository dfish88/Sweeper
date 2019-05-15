#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>

#include "types.h"
#include "logic.h"

/********************
*     CONSTANTS
********************/ 
const int DIRECTIONS = 8;
const int delta_x[] = {-1,-1,0,1,1,1,0,-1};
const int delta_y[] = {0,1,1,1,0,-1,-1,-1};

/********************
*	TYPES
********************/ 
typedef struct
{
	bool mine;
	bool flag;
	bool revealed;
	unsigned int adjacent;
} tile;

struct game
{
	int dimension;
	int tiles_left;
	tile **board;
	int state;
};
/********************
*      HELPERS
********************/ 

/*
* Used to add to linked lists which are used to track
* changes and reveal tiles.
*/ 
point* add(point* tail, int x, int y, char c)
{
	point* new = malloc(sizeof(point));
	new->x = x;
	new->y = y;
	new->c = c;
	new->next = NULL;
	tail->next = new;
	return new;
}

bool check_win(game* g)
{
	if (g->tiles_left <= 0)
		return true;
	else
		return false;
}

void reveal(game* g, int x, int y)
{
	if (!g->board[x][y].revealed)
	{
		g->board[x][y].revealed = true;
		g->tiles_left--;
		printf("Revealed tile (%d,%d), %d left\n", x, y, g->tiles_left);
	}
}


/********************
*	GETTERS
********************/ 
char get_type(game* g, int x, int y)
{
	if (!g->board[x][y].revealed)
	{
		if(g->board[x][y].flag)
			return 'f';
		else
			return 'x';
	}

	if (g->board[x][y].mine)
		return 'm';
	else
		return g->board[x][y].adjacent + '0';
}

char get_type_revealed(game* g, int x, int y)
{
	if (g->board[x][y].mine)
		return 'm';
	else
		return g->board[x][y].adjacent + '0';
}


bool get_mine(game* g, int x, int y)
{
	return g->board[x][y].mine;
}

bool get_flag(game* g, int x, int y)
{
	return g->board[x][y].flag;
}

bool get_revealed(game* g, int x, int y)
{
	return g->board[x][y].revealed;
}

unsigned int get_adjacent(game* g, int x, int y)
{
	return g->board[x][y].adjacent;
}

int get_state(game* g)
{
	return g->state;
}

/********************
*	SETTERS
********************/ 
void add_tile(game* g, int x, int y)
{
	g->board[x][y].mine = false;
	g->board[x][y].flag = false;
	g->board[x][y].adjacent = 0;
	g->board[x][y].revealed = false;
}

void set_mine(game* g, int x, int y)
{
	g->board[x][y].mine = true;
}

void set_flag(game* g, int x, int y)
{
	g->board[x][y].flag = !(g->board[x][y].flag);
}

void set_revealed(game* g, int x, int y)
{
	g->board[x][y].revealed = true;
}

void set_adjacent(game* g, int x, int y, int a)
{
	g->board[x][y].adjacent = a;
}

void set_state(game* g, int s)
{
	if (s > STATE_QUIT)
		return;
	g->state = s;
}

/******************************
*    CONSTRUCTORS/DESTRUCTORS
******************************/ 
game* create_game(int size)
{
	game* g = malloc(sizeof(game));
	g->dimension = size;
	
	int x;
	g->board = malloc(g->dimension * sizeof(tile *));
	for (x = 0; x < g->dimension; x++)
	{
		g->board[x] = calloc(g->dimension,  sizeof(tile));
	}

	g->state = STATE_RUNNING;

	srand(time(0));

	return g;
}

void destroy_game(game* g, int size) 
{
	int x;
	for (x = 0; x < size; x++)
	{
		free(g->board[x]);
	}
	free(g->board);
	free(g);
}

/******************************
*       BUILD BOARD
******************************/ 
bool check_for_mine(game* g, int x, int y)
{
	if (x >= g->dimension || y >= g->dimension)
		return false;
	else if (x < 0 || y < 0)
		return false;
	else if (g->board[x][y].mine)
		return true;
	else
		return false;
}

int count_mines(game* g, int x, int y)
{
	int num_mines = 0;
	int d, new_x, new_y;
	
	for (d = 0; d < DIRECTIONS; d++)
	{
		new_x = x + delta_x[d];
		new_y = y + delta_y[d];

		if (check_for_mine(g, new_x, new_y))
			num_mines++;
	}

	return num_mines;
}

void add_mines(game* g, int x, int y)
{
	// Maximum number of mines
	int limit = (int)((g->dimension * g->dimension)/6);
	int num_mines;
	int placed = 0;

	// Randomly place mines
	for (num_mines = 0; num_mines < limit; num_mines++)
	{
		int rand_x = rand() % g->dimension;
		int rand_y = rand() % g->dimension;

		// Don't place mine on first tile clicked
		if (x == rand_x && y == rand_y)
			continue;

		g->board[rand_x][rand_y].mine = true;
		g->board[rand_x][rand_y].flag = false;
		g->board[rand_x][rand_y].revealed = false;
		g->board[rand_x][rand_y].adjacent = 0;
		placed++;
	}

	g->tiles_left = (g->dimension * g->dimension) - placed;
	printf("Added mines, need to reveal %d tiles to win\n", g->tiles_left);

	// Place all adjacent tiles
	int r, c;
	for (r = 0; r < g->dimension; r++)
	{
		for (c = 0; c < g->dimension; c++)
		{
			// Skip mines that have been added
			if (!(g->board[r][c].mine))
			{
				g->board[r][c].flag = false;
				g->board[r][c].revealed = false;

				// Find how many mines tile is adjacent to
				g->board[r][c].adjacent = count_mines(g, r, c);
			}
		}
	}
}

/******************************
*       REVEALING TILES
******************************/ 

/*
* Returns true if (x,y) is on board and
* tile at (x,y) isn't revealed
*/
bool in_bounds(game* g, int x, int y)
{
	if (x >= g->dimension || y >= g->dimension)
		return false;
	else if (x < 0 || y < 0)
		return false;
	else if (g->board[x][y].revealed)
		return false;
	else
		return true;
}
/*
* Reveals tile at x y and adds to linked list that tracks changes. 
* If tile is a 0 then we add all adjacent tiles to list and reveal them.
* If any of those are 0 tiles we do the same.
* Returns the linked list of changes so graphics can be updated.
*/
point* reveal_tile(game* g, int x, int y)
{
	reveal(g, x, y);
	point* tail = malloc(sizeof(point));
	tail->x = x;
	tail->y = y;
	tail->c = get_type(g, x, y);
	tail->next = NULL;

	point* current = tail;
	point* head = tail;

	int dir, new_x, new_y;

	while (current != NULL)
	{
		// Add all adjacent tiles to list if tile is a 0
		if (g->board[current->x][current->y].adjacent == 0)
		{
			for(dir = 0; dir < DIRECTIONS; dir++)
			{
				new_x = current->x + delta_x[dir];
				new_y = current->y + delta_y[dir];

				if (in_bounds(g, new_x, new_y)) 
				{
					reveal(g, new_x, new_y);
					tail = add(tail, new_x, new_y, get_type(g, new_x, new_y));
				}
			}
		}
		current = current->next;
	}
	return head;
}

point* reveal_mines(game* g, int x, int y)
{
	point* tail = malloc(sizeof(point));
	tail->x = x;
	tail->y = y;
	tail->c = 'b';
	tail->next = NULL;
	point* head = tail;

	int i, j;
	for (i = 0; i < g->dimension; i++)
	{
		for (j = 0; j < g->dimension; j++)
		{
			if (g->board[i][j].mine && !g->board[i][j].revealed)
			{
				g->board[i][j].revealed = true;
				tail = add(tail, i, j, 'm');
			}
		}	
	}
	return head;
}

/******************************
*       PLAYING GAME
******************************/ 
point* make_move(game* g, int x, int y, bool flag)
{
	if (g->board[x][y].revealed)
		return NULL;

	// Flag tile or remove flag from tile
	if (flag)
	{
		return NULL;
	}

	// GAME OVER!
	if (g->board[x][y].mine)
	{
		g->board[x][y].revealed = true;
		g->state = STATE_LOST;
		return reveal_mines(g, x, y);
	}

	point* head = reveal_tile(g, x, y);

	if (check_win(g))
	{
		g->state = STATE_WON;
	}

	return head;
}
