from riotwatcher import RiotWatcher, ApiError
import mysql.connector as sql
from mysql.connector import Error

watcher = RiotWatcher("RGAPI-d2187d57-f8c1-43fc-860a-ffbf466bd351")


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
            print('Retry in {} seconds.'.format(err.headers['Retry-After']))
        elif err.response.status_code == 404:
            print('Summoner Name not found.')
        else:
            raise


def getMatchHistory(region, name):
    query = 'SELECT id FROM summoners WHERE NAME = "{}"'.format(name)
    accountid = connect(query, "select")
    accountidstr = ' '.join([str(elem) for elem in accountid])
    accountidstr = accountidstr[2:-3]
    history = watcher.match.matchlist_by_account(region, accountidstr, queue=420, end_index=10)
    for idx, matches in enumerate(history["matches"]):
        query = "INSERT INTO summonermatches (matchid, region, summonerid, championid, queueid) VALUES ('{}', '{}'," \
                " '{}', '{}', '{}')". \
            format(matches["gameId"], matches["platformId"], accountidstr, matches["champion"], matches["queue"])
        connect(query, "insert")


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

            query = "UPDATE summonermatches SET win = {}, duration = {}, cs = {}, kills = {}, deaths = {}, assists =" \
                    "{}, level = {}, visionscore = {}, spell1 = {}, spell2 = {}, item0 = {}, item1 = {}, item2 = {}," \
                    "item3 = {}, item4 = {}, item5 = {}, item6 = {} WHERE matchid = '{}' AND summonerid = '{}'" \
                .format(win, length, x["stats"]["totalMinionsKilled"], x["stats"]["kills"], x["stats"]["deaths"],
                        x["stats"]["assists"], x["stats"]["champLevel"], x["stats"]["visionScore"], x["spell1Id"],
                        x["spell2Id"], x["stats"]["item0"], x["stats"]["item1"], x["stats"]["item2"],
                        x["stats"]["item3"], x["stats"]["item4"], x["stats"]["item5"], x["stats"]["item6"],
                        matchId, accountID)
            connect(query, "insert")

        else:
            continue


def getRank():
    summ = watcher.summoner.by_name("EUW1", "T3rmiXx")
    rankInfo = watcher.league.by_summoner("EUW1", summ["id"])

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
