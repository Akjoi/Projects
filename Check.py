import re
from FDataBase import FDataBase

class Check(FDataBase):
    def __init__(self, request,db):
        self.__email = request['email']
        self.__login = request['login']
        self.__psw1 = request['psw1']
        self.__psw2 = request['psw2']
        self.__db = db
    # Функция проверки email

    def checkEmail(self):
        pattern =r'^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$'
        res = re.compile(pattern)
        if not res.findall(self.__email):
            return False
        return True
    def checkLogin(self):
        if len(self.__login) > 4:
            return True
        return False

    def checkForm(self):
        if len(self.__psw1) >= 4:
            if self.__psw1 == self.__psw2:
                if self.checkEmail():
                    if self.__db.checkDEmail(self.__email):
                        if self.checkLogin():
                            if self.__db.checkDLogin(self.__login):
                                return {'m': 'Вы успешно зарегистрированы', 'category': 'success'}
                            else:
                                return {'m': 'Пользователь с таким Логином существует', 'category': 'error'}
                        else:
                            return {'m': 'Логин должен быть длины 5 и более', 'category': 'error'}
                    else:
                        return {'m': 'Пользователь с таким Email существует', 'category': 'error'}
                else:
                    return {'m': 'Некорректный Email','category': 'error'}
            else:
                return {'m': 'Пароли должны совпадать','category': 'error'}
        else:
            return {'m':'Пароль должен быть минимум 4 символа', 'category': 'error'}

