import tkinter
from tkinter import *
import const

def start_game(size, frames, img, window):

	difficulty = 'easy'
	dim = const.BUTTON_DIM*const.EASY_DIM
	if size == const.HARD_DIM:
		difficulty = 'hard'
		dim = const.BUTTON_DIM*const.HARD_DIM

	if frames[difficulty] == None:
	
		# Crate frame
		game_frame = tkinter.Frame(frames['bottom'], width=dim, height=dim)
		game_frame.grid(row=0, column=0)
		frames[difficulty] = game_frame

		for r in range(size):
			frames[difficulty].rowconfigure(r, weight=1)
			for c in range(size):
				frames[difficulty].columnconfigure(c, weight=1)
				callback = lambda x=r, y=c: print(str(x) + ", " + str(y))
				temp = tkinter.Button(frames[difficulty], image=img, highlightthickness=0, bd=0, relief=SUNKEN, command = callback)
				temp.grid(row=r, column=c)

	window.update()
	w,h = frames[difficulty].winfo_width(), frames[difficulty].winfo_height() + frames['top'].winfo_height()
	frames['difficulty'].grid_forget()
	window.geometry(str(w) + "x" + str(h) + "+500+150")
	frames[difficulty].grid()
	frames[difficulty].tkraise()

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
