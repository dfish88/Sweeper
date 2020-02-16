import tkinter
from tkinter import *

BUTTON_DIM = 50

def main():

	# Make window
	w, h = 8, 8
	root = tkinter.Tk()
	root.title("Sweeper!")
	root.grid()
	button_img = tkinter.PhotoImage(file="../icons/blank.png")


	for x in range(w):
		for y in range(h):
			temp = tkinter.Button(root, image=button_img, highlightthickness=0, bd=0, relief=SUNKEN, command = lambda x=x, y=y: print(str(x) + "," + str(y)))
			temp.grid(row = x, column=y)

	root.mainloop()


	# Make the board
	board = [[' ' for x in range(w)] for y in range(h)]
main()
