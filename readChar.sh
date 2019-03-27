#!/bin/bash
echo -ne "\033[6n"            # ask the terminal for the position
read -s -d\[ garbage          # discard the first part of the response
read -s -d R position         # store the position in bash variable 'position'
echo "$position"              # print the position
