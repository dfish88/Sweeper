import tkinter
from tkinter import *

BUTTON_DIM = 50

def main():

	# Make Top bar

	# Make window
	row, col = 8, 8
	root = tkinter.Tk()
	root.title("Sweeper!")
	root.grid()
	blank = tkinter.PhotoImage(file="../icons/blank.png")

	for r in range(row):
		for c in range(col):
			callback = lambda x=r, y=c: print(str(x) + ", " + str(y))
			temp = tkinter.Button(root, image=blank, highlightthickness=0, bd=0, relief=SUNKEN, command = callback)
			temp.grid(row = r, column=c)

	root.mainloop()
main()
