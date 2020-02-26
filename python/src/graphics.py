import tkinter
from tkinter import *
from const import *

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


