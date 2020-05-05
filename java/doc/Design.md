#Design Overview
The general design used for this implementation is the Model View Presenter (MVP) design.
The Model is responsible for the game model/logic that tracks the game board, reveals tiles,
determines game state and so on. The View is responsible for displaying the current state of
the Model. The Presenter acts as a mediator between the Model and the View. The Presenter allows
the Model and the View to be completely agnostic of each other which makes changing either of
them easier. The user interacts with the View module by clicking on UI buttons. The View notifies
the Presenter via methods when buttons are clicked and the Presenter updates the Model if necessary
and displays the results via the View. I've made the View as 'dumb' as a I could and it contains
almost no UI logic which has been delegated to the Presenter.

#View
The View module is located in src/View and the public interface of the view is located in UI.java.
UI contains a reference to the UIBoard, the UIStartScreen, UITopPanel, and the Presenter. UIBoard
manages the visual representation of the game board and updating it. UIStartScreen manages the start
screen which allows the user to choose a game difficulty (easy or hard). UITopPanel manages the top
panel of the game window which contains the hint and restart buttons, a game timer, and the game face.
Whenever a button is clicked the View module notifies the Presenter via the Presenter's public interface.
The constructor in UI.java is the entry point of the entire application and calling it starts everything.

#Presenter
The Presenter module is located in src/Application and the public interface of the view is located in
Application.java. Application contains a reference to both the Model and the View. When the View notifies
the Presenter (Application) of button presses the Model is updated if necessary. The Model then returns 
the results of any changes to the Presenter which then displays the results via the View. FaceRepresentation.java
and TileRepresentation.java are enums in the Presenter module that are used by both the Model and the View.
These enums allow the Model to communicate changes to View through the Presenter while only allowing valid
options which would not be the case if using primitive types.

#Model
The Model is located in src/Model and the public interface of the Model is located in Game.java. Game includes
a reference to the GameTimer which tracks the game time and the Board which tracks all the tiles on the game
board. The Game provides methods to update the model such as making a move, performing a hint, and placing
a flag on the board. The Model returns the game state to the caller of these update and provides a get changes
method to get the changes accumulate in the Model since the last call.

#Pros of this approach
- Provides good separation between modules and allows changes to the UI and Model to be made easily

#Cons of this approach
- Adds complexity
- Making the View 'dumb' results in many redundant method calls between the View and Presenter that
  could be removed if a small amount of logic were placed in the View 
