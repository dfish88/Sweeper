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
*      HELPERS
********************/ 
/*int dimension;
int tiles_left;
tile **game_board;
int state;
bool first_move;
*/

/*
* Used to convert a square of the board to 
* a character.
*/
char get_type(game* g, int x, int y)
{
	if (!g->game_board[x][y].revealed)
	{
		if(g->game_board[x][y].flag)
			return 'f';
		else
			return 'x';
	}

	if (g->game_board[x][y].mine)
		return 'm';
	else
		return g->game_board[x][y].adjacent + '0';
}

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
	if (!g->game_board[x][y].revealed)
	{
		g->game_board[x][y].revealed = true;
		g->tiles_left--;
		printf("Revealed tile (%d,%d), %d left\n", x, y, g->tiles_left);
	}
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
	else if (g->game_board[x][y].mine)
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
	srand(time(0));

	// Randomly place mines
	for (num_mines = 0; num_mines < limit; num_mines++)
	{
		int rand_x = rand() % g->dimension;
		int rand_y = rand() % g->dimension;

		// Don't place mine on first tile clicked
		if (x == rand_x && y == rand_y)
			continue;

		// Don't add mine where there is already a mine
		if (g->game_board[rand_x][rand_y].mine)
			continue;

		g->game_board[rand_x][rand_y].mine = true;
		g->game_board[rand_x][rand_y].flag = false;
		g->game_board[rand_x][rand_y].revealed = false;
		g->game_board[rand_x][rand_y].adjacent = 0;
		placed++;
		printf("Placed mine at (%d,%d)\n", rand_x, rand_y);
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
			if (!(g->game_board[r][c].mine))
			{
				g->game_board[r][c].flag = false;
				g->game_board[r][c].revealed = false;

				// Find how many mines tile is adjacent to
				g->game_board[r][c].adjacent = count_mines(g, r, c);
			}
		}
	}
}

/******************************
*       REVEALING TILES
******************************/ 

/*
* Returns true if (x,y) is on game_board and
* tile at (x,y) isn't revealed
*/
bool in_bounds(game* g, int x, int y)
{
	if (x >= g->dimension || y >= g->dimension)
		return false;
	else if (x < 0 || y < 0)
		return false;
	else if (g->game_board[x][y].revealed)
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
		if (g->game_board[current->x][current->y].adjacent == 0)
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

/*
* Called when player clicks on mine, reveal mines and check flags.
*/
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
			// Mines that are not revealed and are not flags
			if (g->game_board[i][j].mine && !g->game_board[i][j].revealed && !g->game_board[i][j].flag)
			{
				g->game_board[i][j].revealed = true;
				tail = add(tail, i, j, 'm');
			}
			// Flags that are not on mines
			else if (!g->game_board[i][j].mine && g->game_board[i][j].flag)
			{
				g->game_board[i][j].revealed = true;
				tail = add(tail, i, j, 'w');
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
	// Build game_board on first click
	if (g->first_move)
	{
		add_mines(g, x, y);
		g->first_move = false;
	}

	// Flag tile or remove flag from tile
	if (flag)
	{
		if (!g->game_board[x][y].revealed)
		{
			if (!g->game_board[x][y].flag)
			{
				g->game_board[x][y].flag = true;
				point* tmp = malloc(sizeof(point));
				tmp->x = x;
				tmp->y = y;
				tmp->c = 'f';
				tmp->next = NULL;
				return tmp;
			}
			else
			{
				g->game_board[x][y].flag = false;
				point* tmp = malloc(sizeof(point));
				tmp->x = x;
				tmp->y = y;
				tmp->c = ' ';
				tmp->next = NULL;
				return tmp;
			}
			
		}
	}

	if (g->game_board[x][y].revealed)
		return NULL;

	// GAME OVER!
	if (g->game_board[x][y].mine)
	{
		g->game_board[x][y].revealed = true;
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

