# EditableBufferedReader

EditableBufferedReader extends from a BufferedReader.  
We enable the posibility of reading Esc Codes.

Read is a function that reads character by character and detect the following keyboard keys:
*   Right Arrow:            ESC [ C
*   Left Arrow:             ESC [ D
*   Home:                   ESC [ H or ESC O H or ESC [ 1 -
*   End                     ESC [ F or ESC O F or ESC [ 4 -
*   Insert:                 ESC [ 2 -
*   Delete:                 ESC [ 3 -
*   Backspace:              127 (Not escape code but we need to take into account)
<<<<<<< HEAD

[Our code here!](https://github.com/mvc1009/EditableBufferedReader/tree/MVC/texteditor)
=======
>>>>>>> 18c8e1bf47594d890fc70a7336d973c2dde7c253
