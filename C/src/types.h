#ifndef TYPES_H
#define TYPES_H
#include <stdbool.h>
 
/********************
*  TYPES/CONSTANTS
********************/
// defined in main
extern const int STATE_RUNNING, STATE_WON, STATE_LOST, STATE_QUIT;

typedef struct point
{
	int x;
	int y;
	char c;
	struct point * next;
} point;
#endif
