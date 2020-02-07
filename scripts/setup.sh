#!/usr/bin/env bash
source venv/bin/activate
pip install -r ./lib/requirements.txt
python ./lib/createDatabase.py
