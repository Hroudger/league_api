#!./venv/bin/python
import sys
from riotwatcher import RiotWatcher, ApiError
import mysql.connector as sql
from mysql.connector import Error

watcher = RiotWatcher("RGAPI-6d780c75-7cbb-4d6c-bd18-6ec53ee2a2e9")


def connect(query, status):
    conn = None
    cursor = None
    records = None

    try:
        conn = sql.connect(host='localhost',
                           database='league_api',
                           user='league_api',
                           port=3306,
                           password='')

        cursor = conn.cursor()
        cursor.execute(query)

        if status == "insert":
            conn.commit()
        elif status == "select":
            records = cursor.fetchall()

    except Error as e:
        print(e)

    finally:
        if conn.is_connected():
            conn.close()
            cursor.close()

    return records


def getSummoner(region, name):
    try:
        summoner = watcher.summoner.by_name(region, name)
        query = "INSERT IGNORE INTO summoners (id, REGION, NAME) VALUES ('{0}', '{1}', '{2}') ON DUPLICATE KEY UPDATE" \
                " REGION='{1}', NAME='{2}'".format(summoner["accountId"], region, summoner["name"])
        connect(query, "insert")
    except ApiError as err:
        if err.response.status_code == 429:
            sys.exit(1)
        elif err.response.status_code == 404:
            sys.exit(2)
        else:
            raise


getSummoner(sys.argv[1], sys.argv[2])
