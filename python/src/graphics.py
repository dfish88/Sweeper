import tkinter
from tkinter import *
import const

def restart_game(frames, window):

	try:
		frames['easy'].grid_forget()
		frames['hard'].grid_forget()
	except:
		pass

	frames['difficulty'].grid()
	frames['difficulty'].tkraise()
	frames['difficulty'].grid_propagate(0)
	window.geometry("400x425+500+150")

def render_game(game, frames, icons, results):
	pass
