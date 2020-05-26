import time
import tkinter
from tkinter import *
from const import *
from graphics import render_game, render_restart
from logic import make_move, hint

# Starts the game when difficulty is chosen by building
# the frame that holds the mine field
def start_game(game, render, size):

	game['size'] = size
	difficulty = 'easy'
	dim = BUTTON_DIM*EASY_DIM
	if size == HARD_DIM:
		difficulty = 'hard'
		dim = BUTTON_DIM*HARD_DIM

	frames = render['frames']
	icons = render['icons']
	smile = render['smile']
	window = render['window']

	# Create frame
	game_frame = tkinter.Frame(frames['bottom'], width=dim, height=dim)
	game_frame.grid(row=0, column=0)
	frames[difficulty] = game_frame

	for r in range(size):

		frames[difficulty].rowconfigure(r, weight=1)

		for c in range(size):

			frames[difficulty].columnconfigure(c, weight=1)
			temp = tkinter.Button(frames[difficulty], image=icons['b'], highlightthickness=0, bd=0, relief=SUNKEN)

			# Set button bindings for each tile
			temp.bind("<Button-1>", lambda event : smile.configure(image=icons['c']))
			temp.bind("<ButtonRelease-1>", lambda event, x=r, y=c: tile_clicked(x, y, game, render))
			temp.bind("<Button-3>", lambda event : smile.configure(image=icons['c']))
			temp.bind("<ButtonRelease-3>", lambda event, x=r, y=c : tile_clicked(x, y, game, render, flag=True))

			temp.grid(row=r, column=c)

	window.update()
	w,h = frames[difficulty].winfo_width(), frames[difficulty].winfo_height() + frames['top'].winfo_height()
	frames['difficulty'].grid_forget()
	window.geometry(str(w) + "x" + str(h) + "+500+150")
	frames[difficulty].grid()
	frames[difficulty].tkraise()

# Callback method for game tiles
def tile_clicked(x, y, game, render, flag=False):

	if flag:	
		changes = make_move(x, y, game, flag=True)
	else:
		if game['board'] == []:
			start_clock(render, 0)
		changes = make_move(x, y, game)

	if game['size'] == 8:
		render_game(render, 'easy', game['state'], changes)
	else:
		render_game(render, 'hard', game['state'], changes)

	if game['state'] is RUNNING:
		render['smile'].configure(image=render['icons']['s'])
	else:
		stop_clock(render)

# Callback method for hint button
def hint_clicked(game, render):

	# Game hasn't started yet
	if game['size'] == 0:
		return 

	if game['board'] == []:
		start_clock(render, 0)

	changes = hint(game)

	if game['size'] == 8:
		render_game(render, 'easy', game['state'], changes)
	else:
		render_game(render, 'hard', game['state'], changes)

	if game['state'] is RUNNING:
		render['smile'].configure(image=render['icons']['s'])
	else:
		stop_clock(render)

# Callback method for restart button
def restart_clicked(game, render):

	stop_clock(render)

	if game['size'] == 8:
		render_restart(render, 'easy')
	elif game['size'] == 16:
		render_restart(render, 'hard')

	game['tiles_left'] = 0
	game['size'] = 0
	game['board'] = []
	game['state'] = RUNNING

