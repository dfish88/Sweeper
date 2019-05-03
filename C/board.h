#include <stdbool.h> 

typedef struct game_board game_board;

game_board* create_board(int size);
void destroy_board(game_board* gb, int size);
void add_tile(game_board* gb, char t, bool m, bool f, unsigned int a, int x, int y);
char tile_type(game_board* gb, int x, int y);
