#ifndef TYPES_H
#define TYPES_H

#include <stdbool.h>
 
// defined in main
extern const int STATE_RUNNING, STATE_WON, STATE_LOST, STATE_QUIT;

/********************
*  TYPES/CONSTANTS
********************/
//extern const int STATE_RUNNING, STATE_WON, STATE_LOST, STATE_QUIT;

typedef struct game game;                                                                                                                                                                                                                 
typedef struct point
{
	int x;
	int y;
	struct point * next;
} point;

#endif
