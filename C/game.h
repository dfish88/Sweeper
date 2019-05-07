#include <stdbool.h> 

/********************
*       TYPES
********************/
typedef struct game game;                                                                                                                                                                                                                 
typedef struct point
{
	int x;
	int y;
	struct point * next;
} point;

