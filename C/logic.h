#include "types.h"

/********************
*      TYPES
********************/ 
typedef struct
{
	bool mine;
	bool flag;
	bool revealed;
	unsigned int adjacent;
} tile;

typedef struct
{
	int dimension;
	int tiles_left;
	tile **game_board;
	int state;
	bool first_move;
} game;

/********************
*	GETTERS
********************/ 
char get_type(int x, int y);
bool get_mine(int x, int y);
bool get_flag(int x, int y);
bool get_revealed(int x, int y);
unsigned int get_adjacent(int x, int y);
int get_state();

/********************
*	SETTERS
********************/ 
void add_tile(int x, int y);
void set_mine(int x, int y);
void set_flag(int x, int y);
void set_revealed(int x, int y);
void set_adjacent(int x, int y, int a);
void set_state(int s);

point* make_move(game* g, int x, int y, bool flag);
void restart_game();

