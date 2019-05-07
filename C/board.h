#include <stdbool.h> 

/********************
*	TYPES
********************/ 
typedef struct game_board game_board;
typedef struct point
{
	int x;
	int y;
	struct point * next;
} point;

/********************
*    INIT/DESTROY
********************/ 
game_board* create_board(int size);
void destroy_board(game_board* gb, int size);

/********************
*	GETTERS
********************/ 
char get_type(game_board* gb, int x, int y);
bool get_mine(game_board* gb, int x, int y);
bool get_flag(game_board* gb, int x, int y);
bool get_revealed(game_board* gb, int x, int y);
unsigned int get_adjacent(game_board* gb, int x, int y);
bool game_over(game_board* gb);

/********************
*	SETTERS
********************/ 
void add_tile(game_board* gb, int x, int y);
void set_mine(game_board* gb, int x, int y);
void set_flag(game_board* gb, int x, int y);
void set_revealed(game_board* gb, int x, int y);
void set_adjacent(game_board* gb, int x, int y, int a);

void print_board(game_board* gb);
void print_board_revealed(game_board* gb);
void add_mines(game_board* gb, int x, int y);
point* reveal_tile(game_board* gb, int x, int y);

