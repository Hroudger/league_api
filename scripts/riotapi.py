from riotwatcher import RiotWatcher, ApiError
import mysql.connector as sql
from mysql.connector import Error

watcher = RiotWatcher("RGAPI-67ba4d09-55d7-43e6-9de3-09d42a813533")


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
        query = "INSERT INTO summoners (id, REGION, NAME) VALUES ('{}', '{}', '{}')"\
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
    query = 'SELECT id FROM summoners WHERE NAME = {}'.format(name)
    accountid = connect(query, "select")
    history = watcher.match.matchlist_by_account(region, accountid, queue=420, end_index=10)
    matchDict = {}
    for idx, matches in enumerate(history["matches"]):
        matchDict.update({idx: {'GameID': matches["gameId"], 'Region': matches["platformId"],
                                'AccountID': accountid, 'ChampionID': matches["champion"]}})
    return matchDict


def getMatchDetails(matchId, champion, region):
    game = watcher.match.by_id(region, matchId)
    gameInfo = {}
    for x in game["participants"]:
        if x["championId"] == champion:
            kda = "{}/{}/{}".format(x["stats"]["kills"], x["stats"]["deaths"], x["stats"]["assists"])
            if x["stats"]["win"] == "Win":
                win = True
            else:
                win = False

            gameInfo = {'Win': win, 'CS': x["stats"]["totalMinionsKilled"], 'KDA': kda, 'SummonerSpell1': x["spell1Id"],
                        'SummonerSpell2': x["spell2Id"], 'Item0': x["stats"]["item0"], 'Item1': x["stats"]["item1"],
                        'Item2': x["stats"]["item2"], 'Item3': x["stats"]["item3"], 'Item4': x["stats"]["item4"],
                        'Item5': x["stats"]["item5"], 'Item6': x["stats"]["item6"]}
        else:
            continue
    return gameInfo


getSummoner("EUW1", "T3rmiXx")
