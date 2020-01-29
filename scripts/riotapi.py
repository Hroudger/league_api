import cassiopeia as cass
import random


cass.set_riot_api_key("RGAPI-4f2bbeb3-a75f-4708-b991-0075bd264982")
cass.set_default_region("EUW")
summoner = cass.get_summoner(name="T3rmiXx")

print(summoner)
