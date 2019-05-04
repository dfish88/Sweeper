#include <stdbool.h> 

/********************
*	TYPES
********************/ 
typedef struct game_board game_board;

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

/********************
*	SETTERS
********************/ 
void add_tile(game_board* gb, int x, int y);
void set_type(game_board* gb, int x, int y, char t);
void set_mine(game_board* gb, int x, int y);
void set_flag(game_board* gb, int x, int y);
void set_revealed(game_board* gb, int x, int y);
void set_adjacent(game_board* gb, int x, int y, int a);

