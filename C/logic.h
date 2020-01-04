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

point* make_move(game* g, int x, int y, bool flag);
void restart_game();

