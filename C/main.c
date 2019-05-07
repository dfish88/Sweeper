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

	while (1)
	{
		printf("\e[1;1H\e[2J");
		print_board(g);

		printf("Enter x: ");
		x = getchar();
		while((c = getchar()) != EOF && c != '\n');

		if (x == 'q')
			break;

		printf("Enter y: ");
		y = getchar();
		while((c = getchar()) != EOF && c != '\n');

		if (y == 'q')
			break;

		changes = reveal_tile(g, x - '0', y - '0');

		while (changes != NULL)
		{
			//printf("change at (%d, %d)\n", changes->x, changes->y);
			tmp = changes->next;
			free(changes);
			changes = tmp;
		}

		if (get_state(g) == LOST)
		{
			printf("\e[1;1H\e[2J");
			print_board(g);
			
			printf("GAME OVER, YOU BLEW UP!\n");
			break;
		}

	}



	destroy_board(g, 4);
}

