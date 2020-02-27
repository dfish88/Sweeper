import tkinter
from tkinter import *
from const import *

def start_game(game, render, size, callback):

	game['size'] = size
	difficulty = 'easy'
	dim = BUTTON_DIM*EASY_DIM

	frames = render['frames']
	icons = render['icons']
	smile = render['smile']
	window = render['window']

	if size == HARD_DIM:
		difficulty = 'hard'
		dim = BUTTON_DIM*HARD_DIM

	# Create frame
	game_frame = tkinter.Frame(frames['bottom'], width=dim, height=dim)
	game_frame.grid(row=0, column=0)
	frames[difficulty] = game_frame

	for r in range(size):

		frames[difficulty].rowconfigure(r, weight=1)

		for c in range(size):

			frames[difficulty].columnconfigure(c, weight=1)
			temp = tkinter.Button(frames[difficulty], image=icons['b'], highlightthickness=0, bd=0, relief=SUNKEN)

			temp.bind("<Button-1>", lambda event : smile.configure(image=icons['c']))
			temp.bind("<ButtonRelease-1>", lambda event, x=r, y=c: callback(x, y, game, render))
			temp.bind("<Button-3>", lambda event : smile.configure(image=icons['c']))
			temp.bind("<ButtonRelease-3>", lambda event, x=r, y=c : callback(x, y, game, render, flag=True))

			temp.grid(row=r, column=c)

	window.update()
	w,h = frames[difficulty].winfo_width(), frames[difficulty].winfo_height() + frames['top'].winfo_height()
	frames['difficulty'].grid_forget()
	window.geometry(str(w) + "x" + str(h) + "+500+150")
	frames[difficulty].grid()
	frames[difficulty].tkraise()

def render_restart(render, difficulty):

	render['smile'].configure(image=render['icons']['s'])
	render['timer'].configure(text="00:00")

	frames = render['frames']
	icons = render['icons']
	window = render['window']

	frames[difficulty].grid_forget()
	for btn in frames[difficulty].grid_slaves():
		btn.configure(image=icons['b'])

	frames['difficulty'].grid()
	frames['difficulty'].tkraise()
	frames['difficulty'].grid_propagate(0)
	window.geometry("400x425+500+150")

def render_game(render, diff, results ):

	icons = render['icons']
	smile = render['smile']
	frames = render['frames']

	changes = results[0]
	state = results[1]
	board = frames[diff]

	for change in changes:

		if change == []:
			continue
	
		x = change[0]
		y = change[1]
		img = icons[change[2]]

		button = board.grid_slaves(x, y)[0]
		button.configure(image=img)

	if state is LOST:
		smile.configure(image=icons['d'])

	if state is WON:
		smile.configure(image=icons['g'])

	if state is not RUNNING:

		# Disable buttons
		for btn in frames[diff].grid_slaves():
			btn.unbind("<Button-1>")
			btn.unbind("<ButtonRelease-1>")
			btn.unbind("<Button-3>")
			btn.unbind("<ButtonRelease-3>")


