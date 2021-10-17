import telebot

from anek_to_message import Anek

obj = Anek()

bot = telebot.TeleBot("1609035497:AAHJNOCs4nnO26OTyr4RqNyJMUul0rg5tiI")

@bot.message_handler(commands=['start'])
def send_welcome(message):
    bot.send_message(message.chat.id,
        'Привет я Тиханыч-бот.'
        'Чтобы получить анек введите /anek.'
        'Важно!!! Я нахожусь в стадии разработки, поэтому местами работаю некорректно.'
        'Если у вас возникли пролемы, сообщите о них командой /problem Текст')

@bot.message_handler(commands=['anek'])
def print_anek(message):
    id = obj.id_of_anek()
    anek = obj.from_id_to_anek(id)
    bot.send_message(message.chat.id,anek)
bot.polling()
