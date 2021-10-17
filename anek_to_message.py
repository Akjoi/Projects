import random
import vk


session = vk.Session(access_token = 'f5396d99b7998bcc96c441835b3f58b337ef584a0a454dd4af4f386886b216afba3ac7795b44d2a69cc41')

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
