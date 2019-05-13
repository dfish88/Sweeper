typedef struct renderer renderer;
extern const int IMAGE_SIZE;

renderer* create_renderer(int d);
void destroy_renderer(renderer* r);

SDL_Renderer* get_renderer(renderer* r);
