import tkinter
from tkinter import *
from const import *

def render_game(frames, diff, icons, results):

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

	if state is not RUNNING:

		# Disable buttons
		for btn in frames[diff].grid_slaves():
			btn.unbind("<Button-1>")
			btn.unbind("<ButtonRelease-1>")
			btn.unbind("<Button-3>")
			btn.unbind("<ButtonRelease-3>")

	if state is LOST:
		pass

	if state is WON:
		pass

