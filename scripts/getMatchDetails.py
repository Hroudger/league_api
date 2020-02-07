#!./venv/bin/python
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


def getMatchDetails(matchId, accountID, region):
    game = watcher.match.by_id(region, matchId)
    query = 'SELECT championid FROM summonermatches WHERE matchid = "{}" AND summonerid = "{}"'.format(matchId,
                                                                                                       accountID)
    champion = connect(query, "select")
    champion = ' '.join([str(elem) for elem in champion])
    champion = int(champion[1:-2])
    length = game["gameDuration"]
    for x in game["participants"]:
        if x["championId"] == champion:
            if x["stats"]["win"] == "Win":
                win = True
            else:
                win = False
            minions = x["stats"]["totalMinionsKilled"] + x["stats"]["neutralMinionsKilled"]

            query = "UPDATE summonermatches SET win = {}, duration = {}, cs = {}, kills = {}, deaths = {}, assists =" \
                    "{}, level = {}, visionscore = {}, spell1 = {}, spell2 = {}, item0 = {}, item1 = {}, item2 = {}," \
                    "item3 = {}, item4 = {}, item5 = {}, item6 = {} WHERE matchid = '{}' AND summonerid = '{}'" \
                .format(win, length, minions, x["stats"]["kills"], x["stats"]["deaths"],
                        x["stats"]["assists"], x["stats"]["champLevel"], x["stats"]["visionScore"], x["spell1Id"],
                        x["spell2Id"], x["stats"]["item0"], x["stats"]["item1"], x["stats"]["item2"],
                        x["stats"]["item3"], x["stats"]["item4"], x["stats"]["item5"], x["stats"]["item6"],
                        matchId, accountID)
            connect(query, "insert")

        else:
            continue


getMatchDetails(sys.argv[1], sys.argv[2], sys.argv[3])
