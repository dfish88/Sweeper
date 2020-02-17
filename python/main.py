import tkinter
from tkinter import *

BUTTON_DIM = 50

def main():

	# Create main window
	root = tkinter.Tk()
	root.title("Sweeper!")
	root.grid()

	# Make Top bar that has hint, smiley, restart, timer
	hint = tkinter.Button(root, text="Hint?")
	smile_img = tkinter.PhotoImage(file="../icons/smile.png")
	smile = tkinter.Label(root, image=smile_img)
	restart = tkinter.Button(root, text="Restart?")
	timer = tkinter.Label(root, text="0:00")

	hint.grid(row=0, column=0, columnspan=2, sticky="nsew")
	smile.grid(row=0, column=3)
	restart.grid(row=0, column=5, columnspan=2, sticky="nsew")
	timer.grid(row=0, column=7)

	# Make window
	row, col = 8, 8
	blank = tkinter.PhotoImage(file="../icons/blank.png")

	for r in range(1, row+1):
		for c in range(col):
			callback = lambda x=r, y=c: print(str(x) + ", " + str(y))
			temp = tkinter.Button(root, image=blank, highlightthickness=0, bd=0, relief=SUNKEN, command = callback)
			temp.grid(row = r, column=c)

	root.mainloop()
main()
