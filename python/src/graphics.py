import tkinter
from tkinter import *
from const import *

def render_changes(render, changes, diff):

	icons = render['icons']
	smile = render['smile']
	frames = render['frames']

	board = frames[diff]

	for change in changes:

		if change == []:
			continue
	
		x = change[0]
		y = change[1]
		img = icons[change[2]]

		button = board.grid_slaves(x, y)[0]
		button.configure(image=img)

def disable_buttons(frame):

	# Disable buttons
	for btn in frame.grid_slaves():
		btn.unbind("<Button-1>")
		btn.unbind("<ButtonRelease-1>")
		btn.unbind("<Button-3>")
		btn.unbind("<ButtonRelease-3>")

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

def render_game(render, diff, state, changes):

	icons = render['icons']
	smile = render['smile']
	frames = render['frames']
	
	render_changes(render, changes, diff)

	if state is WON:
		smile.configure(image=icons['g'])
		disable_buttons(frames[diff])

	elif state is LOST:
		smile.configure(image=icons['d'])
		disable_buttons(frames[diff])

