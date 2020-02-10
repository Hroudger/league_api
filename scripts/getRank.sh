#!/usr/bin/env bash
source venv/bin/activate

if [ -z "$1" ] || [ -z "$2" ]
then
  echo "Bitte geben sie die Region und den Namen an."
else
  python ./lib/getRank.py "$1" "$2"
fi
