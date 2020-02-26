import tkinter
from tkinter import *
import const

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


