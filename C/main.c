#include <stdio.h>
#include <stdlib.h>
#include <SDL2/SDL.h>

#include "logic.h"

int main()
{
	game* gb = create_board(8);
	add_mines(gb, 0, 0);
	printf("\n");

	int x;
	int y;
	int c;
	point* tmp;
	point* changes;

	if (SDL_Init(SDL_INIT_VIDEO) != 0) 
	{
		return EXIT_FAILURE;
	}

	while (1)
	{
		printf("\e[1;1H\e[2J");
		print_board(gb);

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

		changes = reveal_tile(gb, x - '0', y - '0');

		while (changes != NULL)
		{
			//printf("change at (%d, %d)\n", changes->x, changes->y);
			tmp = changes->next;
			free(changes);
			changes = tmp;
		}

		if (game_over(gb))
		{
			printf("\e[1;1H\e[2J");
			print_board(gb);
			
			printf("GAME OVER, YOU BLEW UP!\n");
			break;
		}

	}



	destroy_board(gb, 4);
}

