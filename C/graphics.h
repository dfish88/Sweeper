typedef struct graphics graphics;
extern const int IMAGE_SIZE;

graphics* create_graphics(int d);
void destroy_graphics(graphics* r);

void render_game_running(graphics* g, point* changes);
void render_game_restart(graphics* g);
void render_game_won(graphics* g, point* changes);
void render_game_lost(graphics* g, point* changes);
void render_face_on_click(graphics* g);
