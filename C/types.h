#include <stdbool.h> 

/********************
*  TYPES/CONSTANTS
********************/
#define RUNNING 0
#define WON 1
#define LOST 2
#define QUIT 4

typedef struct game game;                                                                                                                                                                                                                 
typedef struct point
{
	int x;
	int y;
	struct point * next;
} point;