# Load all the icons used during the game
def load_icons(img_dic):

	img_dic['0'] = tkinter.PhotoImage(file="../../img/0.png")
	img_dic['1'] = tkinter.PhotoImage(file="../../img/1.png")
	img_dic['2'] = tkinter.PhotoImage(file="../../img/2.png")
	img_dic['3'] = tkinter.PhotoImage(file="../../img/3.png")
	img_dic['4'] = tkinter.PhotoImage(file="../../img/4.png")
	img_dic['5'] = tkinter.PhotoImage(file="../../img/5.png")
	img_dic['6'] = tkinter.PhotoImage(file="../../img/6.png")
	img_dic['7'] = tkinter.PhotoImage(file="../../img/7.png")
	img_dic['8'] = tkinter.PhotoImage(file="../../img/8.png")
	img_dic['b'] = tkinter.PhotoImage(file="../../img/covered.png")
	img_dic['bm'] = tkinter.PhotoImage(file="../../img/boom.png")
	img_dic['c'] = tkinter.PhotoImage(file="../../img/click.png")
	img_dic['d'] = tkinter.PhotoImage(file="../../img/dead.png")
	img_dic['e'] = tkinter.PhotoImage(file="../../img/empty.png")
	img_dic['f'] = tkinter.PhotoImage(file="../../img/flag.png")
	img_dic['g'] = tkinter.PhotoImage(file="../../img/glasses.png")
	img_dic['m'] = tkinter.PhotoImage(file="../../img/mine.png")
	img_dic['s'] = tkinter.PhotoImage(file="../../img/smile.png")
	img_dic['w'] = tkinter.PhotoImage(file="../../img/wrong.png")

# Starts the game timer
def start_clock(render, sec):

	sec+=1

	minutes = int(sec/60)
	seconds = sec%60

	now = f"{minutes:02d}:{seconds:02d}"
		
	render['timer'].configure(text=now)
	render['timer_id'] = render['frames']['top'].after(1000, lambda : start_clock(render, sec))

# Stops the game timer
def stop_clock(render):

	try:
		if render['timer_id'] != None:
			render['frames']['top'].after_cancel(render['timer_id'])
			render['timer_id'] = None
	except: # Catch exception when timer_id hasn't been created yet
		pass

def main():

	# Dictionary to store frames, dictionry for icons, and game board
	frames = {'easy': None, 'hard': None}
	icons = {}
	board = []
	game = {'tiles_left':1, 'size':0, 'state': RUNNING, 'board':board}
	render = {}

	# Create main window
	root = tkinter.Tk()
	root.title("Sweeper!")
	root.geometry("400x425+500+150")
	root.resizable(width=False, height=False)

	# Top frame has hint, smiley, restart, and timer
	top_frame = tkinter.Frame(root, width=BUTTON_DIM*8, height=BUTTON_DIM)
	top_frame.grid(row=0, column=0, sticky="nsew")
	frames['top'] = top_frame

	# Bottom frame has either gameboard or diffculty frame
	bottom_frame = tkinter.Frame(root)
	bottom_frame.grid(row=1, column=0, sticky="nsew")
	frames['bottom'] = bottom_frame
	
	# difficulty frame for starting screen
	difficulty = tkinter.Frame(bottom_frame, width=BUTTON_DIM*8, height=BUTTON_DIM*8)
	difficulty.grid(row=0, column=0, sticky="nsew")
	difficulty.grid_propagate(0)
	frames['difficulty'] = difficulty

	# Load all icons for game
	load_icons(icons)

	# Make buttons for top frame
	smile = tkinter.Label(top_frame, image=icons['s'])
	restart = tkinter.Button(top_frame, text="Restart?", command=lambda: restart_clicked(game, render))
	timer = tkinter.Label(top_frame, text="00:00")
	hint = tkinter.Button(top_frame, text="Hint?", command=lambda: hint_clicked(game, render))

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
	easy = tkinter.Button(difficulty, text="8x8 (Easy)", command=lambda:start_game(game, render, EASY_DIM))
	hard = tkinter.Button(difficulty, text="16x16 (Hard)", command=lambda:start_game(game, render, HARD_DIM))

	# Place easy and hard buttons in bottom frame in difficulty frame
	difficulty.rowconfigure(0, weight=1)
	easy.grid(row=0, column=0, columnspan=4, rowspan=8, sticky="nsew")
	difficulty.columnconfigure(0, weight=1)
	hard.grid(row=0, column=4, columnspan=4, rowspan=8, sticky="nsew")
	difficulty.columnconfigure(4, weight=1)

	# Create render dictionary to be used by graphics module
	render['frames'] = frames
	render['window'] = root
	render['icons'] = icons
	render['smile'] = smile
	render['timer'] = timer

	root.mainloop()
main()
