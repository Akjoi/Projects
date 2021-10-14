import time
import math
import sqlite3

class FDataBase:
    def __init__(self, db):
        self.__db = db
        self.__cur = db.cursor()

    # Блок про регистрацию
    def addUser(self, login, email, hpsw):
        tm = math.floor(time.time())
        self.__cur.execute("INSERT INTO users VALUES(NULL, ?, ?, ?, ?)", (login, email, hpsw, tm))
        self.__db.commit()
        return True

    def checkDLogin(self, login):
        self.__cur.execute(f"SELECT COUNT() as `count` FROM users WHERE login LIKE '{login}'")
        res = self.__cur.fetchone()
        if res['count'] > 0:
            return False
        return True

    def checkDEmail(self, email):
        self.__cur.execute(f"SELECT COUNT() as `count` FROM users where email LIKE '{email}'")
        res = self.__cur.fetchone()
        if res['count'] > 0:
            return False
        return True

    def addNecro(self, author_id, second_name, first_name, father_name, bdate, ddate, text):
        tm = math.floor(time.time())
        self.__cur.execute("INSERT INTO necrologs VALUES(NULL,?,?,?,?,?,?,?,?)",
                           (author_id, second_name, first_name, father_name, bdate, ddate, text, tm))
        self.__db.commit()
        return True

    def getNecrologs(self):
        self.__cur.execute(f"SELECT * FROM necrologs LIMIT 4")
        res = self.__cur.fetchall()
        return res

    def getUser(self, user_id):
        try:
            self.__cur.execute(f"SELECT * FROM users WHERE id = {user_id} LIMIT 1")
            res = self.__cur.fetchone()
            if not res:
                print("Пользователь не найден")
                return False

            return res
        except sqlite3.Error as e:
            print("Ошибка получения данных из БД " + str(e))
        return False

    def getUserByLogin(self, login):
        try:
            self.__cur.execute(f"SELECT * FROM users WHERE login LIKE '{login}' LIMIT 1")
            res = self.__cur.fetchone()
            if not res:
                print("Пользователь не найден")
                return False
            return res
        except sqlite3.Error as e:
            print("Ошибка получения данных из БД "+str(e))

        return False