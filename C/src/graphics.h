#include "types.h"

extern const int IMAGE_SIZE;

void create_graphics(int d);
void destroy_graphics();

void render_game_running(point* changes);
void render_game_restart();
void render_game_won(point* changes);
void render_game_lost(point* changes);
void render_face_on_click();
