import random
import vk


session = vk.Session(access_token = '')

vkapi = vk.API(session)


class Anek(object):
    def id_of_anek(self):
        with open('file.txt') as f:
            lines = f.readlines()
        id = random.randint(0,len(lines)-1)
        id = lines[id]
        return id
    def from_id_to_anek(self,id):
        posts = '-85443458_{0}'.format(id)
        return vkapi.wall.getById(posts = posts, v = 5.131)[0]['text']
