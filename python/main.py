import tkinter
from tkinter import *

BUTTON_DIM = 50
EASY_DIM = 8
HARD_DIM = 16

def start_game(size, old_frame, window):
	
	old_frame.grid_forget()
	
	game_frame = tkinter.Frame(window, width=BUTTON_DIM*8, height=BUTTON_DIM*8)
	game_frame.grid(row=1, column=0, sticky="nsew")
	#game_frame.grid_propagate(0)

	# Make window
	row, col = size, size
	blank = tkinter.PhotoImage(file="../icons/blank.png")

	for r in range(row):
		for c in range(col):
			callback = lambda x=r, y=c: print(str(x) + ", " + str(y))
			temp = tkinter.Button(game_frame, image=blank, highlightthickness=0, bd=0, relief=SUNKEN, command = callback)
			temp.grid(row=r, column=c)
	game_frame.tkraise()

def main():

	# Create main window
	root = tkinter.Tk()
	root.title("Sweeper!")
	root.resizable(width=False, height=False)

	# Top frame has hint, smiley, restart, and timer
	top_frame = tkinter.Frame(root, width=BUTTON_DIM*8, height=BUTTON_DIM)
	top_frame.grid(row=0, column=0, sticky="nsew")
	
	# Bottom frame has mine field
	bottom_frame = tkinter.Frame(root, width=BUTTON_DIM*8, height=BUTTON_DIM*8)
	bottom_frame.grid(row=1, column=0, sticky="nsew")
	bottom_frame.grid_propagate(0)

	# Make Top bar that has hint, smiley, restart, timer
	hint = tkinter.Button(top_frame, text="Hint?")
	smile_img = tkinter.PhotoImage(file="../icons/smile.png")
	smile = tkinter.Label(top_frame, image=smile_img)
	restart = tkinter.Button(top_frame, text="Restart?")
	timer = tkinter.Label(top_frame, text="0:00")

	hint.grid(row=0, column=0, columnspan=2, sticky="nsew")
	top_frame.columnconfigure(0, weight=1)
	smile.grid(row=0, column=3)
	top_frame.columnconfigure(3, weight=1)
	restart.grid(row=0, column=5, columnspan=2, sticky="nsew")
	top_frame.columnconfigure(5, weight=1)
	timer.grid(row=0, column=7)
	top_frame.columnconfigure(7, weight=1)

	# Offer two choices of difficulty
	easy = tkinter.Button(bottom_frame, text="8x8 (Easy)", command=lambda:start_game(EASY_DIM, bottom_frame, root))
	hard = tkinter.Button(bottom_frame, text="16x16 (Hard)", command=lambda:start_game(HARD_DIM, bottom_frame, root))

	bottom_frame.rowconfigure(0, weight=1)
	easy.grid(row=0, column=0, columnspan=4, rowspan=8, sticky="nsew")
	bottom_frame.columnconfigure(0, weight=1)
	hard.grid(row=0, column=4, columnspan=4, rowspan=8, sticky="nsew")
	bottom_frame.columnconfigure(4, weight=1)

	root.mainloop()
main()
