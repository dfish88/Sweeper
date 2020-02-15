import tkinter
from tkinter import *

BUTTON_DIM = 50

def main():

	# Make window
	w, h = 8, 8
	root = tkinter.Tk()
	root.title("Sweeper!")
	root.grid()

	for x in range(w):
		for y in range(h):
			temp = tkinter.Button(root, text="test")
			temp.grid(row = x, column=y)

	root.mainloop()


	# Make the board
	board = [[' ' for x in range(w)] for y in range(h)]
main()
