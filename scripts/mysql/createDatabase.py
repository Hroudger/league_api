#!./../venv/bin/python
import mysql.connector as sql
from mysql.connector import Error

conn = None
cursor = None

try:
    conn = sql.connect(host='localhost',
                       password='root',
                       port=3306)

    cursor = conn.cursor()
    file = open("league_api.sql", 'r')
    sql = s = " ".join(file.readlines())
    cursor.execute(sql)

except Error as e:
    print(e)

finally:
    if conn.is_connected():
        conn.close()
        cursor.close()
