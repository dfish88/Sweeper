import tkinter
from tkinter import *
import const
from graphics import start_game, restart_game

# Load all the icons used during the game
def load_icons(img_dic):

	img_dic['0'] = tkinter.PhotoImage(file="../../img/0.png")
	img_dic['1'] = tkinter.PhotoImage(file="../../img/0.png")
	img_dic['2'] = tkinter.PhotoImage(file="../../img/0.png")
	img_dic['3'] = tkinter.PhotoImage(file="../../img/0.png")
	img_dic['4'] = tkinter.PhotoImage(file="../../img/0.png")
	img_dic['5'] = tkinter.PhotoImage(file="../../img/0.png")
	img_dic['6'] = tkinter.PhotoImage(file="../../img/0.png")
	img_dic['7'] = tkinter.PhotoImage(file="../../img/0.png")
	img_dic['8'] = tkinter.PhotoImage(file="../../img/0.png")
	img_dic['b'] = tkinter.PhotoImage(file="../../img/blank.png")
	img_dic['bm'] = tkinter.PhotoImage(file="../../img/boom.png")
	img_dic['c'] = tkinter.PhotoImage(file="../../img/click.png")
	img_dic['d'] = tkinter.PhotoImage(file="../../img/dead.png")
	img_dic['e'] = tkinter.PhotoImage(file="../../img/empty.png")
	img_dic['f'] = tkinter.PhotoImage(file="../../img/flag.png")
	img_dic['g'] = tkinter.PhotoImage(file="../../img/glasses.png")
	img_dic['m'] = tkinter.PhotoImage(file="../../img/mine.png")
	img_dic['s'] = tkinter.PhotoImage(file="../../img/smile.png")
	img_dic['w'] = tkinter.PhotoImage(file="../../img/wrong.png")


def main():

	# Create main window
	root = tkinter.Tk()
	root.title("Sweeper!")
	root.geometry("400x425+500+150")
	root.resizable(width=False, height=False)

	# Dictionary to store frames
	frames = {'easy': None, 'hard': None}

	# Top frame has hint, smiley, restart, and timer
	top_frame = tkinter.Frame(root, width=const.BUTTON_DIM*8, height=const.BUTTON_DIM)
	top_frame.grid(row=0, column=0, sticky="nsew")
	frames['top'] = top_frame

	# Bottom frame has either gameboard or diffculty frame
	bottom_frame = tkinter.Frame(root)
	bottom_frame.grid(row=1, column=0, sticky="nsew")
	frames['bottom'] = bottom_frame
	
	# difficulty frame for starting screen
	difficulty = tkinter.Frame(bottom_frame, width=const.BUTTON_DIM*8, height=const.BUTTON_DIM*8)
	difficulty.grid(row=0, column=0, sticky="nsew")
	difficulty.grid_propagate(0)
	frames['difficulty'] = difficulty

	# Load all icons for game
	icons = {}
	load_icons(icons)

	# Make buttons for top frame
	hint = tkinter.Button(top_frame, text="Hint?")
	smile = tkinter.Label(top_frame, image=icons['s'])
	restart = tkinter.Button(top_frame, text="Restart?", command=lambda: restart_game(frames, root))
	timer = tkinter.Label(top_frame, text="0:00")

	# Place buttons in top frame
	hint.grid(row=0, column=0, columnspan=2, sticky="nsew")
	top_frame.columnconfigure(0, weight=1)
	smile.grid(row=0, column=3)
	top_frame.columnconfigure(3, weight=1)
	restart.grid(row=0, column=5, columnspan=2, sticky="nsew")
	top_frame.columnconfigure(5, weight=1)
	timer.grid(row=0, column=7)
	top_frame.columnconfigure(7, weight=1)


	# Create easy and hard buttons for starting screen
	easy = tkinter.Button(difficulty, text="8x8 (Easy)", command=lambda:start_game(const.EASY_DIM, frames, icons['b'], root))
	hard = tkinter.Button(difficulty, text="16x16 (Hard)", command=lambda:start_game(const.HARD_DIM, frames, icons['b'], root))

	# Place easy and hard buttons in bottom frame in difficulty frame
	difficulty.rowconfigure(0, weight=1)
	easy.grid(row=0, column=0, columnspan=4, rowspan=8, sticky="nsew")
	difficulty.columnconfigure(0, weight=1)
	hard.grid(row=0, column=4, columnspan=4, rowspan=8, sticky="nsew")
	difficulty.columnconfigure(4, weight=1)

	root.mainloop()
main()
