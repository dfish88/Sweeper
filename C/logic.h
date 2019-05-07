#include "game.h"

/********************
*    INIT/DESTROY
********************/ 
game* create_board(int size);
void destroy_board(game* gb, int size);

/********************
*	GETTERS
********************/ 
char get_type(game* gb, int x, int y);
bool get_mine(game* gb, int x, int y);
bool get_flag(game* gb, int x, int y);
bool get_revealed(game* gb, int x, int y);
unsigned int get_adjacent(game* gb, int x, int y);
bool game_over(game* gb);

/********************
*	SETTERS
********************/ 
void add_tile(game* gb, int x, int y);
void set_mine(game* gb, int x, int y);
void set_flag(game* gb, int x, int y);
void set_revealed(game* gb, int x, int y);
void set_adjacent(game* gb, int x, int y, int a);

void print_board(game* gb);
void print_board_revealed(game* gb);
void add_mines(game* gb, int x, int y);
point* reveal_tile(game* gb, int x, int y);

