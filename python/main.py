import tkinter
from tkinter import *

BUTTON_DIM = 50
EASY_DIM = 8
HARD_DIM = 16

def start_game(size, container, img, window):
	
	game_frame = tkinter.Frame(container, width=BUTTON_DIM*8, height=BUTTON_DIM*8)
	game_frame.grid(row=0, column=0, sticky="nsew")

	# Make window
	row, col = size, size
	for r in range(row):
		game_frame.rowconfigure(r, weight=1)
		for c in range(col):
			callback = lambda x=r, y=c: print(str(x) + ", " + str(y))
			temp = tkinter.Button(game_frame, image=img, highlightthickness=0, bd=0, padx=0, pady=0, relief=SUNKEN, command = callback)
			game_frame.columnconfigure(c, weight=1)
			temp.grid(row=r, column=c)

	window.update()
	w = game_frame.winfo_width()
	x = window.winfo_x()
	h = game_frame.winfo_height()
	y = window.winfo_y()
	window.geometry(str(w) + "x" + str(h) + "+500+150")
	game_frame.tkraise()

# Load all the icons used during the game
def load_icons(img_dic):

	img_dic['0'] = tkinter.PhotoImage(file="../icons/0.png")
	img_dic['1'] = tkinter.PhotoImage(file="../icons/0.png")
	img_dic['2'] = tkinter.PhotoImage(file="../icons/0.png")
	img_dic['3'] = tkinter.PhotoImage(file="../icons/0.png")
	img_dic['4'] = tkinter.PhotoImage(file="../icons/0.png")
	img_dic['5'] = tkinter.PhotoImage(file="../icons/0.png")
	img_dic['6'] = tkinter.PhotoImage(file="../icons/0.png")
	img_dic['7'] = tkinter.PhotoImage(file="../icons/0.png")
	img_dic['8'] = tkinter.PhotoImage(file="../icons/0.png")
	img_dic['b'] = tkinter.PhotoImage(file="../icons/blank.png")
	img_dic['bm'] = tkinter.PhotoImage(file="../icons/boom.png")
	img_dic['c'] = tkinter.PhotoImage(file="../icons/click.png")
	img_dic['d'] = tkinter.PhotoImage(file="../icons/dead.png")
	img_dic['e'] = tkinter.PhotoImage(file="../icons/empty.png")
	img_dic['f'] = tkinter.PhotoImage(file="../icons/flag.png")
	img_dic['g'] = tkinter.PhotoImage(file="../icons/glasses.png")
	img_dic['m'] = tkinter.PhotoImage(file="../icons/mine.png")
	img_dic['s'] = tkinter.PhotoImage(file="../icons/smile.png")
	img_dic['w'] = tkinter.PhotoImage(file="../icons/wrong.png")

def restart_game(frame, window):

	frame.tkraise()
	window.geometry("400x425+500+150")

def main():

	# Create main window
	root = tkinter.Tk()
	root.title("Sweeper!")
	root.geometry("400x425+500+150")
	root.resizable(width=False, height=False)

	# Top frame has hint, smiley, restart, and timer
	top_frame = tkinter.Frame(root, width=BUTTON_DIM*8, height=BUTTON_DIM)
	top_frame.grid(row=0, column=0, sticky="nsew")

	# Bottom frame has game board frame
	bottom_frame = tkinter.Frame(root)
	bottom_frame.grid(row=1, column=0, sticky="nsew")
	
	# game board frame has mine field
	difficulty = tkinter.Frame(bottom_frame, width=BUTTON_DIM*8, height=BUTTON_DIM*8)
	difficulty.grid(row=0, column=0, sticky="nsew")
	difficulty.grid_propagate(0)

	# Make Top bar that has hint, smiley, restart, timer
	hint = tkinter.Button(top_frame, text="Hint?")
	smile_img = tkinter.PhotoImage(file="../icons/smile.png")
	smile = tkinter.Label(top_frame, image=smile_img)
	restart = tkinter.Button(top_frame, text="Restart?", command=lambda: restart_game(difficulty, root))
	timer = tkinter.Label(top_frame, text="0:00")

	hint.grid(row=0, column=0, columnspan=2, sticky="nsew")
	top_frame.columnconfigure(0, weight=1)
	smile.grid(row=0, column=3)
	top_frame.columnconfigure(3, weight=1)
	restart.grid(row=0, column=5, columnspan=2, sticky="nsew")
	top_frame.columnconfigure(5, weight=1)
	timer.grid(row=0, column=7)
	top_frame.columnconfigure(7, weight=1)

	# Load all icons for game
	icons = {}
	load_icons(icons)

	# Offer two choices of difficulty
	easy = tkinter.Button(difficulty, text="8x8 (Easy)", command=lambda:start_game(EASY_DIM, bottom_frame, icons['b'], root))
	hard = tkinter.Button(difficulty, text="16x16 (Hard)", command=lambda:start_game(HARD_DIM, bottom_frame, icons['b'], root))

	difficulty.rowconfigure(0, weight=1)
	easy.grid(row=0, column=0, columnspan=4, rowspan=8, sticky="nsew")
	difficulty.columnconfigure(0, weight=1)
	hard.grid(row=0, column=4, columnspan=4, rowspan=8, sticky="nsew")
	difficulty.columnconfigure(4, weight=1)

	root.mainloop()
main()
