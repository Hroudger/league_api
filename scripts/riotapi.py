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
        query = "INSERT IGNORE INTO summoners (id, REGION, NAME) VALUES ('{}', '{}', '{}') ON DUPLICATE KEY UPDATE" \
            .format(summoner["accountId"], region, summoner["name"])
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
                " '{}', '{}', '{}')".\
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
    queue = game["queueId"]
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

