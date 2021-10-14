from flask import Flask, render_template, g, request, url_for, redirect, flash
import os
import sqlite3
from FDataBase import FDataBase
from werkzeug.security import generate_password_hash, check_password_hash
from Check import Check
from flask_login import LoginManager, login_required, login_user, current_user
from UserLogin import UserLogin

# Настройки приложения
DATABASE = '/tmp/flsite.db'
DEBUG = True
SECRET_KEY = 'fdgfh78@#5?>gfhf89dx,v06k'


app = Flask(__name__, template_folder='./templates', static_folder='./static')
app.config.from_object(__name__)
app.config.update(dict(DATABASE=os.path.join(app.root_path, 'flsite.db')))
success = False

MAX_CONTENT_LENGTH = 1024 * 1024

login_manager = LoginManager(app)
login_manager.login_view = 'login'
login_manager.login_message = "Чтобы написать некролог, необходимо авторизироваться"
login_manager.login_message_category = "error"

# Вспомогательные функции для работы с базой данных
def connect_db():
    conn = sqlite3.connect(app.config['DATABASE'])
    conn.row_factory = sqlite3.Row
    return conn

def create_db():
    """Вспомогательная функция для создания таблиц БД"""
    db = connect_db()
    with app.open_resource('sq_db.sql', mode='r') as f:
        db.cursor().executescript(f.read())
    db.commit()
    db.close()

def get_db():
    '''Соединение с БД, если оно еще не установлено'''
    if not hasattr(g, 'link_db'):
        g.link_db = connect_db()
    return g.link_db

dbase = None
@app.before_request
def before_request():
    """Установление соединения с БД перед выполнением запроса"""
    global dbase
    db = get_db()
    dbase = FDataBase(db)


@app.teardown_appcontext
def close_db(error):
    '''Закрываем соединение с БД, если оно было установлено'''
    if hasattr(g, 'link_db'):
        g.link_db.close()





@login_manager.user_loader
def load_user(user_id):
    return UserLogin().fromDB(user_id, dbase)

@app.route("/")
def index():
    if current_user.is_authenticated:
        return render_template('main.html', login=current_user.get_login())
    else:
        return render_template('main.html')

@app.route('/necro', methods=["POST", "GET"])
def necro():
    necrologs = dbase.getNecrologs()
    if current_user.is_authenticated:
        return render_template('necro.html', login=current_user.get_login(), necrologs=necrologs)
    else:
        return render_template('necro.html')




@app.route('/write_necro', methods=["POST", "GET"])
@login_required
def write_necro():
    if request.method == "POST":
        print(request.form)
        dbase.addNecro(current_user.get_id(), request.form['second_name'], request.form['first_name'],
                       request.form['father_name'], request.form['bdate'],
                       request.form['ddate'], request.form['message'])
        return render_template('write_necro.html', login=current_user.get_login())
    return render_template('write_necro.html', login=current_user.get_login())






@app.route("/login", methods =['POST',"GET"])
def login():
    global success
    if success:
        flash('Вы успешно Зарегистрированы', category='success')
        success = False

    if request.method == "POST":
        user = dbase.getUserByLogin(request.form['login'])
        if user and check_password_hash(user['psw'], request.form['psw']):
            userlogin = UserLogin().create(user)
            login_user(userlogin)
            return redirect(request.args.get("next") or url_for('index'))

        flash("Неверная пара логин/пароль", "error")
    return render_template("login.html")

@app.route("/register", methods=["POST", "GET"])
def register():
    if request.method == "POST":
        log = Check(request.form, dbase)
        result = log.checkForm()
        if result['category'] == 'error':
            flash(result['m'], category=result['category'])
        else:
            global success
            success = True
            hpsw = generate_password_hash(request.form['psw1'])
            dbase.addUser(request.form['login'], request.form['email'], hpsw )
            return redirect(url_for('login'))
    return render_template("registration.html")



if __name__ == "__main__":
    app.run(debug=True)
