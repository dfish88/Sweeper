CC = gcc
FILES = logic.c graphics.c main.c
CFLAGS = -Wall -g
LFLAGS = -lSDL2 -lSDL2_image -lSDL2_ttf -pthread
OUT = mine

$(OUT): $(FILES)
	$(CC) $(FILES) $(CFLAGS) $(LFLAGS) -o $(OUT)

