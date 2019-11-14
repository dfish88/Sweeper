#include "types.h"

typedef struct game game;                                                                                                                                                                                                                 

/********************
*    INIT/DESTROY
********************/ 
game* create_game(int size);
void destroy_game(game* g, int size);

/********************
*	GETTERS
********************/ 
char get_type(game* g, int x, int y);
bool get_mine(game* g, int x, int y);
bool get_flag(game* g, int x, int y);
bool get_revealed(game* g, int x, int y);
unsigned int get_adjacent(game* g, int x, int y);
int get_state(game* g);

/********************
*	SETTERS
********************/ 
void add_tile(game* g, int x, int y);
void set_mine(game* g, int x, int y);
void set_flag(game* g, int x, int y);
void set_revealed(game* g, int x, int y);
void set_adjacent(game* g, int x, int y, int a);
void set_state(game* g, int s);

point* make_move(game* g, int x, int y, bool flag);
void restart(game** g);

