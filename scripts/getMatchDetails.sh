#!/usr/bin/env bash
source venv/bin/activate

if [ -z "$1" ] || [ -z "$2" ] || [ -z "$3" ]
then
  echo "Bitte geben sie die MatchID, AccountID und die Region an."
else
  python ./lib/getMatchDetails.py "$1" "$2" "$3"
fi
