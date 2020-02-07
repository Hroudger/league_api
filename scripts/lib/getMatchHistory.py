import sys
from riotwatcher import RiotWatcher
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


def getMatchHistory(region, name):
    query = 'SELECT id FROM summoners WHERE NAME = "{}"'.format(name)
    accountid = connect(query, "select")
    accountidstr = ' '.join([str(elem) for elem in accountid])
    accountidstr = accountidstr[2:-3]
    queues = 420, 440
    history = watcher.match.matchlist_by_account(region, accountidstr, queue=queues, end_index=50)
    for idx, matches in enumerate(history["matches"]):
        query = "INSERT IGNORE INTO summonermatches (matchid, region, summonerid, championid, queueid, timestamp)" \
                "VALUES ('{}', '{}', '{}', '{}', '{}', '{}')". \
            format(matches["gameId"], matches["platformId"], accountidstr, matches["champion"],
                   matches["queue"], matches['timestamp'])
        connect(query, "insert")


getMatchHistory(sys.argv[1], sys.argv[2])
