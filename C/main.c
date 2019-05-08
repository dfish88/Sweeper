#include <stdio.h>
#include <stdlib.h>

#include "logic.h"
#include "render.h"

int main()
{
	game* g = create_board(8);
	add_mines(g, 0, 0);
	printf("\n");

	int x;
	int y;
	int c;
	point* tmp;
	point* changes;

	init_render();
	create_window();

	while (get_state(g) == RUNNING)
	{
		printf("\e[1;1H\e[2J");
		print_board(g);

		printf("Enter x: ");
		x = getchar();
		while((c = getchar()) != EOF && c != '\n');

		if (x == 'q')
		{	
			set_state(g, QUIT);
			break;
		}

		printf("Enter y: ");
		y = getchar();
		while((c = getchar()) != EOF && c != '\n');

		if (y == 'q')
		{	
			set_state(g, QUIT);
			break;
		}

		changes = make_move(g, x - '0', y - '0', false);

		while (changes != NULL)
		{
			//printf("change at (%d, %d)\n", changes->x, changes->y);
			tmp = changes->next;
			free(changes);
			changes = tmp;
		}
	}

	printf("\e[1;1H\e[2J");
	print_board(g);

	if (get_state(g) == LOST)
		printf("GAME OVER, YOU BLEW UP!\n");
	else if (get_state(g) == WON)
		printf("YOU WON!\n");
	else if (get_state(g) == QUIT)
		printf("BYE BYE!\n");

	destroy_render();
	destroy_board(g, 4);
}

