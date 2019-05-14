typedef struct graphics graphics;
extern const int IMAGE_SIZE;

graphics* create_graphics(int d);
void destroy_graphics(graphics* r);

void render_game_running(graphics* g, point* changes);
void render_game_lost(graphics* g, point* changes);
