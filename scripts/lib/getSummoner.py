import sys
from riotwatcher import RiotWatcher, ApiError
import mysql.connector as sql
from mysql.connector import Error

watcher = RiotWatcher("RGAPI-a15d5a32-06e3-475d-a59b-a2880f1ca49f")


def connect(query, status):
    conn = None
    cursor = None
    records = None

    try:
        conn = sql.connect(host='localhost',
                           database='league_api',
                           user='league_api',
                           port=3306,
                           password='deletedraven')

        cursor = conn.cursor()
        cursor.execute(query)

        if status == "insert":
            conn.commit()
        elif status == "select":
            records = cursor.fetchall()

    except Error as e:
        print(e)

    finally:
        conn.close()
        cursor.close()

    return records


def getSummoner(region, name):
    try:
        summoner = watcher.summoner.by_name(region, name)
    except ApiError as err:
        if err.response.status_code == 429:
            sys.exit(1)
        elif err.response.status_code == 404:
            sys.exit(2)
        else:
            raise
    query = "INSERT IGNORE INTO summoners (id, REGION, NAME) VALUES ('{0}', '{1}', '{2}') ON DUPLICATE KEY UPDATE" \
            " REGION='{1}', NAME='{2}'".format(summoner["accountId"], region, summoner["name"])
    connect(query, "insert")


getSummoner(sys.argv[1], sys.argv[2])
