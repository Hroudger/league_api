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


def getRank(region, name):
    summ = watcher.summoner.by_name(region, name)
    rankInfo = watcher.league.by_summoner(region, summ["id"])

    if len(rankInfo) == 1:
        if rankInfo[0]["queueType"] == "RANKED_SOLO_5x5":
            query = "INSERT INTO ranking (summonerid, solotier, solorank, sololp) VALUES ('{0}', '{1}', '{2}', '{3}')" \
                    "ON DUPLICATE KEY UPDATE solotier='{1}', solorank='{2}', sololp='{3}'".\
                format(summ["accountId"], rankInfo[0]["tier"], rankInfo[0]["rank"], rankInfo[0]["leaguePoints"])
            connect(query, "insert")
        else:
            query = "INSERT INTO ranking (summonerid, flextier, flexrank, flexlp) VALUES ('{0}', '{1}', '{2}', '{3}')" \
                    "ON DUPLICATE KEY UPDATE flextier='{1}', flexrank='{2}', flexlp='{3}'".\
                format(summ["accountId"], rankInfo[0]["tier"], rankInfo[0]["rank"], rankInfo[0]["leaguePoints"])
            connect(query, "insert")
    else:
        query = "INSERT INTO ranking (summonerid, solotier, solorank, sololp, flextier, flexrank, flexlp)" \
                "VALUES ('{0}', '{1}', '{2}', '{3}', '{4}', '{5}', '{6}') ON DUPLICATE KEY" \
                "UPDATE flextier='{1}', flexrank='{2}', flexlp='{3}' solotier='{4}', solorank='{5}', sololp='{6}'".\
            format(summ["accountId"], rankInfo[1]["tier"], rankInfo[1]["rank"], rankInfo[1]["leaguePoints"],
                   rankInfo[0]["tier"], rankInfo[0]["rank"], rankInfo[0]["leaguePoints"])
        connect(query, "insert")


getRank(sys.argv[1], sys.argv[2])
